package fabric.common.net.handshake;

import java.io.IOException;
import java.net.Socket;

import fabric.net.RemoteNode;

public interface Protocol {
  /**
   * Initiates a handshake with a remote host at the given address.
   * 
   * @param remoteNode
   *          the remote node to connect to.
   * @param s
   *          the socket on which to initiate a handshake.
   */
  ShakenSocket initiate(RemoteNode remoteNode, Socket s) throws IOException;

  /**
   * Receives a handshake via the given socket.
   * 
   * @param remoteNode
   *          the node at the remote endpoint.
   * @param s
   *          the socket on which to receive a handshake.
   */
  ShakenSocket receive(Socket s) throws IOException;

}
