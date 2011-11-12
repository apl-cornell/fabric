package fabric.common.exceptions;

/**
 * Used to indicate the worker is not following protocol.
 */
public class ProtocolError extends FabricException {
  public ProtocolError(String msg) {
    super(msg);
  }
}
