package fabric.common.net;

import java.io.IOException;
import java.net.Socket;

import fabric.common.net.handshake.ShakenSocket;

/**
 * A server channel is capable of receiving new incoming connections, but not
 * of making new outgoing connections.  It is associated both with a local
 * SocketAddress (IP + port) and a remote IP address.
 *  
 * @author mdgeorge
 */
public class ServerChannel extends Channel {
  private final Acceptor.ConnectionQueue queue;

  public ServerChannel(ShakenSocket sock, Acceptor.ConnectionQueue queue) throws IOException {
    super(sock);
    this.queue= queue;
  }

  /** create a new subsocket for an incoming connection and notify the acceptor */
  @Override
  public Connection accept(int sequence) throws IOException {
    throw new NotImplementedException();
    /*
    Connection conn = new Connection(sequence);
    queue.connected(new SubSocket(conn));
    return conn;
    */
  }

  @Override
  public String toString() {
    throw new NotImplementedException();
    // return "channel from " + sock.getInetAddress() + " to " + queue.getAddress();
  }

}

