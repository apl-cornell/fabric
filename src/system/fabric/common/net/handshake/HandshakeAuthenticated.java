package fabric.common.net.handshake;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

import fabric.common.exceptions.NotImplementedException;

public class HandshakeAuthenticated extends HandshakeProtocol {
  final SSLSocketFactory factory;
  
  public HandshakeAuthenticated() {
    super(ProtocolType.AUTHENTICATED);
    throw new NotImplementedException();
  }
  
  @Override
  public ShakenSocket initiateImpl(String name, Socket s) throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(name);
    
//    SSLSocket sock = (SSLSocket) factory.createSocket(s, name, addr.getPort(), true);
//    sock.setUseClientMode(true);
//    sock.startHandshake();
//    
//    Certificate[] certs = sock.getSession().getPeerCertificates();
    
    throw new NotImplementedException();
  }

  @Override
  public ShakenSocket receive(Socket s) {
    // TODO Auto-generated method stub
    throw new NotImplementedException();
  }
}
