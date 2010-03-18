package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import fabric.common.net.naming.SocketAddress;

public class HandshakeUnauthenticated implements HandshakeProtocol {
  //
  // an incredibly simple handshake:
  // client -> server : name
  //
  
  public ShakenSocket initiate(String name, SocketAddress addr) throws IOException {
    Socket s = new Socket(addr.getAddress(), addr.getPort());
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(name);
    out.flush();
    return new ShakenSocket(name, null, s);
  }

  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String name = in.readUTF();
    System.out.println(name + " [" + name.length() + "]");
    return new ShakenSocket(name, null, s);
  }
}
