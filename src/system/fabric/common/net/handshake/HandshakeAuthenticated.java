package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

import javax.net.ssl.SSLContext;

import fabric.common.Crypto;
import fabric.common.KeyMaterial;
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
  //    A -> B: a's store/onum, signed by a's store                           //
  //    A -> B: cert chain for a's store                                      //
  // 3. Principal exchange:                                                   //
  //    B -> A: b's store/onum, signed by b's store                           //
  //    B -> A: cert chain for b's store                                      //
  //                                                                          //
  //////////////////////////////////////////////////////////////////////////////
  
  private final static class Receiver {
    public X509Certificate[] principalChain;
    public SSLSocketFactory  factory;
  }

  private final Map<String, Receiver> receivers;
  private final SSLSocketFactory      initiatorFactory;
  private final Set<TrustAnchor>      initiatorTrust;
  
  private HandshakeAuthenticated(KeyMaterial ... endpoints) throws GeneralSecurityException {
    this.receivers = new HashMap<String, Receiver>(endpoints.length);
    
    for (KeyMaterial keys : endpoints) {
      Receiver receiver = new Receiver();
      
      receiver.principalChain = keys.getPrincipalChain();
      receiver.factory        = createSSLFactory(keys);
      
      receivers.put(keys.getName(), receiver);
    }
    
    this.initiatorFactory = createSSLFactory(endpoints[0]);
    this.initiatorTrust   = endpoints[0].getTrustedCerts();
  }
  
  public ShakenSocket initiate(String name, Socket s) throws IOException {
    DataOutputStream clearOut = new DataOutputStream(s.getOutputStream());
    clearOut.writeUTF(name);
    
    SSLSocket sock = (SSLSocket) initiatorFactory.createSocket(s, name, s.getPort(), true);
    sock.setUseClientMode(true);
    sock.startHandshake();
    
    DataInputStream in = new DataInputStream(sock.getInputStream());
    X509Certificate[] peerChain = readCertificateChain(in);

    // validate peerChain
    validate(peerChain, sock.getSession().getPeerCertificates());

    NodePrincipal peer = getPrincipal(peerChain); 
    return new ShakenSocket(name, peer, sock);
  }

  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String name = in.readUTF();
    Receiver receiver = receivers.get(name);
    
    SSLSocket sock = (SSLSocket) receiver.factory.createSocket(s, name, s.getPort(), true);
    sock.setUseClientMode(false);
    sock.startHandshake();

    DataOutputStream out = new DataOutputStream(sock.getOutputStream());
    writeCertificateChain(out, receiver.principalChain);

    NodePrincipal peer = getPrincipal(sock.getSession().getPeerCertificates());
    
    return new ShakenSocket(name, peer, sock);
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // Factory                                                                  //
  //////////////////////////////////////////////////////////////////////////////
  
  
  public static class Factory implements Protocol.Factory {
    private final HandshakeAuthenticated instance;

    public Factory (KeyMaterial ... keys) throws GeneralSecurityException {
      this.instance = new HandshakeAuthenticated(keys);
    }

    public HandshakeAuthenticated create() {
      return this.instance;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // fabric trust and key managers                                            //
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Simple key manager - clients always choose ".principal" alias,
   * corresponding to keys.getPrincipalChain(), while servers always choose
   * keys.getName() as an alias, corresponding to keys.getNameChain().
   */
  private static class FabricKeyManager implements X509KeyManager {
    private final KeyMaterial keys;

    public FabricKeyManager(KeyMaterial keys) {
      this.keys = keys;
    }
    
    public String chooseClientAlias(String[] keyTypes, Principal[] issuers, Socket s) {
      return ".principal";
    }

    public String chooseServerAlias(String keyType, Principal[] issuers, Socket s) {
      return keys.getName();
    }

    public X509Certificate[] getCertificateChain(String alias) {
      if (alias.equals(".principal"))
        return keys.getPrincipalChain();
      else if (alias.equals(keys.getName()))
        return keys.getNameChain();
      else
        return null;
    }

    public String[] getClientAliases(String keyType, Principal[] issuers) {
      return new String[] {".principal"};
    }

    public PrivateKey getPrivateKey(String keyType) {
      if (keyType.equals(".principal"))
        return keys.getPrivateKey();
      else if (keyType.equals(keys.getName()))
        return keys.getPrivateKey();
      else
        return null;
    }

    public String[] getServerAliases(String arg0, Principal[] arg1) {
      return new String[] {keys.getName()};
    }
  }
  
  /**
   * Simple trust manager - certificate chains are validated using PKIX with the
   * root of trust given by keys.getTrusted()
   */
  private static class FabricTrustManager implements X509TrustManager {
    private final KeyMaterial keys;
    
    public FabricTrustManager(KeyMaterial keys) {
      this.keys = keys;
    }
    
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
      checkTrusted(chain);
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
      checkTrusted(chain);
    }
    
    private void checkTrusted(X509Certificate[] chain) throws CertificateException {
      try {
        Crypto.validateCertificateChain(chain, keys.getTrustedCerts());
      } catch (GeneralSecurityException e) {
        throw new CertificateException("failed to validate certificate", e);
      }
    }

    public X509Certificate[] getAcceptedIssuers() {
      X509Certificate[] result = new X509Certificate[keys.getTrustedCerts().size()];
     
      int i = 0;
      for (TrustAnchor a : keys.getTrustedCerts())
        result[i++] = a.getTrustedCert();
      
      return result;
    }
  }
  
  private static SSLSocketFactory createSSLFactory(KeyMaterial keys) throws GeneralSecurityException {
    SSLContext context = SSLContext.getInstance("SSL");
    KeyManager[]   kms = new KeyManager[]   {new FabricKeyManager(keys)};
    TrustManager[] tms = new TrustManager[] {new FabricTrustManager(keys)};
    context.init(kms, tms, null);
    
    return context.getSocketFactory();
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // private helper methods                                                   //
  //////////////////////////////////////////////////////////////////////////////

  private void validate(X509Certificate[] peerChain, Certificate[] sockChain) throws IOException {
    try {
      Crypto.validateCertificateChain(peerChain, initiatorTrust);
    } catch (GeneralSecurityException e) {
      throw new IOException("failed to validate peer principal certificate chain", e);
    }

    // check that peerChain and ssl chain correspond to the same key.
    PublicKey sslKey  = ((X509Certificate) sockChain[0]).getPublicKey();
    PublicKey certKey = peerChain[0].getPublicKey();
    
    if (!sslKey.equals(certKey))
      throw new IOException ("Failed to authenticate peer principal.");
  }


  private NodePrincipal getPrincipal(Certificate[] chain) {
    X509Certificate head = (X509Certificate) chain[0];
    String storeName = Crypto.getCN(head.getIssuerX500Principal().getName());
    String onum      = Crypto.getCN(head.getSubjectX500Principal().getName());
    
    Store store = Worker.getWorker().getStore(storeName);

    return new NodePrincipal._Proxy(store, Long.parseLong(onum));
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
    try {
      out.write(cert.getEncoded());
    } catch (final CertificateEncodingException e) {
      throw new IOException("failed to write certificate", e);
    }
  }
  
  private static X509Certificate readCertificate(DataInputStream in) throws IOException {
    try {
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      return (X509Certificate)cf.generateCertificate(in);
    } catch (final CertificateException e) {
      throw new IOException("failed to read certificate", e);
    }
  }
}
