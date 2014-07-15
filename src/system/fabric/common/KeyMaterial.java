package fabric.common;

import static fabric.common.Logging.KEY_LOGGER;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
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

/**
 * Convenience class for managing the keystores used by fabric nodes. Setter
 * methods automatically update the keystores on disk. This class is designed
 * for convenience, thus all methods throw an InternalError (rather than a
 * myriad of checked exceptions) if anything goes wrong.
 */
public class KeyMaterial {
  //
  // we maintain the following invariants:
  //
  // 1. if principalChain is not null, then principalChain[0].getPublicKey
  // equals publicKey
  //
  // 2. nameChain[0].getPublicKey equals publicKey
  //
  // 3. publicKey corresponds to privateKey
  //
  // 4. for each chain, there exists a point in time when it was valid with
  // respect to trustedCerts.
  //
  // 5. certs contains the contents of principalChain under the alias
  // principalAlias (".principal")
  //
  // 6. keys contains the contents of nameChain under the alias given by the
  // CN of the subject of nameChain[0]
  //
  // 7. keys contains the contents of trustedCerts under aliases given by
  // hashes of the subjects of those certs.
  //
  // 8. certs and keys are stored in certFileName and keyFileName (this may be
  // violated if the files change via another method)
  //

  private static final String principalAlias = ".principal";

  private final String prinFileName;
  private final String keyFileName;
  private final char[] password;

  private final KeyStore prin;
  private final KeyStore keys;

  private X509Certificate[] principalChain;
  private Set<TrustAnchor> trustedCerts;
  private X509Certificate[] nameChain;
  private PrivateKey privateKey;
  private PublicKey publicKey;

  public KeyMaterial(String name, String prinFileName, String keyFileName,
      char[] password) {
    this.prinFileName = prinFileName;
    this.keyFileName = keyFileName;
    this.password = password;

    this.keys = createKeyStore();
    this.prin = createKeyStore();

    // load key store or fail
    try {
      loadKeyStore(this.keys, this.keyFileName, this.password);
    } catch (FileNotFoundException e) {
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
      KEY_LOGGER.info("loading trusted certificates");
      this.trustedCerts = new HashSet<>(this.keys.size());
      Enumeration<String> i = this.keys.aliases();
      while (i.hasMoreElements()) {
        String alias = i.nextElement();
        if (this.keys.isCertificateEntry(alias)) {
          X509Certificate cert = (X509Certificate) keys.getCertificate(alias);
          trustedCerts.add(new TrustAnchor(cert, null));
          KEY_LOGGER.log(Level.INFO, "  - Certificate");
          KEY_LOGGER.log(Level.INFO, "      Subject: {0}", cert
              .getSubjectX500Principal().getName());
        }
      }
    } catch (KeyStoreException e) {
      throw new InternalError(
          "Failure while extracting trusted certs from keystore", e);
    }

    // extract public key, private key, and name chain from key store
    KeyEntry nameEntry = extractAndValidate(keys, name);
    if (null == nameEntry) throw new InternalError("no key entry for " + name);

    this.publicKey = nameEntry.publicKey;
    this.privateKey = nameEntry.privateKey;
    this.nameChain = nameEntry.chain;

    // extract principal chain from principal store
    KeyEntry prinEntry = extractAndValidate(prin, principalAlias);
    if (null == prinEntry)
      this.principalChain = null;
    else {
      this.principalChain = prinEntry.chain;

      // check that keys for name and principal match
      if (!prinEntry.privateKey.equals(this.privateKey))
        throw new InternalError("Principal's private key does not match "
            + name + "'s private key");

      if (!prinEntry.publicKey.equals(this.publicKey))
        throw new InternalError("Principal's public key does not match " + name
            + "'s public key");
    }
  }

  public NodePrincipal getPrincipal() {
    return getPrincipal(Worker.getWorker());
  }

  /**
   * This method is for use before the worker instance is completely
   * constructed.
   */
  public NodePrincipal getPrincipal(Worker worker) {
    if (null == this.principalChain) return null;

    Principal issuerDN = principalChain[0].getIssuerX500Principal();
    Principal subjectDN = principalChain[0].getSubjectX500Principal();

    String store = Crypto.getCN(issuerDN.getName());
    String subjectCN = Crypto.getCN(subjectDN.getName());
    long onum = SysUtil.getPrincipalOnum(subjectCN);

    // TODO Check that the principal is valid?

    return new NodePrincipal._Proxy(worker.getStore(store), onum);
  }

