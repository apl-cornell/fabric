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

import fabric.client.Client;
import fabric.client.RemoteCore;
import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.common.SSLSocketFactoryTable;
import fabric.common.exceptions.InternalError;
import fabric.core.Options.CoreKeyStores;
import fabric.core.store.ObjectStore;

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
    public final ObjectStore os;
    public final PublicKey publicKey;
    public final PrivateKey privateKey;

    private Core(String name, SSLSocketFactory factory, ObjectStore os,
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

  public ConsoleHandler console;

  public Node(Options opts) {
    this.opts = opts;
    this.cores = new HashMap<String, Core>();
    this.connectionHandler = new ConnectionHandler(this);
    this.console = new ConsoleHandler();

    // Instantiate the cores with their object stores and SSL socket factories.
    for (Map.Entry<String, CoreKeyStores> coreEntry : opts.cores.entrySet()) {
      String coreName = coreEntry.getKey();
      CoreKeyStores keyStores = coreEntry.getValue();

      ObjectStore store = loadCore(coreName);
      SSLSocketFactory sslSocketFactory;

      // Create the SSL socket factory.
      try {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyStores.keyStore, keyStores.password);
        TrustManager[] tm = null;
        if (keyStores.trustStore != null) {
          TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
          tmf.init(keyStores.trustStore);
          tm = tmf.getTrustManagers();
        }
        sslContext.init(kmf.getKeyManagers(), tm, null);
        sslSocketFactory = sslContext.getSocketFactory();

        PublicKey publicKey =
            (PublicKey) keyStores.trustStore.getKey(coreName,
                keyStores.password);
        PrivateKey privateKey =
            (PrivateKey) keyStores.keyStore
                .getKey(coreName, keyStores.password);
        addCore(coreName, sslSocketFactory, store, publicKey, privateKey);
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

    // Start the client before instantiating the cores in case their object
    // stores need initialization. (The initialization code will be run on the
    // client.)
    startClient();

    // Ensure each core's object store has been properly initialized.
    for (Core core : cores.values()) {
      core.os.ensureInit();
    }

    console.println("Core started");
  }

  private ObjectStore loadCore(String coreName) {
    Properties p = new Properties(System.getProperties());

    try {
      InputStream in =
          Resources.readFile("etc", "core", coreName + ".properties");
      p.load(in);
      in.close();
    } catch (IOException e) {
    }

    try {
      String store = p.getProperty("fabric.core.store");
      final ObjectStore os =
          (ObjectStore) Class.forName(store).getConstructor(String.class)
              .newInstance(coreName);

      // register a hook to close the object store gracefully.
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

  private void startClient() {
    try {
      Map<String, RemoteCore> initCoreSet = new HashMap<String, RemoteCore>();
      for (String s : cores.keySet()) {
        initCoreSet.put(s, new InProcessCore(s, cores.get(s)));
      }

      Client.initialize(opts.primaryCoreName, "fab://" + opts.primaryCoreName
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
      ObjectStore os, PublicKey publicKey, PrivateKey privateKey)
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
      // client.setSoTimeout(opts.timeout * 1000);
      connectionHandler.handle(connection);
    }
  }
}
