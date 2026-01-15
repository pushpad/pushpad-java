package xyz.pushpad;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class QueryParams {
  private final Map<String, List<String>> params = new LinkedHashMap<>();

  public QueryParams add(String key, String value) {
    if (key == null || value == null) {
      return this;
    }
    params.computeIfAbsent(key, ignored -> new ArrayList<>()).add(value);
    return this;
  }

  public QueryParams addAll(String key, List<String> values) {
    if (values == null) {
      return this;
    }
    for (String value : values) {
      add(key, value);
    }
    return this;
  }

  public boolean isEmpty() {
    return params.isEmpty();
  }

  public String toQueryString() {
    StringBuilder builder = new StringBuilder();
    boolean first = true;
    for (Map.Entry<String, List<String>> entry : params.entrySet()) {
      for (String value : entry.getValue()) {
        if (!first) {
          builder.append('&');
        }
        first = false;
        builder.append(encode(entry.getKey()));
        builder.append('=');
        builder.append(encode(value));
      }
    }
    return builder.toString();
  }

  private String encode(String value) {
    return URLEncoder.encode(value, StandardCharsets.UTF_8);
  }
}
