package fabric.common.exceptions;

/**
 * A <code>FabricException</code> represents an error that may be communicated
 * by the core to a worker.
 */
public class FabricException extends Exception {
  public FabricException() {
  }

  public FabricException(String msg) {
    super(msg);
  }

  public FabricException(Throwable cause) {
    super(cause);
  }

  public FabricException(String message, Throwable cause) {
    super(message, cause);
  }
}
