package fabric.common.net.handshake;

import java.io.IOException;
import java.net.Socket;

import fabric.common.net.naming.SocketAddress;

public interface HandshakeProtocol {
  ShakenSocket initiate(SocketAddress addr) throws IOException;
  ShakenSocket receive(Socket s)            throws IOException;
}
