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
public class SubSocketFactory {
  private final javax.net.SocketFactory     factory;
  private final Map<InetSocketAddress, Channel> channels;
  
  public SubSocketFactory(SocketFactory factory) {
    this.factory  = factory;
    this.channels = new HashMap<InetSocketAddress, Channel>();
  }
  
  public SubSocket createSocket() {
    return new SubSocket(this);
  }
  
  public SubSocket createSocket(String host, int port) throws IOException {
    SubSocket result = new SubSocket(this);
    result.connect(new InetSocketAddress(host, port));
    return result;
  }

  public SubSocket createSocket(InetAddress host, int port) throws IOException {
    SubSocket result = new SubSocket(this);
    result.connect(new InetSocketAddress(host, port));
    return result;
  }

  public synchronized Channel getChannel(InetSocketAddress addr) throws IOException {
    Channel result = channels.get(addr);
    if (null == result) {
      Socket sock = factory.createSocket(addr.getAddress(), addr.getPort());
      result = new Channel(sock);
      channels.put(addr, result);
    }
    
    return result;
  }
}
