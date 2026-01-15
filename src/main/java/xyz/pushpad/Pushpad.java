package xyz.pushpad;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import xyz.pushpad.notification.Notifications;
import xyz.pushpad.project.Projects;
import xyz.pushpad.sender.Senders;
import xyz.pushpad.subscription.Subscriptions;

public final class Pushpad {
  public static final String DEFAULT_BASE_URL = "https://pushpad.xyz/api/v1";

  private static final ObjectMapper MAPPER = createMapper();

  private final String authToken;
  private final Long projectId;
  private final String baseUrl;
  private final HttpClient httpClient;

  private final Projects projects;
  private final Senders senders;
  private final Notifications notifications;
  private final Subscriptions subscriptions;

  public Pushpad(String authToken) {
    this(authToken, null, DEFAULT_BASE_URL, null);
  }

  public Pushpad(String authToken, Long projectId) {
    this(authToken, projectId, DEFAULT_BASE_URL, null);
  }

  public Pushpad(String authToken, Long projectId, String baseUrl) {
    this(authToken, projectId, baseUrl, null);
  }

  public Pushpad(String authToken, Long projectId, String baseUrl, HttpClient httpClient) {
    this.authToken = authToken;
    this.projectId = normalizeProjectId(projectId);
    this.baseUrl = normalizeBaseUrl(baseUrl);
    this.httpClient = httpClient == null ? HttpClient.newHttpClient() : httpClient;
    this.projects = new Projects(this);
    this.senders = new Senders(this);
    this.notifications = new Notifications(this);
    this.subscriptions = new Subscriptions(this);
  }

  public String getAuthToken() {
    return authToken;
  }

  public Long getProjectId() {
    return projectId;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public HttpClient getHttpClient() {
    return httpClient;
  }

  public Projects projects() {
    return projects;
  }

  public Senders senders() {
    return senders;
  }

  public Notifications notifications() {
    return notifications;
  }

  public Subscriptions subscriptions() {
    return subscriptions;
  }

  public String signatureFor(String uid) {
    String token = authToken == null ? "" : authToken;
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(token.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
      byte[] digest = mac.doFinal(uid.getBytes(StandardCharsets.UTF_8));
      return toHex(digest);
    } catch (Exception e) {
      throw new IllegalStateException("pushpad: unable to generate signature", e);
    }
  }

  public long resolveProjectId(Long provided) throws PushpadException {
    if (provided != null && provided > 0) {
      return provided;
    }
    if (projectId == null || projectId <= 0) {
      throw new PushpadException("pushpad: project ID is required");
    }
    return projectId;
  }

  public HttpResponse<String> requestRaw(
      String method,
      String path,
      QueryParams query,
      Object body,
      int[] okStatuses
  ) throws PushpadException {
    String endpoint = buildEndpoint(path, query);
    HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(endpoint));
    builder.header("Accept", "application/json");

    HttpRequest.BodyPublisher publisher = HttpRequest.BodyPublishers.noBody();
    if (body != null) {
      try {
        String payload = MAPPER.writeValueAsString(body);
        publisher = HttpRequest.BodyPublishers.ofString(payload);
        builder.header("Content-Type", "application/json");
      } catch (JsonProcessingException e) {
        throw new PushpadException("pushpad: unable to serialize request body", e);
      }
    }

    if (authToken != null && !authToken.isEmpty()) {
      builder.header("Authorization", "Bearer " + authToken);
    }

    builder.method(method, publisher);

    HttpResponse<String> response;
    try {
      response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    } catch (IOException e) {
      throw new PushpadException("pushpad: request failed", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new PushpadException("pushpad: request interrupted", e);
    }

    boolean ok = Arrays.stream(okStatuses).anyMatch(code -> code == response.statusCode());
    if (!ok) {
      throw new ApiException(response.statusCode(), response.body());
    }

    return response;
  }

  public <T> T request(
      String method,
      String path,
      QueryParams query,
      Object body,
      int[] okStatuses,
      Class<T> responseType
  ) throws PushpadException {
    if (responseType == null) {
      requestRaw(method, path, query, body, okStatuses);
      return null;
    }
    HttpResponse<String> response = requestRaw(method, path, query, body, okStatuses);
    if (response.body() == null || response.body().isEmpty()) {
      return null;
    }
    try {
      return MAPPER.readValue(response.body(), responseType);
    } catch (JsonProcessingException e) {
      throw new PushpadException("pushpad: unable to parse response", e);
    }
  }

  public <T> T request(
      String method,
      String path,
      QueryParams query,
      Object body,
      int[] okStatuses,
      TypeReference<T> responseType
  ) throws PushpadException {
    if (responseType == null) {
      requestRaw(method, path, query, body, okStatuses);
      return null;
    }
    HttpResponse<String> response = requestRaw(method, path, query, body, okStatuses);
    if (response.body() == null || response.body().isEmpty()) {
      return null;
    }
    try {
      return MAPPER.readValue(response.body(), responseType);
    } catch (JsonProcessingException e) {
      throw new PushpadException("pushpad: unable to parse response", e);
    }
  }

  private static ObjectMapper createMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper;
  }

  private static Long normalizeProjectId(Long projectId) {
    if (projectId == null || projectId <= 0) {
      return null;
    }
    return projectId;
  }

  private static String normalizeBaseUrl(String baseUrl) {
    String base = baseUrl == null ? DEFAULT_BASE_URL : baseUrl.trim();
    if (base.isEmpty()) {
      base = DEFAULT_BASE_URL;
    }
    if (base.endsWith("/")) {
      base = base.substring(0, base.length() - 1);
    }
    return base;
  }

  private String buildEndpoint(String path, QueryParams query) {
    String resolvedPath = Objects.requireNonNullElse(path, "");
    if (!resolvedPath.startsWith("/")) {
      resolvedPath = "/" + resolvedPath;
    }
    String endpoint = baseUrl + resolvedPath;
    if (query != null && !query.isEmpty()) {
      endpoint += "?" + query.toQueryString();
    }
    return endpoint;
  }

  private static String toHex(byte[] bytes) {
    StringBuilder builder = new StringBuilder(bytes.length * 2);
    for (byte b : bytes) {
      builder.append(Character.forDigit((b >> 4) & 0xF, 16));
      builder.append(Character.forDigit(b & 0xF, 16));
    }
    return builder.toString();
  }
}
