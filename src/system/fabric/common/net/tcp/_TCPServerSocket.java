package fabric.common.net.tcp;

import java.net.InetSocketAddress;

import fabric.common.net._ServerSocket;
import fabric.common.net.multiplexed.NotImplementedException;

public class _TCPServerSocket<
  TCPServerSocket        extends _TCPServerSocket        <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPServerSocketFactory extends _TCPServerSocketFactory <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPSocket              extends _TCPSocket              <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPSocketFactory       extends _TCPSocketFactory       <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>
> implements _ServerSocket<InetSocketAddress, Integer, TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory> {

  public final TCPSocket accept() {
    throw new NotImplementedException();
  }

  public final void bind(Integer port) {
    throw new NotImplementedException();
  }

  public final void bind(Integer port, int backlog) {
    throw new NotImplementedException();
  }

  public final void close() {
    throw new NotImplementedException();
  }
}

