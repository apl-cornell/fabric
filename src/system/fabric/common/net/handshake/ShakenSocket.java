package fabric.common.net.handshake;

import java.net.Socket;

import fabric.common.net.RemoteIdentity;

public class ShakenSocket {
  /**
   * The name of the virtual server that received the connection.
   */
  public final String name;

  public final RemoteIdentity remoteIdentity;
  public final Socket sock;

  public ShakenSocket(String name, RemoteIdentity remoteIdentity, Socket sock) {
    this.name = name;
    this.remoteIdentity = remoteIdentity;
    this.sock = sock;
  }
}
