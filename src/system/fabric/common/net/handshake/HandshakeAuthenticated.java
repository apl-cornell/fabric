package fabric.common.net.handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

import fabric.common.exceptions.NotImplementedException;

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
  // 2. SSL Handshake                                                         //
  //    a. A -> B: (principal(A), key(A))                                     //
  //       signed by key(store(principal(A)))                                 //
  //    b. A -> B: (store(principal(A)), key(store(principal(A))              //
  //       signed by key(CA)                                                  //
  //    c. B -> A: (principal(B), key(B))                                     //
  //       signed by key(store(principal(B)))                                 //
  //    d. B -> A: (store(principal(B)), key(store(principal(B))              //
  //       signed by key(CA)                                                  //
  // 3. B -> A: ("B", key(B)) signed by key(CA)                               //
  //                                                                          //
  //////////////////////////////////////////////////////////////////////////////
  
  private final SSLSocketFactory factory;
  
  private HandshakeAuthenticated(SSLSocketFactory factory) {
    this.factory = factory;
  }
  
  public ShakenSocket initiate(String name, Socket s) throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(name);
    
    SSLSocket sock = (SSLSocket) factory.createSocket(s, name, s.getPort(), true);
    sock.setUseClientMode(true);
    sock.startHandshake();
    
    throw new NotImplementedException();
  }

  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String name = in.readUTF();
    
    SSLSocket sock = (SSLSocket) factory.createSocket(s, name, s.getLocalPort(), true);
    sock.setUseClientMode(false);
    sock.startHandshake();
    
    throw new NotImplementedException();
    
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // Factory                                                                  //
  //////////////////////////////////////////////////////////////////////////////
  
  
  public static class Factory implements Protocol.Factory {
    private final SSLSocketFactory factory;
    
    public Factory (KeyStore keys, char[] password) throws GeneralSecurityException {
      // create SSL context
      SSLContext context = SSLContext.getInstance("SSL");
      KeyManager   kms[] = new KeyManager[]   {new FabricKeyManager(keys, password)};
      TrustManager tms[] = new TrustManager[] {new FabricTrustManager(keys)};
      context.init(kms, tms, null);
      
      this.factory = context.getSocketFactory();
    }

    public HandshakeAuthenticated create() {
      return new HandshakeAuthenticated(factory);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Fabric Trust Manager                                                     //
  //////////////////////////////////////////////////////////////////////////////
  
  /**
   * The FabricTrustManager is responsible for validating certificate chains
   * provided by remote connections.  It checks the following criteria:
   * 
   * <ol>
   *   <li>The chain is valid, i.e. each certificate is signed by the previous
   *       certificate.</li>
   *   <li>The root of the chain is a trusted CA.</li>
   *   <li>For incoming connections, the last step in the chain binds a
   *       fabric principal OID to a public key, and that certificate is signed
   *       by the appropriate store.</li>
   *   <li>For outgoing connections, the name of the recipient is the desired
   *       one.</li>
   *   </ol>
   */
  public static class FabricTrustManager implements X509TrustManager {
    
    private List<X509Certificate> trusted;

    public FabricTrustManager (KeyStore trustStore) throws GeneralSecurityException {
      this.trusted = new ArrayList<X509Certificate> (trustStore.size());
      
      // copy trustStore into this.trusted
      Enumeration<String> i = trustStore.aliases();
      while (i.hasMoreElements()) {
        String alias = i.nextElement();
        if (trustStore.isCertificateEntry(alias))
          this.trusted.add((X509Certificate) trustStore.getCertificate(alias));
      }
    }
    
    public void checkClientTrusted(X509Certificate[] chain, String authtype) throws CertificateException {
      // TODO Auto-generated method stub
      throw new NotImplementedException("checkClientTrusted");
    }

    public void checkServerTrusted(X509Certificate[] chain, String authtype) throws CertificateException {
      // TODO Auto-generated method stub
      throw new NotImplementedException("checkServerTrusted");
    }

    public X509Certificate[] getAcceptedIssuers() {
      return trusted.toArray(new X509Certificate[0]);
    }

  }

  //////////////////////////////////////////////////////////////////////////////
  // Fabric Key Manager                                                       //
  //////////////////////////////////////////////////////////////////////////////
  
  public static class FabricKeyManager implements X509KeyManager {
    private Map<String, KeyStore.PrivateKeyEntry> keys;
    
    public FabricKeyManager(KeyStore keys, char[] password) throws GeneralSecurityException {
      this.keys = new HashMap<String, KeyStore.PrivateKeyEntry>(keys.size());
      
      // copy keys into this.keys
      Enumeration<String> i = keys.aliases();
      while (i.hasMoreElements()) {
        String alias = i.nextElement();
        if (keys.isKeyEntry(alias)) {
          KeyStore.ProtectionParameter pass = new KeyStore.PasswordProtection(password);
          this.keys.put(alias, (KeyStore.PrivateKeyEntry) keys.getEntry(alias, pass));
        }
      }
    }

    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
      // TODO: key type
      // TODO: do virtual hosting here?
      return chooseAlias(issuers);
    }

    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
      // TODO: key type
      // TODO: do virtual hosting here?
      return chooseAlias(issuers);
    }

    public X509Certificate[] getCertificateChain(String alias) {
      return (X509Certificate[]) keys.get(alias).getCertificateChain(); 
    }

    public String[] getClientAliases(String keyType, Principal[] issuers) {
      // TODO: key type
      return getAliases(issuers).toArray(new String[0]);
    }

    public String[] getServerAliases(String keyType, Principal[] issuers) {
      return getAliases(issuers).toArray(new String[0]);
    }
    public PrivateKey getPrivateKey(String alias) {
      return keys.get(alias).getPrivateKey();
    }

    /**
     * return an alias with a certificate that is signed by an acceptable CA
     */
    private String chooseAlias(Principal[] issuers) {
      return getAliases(issuers).get(0);
    }
    
    /**
     * return a list of aliases such that the root cert is signed by an
     * acceptable CA
     */
    private List<String> getAliases(Principal[] issuers) {
      List<String> result = new ArrayList<String> (keys.size());
      for (Principal p : issuers) {
        for (Map.Entry<String, KeyStore.PrivateKeyEntry> e : keys.entrySet()) {
          X509Certificate cert = (X509Certificate) e.getValue().getCertificate();
          if (cert.getIssuerX500Principal().equals(p))
            result.add(e.getKey());
        }
      }
      return result;
    }
  }
}
