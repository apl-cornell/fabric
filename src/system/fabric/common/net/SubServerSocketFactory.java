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
  
  public SubServerSocketFactory(javax.net.ServerSocketFactory factory) {
    this.factory = factory;
  }
  
  public SubServerSocket createServerSocket() {
    return new SubServerSocket(this.factory);
  }
  
  public SubServerSocket createServerSocket(int port) throws IOException {
    SubServerSocket result = new SubServerSocket(this.factory);
    result.bind(port);
    return result;
  }

  public SubServerSocket createServerSocket(int port, int backlog) throws IOException {
    SubServerSocket result = new SubServerSocket(this.factory);
    result.bind(port, backlog);
    return result;
  }

  public SubServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException {
    SubServerSocket result = new SubServerSocket(this.factory);
    result.bind(new InetSocketAddress(ifAddress, port), backlog);
    return result;
  }
}
