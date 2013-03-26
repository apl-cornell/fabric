package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HandshakeUnauthenticated implements Protocol {
  //
  // an incredibly simple handshake:
  // client -> server : name
  //

  /**
   * @param name The name of the remote host.
   */
  @Override
  public ShakenSocket initiate(String name, Socket s) throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(name);
    out.flush();
    return new ShakenSocket(name, null, s);
  }

  @Override
  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String name = in.readUTF();
    return new ShakenSocket(name, null, s);
  }

}
