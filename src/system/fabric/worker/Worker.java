package fabric.worker;

import static fabric.common.Logging.WORKER_LOGGER;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;

import javax.net.ssl.*;

import fabric.common.*;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.TerminationException;
import fabric.common.exceptions.UsageError;
import fabric.common.net.naming.DefaultNameService;
import fabric.common.net.naming.DefaultNameService.PortType;
import fabric.common.net.naming.NameService;
import fabric.dissemination.FetchManager;
import fabric.dissemination.Glob;
import fabric.dissemination.pastry.Cache;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.arrays.ObjectArray;
import fabric.lang.security.*;
import fabric.worker.remote.RemoteCallManager;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.transaction.Log;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.transaction.TransactionRegistry;

/**
 * This is the main interface to the Fabric API. Applications wishing to use
 * Fabric must first initialize it by calling one of the <code>initialize</code>
 * methods, and can then use the singleton Worker instance to access Stores and
 * Objects.
 */
public final class Worker {
  public final String name;

  public final int port;

  // A map from store hostnames to Store objects
  protected final Map<String, RemoteStore> stores;

  // A map from worker hostnames to RemoteWorker objects.
  private final Map<String, RemoteWorker> remoteWorkers;

  protected final LocalStore localStore;

  // A KeyStore holding the worker's key pair and any trusted CA certificates.
  protected final KeyStore keyStore;

  // A socket factory for creating TLS connections.
  public final SSLSocketFactory sslSocketFactory;

  // The principal on whose behalf this worker is running.
  protected final NodePrincipal principal;
  public final java.security.Principal javaPrincipal;

  // The timeout (in milliseconds) to use whilst attempting to connect to a
  // store node.
  public final int timeout;

  // The number of times to retry connecting to each store node.
  public final int retries;

  // The name service to use for finding stores.
  public final NameService storeNameService;

  // The name service to use for finding workers.
  public final NameService workerNameService;
  
  // The manager to use for fetching objects from stores.
  protected final FetchManager fetchManager;

  // The collection of dissemination caches used by this worker's dissemination
  // node.
  private final List<Cache> disseminationCaches;

  private final RemoteCallManager remoteCallManager;

  public static final Random RAND = new Random();
  private static final int DEFAULT_TIMEOUT = 2;

  // force Timing to load.
  @SuppressWarnings("unused")
  private static final Timing t = Timing.APP;

  /**
   * Initializes the Fabric <code>Worker</code>. When connecting to a store, the
   * worker will retry each store node the specified number of times before
   * failing. A negative retry-count is interpreted as an infinite retry-count.
   * 
   * @param keyStore
   *          The worker's key store. Should contain the worker's X509
   *          certificate and any trusted CA certificates.
   * @param passwd
   *          The password for unlocking the key store.
   * @param cacheSize
   *          The object cache size, in number of objects; must be positive.
   * @param maxConnections
   *          The maximum number of connections to store nodes to maintain; must
   *          be positive.
   * @param timeout
   *          The timeout value to be used in seconds; must be positive.
   * @param retries
   *          The number of times to retry before failing to connect.
   * @param useSSL
   *          Whether SSL encryption is desired. Used for debugging purposes.
   */
  private static Worker initialize(String name, int port, String principalURL,
      KeyStore keyStore, char[] passwd, int maxConnections, int timeout,
      int retries, boolean useSSL, String fetcher, Properties dissemConfig,
      Map<String, RemoteStore> initStoreSet) throws InternalError,
      UnrecoverableKeyException, IllegalStateException, UsageError {

    if (instance != null)
      throw new IllegalStateException(
          "The Fabric worker has already been initialized");

    WORKER_LOGGER.info("Initializing Fabric worker");
    WORKER_LOGGER.config("maximum connections: " + maxConnections);
    WORKER_LOGGER.config("timeout:             " + timeout);
    WORKER_LOGGER.config("retries:             " + retries);
    WORKER_LOGGER.config("use ssl:             " + useSSL);
    instance =
        new Worker(name, port, principalURL, keyStore, passwd, maxConnections,
            timeout, retries, useSSL, fetcher, dissemConfig, initStoreSet);

    Threading.getPool().execute(instance.remoteCallManager);
    instance.localStore.initialize();
    
    System.out.println("Worker started");

    return instance;
  }

  /**
   * The singleton Worker instance.
   */
  protected static Worker instance;

