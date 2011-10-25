package fabric.worker;

/**
 * An <code>AbortException</code> is thrown to indicate that a transaction was aborted.
 */
public class AbortException extends RuntimeException {
  public AbortException() {
  }

  public AbortException(Throwable cause) {
    super(cause);
  }

  public AbortException(String message) {
    super(message);
  }

  public AbortException(String message, Throwable cause) {
    super(message, cause);
  }
}
