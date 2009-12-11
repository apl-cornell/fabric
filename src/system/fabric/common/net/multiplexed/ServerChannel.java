package fabric.common.net.multiplexed;

import java.io.IOException;
import java.net.Socket;

/**
 * A server channel is capable of receiving new incoming connections, but not
 * of making new outgoing connections.  It is associated both with a local
 * SocketAddress (IP + port) and a remote IP address.
 *  
 * @author mdgeorge
 */
public class ServerChannel extends Channel {
  private final Acceptor acc;
  
  public ServerChannel(Socket sock, Acceptor acc) throws IOException {
    super(sock);
    this.acc = acc;
  }
  
  /** create a new subsocket for an incoming connection and notify the acceptor */
  @Override
  public Connection accept(int sequence) throws IOException {
    Connection conn = new Connection(sequence);
    acc.connected(new SubSocket(conn));
    return conn;
  }

  @Override
  public String toString() {
    return "channel from " + sock.getInetAddress() + " to " + acc.getAddress();
  }

}