  @SuppressWarnings("unchecked")
  private Worker(String name, int port, String principalURL, KeyStore keyStore,
      char[] passwd, int maxConnections, int timeout, int retries,
      boolean useSSL, String fetcher, Properties dissemConfig,
      Map<String, RemoteStore> initStoreSet) throws InternalError,
      UnrecoverableKeyException, UsageError {
    // Sanitise input.
    if (timeout < 1) timeout = DEFAULT_TIMEOUT;

    this.name = name;
    this.port = port;
    this.timeout = 1000 * timeout;
    this.retries = retries;
    fabric.common.Options.DEBUG_NO_SSL = !useSSL;
    
    this.keyStore = keyStore;
    try {
      this.storeNameService  = new DefaultNameService(PortType.STORE);
      this.workerNameService = new DefaultNameService(PortType.WORKER);
    } catch(IOException e) {
      throw new InternalError("Unable to load the name service", e);
    }
    
    this.stores = new HashMap<String, RemoteStore>();
    if (initStoreSet != null) this.stores.putAll(initStoreSet);
    this.remoteWorkers = new HashMap<String, RemoteWorker>();
    this.localStore = new LocalStore();

    // Set up the SSL socket factory.
    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
      kmf.init(keyStore, passwd);

      TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
      tmf.init(keyStore);
      TrustManager[] tm = tmf.getTrustManagers();
      sslContext.init(kmf.getKeyManagers(), tm, null);
      this.sslSocketFactory = sslContext.getSocketFactory();
      SSLSocketFactoryTable.register(name, sslSocketFactory);

      this.javaPrincipal =
          ((X509KeyManager) kmf.getKeyManagers()[0]).getCertificateChain(name)[0]
              .getSubjectX500Principal();
    } catch (KeyManagementException e) {
      throw new InternalError("Unable to initialise key manager factory.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    } catch (KeyStoreException e) {
      throw new InternalError("Unable to initialise key manager factory.", e);
    }

    // Initialize the reference to the principal object.
    if (principalURL != null) {
      try {
        URI principalPath = new URI(principalURL);
        Store store = getStore(principalPath.getHost());
        long onum = Long.parseLong(principalPath.getPath().substring(1));
        this.principal = new NodePrincipal._Proxy(store, onum);
      } catch (URISyntaxException e) {
        throw new UsageError("Invalid principal URL specified.", 1);
      }
    } else {
      this.principal = null;
    }

    this.remoteCallManager = new RemoteCallManager(this);
    this.disseminationCaches = new ArrayList<Cache>(1);

    // Initialize the fetch manager. This MUST be the last thing done in the
    // constructor, or the fetch manager will not be properly shut down if
    // there's an error while initializing the worker.
    try {
      Constructor<FetchManager> fetchManagerConstructor =
          (Constructor<FetchManager>) Class.forName(fetcher).getConstructor(
              Worker.class, Properties.class);
      this.fetchManager =
          fetchManagerConstructor.newInstance(this, dissemConfig);
    } catch (Exception e) {
      throw new InternalError("Unable to load fetch manager", e);
    }
  }

  /**
   * Returns the Singleton Worker instance.
   * 
   * @return the Worker instance
   * @throws IllegalStateException
   *           if the Fabric worker is uninitialized
   */
  public static Worker getWorker() throws IllegalStateException {
    if (instance == null)
      throw new IllegalStateException(
          "The Fabric worker is uninitialized.  Call Worker.init(...)");
    return instance;
  }

  /**
   * Returns a <code>Store</code> object representing the given store.
   * 
   * @param name
   *          The store's host name.
   * @return The corresponding <code>Store</code> object.
   */
  public RemoteStore getStore(String name) {
    RemoteStore result = stores.get(name);
    if (result == null) {
      result = new RemoteStore(name);
      stores.put(name, result);
    }
    return result;
  }

  /**
   * @return a <code>RemoteWorker</code> object
   */
  public RemoteWorker getWorker(String name) {
    RemoteWorker result;
    synchronized (remoteWorkers) {
      result = remoteWorkers.get(name);
      if (result == null) {
        result = new RemoteWorker(name);
        remoteWorkers.put(name, result);
      }
    }
    return result;
  }

  public LocalStore getLocalStore() {
    return localStore;
  }

  /**
   * @return a RemoteWorker object representing the local worker.
   */
  public RemoteWorker getLocalWorker() {
    return getWorker(name);
  }

  /**
   * Returns the fetch manager.
   */
  public FetchManager fetchManager() {
    return fetchManager;
  }

