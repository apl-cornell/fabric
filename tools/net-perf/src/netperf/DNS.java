package netperf;

import java.io.IOException;
import java.net.InetAddress;

import fabric.common.net.naming.NameService;
import fabric.common.net.naming.SocketAddress;

public class DNS implements NameService {

  private static final int PORT_NUM = 17866;

  @Override
  public SocketAddress resolve(String name, PortType portType)
      throws IOException {
    return new SocketAddress(InetAddress.getByName(name), PORT_NUM);
  }

  @Override
  public SocketAddress localResolve(String name, PortType portType)
      throws IOException {
    return new SocketAddress(
        InetAddress.getByAddress(new byte[] { 0, 0, 0, 0 }), PORT_NUM);
  }

}
