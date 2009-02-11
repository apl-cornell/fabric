package fabric.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import fabric.client.Client;
import fabric.common.InternalError;
import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.core.Options.CoreKeyStores;
import fabric.core.store.ObjectStore;

public class Node {
  
  public Options opts;

  /**
   * A map from core host-names to corresponding <code>SSLSocketFactory</code>s
   * and <code>TransactionManager</code>s.
   */
  protected Map<String, Core> cores;

  protected class Core {
    public final SSLSocketFactory   factory;
    public final TransactionManager tm;
    public final SurrogateManager   sm;
    public final ObjectStore        os;
    public final PublicKey          publicKey;
    public final PrivateKey         privateKey;

    private Core(SSLSocketFactory factory, ObjectStore os,
        TransactionManager tm, SurrogateManager sm, PublicKey publicKey,
        PrivateKey privateKey) {
      this.factory = factory;
      this.os = os;
      this.tm = tm;
      this.sm = sm;
      this.publicKey = publicKey;
      this.privateKey = privateKey;
    }
  }
  
  /**
   * The number of additional connections the node can handle.
   */
  private int availConns;

  /**
   * The thread pool.
   */
  private Stack<Worker> pool;
  
  public ConsoleHandler console;

  public Node(Options opts) {
    this.opts = opts;
    this.cores = new HashMap<String, Core>();
    this.console = new ConsoleHandler();
    
    // Instantiate the cores with their object stores and SSL socket factories.
    for (Map.Entry<String, CoreKeyStores> coreEntry : opts.cores
        .entrySet()) {
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
      InputStream in = Resources.readFile("etc", "core", 
          coreName + ".properties");
      p.load(in);
      in.close();
    } catch (IOException e) {}

    try {
      String store = p.getProperty("fabric.core.store");
      final ObjectStore os = (ObjectStore) Class.forName(store).getConstructor(
          String.class).newInstance(coreName);
      
      // register a hook to close the object store gracefully.
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          try {
            os.close();
          } catch (final IOException exc) {}
        }
      });

      return os;
    } catch (Exception exc) {
      throw new InternalError("could not initialize core", exc);
    }
  }
  
  private void startClient() {
    try {
      Client.initialize(opts.primaryCoreName, "fab://" + opts.primaryCoreName
          + "/" + ONumConstants.CORE_PRINCIPAL, new Client.PostInitExec() {
        public void run(Client client) {
          for (String s : cores.keySet()) {
            client.setCore(s, new InProcessCore(s, cores.get(s)));
          }
        }
      });
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
    if (cores.containsKey(coreName))
      throw new DuplicateCoreException();
    
    TransactionManager tm = new TransactionManager(os);
    SurrogateManager sm = new SimpleSurrogateManager(tm);
    Core c = new Core(sslSocketFactory, os, tm, sm, publicKey, privateKey);
    cores.put(coreName, c);
  }
  
  /**
   * Returns the core corresponding to the given name.
   * 
   * @param name Name of core to retrieve.
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
    // Set up the thread pool.
    availConns = opts.maxConnect;
    pool = new Stack<Worker>();
    for (int i = 0; i < opts.threadPool; i++) {
      pool.push(new Worker(this));
    }

    // Start listening.
    ServerSocket server = new ServerSocket(opts.port);

    // The main server loop.
    while (true) {
      // If we can't handle any more connections, block until an existing
      // connection is done.
      synchronized (this) {
        while (availConns == 0) {
          try {
            this.wait();
          } catch (InterruptedException e) {
            continue;
          }
        }
      }

      // Accept a connection and give it to a worker thread.
      Socket client = server.accept();
      Worker worker = getWorker();

      // XXX not setting timeout
      // client.setSoTimeout(opts.timeout * 1000);
      worker.handle(client);
    }
  }

  /**
   * Returns an available <code>Worker</code> object.
   */
  private synchronized Worker getWorker() {
    availConns--;

    if (pool.isEmpty()) return new Worker(this);
    return pool.pop();
  }

  /**
   * This is invoked by a <code>Worker</code> to notify this node that it is
   * finished serving a client.
   */
  protected synchronized void workerDone(Worker worker) {
    availConns++;

    // Clean up the worker and add it to the thread pool if there's room.
    if (pool.size() == opts.threadPool) return;
    worker.cleanup();
    pool.push(worker);
  }
}
