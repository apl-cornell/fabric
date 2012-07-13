package fabric.common.exceptions;

public class NotImplementedException extends RuntimeException {
  public NotImplementedException() {
    super("method not yet implemented");
  }

  public NotImplementedException(String message) {
    super("not yet implemented: " + message);
  }

  public NotImplementedException(Exception cause) {
    super("error handling not yet implemented", cause);
  }
}
