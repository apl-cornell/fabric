package fabric.net;

import java.io.Serializable;

import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.NotImplementedException;
import fabric.messages.Message;

/**
 * Abstracts remote stores and remote workers.
 */
public abstract class RemoteNode implements Serializable {
  /**
   * The DNS hostname of the node.
   */
  public final String name;

  protected RemoteNode(String name) {
    this.name = name;
  }

  /**
   * @return the node's hostname.
   */
  public final String name() {
    return name;
  }

  protected <R extends Message.Response, E extends FabricException> R send(
      Message<R, E> message) throws E {
    throw new NotImplementedException();
  }
}
