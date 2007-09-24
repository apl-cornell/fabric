package fabric.client;

public class AbortException extends RuntimeException {
  public AbortException(Throwable cause) {
    super(cause);
  }
}
