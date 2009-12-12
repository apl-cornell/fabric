package fabric.common.net.tcp;

import java.net.InetSocketAddress;

import fabric.common.net._SocketFactory;
import fabric.common.net.multiplexed.NotImplementedException;

public class _TCPSocketFactory<
  TCPServerSocket        extends _TCPServerSocket        <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPServerSocketFactory extends _TCPServerSocketFactory <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPSocket              extends _TCPSocket              <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPSocketFactory       extends _TCPSocketFactory       <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>
> implements _SocketFactory<InetSocketAddress, Integer, TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory> {

  public final TCPSocket createSocket() {
    throw new NotImplementedException();
  }

  public final TCPSocket createSocket(InetSocketAddress address) {
    throw new NotImplementedException();
  }
}
