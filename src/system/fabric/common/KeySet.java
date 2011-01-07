package fabric.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Set;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.exceptions.UsageError;
import fabric.lang.security.NodePrincipal;

/**
 * Convenience class for managing the keystores used by fabric nodes.  Setter
 * methods automatically update the keystores on disk.
 */
public class KeySet {
  private final String certFileName;
  private final String keyFileName;
  private final char[] password;

  private final KeyStore certs;
  private final KeyStore keys;
  
  public KeySet(String certFileName, String keyFileName, char[] password) {
    this.certFileName = certFileName;
    this.keyFileName  = keyFileName;
    this.password     = password;

    throw new NotImplementedException();
  }
  
  public NodePrincipal getPrincipal() {
    throw new NotImplementedException();
  }
  
  public X509Certificate[] getPrincipalChain() {
    throw new NotImplementedException();
  }
  
  public void setPrincipalChain(X509Certificate[] chain) {
    throw new NotImplementedException();
  }
  
  public Set<TrustAnchor> getTrustedCerts() {
    throw new NotImplementedException();
  }
  
  public void addTrustedCert(X509Certificate cert) {
    throw new NotImplementedException();
  }

  public PublicKey getPublicKey() {
    throw new NotImplementedException();
  }
  
  public PrivateKey getPrivateKey() {
    throw new NotImplementedException();
  }
  
  public String getName() {
    throw new NotImplementedException();
  }
  
  public X509Certificate[] getNameChain() {
    throw new NotImplementedException();
  }
  
  public void setNameChain(String name, X509Certificate[] chain) {
    throw new NotImplementedException();
  }

  
  /* from fabric.store.Store:
   
    FileInputStream in = null;
    try {
      keyStore = KeyStore.getInstance("JKS");
      in       = new FileInputStream(config.keystore);
      keyStore.load(in, password);
      in.close();
    } catch (KeyStoreException e) {
      throw new InternalError("Unable to open key store.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    } catch (CertificateException e) {
      throw new InternalError("Unable to open key store.", e);
    } catch (FileNotFoundException e) {
      throw new InternalError("File not found: " + e.getMessage());
    } catch (IOException e) {
      if (e.getCause() instanceof UnrecoverableKeyException)
        throw new InternalError("Unable to open key store: invalid password.");
      throw new InternalError("Unable to open key store.", e);
    }

    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
      kmf.init(keyStore, password);

      TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
      tmf.init(keyStore);
      TrustManager[] tm = tmf.getTrustManagers();
      sslContext.init(kmf.getKeyManagers(), tm, null);

      this.certificateChain =
          keyStore.getCertificateChain(name);
      Certificate publicKeyCert =
          keyStore.getCertificate(name);
      this.publicKey  = publicKeyCert.getPublicKey();
      this.privateKey = (PrivateKey) keyStore.getKey(name, password);
      
    } catch (KeyManagementException e) {
      throw new InternalError("Unable to initialise key manager factory.", e);
    } catch (UnrecoverableKeyException e1) {
      throw new InternalError("Unable to open key store.", e1);
    } catch (NoSuchAlgorithmException e1) {
      throw new InternalError(e1);
    } catch (KeyStoreException e1) {
      throw new InternalError("Unable to initialise key manager factory.", e1);
    }
    
    from fabric.worker.Worker:

    Certificate[] certChain = getStore(homeStore).makeWorkerPrincipal(workerKey);
    
    // Add the certificate to the key store.
    Key privateKey = keyStore.getKey(name, passwd);
    certStore.setKeyEntry(".principal", privateKey, passwd, certChain);
    

    

    KeyStore certStore = KeyStore.getInstance("JKS");
    try {
      InputStream in = new FileInputStream(certStoreFilename);
      certStore.load(in, passwd);
      in.close();
    } catch (IOException e) {
      certStore.load(null, passwd);
    }
    X509Certificate principalCert =
        (X509Certificate) certStore.getCertificate(".principal");
    if (principalCert != null) {
      Principal issuerDN = principalCert.getIssuerX500Principal();
      Principal subjectDN = principalCert.getSubjectX500Principal();

      String store = Crypto.getCN(issuerDN.getName());
      long onum = Long.parseLong(Crypto.getCN(subjectDN.getName()));
      // TODO Check that the principal is valid.
      return new NodePrincipal._Proxy(getStore(store), onum);
    }
    
    // Connect to the home store and have it create a new principal for us.
    X509Certificate nameCert = (X509Certificate) keyStore.getCertificate(name);
    PublicKey workerKey = nameCert.getPublicKey();
    Certificate[] certChain = getStore(homeStore).makeWorkerPrincipal(workerKey);
    
    // Add the certificate to the key store.
    Key privateKey = keyStore.getKey(name, passwd);
    certStore.setKeyEntry(".principal", privateKey, passwd, certChain);
    
    // Save the new key store.
    File file = new File(certStoreFilename);
    file.getParentFile().mkdirs();
    OutputStream out = new FileOutputStream(certStoreFilename);
    certStore.store(out, passwd);
    out.close();
    
    X509Certificate cert = (X509Certificate) certChain[0];
    Principal issuerDN = cert.getIssuerX500Principal();
    Principal subjectDN = cert.getSubjectX500Principal();
    
    String store = Crypto.getCN(issuerDN.getName());
    long onum = Long.parseLong(Crypto.getCN(subjectDN.getName()));
    return new NodePrincipal._Proxy(getStore(store), onum);

   */
}
