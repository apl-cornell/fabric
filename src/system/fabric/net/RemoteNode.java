package fabric.net;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;

import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.SubSocket;
import fabric.common.net.SubSocketFactory;
import fabric.lang.security.Principal;
import fabric.messages.AsyncMessage;
import fabric.messages.Message;

/**
 * Abstracts remote stores and remote workers.
 */
public abstract class RemoteNode<This extends RemoteNode<This>>
    implements Serializable {
  /**
   * The node's Fabric node name. (Likely different from its DNS host name.)
   */
  public final String name;

  private transient final ConcurrentMap<SubSocketFactory<This>, BlockingDeque<SubSocket<This>>> subSocketCache;

  /**
   * Maximum number of cached subsocket connections.
   */
  private static final int MAX_QUEUE_SIZE = 10;

  protected RemoteNode(String name) {
    this.name = name;
    this.subSocketCache = new ConcurrentHashMap<>(2);
  }

  /**
   * @return the node's Fabric node name (Likely different from its DNS host
   *          name).
   */
  public final String name() {
    return name;
  }

  public abstract Principal getPrincipal();

  private BlockingDeque<SubSocket<This>> getSocketDeque(
      SubSocketFactory<This> factory) {
    BlockingDeque<SubSocket<This>> result = subSocketCache.get(factory);
    if (result != null) return result;

    result = new LinkedBlockingDeque<>(MAX_QUEUE_SIZE);
    BlockingDeque<SubSocket<This>> existing =
        subSocketCache.putIfAbsent(factory, result);

    if (existing != null) return existing;
    return result;
  }

  protected SubSocket<This> getSocket(SubSocketFactory<This> factory)
      throws IOException {
    BlockingDeque<SubSocket<This>> queue = getSocketDeque(factory);

    SubSocket<This> result = queue.poll();
    if (result != null) return result;

    @SuppressWarnings("unchecked")
    This this_ = (This) this;
    return factory.createSocket(this_);
  }

  protected void recycle(SubSocketFactory<This> factory, SubSocket<This> socket)
      throws IOException {
    BlockingDeque<SubSocket<This>> queue = getSocketDeque(factory);
    if (!queue.offer(socket)) socket.close();
  }

  protected <R extends Message.Response, E extends FabricException> R send(
      SubSocketFactory<This> subSocketFactory, Message<R, E> message) throws E {
    try {
      SubSocket<This> socket = getSocket(subSocketFactory);
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

  protected void send(SubSocketFactory<This> subSocketFactory,
      AsyncMessage message) {
    try {
      SubSocket<This> socket = getSocket(subSocketFactory);
      try {
        message.send(socket);
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
}
