package net.echo;

import fabric.common.net.naming.NameService;
import fabric.common.net.naming.SocketAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class BogusNameService implements NameService {
  public final int port;
  
  public BogusNameService(int port) {
    this.port = port;
  }
  
  public SocketAddress localResolve(String name) {
    return new SocketAddress(null, this.port);
  }

  public SocketAddress resolve(String name) throws UnknownHostException {
    return new SocketAddress(InetAddress.getLocalHost(), this.port);
  }
}
