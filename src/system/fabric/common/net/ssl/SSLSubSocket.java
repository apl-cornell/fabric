package fabric.common.net.ssl;

import javax.net.ssl.SSLSession;

import fabric.common.net.*;

public class SSLSubSocket extends SubSocket {

  public SSLSubSocket(SSLSubSocketFactory factory) {
    super(factory);
    throw new NotImplementedException();
  }

  public SSLSession getSession() {
    throw new NotImplementedException();
  }
}
