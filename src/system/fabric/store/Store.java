/**
 * 
 */
package fabric.store;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.logging.Level;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import fabric.common.ConfigProperties;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.SubServerSocket;
import fabric.common.net.SubSocket;
import fabric.store.db.ObjectDB;

import static fabric.common.Logging.STORE_LOGGER;

class Store implements Runnable {
  public final Node               node;
  public final String             name;
  public final TransactionManager tm;
  public final SurrogateManager   sm;
  public final ObjectDB           os;
  public final Certificate[]      certificateChain;
  public final PublicKey          publicKey;
  public final PrivateKey         privateKey;
  public final ConfigProperties   config;

  Store(Node node, String name) {
    //
    // read properties file
    //
    
    this.config = new ConfigProperties(name);
    
    //
    // Set up SSL
    //

    char[] password = config.password;
    
    KeyStore keyStore;
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
    
    this.node = node;
    this.name = name;
    this.os   = loadStore();
    this.tm   = new TransactionManager(this.os, this.privateKey);
    this.sm   = new SimpleSurrogateManager(tm);
  }

  //////////////////////////////////////////////////////////////////////////////
  // lifecycle                                                                //
  //////////////////////////////////////////////////////////////////////////////

  
  public void initialize() {
    // Ensure each store's object database has been properly initialized.
    os.ensureInit();
  }
  
  /**
   * The main execution body of a store node.
   */
  public void run() {
    SubServerSocket server = node.getServerSocketFactory().createServerSocket();

    try {
      // Start listening.
      server.bind(name);

      // The main server loop.
      while (true) {
        // Accept a connection and handle it.
        SubSocket connection = server.accept();

        // XXX not setting timeout
        // worker.setSoTimeout(opts.timeout * 1000);
        throw new NotImplementedException();
        // connectionHandler.handle(connection);
      }
    } catch (final IOException e) {
      STORE_LOGGER.log(Level.WARNING, "Store " + name + " suffered communications failure, shutting down", e);
    }
  }

  public void shutdown() {
    try { os.close(); } catch(final IOException exc) { /* do nothing */ }
  }
  
  private ObjectDB loadStore() {
    try {
      // construct ObjectDB with class specified by properties file
      final Class<?>       osClass = Class.forName(config.backendClass);
      final Constructor<?> osCons  = osClass.getConstructor(String.class);
      final ObjectDB       os      = (ObjectDB) osCons.newInstance(config.name);

      return os;
    } catch (Exception exc) {
      throw new InternalError("could not initialize store", exc);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // message handlers                                                         //
  //////////////////////////////////////////////////////////////////////////////

  
  
}