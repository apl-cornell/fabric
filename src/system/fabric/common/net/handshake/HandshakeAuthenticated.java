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
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

import fabric.common.Crypto;
import fabric.common.exceptions.NotImplementedException;
import fabric.lang.security.NodePrincipal;

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
  //    2a. A -> B: certificate chain                                         //
  //       [0]:    principal signed by store                                  //
  //       [1...]: supporting certificates                                    //
  //                                                                          //
  //    2b. B -> A: certificate chain                                         //
  //       [0]:    "B"                                                        //
  //       [1]:    principal signed by store                                  //
  //       [2...]: supporting certificates                                    //
  //                                                                          //
  //////////////////////////////////////////////////////////////////////////////
  
  private final KeyStore                              keystore;
  private final Map<String, KeyStore.PrivateKeyEntry> keysByAlias;
  private final Map<X500Principal, X509Certificate>   trustedBySubject;
  
  private HandshakeAuthenticated(KeyStore keystore, char[] password) throws GeneralSecurityException {
    this.keystore         = keystore;
    this.keysByAlias      = new HashMap<String, KeyStore.PrivateKeyEntry> (keystore.size());
    this.trustedBySubject = new HashMap<X500Principal, X509Certificate>   (keystore.size());
    
    // sort trustStore into this.trusted and this.keys
    Enumeration<String> i = keystore.aliases();
    while (i.hasMoreElements()) {
      String alias = i.nextElement();
      
      // trusted certificates
      if (keystore.isCertificateEntry(alias)) {
        X509Certificate cert = (X509Certificate) keystore.getCertificate(alias); 
        this.trustedBySubject.put(cert.getSubjectX500Principal(), cert);
      }
      
      // keys
      if (keystore.isKeyEntry(alias)) {
        KeyStore.ProtectionParameter pass = new KeyStore.PasswordProtection(password);
        this.keysByAlias.put(alias, (KeyStore.PrivateKeyEntry) keystore.getEntry(alias, pass));
      }
    }
  }
  
  public ShakenSocket initiate(String name, Socket s) throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    out.writeUTF(name);
    
    SSLSocket sock = createSocket(name, s);
    sock.setUseClientMode(true);
    sock.startHandshake();

    NodePrincipal principal = getFabricPrincipal(sock); 
    
    return new ShakenSocket(name, principal, sock);
  }

  public ShakenSocket receive(Socket s) throws IOException {
    DataInputStream in = new DataInputStream(s.getInputStream());
    String name = in.readUTF();
    
    SSLSocket sock = createSocket(name, s);
    sock.setUseClientMode(false);
    sock.startHandshake();
    
    NodePrincipal principal = getFabricPrincipal(sock);
    return new ShakenSocket(name, principal, sock);
    
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
  // Fabric Trust Manager                                                     //
  //////////////////////////////////////////////////////////////////////////////
  
  /**
   * <p>The FabricTrustManager is responsible for validating certificate chains
   * provided by remote connections.  It checks that client chains are of the
   * following form:
   * 
   * <ol start="0">
   *    <li>cert binding store/onum, signed by store</li>
   *    <li>other certificates certifying store</li>
   *    </ol>
   *
   * and that server chains are of the following form:
   * <ol start="0">
   *    <li>cert binding name</li>
   *    <li>cert binding store/onum, signed by store</li>
   *    <li>other certificates certifying name and store</li>
   *    </ol>
   * </p>
   * 
   * <p>In either case, the "other certs" should contain a PKIX path mapping to
   * a trusted CA.  We do not require that the chain <emph>is</emph> a valid PKIX chain,
   * because SSL apparently does not allow for negotiation of trusted CAs.
   * Instead, we only require that the chain <emph>contains</emph> a valid PKIX
   * path.  This class extracts that path, and validates it using java's PKIX
   * facilities.</p>
   * 
   * <p>Note that checking may diverge if the certificate chain is cyclic.</p>  
   */
  public class FabricTrustManager implements X509TrustManager {
    private final String name;
    
    /** @param name the expected name of the remote endpoint. */
    public FabricTrustManager (String name) {
      this.name = name;
    }
    
    public void checkClientTrusted(X509Certificate[] certs, String authtype) throws CertificateException {
      if (certs.length < 1)
        throw new CertificateException ("No certificate chain presented.");
      
      // construct the chain from the provided certs
      Map<X500Principal, List<X509Certificate>> groupedCerts = groupCertificatesBySubject(certs);
      List<X509Certificate> chain = buildChain(certs[0], groupedCerts);
      
      if (null == chain)
        throw new CertificateException ("Failed to construct trust chain");
      
      // validate the resulting chain
      Crypto.validateCertificateChain(chain.toArray(new X509Certificate[0]), keystore);
    }

    public void checkServerTrusted(X509Certificate[] certs, String authtype) throws CertificateException {
      if (certs.length < 2)
        throw new CertificateException ("Insufficient certificate chain presented");
      
      // construct the chains from the provided certs
      Map<X500Principal, List<X509Certificate>> groupedCerts = groupCertificatesBySubject(certs);
      List<X509Certificate> nameChain = buildChain(certs[0], groupedCerts);
      List<X509Certificate> oidChain  = buildChain(certs[1], groupedCerts);
      
      // validate the resulting chains
      Crypto.validateCertificateChain(nameChain.toArray(new X509Certificate[0]), keystore);
      Crypto.validateCertificateChain( oidChain.toArray(new X509Certificate[0]), keystore);
    }

    public X509Certificate[] getAcceptedIssuers() {
      return trustedBySubject.values().toArray(new X509Certificate[0]);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Fabric Key Manager                                                       //
  //////////////////////////////////////////////////////////////////////////////
  
  public class FabricKeyManager implements X509KeyManager {
    private final String name;
    
    public FabricKeyManager(String name) {
      this.name = name;
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
      return (X509Certificate[]) keysByAlias.get(alias).getCertificateChain(); 
    }

    public String[] getClientAliases(String keyType, Principal[] issuers) {
      // TODO: key type
      return getAliases(issuers).toArray(new String[0]);
    }

    public String[] getServerAliases(String keyType, Principal[] issuers) {
      return getAliases(issuers).toArray(new String[0]);
    }
    public PrivateKey getPrivateKey(String alias) {
      return keysByAlias.get(alias).getPrivateKey();
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
      List<String> result = new ArrayList<String> (keysByAlias.size());
      for (Principal p : issuers) {
        for (Map.Entry<String, KeyStore.PrivateKeyEntry> e : keysByAlias.entrySet()) {
          X509Certificate cert = (X509Certificate) e.getValue().getCertificate();
          if (cert.getIssuerX500Principal().equals(p))
            result.add(e.getKey());
        }
      }
      return result;
    }
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // private helper methods                                                   //
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Group an array of certificates into a map indexed by subject principal.
   * Only valid certificates will be returned.
   */
  private static Map<X500Principal, List<X509Certificate>> groupCertificatesBySubject(X509Certificate[] certs) {
    Map<X500Principal, List<X509Certificate>> result = new HashMap<X500Principal, List<X509Certificate>> (certs.length);
    
    for (X509Certificate cert : certs) {
      try { cert.checkValidity(); } catch (CertificateException e) {continue;}
      
      List<X509Certificate> entry = result.get(cert.getSubjectX500Principal());
      if (null == entry) {
        entry = new ArrayList<X509Certificate> ();
        result.put(cert.getSubjectX500Principal(), entry);
      }
      
      entry.add(cert);
    }
    
    return result;
  }
  
  /**
   * Attempts to build a certificate chain from a root certificate to a trusted CA. 
   * @param root
   *          the certificate we wish to validate
   * @param certs
   *          the collection of auxiliary certificates, indexed by subject
   * @return
   *          a list of certificates with a the root first and a trusted CA
   *          last, or null of no such chain could be constructed.
   */
  private List<X509Certificate> buildChain(X509Certificate root, Map<X500Principal, List<X509Certificate>> certs) {

    X509Certificate caCert = trustedBySubject.get(root.getIssuerX500Principal());
    if (caCert != null) {
      // we have a trusted certificate for the issuer, so we can build a chain
      ArrayList<X509Certificate> chain = new ArrayList<X509Certificate>();
      chain.add(root);
      chain.add(caCert);
      return chain;
    }
    else
    {
      // recursively try to build a chain to a trusted root.
      List<X509Certificate> candidates = certs.get(root.getIssuerX500Principal());
      if (null == candidates)
        return null;
      
      for (X509Certificate candidate : candidates) {
        List<X509Certificate> chain = buildChain(candidate, certs);
        if (chain != null) {
          chain.add(0, root);
          return chain;
        }
      }
      
      return null;
    }
  }
  
  /**
   * Creates an SSLSocket for the given name over the given socket.
   */
  private SSLSocket createSocket(String name, Socket s) throws IOException {
    try {
      SSLContext context = SSLContext.getInstance("SSL");
      KeyManager   kms[] = new KeyManager[]   {new FabricKeyManager(name)};
      TrustManager tms[] = new TrustManager[] {new FabricTrustManager(name)};
      context.init(kms, tms, null);
      return (SSLSocket) context.getSocketFactory().createSocket(s, name, s.getPort(), true);
    } catch (final GeneralSecurityException e) {
      throw new IOException("Failed to initialize SSL connection", e);
    }
  }
  
  private static NodePrincipal getFabricPrincipal(SSLSocket socket) {
    throw new NotImplementedException();
  }
}
