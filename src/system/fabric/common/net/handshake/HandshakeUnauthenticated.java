package fabric.common.net.handshake;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import fabric.common.net.naming.SocketAddress;

public class HandshakeUnauthenticated implements HandshakeProtocol {
  //
  // an incredibly simple handshake:
  // client -> server : len, name
  //
  
  public ShakenSocket initiate(String name, SocketAddress addr) throws IOException {
    Socket s = new Socket(addr.getAddress(), addr.getPort());
    OutputStream out = s.getOutputStream();
    out.write(name.length());
    out.write(name.getBytes());
    out.flush();
    return new ShakenSocket(name, null, s);
  }

  public ShakenSocket receive(Socket s) throws IOException {
    InputStream in = s.getInputStream();
    int    len  = in.read();
    byte[] name = new byte[len];
    in.read(name, 0, len);
    return new ShakenSocket(new String(name), null, s);
  }
}
