package fabric.common.net.handshake;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.cert.Certificate;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.naming.SocketAddress;

public class HandshakeAuthenticated implements HandshakeProtocol {
  final SSLSocketFactory factory;
  
  public HandshakeAuthenticated() {
    throw new NotImplementedException();
  }
  
  public ShakenSocket initiate(String name, SocketAddress addr) throws IOException {
    Socket s = new Socket(addr.getAddress(), addr.getPort());
    s.setKeepAlive(false);
    s.setTcpNoDelay(true);
    
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(name);
    
    SSLSocket sock = (SSLSocket) factory.createSocket(s, name, addr.getPort(), true);
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
