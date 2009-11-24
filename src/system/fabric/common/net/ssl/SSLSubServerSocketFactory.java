package fabric.common.net.ssl;

import java.io.IOException;
import java.net.InetAddress;

import javax.net.ssl.SSLServerSocketFactory;

import fabric.common.net.NotImplementedException;
import fabric.common.net.SubServerSocketFactory;

public class SSLSubServerSocketFactory extends SubServerSocketFactory {

  public SSLSubServerSocketFactory(SSLServerSocketFactory factory) {
    super(factory);
    throw new NotImplementedException();
  }

  @Override
  public SSLSubServerSocket createServerSocket() {
    throw new NotImplementedException();
  }

  @Override
  public SSLSubServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException {
    throw new NotImplementedException();
  }

  @Override
  public SSLSubServerSocket createServerSocket(int port, int backlog) throws IOException {
    throw new NotImplementedException();
  }

  @Override
  public SSLSubServerSocket createServerSocket(int port) throws IOException {
    throw new NotImplementedException();
  }
}
