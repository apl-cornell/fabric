package fabric.common.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * factory for creating SubServerSockets. This class decorates a
 * javax.net.ServerSocketFactory, which is used for instantiating the underlying
 * channels.
 * 
 * @author mdgeorge
 */
public class SubServerSocketFactory {
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
  public SubServerSocket createServerSocket(int port) throws IOException {
    SubServerSocket result = new SubServerSocket(this);
    result.bind(port);
    return result;
  }

  /** @see javax.net.ServerSocketFactory#createServerSocket(int, int) */
  public SubServerSocket createServerSocket(int port, int backlog) throws IOException {
    SubServerSocket result = new SubServerSocket(this);
    result.bind(port, backlog);
    return result;
  }

  /** @see javax.net.ServerSocketFactory#createServerSocket(int, int, InetAddress) */
  public SubServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException {
    SubServerSocket result = new SubServerSocket(this);
    result.bind(new InetSocketAddress(ifAddress, port), backlog);
    return result;
  }
  
  java.net.ServerSocket createSocketImpl(int port, int backlog, InetAddress ifAddress) throws IOException {
    return factory.createServerSocket(port, backlog, ifAddress);
  }
}
