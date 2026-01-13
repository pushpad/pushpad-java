package xyz.pushpad;

public class ApiException extends PushpadException {
  private final int statusCode;
  private final String body;

  public ApiException(int statusCode, String body) {
    super(buildMessage(statusCode, body));
    this.statusCode = statusCode;
    this.body = body;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getBody() {
    return body;
  }

  private static String buildMessage(int statusCode, String body) {
    if (body == null || body.isEmpty()) {
      return String.format("pushpad: unexpected status code %d", statusCode);
    }
    return String.format("pushpad: status %d: %s", statusCode, body);
  }
}
