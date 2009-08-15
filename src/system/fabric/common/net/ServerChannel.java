package fabric.common.net;

import java.net.Socket;

public class ServerChannel extends Channel {

  public ServerChannel(Socket sock, Acceptor acc) {
    super(sock);
    throw new NotImplementedException();
  }
  
  @Override
  protected void handleUnknownSequence(int sequenceNumber) {
    throw new NotImplementedException();
  }

}
