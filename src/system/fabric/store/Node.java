package fabric.store;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.*;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.*;

import fabric.worker.Worker;
import fabric.worker.RemoteStore;
import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.common.SSLSocketFactoryTable;
import fabric.common.exceptions.InternalError;
import fabric.store.Options.StoreKeyRepositories;
import fabric.store.db.ObjectDB;

public class Node {

  public Options opts;

  /**
   * A map from store host-names to corresponding <code>SSLSocketFactory</code>s
   * and <code>TransactionManager</code>s.
   */
  protected Map<String, Store> stores;

  private final ConnectionHandler connectionHandler;

  protected class Store {
    public final String name;
    public final SSLSocketFactory factory;
    public final TransactionManager tm;
    public final SurrogateManager sm;
    public final ObjectDB os;
    public final Certificate[] certificateChain;
    public final PublicKey publicKey;
    public final PrivateKey privateKey;

    private Store(String name, SSLSocketFactory factory, ObjectDB os,
        TransactionManager tm, SurrogateManager sm,
        Certificate[] certificateChain, PublicKey publicKey,
        PrivateKey privateKey) {
      this.name = name;
      this.factory = factory;
      this.os = os;
      this.tm = tm;
      this.sm = sm;
      this.certificateChain = certificateChain;
      this.publicKey = publicKey;
      this.privateKey = privateKey;
    }
  }

  public Node(Options opts) {
    this.opts = opts;
    this.stores = new HashMap<String, Store>();
    this.connectionHandler = new ConnectionHandler(this);

    // Instantiate the stores with their object databases and SSL socket
    // factories.
    for (Map.Entry<String, StoreKeyRepositories> storeEntry : opts.stores
        .entrySet()) {
      String storeName = storeEntry.getKey();
      StoreKeyRepositories keyRepositories = storeEntry.getValue();

      ObjectDB objectDB = loadStore(storeName);
      SSLSocketFactory sslSocketFactory;

      // Create the SSL socket factory.
      try {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyRepositories.keyStore, keyRepositories.password);
        TrustManager[] tm = null;
        if (keyRepositories.trustStore != null) {
          TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
          tmf.init(keyRepositories.trustStore);
          tm = tmf.getTrustManagers();
        }
        sslContext.init(kmf.getKeyManagers(), tm, null);
        sslSocketFactory = sslContext.getSocketFactory();

        Certificate[] certificateChain =
            keyRepositories.keyStore.getCertificateChain(storeName);
        PublicKey publicKey =
            (PublicKey) keyRepositories.trustStore.getKey(storeName,
                keyRepositories.password);
        PrivateKey privateKey =
            (PrivateKey) keyRepositories.keyStore.getKey(storeName,
                keyRepositories.password);
        addStore(storeName, sslSocketFactory, objectDB, certificateChain,
            publicKey, privateKey);
        SSLSocketFactoryTable.register(storeName, sslSocketFactory);
      } catch (KeyManagementException e) {
        throw new InternalError("Unable to initialise key manager factory.", e);
      } catch (UnrecoverableKeyException e1) {
        throw new InternalError("Unable to open key store.", e1);
      } catch (NoSuchAlgorithmException e1) {
        throw new InternalError(e1);
      } catch (KeyStoreException e1) {
        throw new InternalError("Unable to initialise key manager factory.", e1);
      } catch (DuplicateStoreException e) {
        // Should never happen.
      }
    }

    // Start the worker before instantiating the stores in case their object
    // databases need initialization. (The initialization code will be run on
    // the worker.)
    startWorker();

    // Ensure each store's object database has been properly initialized.
    for (Store store : stores.values()) {
      store.os.ensureInit();
    }

    System.out.println("Store started");
  }

  private ObjectDB loadStore(String storeName) {
    Properties p = new Properties(System.getProperties());

    try {
      InputStream in =
          Resources.readFile("etc", "store", storeName + ".properties");
      p.load(in);
      in.close();
    } catch (IOException e) {
    }

    try {
      String database = p.getProperty("fabric.store.db");
      final ObjectDB os =
          (ObjectDB) Class.forName(database).getConstructor(String.class)
              .newInstance(storeName);

      // register a hook to close the object database gracefully.
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          try {
            os.close();
          } catch (final IOException exc) {
          }
        }
      });

      return os;
    } catch (Exception exc) {
      throw new InternalError("could not initialize store", exc);
    }
  }

  private void startWorker() {
    try {
      Map<String, RemoteStore> initStoreSet =
          new HashMap<String, RemoteStore>();
      for (String s : stores.keySet()) {
        initStoreSet.put(s, new InProcessStore(s, stores.get(s)));
      }

      Worker.initialize(opts.primaryStoreName, "fab://" + opts.primaryStoreName
          + "/" + ONumConstants.STORE_PRINCIPAL, initStoreSet);
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

  /**
   * Adds a new Store to this node.
   * 
   * @param storeName
   *          the host name for the store being added.
   * @param sslSocketFactory
   *          the <code>SSLSocketFactory</code> for initiating SSL sessions with
   *          the store.
   * @param tm
   *          a <code>TransactionManager</code> to use for the store being
   *          added.
   * @param certificateChain
   *          The store's SSL certificate chain.
   * @param publicKey
   *          The store's public SSL key.
   * @param privateKey
   *          The store's private SSL key, used for signing disseminated
   *          objects.
   */
  private void addStore(String storeName, SSLSocketFactory sslSocketFactory,
      ObjectDB os, Certificate[] certificateChain, PublicKey publicKey,
      PrivateKey privateKey) throws DuplicateStoreException {
    if (stores.containsKey(storeName)) throw new DuplicateStoreException();

    TransactionManager tm = new TransactionManager(os, privateKey);
    SurrogateManager sm = new SimpleSurrogateManager(tm);
    Store store =
        new Store(storeName, sslSocketFactory, os, tm, sm, certificateChain,
            publicKey, privateKey);
    stores.put(storeName, store);
  }

  /**
   * Returns the store corresponding to the given name.
   * 
   * @param name
   *          Name of store to retrieve.
   * @return The requested store, or null if it does not exist.
   */
  public Store getStore(String name) {
    return stores.get(name);
  }

  /**
   * Given the host name for an object store, returns its corresponding
   * <code>TransactionManager</code>.
   * 
   * @return null if there is no corresponding binding.
   */
  public TransactionManager getTransactionManager(String storeName) {
    Store c = stores.get(storeName);
    return c == null ? null : c.tm;
  }

  /**
   * Given the host name for an object store, returns its corresponding
   * <code>SSLSocketFactory</code>.
   */
  public SSLSocketFactory getSSLSocketFactory(String storeName) {
    Store c = stores.get(storeName);
    return c == null ? null : c.factory;
  }

  public SurrogateManager getSurrogateManager(String storeName) {
    Store c = stores.get(storeName);
    return c == null ? null : c.sm;
  }

  public PrivateKey getPrivateKey(String storeName) {
    Store c = stores.get(storeName);
    return c == null ? null : c.privateKey;
  }

  /**
   * The main execution body of a store node.
   */
  public void start() throws IOException {
    // Start listening.
    ServerSocketChannel server = ServerSocketChannel.open();
    server.configureBlocking(true);
    server.socket().bind(new InetSocketAddress(opts.port));

    // The main server loop.
    while (true) {
      // Accept a connection and give it to the connection handler.
      SocketChannel connection = server.accept();

      // XXX not setting timeout
      // worker.setSoTimeout(opts.timeout * 1000);
      connectionHandler.handle(connection);
    }
  }
}
