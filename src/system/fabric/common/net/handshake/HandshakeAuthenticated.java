/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.common.net.handshake;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.cert.Certificate;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import fabric.common.net.NotImplementedException;
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