  /**
   * Registers that a worker has a new dissemination cache.
   */
  public void registerDisseminationCache(Cache cache) {
    this.disseminationCaches.add(cache);
  }

  /**
   * Updates the dissemination caches with the given object glob.
   * 
   * @return true iff there was a cache entry for the given oid.
   */
  public boolean updateDissemCaches(RemoteStore store, long onum, Glob update) {
    boolean result = false;

    for (Cache cache : disseminationCaches) {
      result |= cache.updateEntry(store, onum, update);
    }

    return result;
  }

  /**
   * Updates the worker cache with the given object group.
   * 
   * @return true iff there was a cache entry for any object in the group.
   */
  public boolean updateCache(RemoteStore store, ObjectGroup group) {
    boolean result = false;
    for (SerializedObject obj : group.objects().values()) {
      result |= store.updateCache(obj);
    }

    return result;
  }

  /**
   * @return the Fabric notion of the worker principal.
   */
  public NodePrincipal getPrincipal() {
    return principal;
  }

  /**
   * @return the Java notion of the worker principal.
   */
  public java.security.Principal getJavaPrincipal() {
    return javaPrincipal;
  }

  /**
   * Clears out the worker cache (but leaves dissemination cache intact). To be
   * used for (performance) testing only.
   */
  public void clearCache() {
    for (RemoteStore store : stores.values()) {
      store.clearCache();
    }
  }

  /**
   * Called to shut down and clean up worker.
   */
  public void shutdown() {
    fetchManager.destroy();
  }

  public static void initialize(String name) throws UnrecoverableKeyException,
      KeyStoreException, NoSuchAlgorithmException, CertificateException,
      IllegalStateException, IOException, InternalError, UsageError {
    initialize(name, null, null);
  }

  public static void initialize(String name, String principalURL,
      Map<String, RemoteStore> initStoreSet) throws IOException,
      KeyStoreException, NoSuchAlgorithmException, CertificateException,
      UnrecoverableKeyException, IllegalStateException, InternalError,
      UsageError {
    
    ConfigProperties props = new ConfigProperties(name);
    
    if (principalURL == null)
      principalURL = props.workerPrincipal;

    KeyStore keyStore = KeyStore.getInstance("JKS");
    char[]   passwd   = props.password;
    String filename   = props.keystore;
    
    InputStream in = new FileInputStream(filename);
    keyStore.load(in, passwd);
    in.close();

    int port           = props.workerPort;
    int maxConnections = props.maxConnections;
    int timeout        = props.timeout;
    int retries        = props.retries;

    String fetcher = props.dissemClass;
    Properties dissemConfig = props.disseminationProperties;
    boolean useSSL = props.useSSL;

    initialize(name, port, principalURL, keyStore, passwd, maxConnections,
        timeout, retries, useSSL, fetcher, dissemConfig, initStoreSet);
  }

  // TODO: throws exception?
  public static void main(String[] args) throws Throwable {
    WORKER_LOGGER.info("Worker node");
    WORKER_LOGGER.config("Fabric version " + new Version());
    WORKER_LOGGER.info("");

    // Parse the command-line options.
    Worker worker = null;
    final Options opts;
    try {
      try {
        opts = new Options(args);
        initialize(opts.name);

        worker = getWorker();
        if (worker.getPrincipal() == null && opts.store == null
            && opts.app != null) {
          throw new UsageError(
              "No fabric.worker.principal specified in the worker "
                  + "configuration.  Either\nspecify one or create a principal "
                  + "with --make-principal.");
        }
      } catch (UsageError ue) {
        PrintStream out = ue.exitCode == 0 ? System.out : System.err;
        if (ue.getMessage() != null && ue.getMessage().length() > 0) {
          out.println(ue.getMessage());
          out.println();
        }

        Options.usage(out);
        throw new TerminationException(ue.exitCode);
      }

      // log the command line
      StringBuilder cmd = new StringBuilder("Command Line: Worker");
      for (String s : args) {
        cmd.append(" ");
        cmd.append(s);
      }
      WORKER_LOGGER.config(cmd.toString());

      if (opts.store != null) {
        // Create a principal object on the given store.
        final String name = worker.getJavaPrincipal().getName();
        final Store store = worker.getStore(opts.store);

        runInSubTransaction(new Code<Void>() {
          public Void run() {
            NodePrincipal principal =
                new NodePrincipal._Impl(store, null, name);
            principal.addDelegatesTo(store.getPrincipal());

            System.out.println("Worker principal created:");
            System.out.println("fab://" + opts.store + "/"
                + principal.$getOnum());
            return null;
          }
        });

        return;
      }

      if (opts.app == null) {
        // Act as a dissemination node.
        while (true) {
          try {
            Thread.sleep(Long.MAX_VALUE);
          } catch (InterruptedException e) {
          }
        }
      }

      // Attempt to read the principal object to ensure that it exists.
      final NodePrincipal workerPrincipal = worker.getPrincipal();
      runInSubTransaction(new Code<Void>() {
        public Void run() {
          WORKER_LOGGER.config("Worker principal is " + workerPrincipal);
          return null;
        }
      });

      // Run the requested application.
      Class<?> mainClass = Class.forName(opts.app[0] + "$_Impl");
      Method main =
          mainClass.getMethod("main", new Class[] { ObjectArray.class });
      final String[] newArgs = new String[opts.app.length - 1];
      for (int i = 0; i < newArgs.length; i++)
        newArgs[i] = opts.app[i + 1];

      final Store local = worker.getLocalStore();
      Object argsProxy = runInSubTransaction(new Code<Object>() {
        public Object run() {
          ConfPolicy conf =
              LabelUtil._Impl.readerPolicy(local, workerPrincipal,
                  workerPrincipal);
          IntegPolicy integ =
              LabelUtil._Impl.writerPolicy(local, workerPrincipal,
                  workerPrincipal);
          Label label = LabelUtil._Impl.toLabel(local, conf, integ);
          return WrappedJavaInlineable.$wrap(local, label, newArgs);
        }
      });

      MainThread.invoke(opts, main, argsProxy);
    } finally {
      if (worker != null)
        worker.shutdown();
    }
  }

