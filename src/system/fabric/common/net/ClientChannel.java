package fabric.common.net;

import java.io.IOException;
import java.net.Socket;

class ClientChannel extends Channel {
  private int nextSequenceNumber;
  
  public ClientChannel(Socket s) {
    super(s);
    throw new NotImplementedException();
  }

  public Connection connect() throws IOException {
    return new Connection(nextSequenceNumber++);
  }
  
  @Override
  protected void handleUnknownSequence(int sequenceNumber) {
    throw new NotImplementedException();
  }
}
