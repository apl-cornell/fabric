package fabric.common.net.tcp;

import fabric.common.net._ServerSocketFactory;
import fabric.common.net.multiplexed.NotImplementedException;
import java.net.InetSocketAddress;

public class _TCPServerSocketFactory<
  TCPServerSocket        extends _TCPServerSocket        <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPServerSocketFactory extends _TCPServerSocketFactory <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPSocket              extends _TCPSocket              <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPSocketFactory       extends _TCPSocketFactory       <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>
> implements _ServerSocketFactory<InetSocketAddress, Integer, TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory> {

  public final TCPServerSocket createServerSocket() {
    throw new NotImplementedException();
  }

  public final TCPServerSocket createServerSocket(Integer port) {
    throw new NotImplementedException();
  }

  public final TCPServerSocket createServerSocket(Integer port, int backlog) {
    throw new NotImplementedException();
  }
}


