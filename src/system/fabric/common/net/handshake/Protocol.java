package fabric.common.net.handshake;

import java.io.IOException;
import java.net.Socket;

import fabric.net.RemoteNode;
import fabric.worker.remote.RemoteWorker;

/**
 * @param <Node> the type of node at the remote endpoint.
 */
public interface Protocol<Node extends RemoteNode<Node>> {
  /**
   * Initiates a handshake with a remote host at the given address.
   *
   * @param remoteNode
   *          the remote node to connect to.
   * @param s
   *          the socket on which to initiate a handshake.
   */
  ShakenSocket<Node> initiate(Node remoteNode, Socket s) throws IOException;

  /**
   * Receives a handshake via the given socket.
   *
   * @param remoteNode
   *          the node at the remote endpoint.
   * @param s
   *          the socket on which to receive a handshake.
   */
  ShakenSocket<RemoteWorker> receive(Socket s) throws IOException;

}
