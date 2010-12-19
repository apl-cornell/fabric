package fabric.common.net.handshake;

import java.io.IOException;
import java.net.Socket;

public interface Protocol {
  /**
   * Initiates a handshake with a remote host at the given address.
   * 
   * @param name
   *          the name of the remote virtual host to connect to.
   * 
   * @param s
   *          the socket on which to initiate a handshake.
   */
  ShakenSocket initiate(String name, Socket s) throws IOException;
  
  /**
   * Receives a handshake via the given socket.
   * 
   * @param s
   *          the socket on which to receive a handshake.
   */
  ShakenSocket receive(Socket s)                         throws IOException;

  interface Factory {
    Protocol create();
  }
}
