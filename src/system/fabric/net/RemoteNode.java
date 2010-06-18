package fabric.net;

import java.io.IOException;

import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.naming.SocketAddress;
import fabric.messages.Message;

/**
 * Abstracts remote stores and remote workers.
 */
public abstract class RemoteNode {
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

  protected <R extends Message.Response> R send(Message<R> message) {
    throw new NotImplementedException();
  }
}
