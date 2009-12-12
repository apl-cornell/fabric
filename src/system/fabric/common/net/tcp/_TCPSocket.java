package fabric.common.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import fabric.common.net._Socket;
import fabric.common.net.multiplexed.NotImplementedException;


public class _TCPSocket<
  TCPServerSocket        extends _TCPServerSocket        <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPServerSocketFactory extends _TCPServerSocketFactory <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPSocket              extends _TCPSocket              <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
  TCPSocketFactory       extends _TCPSocketFactory       <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>
> implements _Socket<InetSocketAddress, Integer, TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory> {

  public void close() throws IOException {
    throw new NotImplementedException();
  }

  public void connect(InetSocketAddress addr) throws IOException {
    throw new NotImplementedException();
  }

  public OutputStream getOutputStream() throws IOException {
    throw new NotImplementedException();
  }

  public InputStream getInputStream() throws IOException {
    throw new NotImplementedException();
  }
}
