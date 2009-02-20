package fabric.client.remote;

import fabric.common.FabricException;

/**
 * Thrown to indicate a remote call had an exceptional result.
 */
public class RemoteCallException extends FabricException {
  public RemoteCallException(Throwable cause) {
    super(cause);
  }
}
