package fabric.common.net.handshake;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.cert.Certificate;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import fabric.common.exceptions.NotImplementedException;

public class HandshakeAuthenticated implements Protocol {
  final SSLSocketFactory factory;
  
  public HandshakeAuthenticated() {
    throw new NotImplementedException();
  }
  
  public ShakenSocket initiate(String name, Socket s) throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(name);
    
    SSLSocket sock = (SSLSocket) factory.createSocket(s, name, s.getPort(), true);
    sock.setUseClientMode(true);
    sock.startHandshake();
    
    Certificate[] certs = sock.getSession().getPeerCertificates();
    
    throw new NotImplementedException();
  }

  public ShakenSocket receive(Socket s) {
    // TODO Auto-generated method stub
    throw new NotImplementedException();
  }
}
