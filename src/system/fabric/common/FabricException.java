package fabric.common;

/**
 * A <code>FabricException</code> represents an error that may be communicated
 * by the core to a client.
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
}
