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

import javax.net.ssl.*;

import fabric.common.InternalError;
import fabric.common.Resources;
import fabric.core.Options.CoreKeyStores;

// TODO implement dissemination nodes
public class Node {
  public Options opts;

  /**
   * A map from core host-names to corresponding <code>SSLSocketFactory</code>s
   * and <code>TransactionManager</code>s.
   */
  protected Map<String, Core> cores;

  protected class Core {
    public SSLSocketFactory   factory;
    public TransactionManager tm;
    public SurrogateManager   sm;
    public ObjectStore        os;
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
    
    // Initialize the cores with their object stores and SSL socket factories.
    for (Map.Entry<String, CoreKeyStores> coreEntry : opts.cores
        .entrySet()) {
      String coreName = coreEntry.getKey();
      CoreKeyStores keyStores = coreEntry.getValue();

      ObjectStore core = loadCore(coreName);
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

        addCore(coreName, sslSocketFactory, core);
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

  /**
   * Adds a new Core to this node.
   * 
   * @param coreName
   *                the host name for the core being added.
   * @param sslSocketFactory
   *                the <code>SSLSocketFactory</code> for initiating SSL
   *                sessions with the core.
   * @param tm
   *                a <code>TransactionManager</code> to use for the core
   *                being added.
   */
  public void addCore(String coreName, SSLSocketFactory sslSocketFactory,
      ObjectStore os) throws DuplicateCoreException {
    if (cores.containsKey(coreName))
      throw new DuplicateCoreException();
    
    Core c    = new Core();
    c.os      = os;
    c.tm      = new TransactionManager(os);
    c.sm      = new SimpleSurrogateManager(os);
    c.factory = sslSocketFactory;
    cores.put(coreName, c);
  }

  /**
   * Given the host name for an object core, returns its corresponding
   * <code>TransactionManager</code>.
   * 
   * @return null if there is no corresponding binding.
   */
  public TransactionManager getTransactionManager(String coreName) {
    return cores.containsKey(coreName) ? cores.get(coreName).tm : null;
  }

  /**
   * Given the host name for an object core, returns its corresponding
   * <code>SSLSocketFactory</code>.
   */
  public SSLSocketFactory getSSLSocketFactory(String coreName) {
    return cores.get(coreName).factory;
  }

  public SurrogateManager getSurrogateManager(String coreName) {
    return cores.get(coreName).sm;
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
