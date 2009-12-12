package fabric.common.net.ssl;

import java.io.IOException;
import java.net.InetAddress;

import javax.net.ssl.SSLServerSocketFactory;

import fabric.common.net.multiplexed.NotImplementedException;
import fabric.common.net.multiplexed.SubServerSocketFactory;

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
  public SSLSubServerSocket createServerSocket(Integer port, int backlog, InetAddress ifAddress) throws IOException {
    throw new NotImplementedException();
  }

  @Override
  public SSLSubServerSocket createServerSocket(Integer port, int backlog) throws IOException {
    throw new NotImplementedException();
  }

  @Override
  public SSLSubServerSocket createServerSocket(Integer port) throws IOException {
    throw new NotImplementedException();
  }
}
