package fabric.common.net.ssl;

import java.io.IOException;

import fabric.common.net.multiplexed.NotImplementedException;
import fabric.common.net.multiplexed.SubServerSocket;

public class SSLSubServerSocket extends SubServerSocket {
  protected SSLSubServerSocket(SSLSubServerSocketFactory factory) {
    super(factory);
    throw new NotImplementedException();
  }

  @Override
  public SSLSubSocket accept() throws IOException {
    throw new NotImplementedException();
  }

}
