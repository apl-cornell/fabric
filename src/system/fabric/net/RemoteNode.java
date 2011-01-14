package fabric.net;

import java.io.IOException;
import java.io.Serializable;

import fabric.common.KeyMaterial;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.SubSocket;
import fabric.common.net.SubSocketFactory;
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
      SubSocketFactory subSocketFactory, Message<R, E> message) throws E {
    try {
      SubSocket socket = subSocketFactory.createSocket(name);
      return message.send(socket);
    } catch (IOException e) {
      throw new NotImplementedException(e);
    }
  }
  
  public static SubSocketFactory createAuthFactory(KeyMaterial ... keys) {
    throw new NotImplementedException();
  }
}
