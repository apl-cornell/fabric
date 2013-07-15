package fabric.common.net.handshake;

import java.net.Socket;

import fabric.common.net.RemoteIdentity;
import fabric.net.RemoteNode;

/**
 * @param <Node> the type of node at the remote endpoint.
 */
public class ShakenSocket<Node extends RemoteNode<Node>> {
  /**
   * The name of the virtual server that received the connection.
   */
  public final String name;

  public final RemoteIdentity<Node> remoteIdentity;
  public final Socket sock;

  public ShakenSocket(String name, RemoteIdentity<Node> remoteIdentity,
      Socket sock) {
    this.name = name;
    this.remoteIdentity = remoteIdentity;
    this.sock = sock;
  }
}
