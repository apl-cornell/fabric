package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HandshakeUnauthenticated extends HandshakeProtocol {
  
  public HandshakeUnauthenticated() {
    super(ProtocolType.UNAUTHENTICATED);
  }
  //
  // an incredibly simple handshake:
  // client -> server : name
  //
  
  @Override
  public ShakenSocket initiateImpl(String name, Socket s) throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(name);
    out.flush();
    return new ShakenSocket(name, null, s);
  }

  @Override
  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String name = in.readUTF();
    System.out.println(name + " [" + name.length() + "]");
    return new ShakenSocket(name, null, s);
  }
}
