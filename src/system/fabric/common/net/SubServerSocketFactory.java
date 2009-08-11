package fabric.common.net;

import java.net.InetAddress;

/**
 * factory for creating SubServerSockets. This class decorates a
 * javax.net.ServerSocketFactory, which is used for instantiating the underlying
 * channels.
 * 
 * @author mdgeorge
 */
public class SubServerSocketFactory {
  
  public SubServerSocket createServerSocket() {
    throw new NotImplementedException(); 
  }
  
  public SubServerSocket createServerSocket(int port) {
    throw new NotImplementedException();
  }

  public SubServerSocket createServerSocket(int port, int backlog) {
    throw new NotImplementedException();
  }

  public SubServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) {
    throw new NotImplementedException();
  }

}
