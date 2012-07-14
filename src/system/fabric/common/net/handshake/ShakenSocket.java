package fabric.common.net.handshake;

import java.net.Socket;

import fabric.lang.security.Principal;

public class ShakenSocket {
  public final String name;
  public final Principal principal;
  public final Socket sock;

  public ShakenSocket(String name, Principal principal, Socket sock) {
    this.name = name;
    this.principal = principal;
    this.sock = sock;
  }
}
