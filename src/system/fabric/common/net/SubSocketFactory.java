package fabric.common.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.net.SocketFactory;


/**
 * A factory for creating SubSockets. The factory decorates a
 * javax.net.SocketFactory, which is used for creating the underlying channels.
 * 
 * @author mdgeorge
 */
public final class SubSocketFactory {
  private final javax.net.SocketFactory     factory;
  private final Map<InetSocketAddress, ClientChannel> channels;

  /**
   * Create a new SubSocket factory that decorates the given SocketFactory.
   * Note that SubSockets created from different SubSocketFactories will not
   * attempt to share channels (as these channels may have different underlying
   * socket implementations).
   */ 
  public SubSocketFactory(SocketFactory factory) {
    this.factory  = factory;
    this.channels = new HashMap<InetSocketAddress, ClientChannel>();
  }

  /** @see javax.net.SocketFactory#createSocket() */
  public SubSocket createSocket() {
    return new SubSocket(this);
  }

  /** @see javax.net.SocketFactory#createSocket(String, int) */
  public SubSocket createSocket(String host, int port) throws IOException {
    return createSocket(new InetSocketAddress(host, port));
  }

  /** @see javax.net.SocketFactory#createSocket(InetAddress, int) */
  public SubSocket createSocket(InetAddress host, int port) throws IOException {
    return createSocket(new InetSocketAddress(host, port));
  }

  public SubSocket createSocket(InetSocketAddress addr) throws IOException {
    SubSocket result = createSocket();
    result.connect(addr);
    return result;
  }

  /**
   * return a channel associated with the given address, creating it if necessary.
   */
  synchronized ClientChannel getChannel(InetSocketAddress addr) throws IOException {
    ClientChannel result = channels.get(addr);
    if (null == result) {
      Socket sock = factory.createSocket(addr.getAddress(), addr.getPort());
      result = new ClientChannel(sock);
      channels.put(addr, result);
    }

    return result;
  }
}
