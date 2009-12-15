package fabric.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class TCPSocketFamily extends BaseSocketFamily {

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

	public class _TCPSocket<
		TCPServerSocket        extends _TCPServerSocket        <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
		TCPServerSocketFactory extends _TCPServerSocketFactory <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
		TCPSocket              extends _TCPSocket              <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>,
		TCPSocketFactory       extends _TCPSocketFactory       <TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory>
	> implements _Socket<InetSocketAddress, Integer, TCPServerSocket, TCPServerSocketFactory, TCPSocket, TCPSocketFactory> {

		public final void close() throws IOException {
			throw new NotImplementedException();
		}

		public final void connect(InetSocketAddress addr) throws IOException {
			throw new NotImplementedException();
		}

		public final OutputStream getOutputStream() throws IOException {
			throw new NotImplementedException();
		}

		public final InputStream getInputStream() throws IOException {
			throw new NotImplementedException();
		}
	}
}

/*
** vim: ts=2 sw=2 cindent ai
*/
