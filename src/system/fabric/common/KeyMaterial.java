package fabric.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.Certificate;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import javax.security.auth.x500.X500Principal;

import fabric.common.exceptions.InternalError;
import fabric.lang.security.NodePrincipal;
import fabric.worker.Worker;

import static fabric.common.Logging.MISC_LOGGER;

/**
 * Convenience class for managing the keystores used by fabric nodes.  Setter
 * methods automatically update the keystores on disk.  This class is designed
 * for convenience, thus all methods throw an InternalError (rather than a 
 * myriad of checked exceptions) if anything goes wrong.
 */
public class KeyMaterial {
  //
  // we maintain the following invariants:
  //
  //   1. if principalChain is not null, then principalChain[0].getPublicKey
  //      equals publicKey
  //  
  //   2. nameChain[0].getPublicKey equals publicKey
  //
  //   3. publicKey corresponds to privateKey
  //
  //   4. for each chain, there exists a point in time when it was valid with
  //      respect to trustedCerts.
  //
  //   5. certs contains the contents of principalChain under the alias
  //      principalAlias (".principal")
  //
  //   6. keys contains the contents of nameChain under the alias given by the
  //      CN of the subject of nameChain[0]
  //
  //   7. keys contains the contents of trustedCerts under aliases given by
  //      hashes of the subjects of those certs.
  //
  //   8. certs and keys are stored in certFileName and keyFileName (this may be
  //      violated if the files change via another method)
  //

  private static final String principalAlias = ".principal";
  
  private final String prinFileName;
  private final String keyFileName;
  private final char[] password;

  private final KeyStore prin;
  private final KeyStore keys;
  
  private X509Certificate[] principalChain;
  private Set<TrustAnchor>  trustedCerts;
  private X509Certificate[] nameChain;
  private PrivateKey        privateKey;
  private PublicKey         publicKey;
  
  public KeyMaterial(String name, String prinFileName, String keyFileName, char[] password) {
    this.prinFileName = prinFileName;
    this.keyFileName  = keyFileName;
    this.password     = password;

    this.keys = createKeyStore();
    this.prin = createKeyStore(); 

    // load key store or fail
    try {
      loadKeyStore(this.keys, this.keyFileName, this.password);
    } catch(FileNotFoundException e) {
      throw new InternalError("File not found: " + e.getMessage());
    }
    
    // load cert store or initialize an empty one
    try {
      loadKeyStore(this.prin, this.prinFileName, this.password);
    } catch (FileNotFoundException e) {
      loadEmptyKeyStore(this.prin);
    }

    // extract trusted certs from key store
    try {
      this.trustedCerts = new HashSet<TrustAnchor> (this.keys.size());
      Enumeration<String> i = this.keys.aliases();
      while (i.hasMoreElements()) {
        String alias = i.nextElement();
        if (this.keys.isCertificateEntry(alias)) {
          X509Certificate cert = (X509Certificate) keys.getCertificate(alias);
          trustedCerts.add(new TrustAnchor(cert, null));
        }
      }
    } catch (KeyStoreException e) {
      throw new InternalError("Failure while extracting trusted certs from keystore", e);
    }
    
    // extract public key, private key, and name chain from key store
    try {
      if (!this.keys.containsAlias(name) || !this.keys.isKeyEntry(name))
        throw new InternalError("No key for " + name + " in keystore");
      
      // java arrays are evil
      Certificate[] chain = this.keys.getCertificateChain(name);
      
      if (!Crypto.validateCertificateChain(chain, this.trustedCerts))
        throw new InternalError("Invalid certificate chain for " + name);
      
      this.nameChain  = Arrays.copyOf(chain, chain.length, X509Certificate[].class);
      this.privateKey = (PrivateKey) this.keys.getKey(name, password);
      this.publicKey  = this.nameChain[0].getPublicKey();
      
      // TODO: check that public key and private key match
    } catch (Exception e) {
      throw new InternalError("failed to load key for " + name + " from keystore", e);
    }
    
    // extract principal chain from principal store
    try {
      if (!this.prin.containsAlias(principalAlias))
        this.principalChain = null;
      else if (!this.prin.isKeyEntry(principalAlias))
        throw new InternalError("No key for principal entry in keystore");
      else {
        Certificate[] chain = this.prin.getCertificateChain(principalAlias);
        
        if (!Crypto.validateCertificateChain(chain, this.trustedCerts))
          throw new InternalError("Invalid certificate chain for principal");

        this.principalChain = Arrays.copyOf(chain, chain.length, X509Certificate[].class);

        PrivateKey prinPrivateKey = (PrivateKey) this.keys.getKey(principalAlias, password);
        if (!prinPrivateKey.equals(this.privateKey))
          throw new InternalError("Principal's private key does not match " + name + "'s private key");
        
        PublicKey  prinPublicKey  = this.principalChain[0].getPublicKey();
        if (!prinPublicKey.equals(this.publicKey))
          throw new InternalError("Principal's public key does not match " + name + "'s public key");
      }
    } catch(Exception e) {
      throw new InternalError("failed to load principal key from keystore", e);
    }

  }
  
