package fabric.client;

import fabric.common.FabricRuntimeException;

/**
 * An <code>UnreachableNodeException</code> is used to indicate that an attempt
 * to connect to a remote node has failed.
 */
public class UnreachableNodeException extends FabricRuntimeException {
  private RemoteNode node;

  public UnreachableNodeException(RemoteNode node) {
    this.node = node;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Throwable#getMessage()
   */
  @Override
  public String getMessage() {
    return "Unreachable node: " + node;
  }

}
