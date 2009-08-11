package fabric.common.net;

import java.net.InetAddress;

/**
 * A factory for creating SubSockets. The factory decorates a
 * javax.net.SocketFactory, which is used for creating the underlying channels.
 * 
 * @author mdgeorge
 */
public class SubSocketFactory {
  public SubSocket createSocket() {
    throw new NotImplementedException();
  }
  
  public SubSocket createSocket(String host, int port) {
    throw new NotImplementedException();
  }

  public SubSocket createSocket(InetAddress host, int port) {
    throw new NotImplementedException();
  }

  public SubSocket createSocket(String host, int port, InetAddress localAddress, int localPort) {
    throw new NotImplementedException();
  }

  public SubSocket createSocket(InetAddress host, int port, InetAddress localAddress, int localPort) {
    throw new NotImplementedException();
  }
}
