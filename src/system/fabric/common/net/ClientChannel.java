package fabric.common.net;

import java.io.IOException;
import java.net.Socket;

import fabric.common.net.handshake.ShakenSocket;


/**
 * Client channels are capable of making outgoing requests, but not of receiving
 * new incoming requests.  They are only associated with a remote address, and
 * have no distinguished local address.
 * 
 * @author mdgeorge
 */
class ClientChannel extends Channel {
  private int nextSequenceNumber;

  public ClientChannel(ShakenSocket s) throws IOException {
    super(s);
    nextSequenceNumber = 0;
  }

  /** initiate a new substream */
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

