package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import fabric.common.net.handshake.HandshakeProtocol.ProtocolType;
import fabric.common.net.naming.NameService;

public class HandshakeAcceptor {
  private final ProtocolType[] handshakes;
  
  public HandshakeAcceptor(ProtocolType[] handshakeTypes,
      NameService nameService) {
    this.handshakes = new ProtocolType[256];
    for (ProtocolType type : handshakeTypes) {
      handshakes[type.ordinal()] = type;
    }
  }
  
  /**
   * Receives a handshake from the given socket.
   */
  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    ProtocolType protocolType = ProtocolType.values()[in.readByte()];
    
    HandshakeProtocol protocol = protocolType.instantiate();
    return protocol.receive(s);
  }
}
