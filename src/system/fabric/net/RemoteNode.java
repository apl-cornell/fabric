package fabric.net;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;

import fabric.common.KeyMaterial;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.SubSocket;
import fabric.common.net.SubSocketFactory;
import fabric.lang.security.Principal;
import fabric.messages.Message;

/**
 * Abstracts remote stores and remote workers.
 */
public abstract class RemoteNode implements Serializable {
  /**
   * The node's Fabric node name. (Likely different from its DNS host name.)
   */
  public final String name;

  private transient final ConcurrentMap<SubSocketFactory, BlockingDeque<SubSocket>> subSocketCache;

  /**
   * Maximum number of cached subsocket connections.
   */
  private static final int MAX_QUEUE_SIZE = 10;

  protected RemoteNode(String name) {
    this.name = name;
    this.subSocketCache =
        new ConcurrentHashMap<SubSocketFactory, BlockingDeque<SubSocket>>(2);
  }

  /**
   * @return the node's Fabric node name (Likely different from its DNS host
   *          name).
   */
  public final String name() {
    return name;
  }

  public abstract Principal getPrincipal();

  private BlockingDeque<SubSocket> getSocketDeque(SubSocketFactory factory) {
    BlockingDeque<SubSocket> result = subSocketCache.get(factory);
    if (result != null) return result;

    result = new LinkedBlockingDeque<SubSocket>(MAX_QUEUE_SIZE);
    BlockingDeque<SubSocket> existing =
        subSocketCache.putIfAbsent(factory, result);

    if (existing != null) return existing;
    return result;
  }

  protected SubSocket getSocket(SubSocketFactory factory) throws IOException {
    BlockingDeque<SubSocket> queue = getSocketDeque(factory);

    SubSocket result = queue.poll();
    if (result != null) return result;

    return factory.createSocket(this);
  }

  protected void recycle(SubSocketFactory factory, SubSocket socket)
      throws IOException {
    BlockingDeque<SubSocket> queue = getSocketDeque(factory);
    if (!queue.offer(socket)) socket.close();
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
