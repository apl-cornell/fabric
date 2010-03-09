package fabric.common.net;

public class NotImplementedException extends RuntimeException {
  public NotImplementedException() {
    super("method not yet implemented");
  }
  
  public NotImplementedException(Exception cause) {
    super("error handling not yet implemented", cause);
  }
}
