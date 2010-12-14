package fabric.common.net.handshake;

import java.io.IOException;
import java.net.Socket;

import fabric.common.net.naming.SocketAddress;

public interface HandshakeProtocol {
  /**
   * Initiates a handshake with a remote host at the given address.
   * 
   * @param name
   *          name of the virtual host
   * @param addr
   *          the IP address and port number for the host with which to initiate
   *          a handshake.
   */
  ShakenSocket initiate(String name, SocketAddress addr) throws IOException;
  
  /**
   * Receives a handshake via the given socket.
   * 
   * @param s
   *          the socket on which to receive a handshake.
   */
  ShakenSocket receive(Socket s)                         throws IOException;
}
