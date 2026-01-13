package xyz.pushpad;

public class PushpadException extends Exception {
  public PushpadException(String message) {
    super(message);
  }

  public PushpadException(String message, Throwable cause) {
    super(message, cause);
  }
}
