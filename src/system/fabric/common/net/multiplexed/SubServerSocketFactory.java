package fabric.common.net.multiplexed;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import fabric.common.net._ServerSocketFactory;

/**
 * factory for creating SubServerSockets. This class decorates a
 * javax.net.ServerSocketFactory, which is used for instantiating the underlying
 * channels.
 * 
 * @author mdgeorge
 */
public class SubServerSocketFactory implements _ServerSocketFactory<InetSocketAddress, Integer, SubServerSocket,SubServerSocketFactory,SubSocket,SubSocketFactory> {
  private final javax.net.ServerSocketFactory factory;
  
  /** Creates a new SubServerSocketFactory decorating the given
   * ServerSocketFactory.
   * 
   * @param factory the ServerSocketFactory that will be used to create the
   *        ServerSockets used to implement SubServerSockets returned by this
   */
  public SubServerSocketFactory(javax.net.ServerSocketFactory factory) {
    this.factory = factory;
  }
  
  /** @see javax.net.ServerSocketFactory#createServerSocket() */
  public SubServerSocket createServerSocket() {
    return new SubServerSocket(this);
  }
  
  /** @see javax.net.ServerSocketFactory#createServerSocket(int) */
  public SubServerSocket createServerSocket(Integer port) throws IOException {
    SubServerSocket result = new SubServerSocket(this);
    result.bind(port);
    return result;
  }

  /** @see javax.net.ServerSocketFactory#createServerSocket(int, int) */
  public SubServerSocket createServerSocket(Integer port, int backlog) throws IOException {
    SubServerSocket result = new SubServerSocket(this);
    result.bind(port, backlog);
    return result;
  }

  /** @see javax.net.ServerSocketFactory#createServerSocket(int, int, InetAddress) */
  public SubServerSocket createServerSocket(Integer port, int backlog, InetAddress ifAddress) throws IOException {
    SubServerSocket result = new SubServerSocket(this);
    result.bind(new InetSocketAddress(ifAddress, port), backlog);
    return result;
  }
  
  java.net.ServerSocket createSocketImpl(Integer port, int backlog, InetAddress ifAddress) throws IOException {
    return factory.createServerSocket(port, backlog, ifAddress);
  }
}