  public NodePrincipal getPrincipal() {
    if (null == this.principalChain)
      return null;

    Principal issuerDN  = principalChain[0].getIssuerX500Principal();
    Principal subjectDN = principalChain[0].getSubjectX500Principal();

    String store =                Crypto.getCN(issuerDN.getName());
    long   onum  = Long.parseLong(Crypto.getCN(subjectDN.getName()));

    // TODO Check that the principal is valid?
    
    return new NodePrincipal._Proxy(Worker.getWorker().getStore(store), onum);
  }
  
  public X509Certificate[] getPrincipalChain() {
    // TODO: should be unmodifiable
    return this.principalChain;
  }
  
  public void setPrincipalChain(X509Certificate[] chain) {
    if (this.principalChain != null)
      MISC_LOGGER.log(Level.WARNING, "overwriting existing principal chain in certstore file");
    
    try {
      if (!Crypto.validateCertificateChain(chain, getTrustedCerts()))
        throw new CertificateException("Invalid Certificate Chain");
      
      if (!chain[0].getPublicKey().equals(getPublicKey()))
        throw new KeyManagementException("Certificate does not certify correct public key");
      
      this.prin.setKeyEntry(principalAlias, getPrivateKey(), password, chain);
      saveCertStore();
      
      this.principalChain = chain;
    } catch(Exception e) {
      throw new InternalError("Failed to update principal", e);
    }
  }
  
  public Set<TrustAnchor> getTrustedCerts() {
    return Collections.unmodifiableSet(this.trustedCerts);
  }
  
  public PublicKey getPublicKey() {
    return this.publicKey;
  }
  
  public PrivateKey getPrivateKey() {
    return this.privateKey;
  }
  
  public String getName() {
    X500Principal namePrincipal = nameChain[0].getSubjectX500Principal();
    return Crypto.getCN(namePrincipal.getName());
  }
  
  public X509Certificate[] getNameChain() {
    // TODO: make immutable
    return this.nameChain;
  }

  //////////////////////////////////////////////////////////////////////////////
  // private helper methods                                                   //
  //////////////////////////////////////////////////////////////////////////////
  
  private void saveCertStore() throws Exception {
    saveStore(prinFileName, prin, password);
  }
  
  private static void saveStore(String filename, KeyStore store, char[] password) throws Exception {
    File file = new File(filename);
    file.getParentFile().mkdirs();
    OutputStream out = new FileOutputStream(file);
    store.store(out, password);
    out.close();
  }
  
  private static KeyStore createKeyStore() {
    try {
      return KeyStore.getInstance("JKS");
    } catch (KeyStoreException e) {
      throw new InternalError("Unable to create key store.", e);
    }
  }
  
  private static void loadEmptyKeyStore(KeyStore store) {
    try {
      store.load(null);
    } catch (Exception e) {
      throw new InternalError("Failed to initialize empty key store", e);
    }
  }
  
  private static void loadKeyStore(KeyStore store, String filename, char[] password) throws FileNotFoundException, InternalError {
    try {
      InputStream in = new FileInputStream(filename); 
      store.load(in, password);
      in.close();
    } catch (FileNotFoundException e) {
      throw e;
    } catch (IOException e) {
      if (e.getCause() instanceof UnrecoverableKeyException)
        throw new InternalError("Unable to read \"" + filename + "\": invalid password.");
      throw new InternalError("Unable to read \"" + filename + "\".", e);
    } catch (Exception e) {
      throw new InternalError("Unable to read \"" + filename + "\".", e);
    }
  }
}
