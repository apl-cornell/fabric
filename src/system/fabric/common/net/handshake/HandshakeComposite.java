package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import fabric.common.exceptions.NotImplementedException;

public class HandshakeComposite implements Protocol {

  private Map<String, Protocol> handshakes;
  private Protocol              outgoing;
  
  public HandshakeComposite(Protocol ... protocols) {
    this.handshakes = new HashMap<String, Protocol>(protocols.length);
    for (Protocol p : protocols) {
      this.handshakes.put(p.getClass().getName(), p);
    }
    
    this.outgoing   = protocols[0];
  }
  
  public ShakenSocket initiate(String name, Socket s) throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(outgoing.getClass().getName());
    return outgoing.initiate(name, s);
  }

  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String protName = in.readUTF();

    Protocol protocol = this.handshakes.get(protName);
    if (null == protocol)
      // TODO
      throw new NotImplementedException(handshakes.keySet() + "||" + protName);
    
    return protocol.receive(s);
  }

}
