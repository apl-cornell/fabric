package fabric.common;

/**
 * A <code>FabricException</code> represents an error that may be communicated
 * by the core to a client.
 */
// FIXME should not be runtime exception
public class FabricException extends RuntimeException {
  public FabricException() {
  }

  public FabricException(String msg) {
    super(msg);
  }

  public FabricException(Throwable cause) {
    super(cause);
  }
}