  public X509Certificate[] getPrincipalChain() {
    // TODO: should be unmodifiable
    return this.principalChain;
  }

  public void setPrincipalChain(X509Certificate[] chain) {
    if (this.principalChain != null)
      KEY_LOGGER.log(Level.WARNING,
          "overwriting existing principal chain in certstore file");

    KEY_LOGGER.info("setting principal chain");
    for (X509Certificate cert : chain) {
      KEY_LOGGER.fine("  - Certificate");
      KEY_LOGGER.fine("      Subject: " + cert.getSubjectX500Principal());
      KEY_LOGGER.fine("      Issuer:  " + cert.getIssuerX500Principal());
    }

    try {
      Crypto.validateCertificateChain(chain, getTrustedCerts());

      if (!chain[0].getPublicKey().equals(getPublicKey()))
        throw new KeyManagementException(
            "Certificate does not certify correct public key");

      this.prin.setKeyEntry(principalAlias, getPrivateKey(), password, chain);
      saveCertStore();

      this.principalChain = chain;
    } catch (Exception e) {
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

  // ////////////////////////////////////////////////////////////////////////////
  // private helper methods //
  // ////////////////////////////////////////////////////////////////////////////

  private static class KeyEntry {
    public X509Certificate[] chain;
    public PrivateKey privateKey;
    public PublicKey publicKey;
  }

  private KeyEntry extractAndValidate(KeyStore store, String alias) {
    try {
      KEY_LOGGER.log(Level.INFO, "loading material for {0} from keystore",
          alias);
      if (!store.containsAlias(alias)) return null;

      if (!store.isKeyEntry(alias))
        throw new InternalError("No key for " + alias + " in keystore");

      // java arrays are evil
      Certificate[] chain = store.getCertificateChain(alias);

      try {
        Crypto.validateCertificateChain(chain, this.trustedCerts);
      } catch (GeneralSecurityException e) {
        throw new InternalError("Invalid certificate chain for " + alias, e);
      }

      KeyEntry result = new KeyEntry();

      result.chain =
          Arrays.copyOf(chain, chain.length, X509Certificate[].class);
      result.privateKey = (PrivateKey) store.getKey(alias, this.password);
      result.publicKey = result.chain[0].getPublicKey();

      if (KEY_LOGGER.isLoggable(Level.FINE)) {
        KEY_LOGGER.log(Level.FINE, "certificate chain for {0}:", alias);
        for (X509Certificate cert : result.chain) {
          KEY_LOGGER.log(Level.FINE, "  - Certificate");
          KEY_LOGGER.log(Level.FINE, "      Subject: {0}", cert
              .getSubjectX500Principal().getName());
          KEY_LOGGER.log(Level.FINE, "      Issuer:  {0}", cert
              .getIssuerX500Principal().getName());
        }

        KEY_LOGGER.log(Level.FINE, "other key aliases:");
        Enumeration<String> i = store.aliases();
        while (i.hasMoreElements()) {
          String key = i.nextElement();
          if (store.isKeyEntry(key))
            KEY_LOGGER.log(Level.FINE, "  - Certificate: {0}", key);
        }

        KEY_LOGGER.log(Level.FINE, "public  key: {0}", result.publicKey);
        KEY_LOGGER.log(Level.FINE, "private key: {0}", result.privateKey);
      }

      return result;
    } catch (Exception e) {
      throw new InternalError("failed to load key for " + alias
          + " from keystore", e);
    }
  }

  private void saveCertStore() throws Exception {
    saveStore(prinFileName, prin, password);
  }

  private static void saveStore(String filename, KeyStore store, char[] password)
      throws Exception {
    File file = new File(filename);
    file.getParentFile().mkdirs();
    try (OutputStream out = new FileOutputStream(file)) {
      store.store(out, password);
    }
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

  private static void loadKeyStore(KeyStore store, String filename,
      char[] password) throws FileNotFoundException, InternalError {
    try (InputStream in = new FileInputStream(filename)) {
      store.load(in, password);
    } catch (FileNotFoundException e) {
      throw e;
    } catch (IOException e) {
      if (e.getCause() instanceof UnrecoverableKeyException)
        throw new InternalError("Unable to read \"" + filename
            + "\": invalid password.");
      throw new InternalError("Unable to read \"" + filename + "\".", e);
    } catch (Exception e) {
      throw new InternalError("Unable to read \"" + filename + "\".", e);
    }
  }
}