  public void setStore(String name, RemoteStore store) {
    stores.put(name, store);
  }

  /**
   * Executes the given code from within a Fabric transaction. Should not be
   * called by generated code. This is here to abstract away the details of
   * starting and finishing transactions.
   * 
   * @param tid
   *          The parent transaction for the subtransaction that will be
   *          created.
   */
  public static <T> T runInTransaction(TransactionID tid, Code<T> code) {
    TransactionManager tm = TransactionManager.getInstance();
    Log oldLog = tm.getCurrentLog();

    Log log = TransactionRegistry.getOrCreateInnermostLog(tid);
    tm.associateAndSyncLog(log, tid);

    try {
      return runInSubTransaction(code);
    } finally {
      tm.associateLog(oldLog);
    }
  }

  /**
   * Executes the given code from within a Fabric subtransaction of the current
   * transaction. Should not be called by generated code. This is here to
   * abstract away the details of starting and finishing transactions.
   */
  public static <T> T runInSubTransaction(Code<T> code) {
    TransactionManager tm = TransactionManager.getInstance();

    boolean success = false;
    int backoff = 1;
    while (!success) {
      if (backoff > 32) {
        while (true) {
          try {
            Thread.sleep(backoff);
            break;
          } catch (InterruptedException e) {
          }
        }
      }

      if (backoff < 5000) backoff *= 2;

      success = true;
      tm.startTransaction();

      try {
        return code.run();
      } catch (RetryException e) {
        success = false;
        continue;
      } catch (TransactionRestartingException e) {
        success = false;
        
        TransactionID currentTid = tm.getCurrentTid();
        if (e.tid.isDescendantOf(currentTid))
          // Restart this transaction.
          continue;
        
        // Need to restart a parent transaction.
        if (currentTid.parent != null) throw e;
        
        throw new InternalError("Something is broken with transaction "
            + "management. Got a signal to restart a different transaction "
            + "than the one being managed.");
      } catch (Throwable e) {
        success = false;
        
        // Retry if the exception was a result of stale objects.
        if (tm.checkForStaleObjects()) continue;
        
        throw new AbortException(e);
      } finally {
        if (success) {
          try {
            tm.commitTransaction();
          } catch (AbortException e) {
            success = false;
          } catch (TransactionRestartingException e) {
            success = false;
            
            // This is the TID for the parent of the transaction we just tried
            // to commit.
            TransactionID currentTid = tm.getCurrentTid();
            if (currentTid == null || e.tid.isDescendantOf(currentTid)
                && !currentTid.equals(e.tid))
              // Restart the transaction just we tried to commit.
              continue;
            
            // Need to restart a parent transaction.
            throw e;
          }
        } else {
          tm.abortTransaction();
        }
      }
    }

    throw new InternalError();
  }

  public static interface Code<T> {
    T run() throws Throwable;
  }


}
