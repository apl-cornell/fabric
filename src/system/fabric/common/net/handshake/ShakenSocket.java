package fabric.common.net.handshake;

import java.net.Socket;

import fabric.lang.security.NodePrincipal;

public class ShakenSocket {
  public final String        name;
  public final NodePrincipal principal;
  public final Socket        sock;
  
  public ShakenSocket(String name, NodePrincipal principal, Socket sock) {
    this.name      = name;
    this.principal = principal;
    this.sock      = sock;
  }
}
