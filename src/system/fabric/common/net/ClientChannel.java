package fabric.common.net;

import java.io.IOException;
import java.net.Socket;

class ClientChannel extends Channel {
  private int nextSequenceNumber;
  
  public ClientChannel(Socket s) throws IOException {
    super(s);
    nextSequenceNumber = 0;
  }

  public Connection connect() throws IOException {
    return new Connection(nextSequenceNumber++); 
  }
  
  @Override
  public Connection accept(int sequence) throws IOException {
    throw new IOException("unexpected accept request on client channel");
  }

  @Override
  public String toString() {
    return "channel to " + sock.getRemoteSocketAddress();
  }
}
