package fabric.common;


/**
 * Used to indicate the client is not following protocol.
 */
public class ProtocolError extends FabricException {
  public ProtocolError(String msg) {
    super(msg);
  }
}
