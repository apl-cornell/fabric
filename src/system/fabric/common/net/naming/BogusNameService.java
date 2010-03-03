package fabric.common.net.naming;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class BogusNameService implements NameService {
  public final int port;
  
  public BogusNameService(int port) {
    this.port = port;
  }
  
  public SocketAddress localResolve(String name) throws UnknownHostException {
    return new SocketAddress(name, InetAddress.getLocalHost(), this.port);
  }

  public SocketAddress resolve(String name) throws UnknownHostException {
    return new SocketAddress(name, InetAddress.getLocalHost(), this.port);
  }

}
