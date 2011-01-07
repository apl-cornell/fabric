package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import fabric.common.Crypto;
import fabric.common.exceptions.NotImplementedException;
import fabric.lang.security.NodePrincipal;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * @see https://apl.cs.cornell.edu/wiki/Fabric:Fabric_Communication_Layer
 * @author mdgeorge
 */
public class HandshakeAuthenticated implements Protocol {
  
  //////////////////////////////////////////////////////////////////////////////
  //                                                                          //
  // Authenticated Handshake Protocol:                                        //
  //                                                                          //
  // 1. A -> B: "B"                                                           //
  // 2. SSL Handshake:                                                        //
  //    B -> A: cert chain certifying that B acts for "B"                     //
  // 3. Principal exchange:                                                   //
  //    A -> B: a's store/onum, signed by a's store                           //
  //    A -> B: cert chain for a's store                                      //
  //    B -> A: b's store/onum, signed by b's store                           //
  //    B -> A: cert chain for b's store                                      //
  //                                                                          //
  //////////////////////////////////////////////////////////////////////////////
  
  private final static class Endpoint {
    public final X509Certificate[] chain;
    public final SSLSocketFactory  factory;
    public final Set<TrustAnchor>  trust;
    
    public Endpoint() {
      throw new NotImplementedException();
    }
  }
  
  private final Map<String, Endpoint> receivers;
  private final Endpoint              initiator;
  
  private HandshakeAuthenticated(KeyStore keystore, char[] password) throws GeneralSecurityException {
    throw new NotImplementedException();
  }
  
  public ShakenSocket initiate(String name, Socket s) throws IOException {
    DataOutputStream clearOut = new DataOutputStream(s.getOutputStream());
    clearOut.writeUTF(name);
    
    SSLSocket sock = (SSLSocket) initiator.factory.createSocket(s, name, s.getPort(), true);
    sock.setUseClientMode(true);
    sock.startHandshake();
    
    NodePrincipal peer = exchangePrincipals(sock, initiator); 

    return new ShakenSocket(name, peer, sock);
  }

  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String name = in.readUTF();
    Endpoint receiver = receivers.get(name);
    
    SSLSocket sock = (SSLSocket) receiver.factory.createSocket(s, name, s.getPort(), true);
    sock.setUseClientMode(false);
    sock.startHandshake();
    
    NodePrincipal peer = exchangePrincipals(sock, receiver);
    
    return new ShakenSocket(name, peer, sock);
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // Factory                                                                  //
  //////////////////////////////////////////////////////////////////////////////
  
  
  public static class Factory implements Protocol.Factory {
    private final HandshakeAuthenticated instance;

    public Factory (KeyStore keystore, char[] password) throws GeneralSecurityException {
      this.instance = new HandshakeAuthenticated(keystore, password);
    }

    public HandshakeAuthenticated create() {
      return this.instance;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // private helper methods                                                   //
  //////////////////////////////////////////////////////////////////////////////

  private static NodePrincipal exchangePrincipals(Socket sock, Endpoint local) throws IOException {
    DataOutputStream out = new DataOutputStream(sock.getOutputStream());
    DataInputStream  in  = new DataInputStream(sock.getInputStream());
    
    writeCertificateChain(out, local.chain);
    X509Certificate[] peerChain = readCertificateChain(in);
    
    if (!Crypto.validateCertificateChain(peerChain, local.trust))
      throw new IOException("failed to validate peer principal certificate chain");
    
    String storeName = Crypto.getCN(peerChain[0].getIssuerX500Principal().getName());
    String onum      = Crypto.getCN(peerChain[0].getSubjectX500Principal().getName());
    
    Store store = Worker.getWorker().getStore(storeName);
    
    throw new NotImplementedException("Need to ensure that peer's cert chain matches the key used during the SSL handshake to prevent replay attacks.");
    // return new NodePrincipal._Proxy(store, Long.parseLong(onum));
  }
  
  private static void writeCertificateChain(DataOutputStream out, X509Certificate[] chain) throws IOException {
    out.writeInt(chain.length);
    for (X509Certificate cert : chain)
      writeCertificate(out, cert);
  }
    
  private static X509Certificate[] readCertificateChain(DataInputStream in) throws IOException {
    int length = in.readInt();
    X509Certificate[] chain = new X509Certificate[length];
    for (int i = 0; i < length; i++)
      chain[i] = readCertificate(in);
    return chain;
  }
  
  private static void writeCertificate(DataOutputStream out, X509Certificate cert) throws IOException {
    throw new NotImplementedException();
  }
  
  private static X509Certificate readCertificate(DataInputStream in) throws IOException {
    throw new NotImplementedException();
  }
}
