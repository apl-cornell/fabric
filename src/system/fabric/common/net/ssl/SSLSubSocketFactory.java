package fabric.common.net.ssl;

import java.io.IOException;
import java.net.InetAddress;

import javax.net.ssl.SSLSocketFactory;

import fabric.common.net.multiplexed.*;

public class SSLSubSocketFactory extends SubSocketFactory {

  public SSLSubSocketFactory(SSLSocketFactory factory) {
    super(factory);
  }

  @Override
  public SSLSubSocket createSocket() {
    return new SSLSubSocket(this);
  }

  @Override
  public SSLSubSocket createSocket(InetAddress host, int port) throws IOException {
    return (SSLSubSocket) super.createSocket(host, port);
  }

  @Override
  public SSLSubSocket createSocket(String host, int port) throws IOException {
    return (SSLSubSocket) super.createSocket(host, port);
  }
}
