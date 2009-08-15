package fabric.common.net;

import java.io.IOException;
import java.net.Socket;

public class ServerChannel extends Channel {
  private final Acceptor acc;
  
  public ServerChannel(Socket sock, Acceptor acc) throws IOException {
    super(sock);
    this.acc = acc;
  }
  
  @Override
  public Connection accept(int sequence) throws IOException {
    Connection conn = new Connection(sequence);
    acc.connected(new SubSocket(conn));
    return conn;
  }

  @Override
  public String toString() {
    return "channel from " + sock.getRemoteSocketAddress() + " to " + acc.getAddress();
  }

}
