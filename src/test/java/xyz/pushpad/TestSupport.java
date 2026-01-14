package xyz.pushpad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;

public final class TestSupport {
  public static final ObjectMapper MAPPER = new ObjectMapper();

  private TestSupport() {
  }

  public static void resetPushpad() {
    Pushpad.setAuthToken(null);
    Pushpad.setProjectId(null);
    Pushpad.setBaseUrl(Pushpad.DEFAULT_BASE_URL);
  }

  public static MockServer startServer(MockResponse response) throws IOException {
    return new MockServer(response);
  }

  public static void assertJsonEquals(String expected, String actual) throws IOException {
    Object expectedValue = MAPPER.readValue(expected, Object.class);
    Object actualValue = MAPPER.readValue(actual, Object.class);
    Assertions.assertEquals(expectedValue, actualValue);
  }

  public static Map<String, List<String>> parseQuery(String query) {
    if (query == null || query.isEmpty()) {
      return Collections.emptyMap();
    }
    Map<String, List<String>> parsed = new LinkedHashMap<>();
    String[] pairs = query.split("&");
    for (String pair : pairs) {
      int index = pair.indexOf('=');
      String key = index >= 0 ? pair.substring(0, index) : pair;
      String value = index >= 0 ? pair.substring(index + 1) : "";
      key = URLDecoder.decode(key, StandardCharsets.UTF_8);
      value = URLDecoder.decode(value, StandardCharsets.UTF_8);
      parsed.computeIfAbsent(key, ignored -> new ArrayList<>()).add(value);
    }
    return parsed;
  }

  public static String headerValue(RecordedRequest request, String name) {
    for (Map.Entry<String, List<String>> entry : request.headers.entrySet()) {
      if (entry.getKey().equalsIgnoreCase(name)) {
        if (entry.getValue().isEmpty()) {
          return null;
        }
        return entry.getValue().get(0);
      }
    }
    return null;
  }

  public static void assertHeaderEquals(RecordedRequest request, String name, String expected) {
    String value = headerValue(request, name);
    Assertions.assertEquals(expected, value, "Header " + name + " mismatch");
  }

  public static void assertHeaderStartsWith(RecordedRequest request, String name, String expectedPrefix) {
    String value = headerValue(request, name);
    Assertions.assertNotNull(value, "Header " + name + " missing");
    Assertions.assertTrue(value.startsWith(expectedPrefix),
        "Header " + name + " expected prefix " + expectedPrefix + ", got " + value);
  }

  public static final class MockServer implements AutoCloseable {
    private final HttpServer server;
    private final MockResponse response;
    private final BlockingQueue<RecordedRequest> requests = new ArrayBlockingQueue<>(1);

    MockServer(MockResponse response) throws IOException {
      this.response = Objects.requireNonNull(response, "response");
      server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
      server.createContext("/", exchange -> {
        String body = readBody(exchange.getRequestBody());
        RecordedRequest recorded = new RecordedRequest(
            exchange.getRequestMethod(),
            exchange.getRequestURI(),
            exchange.getRequestHeaders(),
            body
        );
        requests.offer(recorded);

        byte[] bytes = response.body == null ? new byte[0] : response.body.getBytes(StandardCharsets.UTF_8);
        Headers headers = exchange.getResponseHeaders();
        if (response.headers != null) {
          for (Map.Entry<String, String> entry : response.headers.entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
          }
        }
        if (response.body != null && headerValue(headers, "Content-Type") == null) {
          headers.add("Content-Type", "application/json");
        }
        exchange.sendResponseHeaders(response.status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
          output.write(bytes);
        }
      });
      server.start();
    }

    public String baseUrl() {
      InetSocketAddress address = server.getAddress();
      return "http://" + address.getHostString() + ":" + address.getPort() + "/api/v1";
    }

    public RecordedRequest takeRequest() throws InterruptedException {
      RecordedRequest request = requests.poll(5, TimeUnit.SECONDS);
      Assertions.assertNotNull(request, "Expected a request");
      return request;
    }

    @Override
    public void close() {
      server.stop(0);
    }

    private static String readBody(InputStream input) throws IOException {
      if (input == null) {
        return "";
      }
      byte[] buffer = input.readAllBytes();
      return new String(buffer, StandardCharsets.UTF_8);
    }

    private static String headerValue(Headers headers, String name) {
      for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
        if (entry.getKey().equalsIgnoreCase(name)) {
          if (entry.getValue().isEmpty()) {
            return null;
          }
          return entry.getValue().get(0);
        }
      }
      return null;
    }
  }

  public static final class MockResponse {
    public final int status;
    public final String body;
    public final Map<String, String> headers;

    public MockResponse(int status, String body) {
      this(status, body, null);
    }

    public MockResponse(int status, String body, Map<String, String> headers) {
      this.status = status;
      this.body = body;
      this.headers = headers;
    }
  }

  public static final class RecordedRequest {
    public final String method;
    public final String path;
    public final String query;
    public final Map<String, List<String>> headers;
    public final String body;

    RecordedRequest(String method, URI uri, Headers headers, String body) {
      this.method = method;
      this.path = uri.getPath();
      this.query = uri.getQuery();
      this.headers = new LinkedHashMap<>(headers);
      this.body = body == null ? "" : body;
    }
  }
}
