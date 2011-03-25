package fabric.worker;

import static fabric.common.Logging.WORKER_LOGGER;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import fabric.common.ConfigProperties;
import fabric.common.KeyMaterial;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.SysUtil;
import fabric.common.Threading;
import fabric.common.Timing;
import fabric.common.TransactionID;
import fabric.common.Version;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.TerminationException;
import fabric.common.exceptions.UsageError;
import fabric.common.net.SubServerSocketFactory;
import fabric.common.net.SubSocketFactory;
import fabric.common.net.handshake.HandshakeAuthenticated;
import fabric.common.net.handshake.HandshakeBogus;
import fabric.common.net.handshake.HandshakeComposite;
import fabric.common.net.handshake.HandshakeUnauthenticated;
import fabric.common.net.handshake.Protocol;
import fabric.common.net.naming.DefaultNameService;
import fabric.common.net.naming.DefaultNameService.PortType;
import fabric.common.net.naming.NameService;
import fabric.dissemination.FetchManager;
import fabric.dissemination.Glob;
import fabric.dissemination.pastry.Cache;
import fabric.lang.FabricClassLoader;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.arrays.ObjectArray;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.IntegPolicy;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.NodePrincipal;
import fabric.worker.remote.RemoteCallManager;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.shell.WorkerShell;
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

  public final ConfigProperties config;

  // The path for Fabric signatures
  public String sigcp;

  // The path for FabIL signatures
  public String filsigcp;

  // The directory for dynamically compiled mobile code
  public String code_cache;

  // A map from store hostnames to Store objects
  protected final Map<String, RemoteStore> stores;

  // A map from worker hostnames to RemoteWorker objects.
  private final Map<String, RemoteWorker> remoteWorkers;

  protected final LocalStore localStore;

  // A subsocket factory for unauthenticated connections to stores.
  public final SubSocketFactory unauthToStore;
  
  // A subsocket factory for authenticated connections to stores.
  public final SubSocketFactory authToStore;
  
  // A subsocket factory for authenticated connections to workers
  public final SubSocketFactory authToWorker;
  
  // The subserversocket factory
  public final SubServerSocketFactory authFromAll;
  
  // The manager to use for fetching objects from stores.
  protected final FetchManager fetchManager;
  
  protected final NodePrincipal principal;

  // The collection of dissemination caches used by this worker's dissemination
  // node.
  private final List<Cache> disseminationCaches;

  private final RemoteCallManager remoteCallManager;

  public static final Random RAND = new Random();

  // force Timing to load.
  @SuppressWarnings("unused")
  private static final Timing t = Timing.APP;


  /**
   * Initializes the Fabric <code>Worker</code>. When connecting to a store, the
   * worker will retry each store node the specified number of times before
   * failing. A negative retry-count is interpreted as an infinite retry-count.
   * 
   * @param principalOnum
   *          Gives the onum of the worker's principal if this worker is being
   *          initialized for a store; otherwise, this should be null.
   * @param keyStoreFilename
   *          The name of a file containing the worker's key store. This key
   *          store should contain X509 certificates for the worker's name and
   *          any trusted CA certificates.
   * @param certStoreFilename
   *          The name of a file containing a key store. This key store should
   *          contain an X509 certificate for the worker's principal object.
   * @param passwd
   *          The password for unlocking the key stores.
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
  private static Worker initialize(ConfigProperties config,
      Long principalOnum, Map<String, RemoteStore> initStoreSet) throws InternalError,
      IllegalStateException, UsageError, IOException, GeneralSecurityException {

    if (instance != null)
      throw new IllegalStateException(
          "The Fabric worker has already been initialized");

    WORKER_LOGGER.info("Initializing Fabric worker");
    WORKER_LOGGER.config("maximum connections: " + config.maxConnections);
    WORKER_LOGGER.config("timeout:             " + config.timeout);
    WORKER_LOGGER.config("retries:             " + config.retries);
    WORKER_LOGGER.config("use ssl:             " + config.useSSL);
    instance = new Worker(config, principalOnum, initStoreSet);

    Threading.getPool().execute(instance.remoteCallManager);
    instance.localStore.initialize();
    
    System.out.println("Worker started");

    return instance;
  }

  /**
   * The singleton Worker instance.
   */
  protected static Worker instance;

  public static boolean isInitialized() {
    return instance != null;
  }
  
  @SuppressWarnings("unchecked")
  private Worker(ConfigProperties config, Long principalOnum, Map<String, RemoteStore> initStoreSet) throws InternalError, UsageError,
      IOException, GeneralSecurityException {
    // Sanitise input.
    
    this.config = config;
    
    fabric.common.Options.DEBUG_NO_SSL = !config.useSSL;
    
    this.stores = new HashMap<String, RemoteStore>();
    if (initStoreSet != null) this.stores.putAll(initStoreSet);
    this.remoteWorkers = new HashMap<String, RemoteWorker>();
    this.localStore = new LocalStore();

    NameService storeNameService  = new DefaultNameService(PortType.STORE);
    NameService workerNameService = new DefaultNameService(PortType.WORKER);

    // initialize the various socket factories
    Protocol authProt;
    if (config.useSSL)
      authProt = new HandshakeAuthenticated(config.getKeyMaterial());
    else
      authProt = new HandshakeBogus();
    
    Protocol authenticateProtocol = new HandshakeComposite(authProt);
    Protocol nonAuthenticateProtocol = new HandshakeComposite(new HandshakeUnauthenticated());

    this.authToStore   = new SubSocketFactory(authenticateProtocol, storeNameService);
    this.authToWorker  = new SubSocketFactory(authenticateProtocol, workerNameService);
    this.unauthToStore = new SubSocketFactory(nonAuthenticateProtocol, storeNameService);
    this.authFromAll   = new SubServerSocketFactory(authenticateProtocol, workerNameService);
    

    this.remoteCallManager = new RemoteCallManager(this);
    this.disseminationCaches = new ArrayList<Cache>(1);

    // Initialize the fetch manager.
    try {
      Constructor<FetchManager> fetchManagerConstructor =
          (Constructor<FetchManager>) Class.forName(config.dissemClass).getConstructor(
              Worker.class, Properties.class);
      this.fetchManager =
          fetchManagerConstructor.newInstance(this, config.disseminationProperties);
    } catch (Exception e) {
      throw new InternalError("Unable to load fetch manager", e);
    }
    
    this.principal = initializePrincipal(config.homeStore, principalOnum, this.config.getKeyMaterial());
  }
  
  /**
   *  Initialize the reference to the principal object.
   */
  private NodePrincipal initializePrincipal(String homeStore,
      Long principalOnum, KeyMaterial keys)
      throws UsageError {
    if (principalOnum != null) {
      // First, handle the case where we're initializing a store's worker.
      return new NodePrincipal._Proxy(getStore(config.name), principalOnum);
    }

    // Next, look in the key set for a principal certificate.
    NodePrincipal p = keys.getPrincipal(this);
    if (p != null)
      return p;
    
    // Still no principal? Create one.
    if (homeStore == null) {
      throw new UsageError(
          "No fabric.worker.homeStore specified in the worker configuration.");
    }
    
    PublicKey workerKey = keys.getPublicKey();
    X509Certificate[] certChain = getStore(homeStore).makeWorkerPrincipal(this, workerKey);
    
    // Add the certificate to the key store.
    keys.setPrincipalChain(certChain);
    
    return keys.getPrincipal(this);
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
    return getWorker(config.name);
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
   * Clears out the worker cache (but leaves dissemination cache intact). To be
   * used for (performance) testing only.
   */
  public void clearCache() {
    for (RemoteStore store : stores.values()) {
      store.clearCache();
    }
  }
  
  /**
   * Shuts down and cleans up the worker.
   */
  public void shutdown() {
    fetchManager.destroy();
  }

  public static void initialize(String name) throws IllegalStateException,
      IOException, InternalError, UsageError, GeneralSecurityException {
    initialize(name, null, null);
  }

  public static void initializeForStore(String name,
      Map<String, RemoteStore> initStoreSet) throws IOException,
      IllegalStateException, InternalError, UsageError,
      GeneralSecurityException {
    initialize(name, ONumConstants.STORE_PRINCIPAL, initStoreSet);
  }

  /**
   * @param principalOnum
   *          non-null iff worker is being initialized for a store.
   */
  private static void initialize(String name, Long principalOnum,
      Map<String, RemoteStore> initStoreSet) throws IOException,
      IllegalStateException, InternalError, UsageError,
      GeneralSecurityException {
    ConfigProperties props = new ConfigProperties(name);
    
    initialize(props, principalOnum, initStoreSet);
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
        worker.sigcp = opts.sigcp;
        worker.filsigcp = opts.filsigcp;
        worker.code_cache = opts.code_cache;
      } catch (UsageError ue) {
        PrintStream out = ue.exitCode == 0 ? System.out : System.err;
        if (ue.getMessage() != null && ue.getMessage().length() > 0) {
          out.println(ue.getMessage());
          out.println();
        }

        Options.printUsage(out, ue.showSecretMenu);
        throw new TerminationException(ue.exitCode);
      }

      // log the command line
      StringBuilder cmd = new StringBuilder("Command Line: Worker");
      for (String s : args) {
        cmd.append(" ");
        cmd.append(s);
      }
      WORKER_LOGGER.config(cmd.toString());

      // Attempt to read the principal object to ensure that it exists.
      final NodePrincipal workerPrincipal = worker.getPrincipal();
      runInSubTransaction(new Code<Void>() {
        public Void run() {
          WORKER_LOGGER.config("Worker principal is " + workerPrincipal);
          return null;
        }
      });

      if (opts.app != null) {
        // Run the requested application.
        String app = opts.app[0];
        String[] appArgs = new String[opts.app.length - 1];
        for (int i = 0; i < appArgs.length; i++) {
          appArgs[i] = opts.app[i + 1];
        }
        
        worker.runFabricApp(app, appArgs);
      } else {
        // Drop into the worker shell.
        new WorkerShell(worker).run();
      }
    } finally {
      if (worker != null) worker.shutdown();
    }
  }
  
  public void setStore(String name, RemoteStore store) {
    stores.put(name, store);
  }
  
  /**
   * Runs the given Fabric program.
   * 
   * @param mainClassName the unmangled name of the application's main class.
   * @param args arguments to be passed to the application.
   * @throws Throwable 
   */
  public void runFabricApp(String mainClassName, final String[] args)
      throws Throwable {
    FabricClassLoader loader = new FabricClassLoader(Worker.class.getClassLoader()); 
    Class<?> mainClass = loader.loadClass(SysUtil.mangle(mainClassName));
    Method main =
        mainClass.getMethod("main", new Class[] { ObjectArray.class });

    final Store local = getLocalStore();
    final NodePrincipal workerPrincipal = getPrincipal();
    Object argsProxy = runInSubTransaction(new Code<Object>() {
      public Object run() {
        ConfPolicy conf =
            LabelUtil._Impl.readerPolicy(local, workerPrincipal,
                workerPrincipal);
        IntegPolicy integ =
            LabelUtil._Impl.writerPolicy(local, workerPrincipal,
                workerPrincipal);
        Label label = LabelUtil._Impl.toLabel(local, conf, integ);
        return WrappedJavaInlineable.$wrap(local, label, args);
      }
    });

    MainThread.invoke(main, argsProxy);
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
