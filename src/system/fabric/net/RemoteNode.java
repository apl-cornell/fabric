package fabric.net;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

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

  private transient final Map<SubSocketFactory, Deque<SubSocket>> subSocketCache;

  /**
   * Maximum number of cached subsocket connections.
   */
  private static final int MAX_QUEUE_SIZE = 10;

  protected RemoteNode(String name) {
    this.name = name;
    this.subSocketCache = new HashMap<SubSocketFactory, Deque<SubSocket>>(2);
  }

  /**
   * @return the node's hostname.
   */
  public final String name() {
    return name;
  }

  private Deque<SubSocket> getSocketDeque(SubSocketFactory factory) {
    synchronized (subSocketCache) {
      Deque<SubSocket> result = subSocketCache.get(factory);
      if (result == null) {
        result = new ArrayDeque<SubSocket>();
        subSocketCache.put(factory, result);
      }

      return result;
    }
  }

  protected SubSocket getSocket(SubSocketFactory factory) throws IOException {
    Deque<SubSocket> queue = getSocketDeque(factory);
    synchronized (queue) {
      if (queue.isEmpty()) return factory.createSocket(name);
      return queue.pop();
    }
  }

  protected void recycle(SubSocketFactory factory, SubSocket socket)
      throws IOException {
    Deque<SubSocket> queue = getSocketDeque(factory);
    synchronized (queue) {
      if (queue.size() < MAX_QUEUE_SIZE)
        queue.addFirst(socket);
      else socket.close();
    }
  }

  protected <R extends Message.Response, E extends FabricException> R send(
      SubSocketFactory subSocketFactory, Message<R, E> message) throws E {
    try {
      SubSocket socket = getSocket(subSocketFactory);
      try {
        return message.send(socket);
      } catch (IOException e) {
        socket = null;
        throw e;
      } finally {
        if (socket != null) {
          recycle(subSocketFactory, socket);
        }
      }
    } catch (IOException e) {
      throw new NotImplementedException(e);
    }
  }

  public static SubSocketFactory createAuthFactory(KeyMaterial... keys) {
    throw new NotImplementedException();
  }
}
