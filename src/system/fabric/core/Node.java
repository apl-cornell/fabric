package fabric.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.*;

import fabric.worker.Worker;
import fabric.worker.RemoteCore;
import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.common.SSLSocketFactoryTable;
import fabric.common.exceptions.InternalError;
import fabric.core.Options.CoreKeyRepositories;
import fabric.core.db.ObjectDB;

public class Node {

  public Options opts;

  /**
   * A map from core host-names to corresponding <code>SSLSocketFactory</code>s
   * and <code>TransactionManager</code>s.
   */
  protected Map<String, Core> cores;

  private final ConnectionHandler connectionHandler;

  protected class Core {
    public final String name;
    public final SSLSocketFactory factory;
    public final TransactionManager tm;
    public final SurrogateManager sm;
    public final ObjectDB os;
    public final PublicKey publicKey;
    public final PrivateKey privateKey;

    private Core(String name, SSLSocketFactory factory, ObjectDB os,
        TransactionManager tm, SurrogateManager sm, PublicKey publicKey,
        PrivateKey privateKey) {
      this.name = name;
      this.factory = factory;
      this.os = os;
      this.tm = tm;
      this.sm = sm;
      this.publicKey = publicKey;
      this.privateKey = privateKey;
    }
  }

  public Node(Options opts) {
    this.opts = opts;
    this.cores = new HashMap<String, Core>();
    this.connectionHandler = new ConnectionHandler(this);

    // Instantiate the cores with their object databases and SSL socket
    // factories.
    for (Map.Entry<String, CoreKeyRepositories> coreEntry : opts.cores
        .entrySet()) {
      String coreName = coreEntry.getKey();
      CoreKeyRepositories keyRepositories = coreEntry.getValue();

      ObjectDB objectDB = loadCore(coreName);
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

        PublicKey publicKey =
            (PublicKey) keyRepositories.trustStore.getKey(coreName,
                keyRepositories.password);
        PrivateKey privateKey =
            (PrivateKey) keyRepositories.keyStore.getKey(coreName,
                keyRepositories.password);
        addCore(coreName, sslSocketFactory, objectDB, publicKey, privateKey);
        SSLSocketFactoryTable.register(coreName, sslSocketFactory);
      } catch (KeyManagementException e) {
        throw new InternalError("Unable to initialise key manager factory.", e);
      } catch (UnrecoverableKeyException e1) {
        throw new InternalError("Unable to open key store.", e1);
      } catch (NoSuchAlgorithmException e1) {
        throw new InternalError(e1);
      } catch (KeyStoreException e1) {
        throw new InternalError("Unable to initialise key manager factory.", e1);
      } catch (DuplicateCoreException e) {
        // Should never happen.
      }
    }

    // Start the worker before instantiating the cores in case their object
    // databases need initialization. (The initialization code will be run on
    // the worker.)
    startWorker();

    // Ensure each core's object database has been properly initialized.
    for (Core core : cores.values()) {
      core.os.ensureInit();
    }

    System.out.println("Core started");
  }

  private ObjectDB loadCore(String coreName) {
    Properties p = new Properties(System.getProperties());

    try {
      InputStream in =
          Resources.readFile("etc", "core", coreName + ".properties");
      p.load(in);
      in.close();
    } catch (IOException e) {
    }

    try {
      String database = p.getProperty("fabric.core.db");
      final ObjectDB os =
          (ObjectDB) Class.forName(database).getConstructor(String.class)
              .newInstance(coreName);

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
      throw new InternalError("could not initialize core", exc);
    }
  }

  private void startWorker() {
    try {
      Map<String, RemoteCore> initCoreSet = new HashMap<String, RemoteCore>();
      for (String s : cores.keySet()) {
        initCoreSet.put(s, new InProcessCore(s, cores.get(s)));
      }

      Worker.initialize(opts.primaryCoreName, "fab://" + opts.primaryCoreName
          + "/" + ONumConstants.CORE_PRINCIPAL, initCoreSet);
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

  /**
   * Adds a new Core to this node.
   * 
   * @param coreName
   *          the host name for the core being added.
   * @param sslSocketFactory
   *          the <code>SSLSocketFactory</code> for initiating SSL sessions with
   *          the core.
   * @param tm
   *          a <code>TransactionManager</code> to use for the core being added.
   * @param publicKey
   *          The core's public key.
   * @param privateKey
   *          The core's private key, used for signing disseminated objects.
   */
  private void addCore(String coreName, SSLSocketFactory sslSocketFactory,
      ObjectDB os, PublicKey publicKey, PrivateKey privateKey)
      throws DuplicateCoreException {
    if (cores.containsKey(coreName)) throw new DuplicateCoreException();

    TransactionManager tm = new TransactionManager(os, privateKey);
    SurrogateManager sm = new SimpleSurrogateManager(tm);
    Core core =
        new Core(coreName, sslSocketFactory, os, tm, sm, publicKey, privateKey);
    cores.put(coreName, core);
  }

  /**
   * Returns the core corresponding to the given name.
   * 
   * @param name
   *          Name of core to retrieve.
   * @return The requested core, or null if it does not exist.
   */
  public Core getCore(String name) {
    return cores.get(name);
  }

  /**
   * Given the host name for an object core, returns its corresponding
   * <code>TransactionManager</code>.
   * 
   * @return null if there is no corresponding binding.
   */
  public TransactionManager getTransactionManager(String coreName) {
    Core c = cores.get(coreName);
    return c == null ? null : c.tm;
  }

  /**
   * Given the host name for an object core, returns its corresponding
   * <code>SSLSocketFactory</code>.
   */
  public SSLSocketFactory getSSLSocketFactory(String coreName) {
    Core c = cores.get(coreName);
    return c == null ? null : c.factory;
  }

  public SurrogateManager getSurrogateManager(String coreName) {
    Core c = cores.get(coreName);
    return c == null ? null : c.sm;
  }

  public PrivateKey getPrivateKey(String coreName) {
    Core c = cores.get(coreName);
    return c == null ? null : c.privateKey;
  }

  /**
   * The main execution body of a core node.
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
