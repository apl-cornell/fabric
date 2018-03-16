package fabric.worker;

import static fabric.common.Logging.TIMING_LOGGER;
import static fabric.common.Logging.WORKER_LOGGER;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import fabric.common.ConfigProperties;
import fabric.common.KeyMaterial;
import fabric.common.Logging;
import fabric.common.NSUtil;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
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
import fabric.common.net.naming.NameService;
import fabric.common.net.naming.NameService.PortType;
import fabric.common.net.naming.TransitionalNameService;
import fabric.dissemination.AbstractGlob;
import fabric.dissemination.FetchManager;
import fabric.lang.FabricClassLoader;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.arrays.ObjectArray;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.IntegPolicy;
import fabric.lang.security.Label;
import fabric.lang.security.LabelCache;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.NodePrincipal;
import fabric.net.RemoteNode;
import fabric.worker.admin.WorkerAdmin;
import fabric.worker.admin.WorkerNotRunningException;
import fabric.worker.remote.InProcessRemoteWorker;
import fabric.worker.remote.RemoteCallManager;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.shell.ChainedCommandSource;
import fabric.worker.shell.CommandSource;
import fabric.worker.shell.DummyCommandSource;
import fabric.worker.shell.InteractiveCommandSource;
import fabric.worker.shell.TokenizedCommandSource;
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

  /** The path for Fabric runtime classes */
  public String bootcp;

  /** The path for Fabric signatures */
  public String sigcp;

  /** The path for FabIL signatures */
  public String filsigcp;

  /** The directory for dynamically compiled mobile code */
  public String codeCache;

  /** Flag for indicating if worker needs to cache compiled code in local file system */
  public boolean outputToLocalFS;

  /** The loader used by this worker for loading classes from fabric */
  public FabricClassLoader loader;

  /** A map from store hostnames to Store objects */
  protected final ConcurrentMap<String, RemoteStore> stores;

  /** A map from worker hostnames to RemoteWorker objects. */
  private final ConcurrentMap<String, RemoteWorker> remoteWorkers;

  protected final LocalStore localStore;

  /** A subsocket factory for unauthenticated connections to stores. */
  public final SubSocketFactory<RemoteStore> unauthToStore;

  /** A subsocket factory for authenticated connections to stores. */
  public final SubSocketFactory<RemoteStore> authToStore;

  /** A subsocket factory for authenticated connections to workers */
  public final SubSocketFactory<RemoteWorker> authToWorker;

  /** The subserversocket factory */
  public final SubServerSocketFactory authFromAll;

  /** The manager to use for fetching objects from stores. */
  protected final FetchManager fetchManager;

  /** The global label cache. */
  public final LabelCache labelCache;

  protected final NodePrincipal principal;

  private final RemoteCallManager remoteCallManager;

  public final InProcessRemoteWorker inProcessRemoteWorker;

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
  private static Worker initialize(ConfigProperties config, Long principalOnum,
      Map<String, RemoteStore> initStoreSet) throws InternalError,
      IllegalStateException, UsageError, IOException, GeneralSecurityException {

    if (instance != null) throw new IllegalStateException(
        "The Fabric worker has already been initialized");

    WORKER_LOGGER.info("Initializing Fabric worker");
    WORKER_LOGGER.config("use ssl:             " + config.useSSL);

    instanceName = config.name;
    instance = new Worker(config, principalOnum, initStoreSet);

    Threading.getPool().execute(instance.remoteCallManager);
    instance.localStore.initialize();

    // Load up the profiler.
    try {
      Class.forName("fabric.common.Profiling");
    } catch (ClassNotFoundException e) {
    }

    System.out.println("Worker started");
    TIMING_LOGGER.log(Level.INFO, "Worker started");

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        TIMING_LOGGER.log(Level.INFO, "Worker shutting down");
      }
    });

    return instance;
  }

  /**
   * The singleton Worker instance.
   */
  protected static Worker instance;

  /**
   * Name for the singleton instance, kept separately so it can be accessed
   * before the Worker is fully initialized.
   */
  protected static String instanceName;

  public static boolean isInitialized() {
    return instance != null;
  }

  /** convenience method for getting the node's name */
  public String getName() {
    return config.name;
  }

  private Worker(ConfigProperties config, Long principalOnum,
      Map<String, RemoteStore> initStoreSet)
      throws InternalError, UsageError, IOException, GeneralSecurityException {
    // Sanitise input.

    this.config = config;

    fabric.common.Options.DEBUG_NO_SSL = !config.useSSL;

    this.stores = new ConcurrentHashMap<>();
    if (initStoreSet != null) this.stores.putAll(initStoreSet);
    this.remoteWorkers = new ConcurrentHashMap<>();
    this.localStore = new LocalStore();

    NameService nameService = new TransitionalNameService();

    // initialize the various socket factories
    final Protocol<RemoteStore> authenticateToStoreProtocol =
        makeAuthenticateProtocol(config.getKeyMaterial());
    final Protocol<RemoteWorker> authenticateToWorkerProtocol =
        makeAuthenticateProtocol(config.getKeyMaterial());

    Protocol<RemoteStore> nonAuthenticateProtocol = new HandshakeComposite<>(
        new HandshakeUnauthenticated<RemoteStore>(config.name));

    this.authToStore = new SubSocketFactory<>(config,
        authenticateToStoreProtocol, nameService, PortType.STORE);
    this.authToWorker = new SubSocketFactory<>(config,
        authenticateToWorkerProtocol, nameService, PortType.WORKER);
    this.unauthToStore = new SubSocketFactory<>(config, nonAuthenticateProtocol,
        nameService, PortType.STORE);
    this.authFromAll = new SubServerSocketFactory(config,
        authenticateToWorkerProtocol, nameService, PortType.WORKER);

    this.inProcessRemoteWorker = new InProcessRemoteWorker(this);
    this.remoteWorkers.put(config.name, inProcessRemoteWorker);

    this.remoteCallManager = new RemoteCallManager(this);

    // Initialize the fetch manager.
    try {
      Constructor<FetchManager> fetchManagerConstructor =
          (Constructor<FetchManager>) Class.forName(config.dissemClass)
              .getConstructor(Worker.class, Properties.class);
      this.fetchManager = fetchManagerConstructor.newInstance(this,
          config.disseminationProperties);
    } catch (Exception e) {
      throw new InternalError("Unable to load fetch manager", e);
    }

    this.labelCache = new LabelCache();

    this.principal = initializePrincipal(config.homeStore, principalOnum,
        this.config.getKeyMaterial());
  }

  private <Node extends RemoteNode<Node>> Protocol<Node> makeAuthenticateProtocol(
      KeyMaterial key) throws GeneralSecurityException {
    final Protocol<Node> protocol;
    if (config.useSSL)
      protocol = new HandshakeAuthenticated<>(config.getKeyMaterial());
    else protocol = new HandshakeBogus<>();

    return new HandshakeComposite<>(protocol);
  }

  /**
   * Initialize the reference to the principal object.
   */
  private NodePrincipal initializePrincipal(String homeStore,
      Long principalOnum, KeyMaterial keys) throws UsageError {
    if (principalOnum != null) {
      // First, handle the case where we're initializing a store's worker.
      return new NodePrincipal._Proxy(getStore(config.name), principalOnum);
    }

    // Next, look in the key set for a principal certificate.
    NodePrincipal p = keys.getPrincipal(this);
    if (p != null) return p;

    // Still no principal? Create one.
    if (homeStore == null) {
      throw new UsageError(
          "No fabric.worker.homeStore specified in the worker configuration.");
    }

    PublicKey workerKey = keys.getPublicKey();
    X509Certificate[] certChain =
        getStore(homeStore).makeWorkerPrincipal(this, workerKey);

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
    if (instance == null) throw new IllegalStateException(
        "The Fabric worker is uninitialized.  Call Worker.init(...)");
    return instance;
  }

  /**
   * Returns the Singleton Worker instance's name.
   *
   * @return the Worker instance's name.
   * @throws IllegalStateException
   *           if the Fabric worker's name is uninitialized
   */
  public static String getWorkerName() throws IllegalStateException {
    if (instanceName == null) throw new IllegalStateException(
        "The Fabric worker's name is uninitialized.  Call Worker.init(...)");
    return instanceName;
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
      RemoteStore existingStore = stores.putIfAbsent(name, result);
      if (existingStore != null) return existingStore;
    }

    return result;
  }

  /**
   * @return a <code>RemoteWorker</code> object
   */
  public RemoteWorker getWorker(String name) {
    RemoteWorker result = remoteWorkers.get(name);
    if (result == null) {
      result = new RemoteWorker(name);
      RemoteWorker existingWorker = remoteWorkers.putIfAbsent(name, result);
      if (existingWorker != null) return existingWorker;
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
   * Updates the dissemination and worker caches with the given glob.
   *
   * @return true iff either of the caches were updated.
   */
  public boolean updateCaches(RemoteStore store, long onum,
      AbstractGlob<?> update) {
    return fetchManager.updateCaches(store, onum, update);
  }

  /**
   * Updates the worker cache with the given object group, as follows:
   * <ul>
   * <li>If the cache contains a deserialized copy of an old version of any
   * object in the group, then that old version is replaced with a serialized
   * copy of the new version.
   * <li>If the cache contains a serialized copy of an old version of any object
   * in the group, then that old version is evicted.
   * </ul>
   *
   * Transactions using any updated object are aborted and retried.
   *
   * @return true iff after this update operation, the cache contains any member
   *     of the group.
   */
  public boolean updateCache(RemoteStore store, ObjectGroup group) {
    boolean result = false;
    for (SerializedObject obj : group.objects().values()) {
      store.updateCache(obj);
      result = result || store.readFromCache(obj.getOnum()) != null;
    }
    return result;
  }

  /**
   * Detemines which of a given set of onums are resident in cache.
   */
  public List<Long> findOnumsInCache(RemoteStore store, List<Long> onums) {
    List<Long> result = new ArrayList<>();
    for (long onum : onums) {
      if (store.readFromCache(onum) != null) result.add(onum);
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
    initialize(new ConfigProperties(name));
  }

  public static void initialize(ConfigProperties props)
      throws IllegalStateException, InternalError, UsageError, IOException,
      GeneralSecurityException {
    initialize(props, null, null);
  }

  public static void initializeForStore(String name,
      Map<String, RemoteStore> initStoreSet)
      throws IOException, IllegalStateException, InternalError, UsageError,
      GeneralSecurityException {
    initialize(new ConfigProperties(name), ONumConstants.STORE_PRINCIPAL,
        initStoreSet);
  }

  public FabricClassLoader getClassLoader() {
    if (loader == null)
      loader = new FabricClassLoader(Worker.class.getClassLoader());
    return loader;
  }

  public static void main(String[] args) throws Throwable {
    WORKER_LOGGER.info("Worker node");
    WORKER_LOGGER.config("Fabric version " + new Version());
    WORKER_LOGGER.info("");

    Worker worker = null;
    try {
      try {
        // Parse the command-line options and read in the worker's configuration.
        final Options opts = new Options(args);
        final ConfigProperties config = new ConfigProperties(opts.name);

        // log the command line
        StringBuilder cmd = new StringBuilder("Command Line: Worker");
        for (String s : args) {
          cmd.append(" ");
          cmd.append(s);
        }
        WORKER_LOGGER.config(cmd.toString());

        try {
          // If an instance of the worker is already running, connect to it and
          // act as a remote terminal.
          WorkerAdmin.connect(config.workerAdminPort, opts.cmd);
          return;
        } catch (WorkerNotRunningException e) {
          // Fall through and initialize the worker.
        }

        initialize(config);
        worker = getWorker();
        worker.sigcp = opts.sigcp;
        worker.filsigcp = opts.filsigcp;
        worker.codeCache = opts.codeCache;
        worker.bootcp = opts.bootcp;
        worker.outputToLocalFS = opts.outputToLocalFS;

        // Attempt to read the principal object to ensure that it exists.
        final NodePrincipal workerPrincipal = worker.getPrincipal();
        runInSubTransaction(new Code<Void>() {
          @Override
          public Void run() {
            WORKER_LOGGER.config("Worker principal is " + workerPrincipal);
            return null;
          }
        });

        // Start listening on the admin port.
        WorkerAdmin.listen(config.workerAdminPort, worker);

        // Construct the source of commands for the worker shell.
        CommandSource commandSource;
        if (opts.cmd != null) {
          commandSource = new TokenizedCommandSource(opts.cmd);
          if (opts.keepOpen) {
            CommandSource nextSource;
            if (opts.interactiveShell) {
              nextSource = new InteractiveCommandSource(worker);
            } else {
              nextSource = new DummyCommandSource();
            }
            commandSource = new ChainedCommandSource(commandSource, nextSource);
          }
        } else if (opts.interactiveShell) {
          commandSource = new InteractiveCommandSource(worker);
        } else {
          commandSource = new DummyCommandSource();
        }

        // Drop into the worker shell.
        new WorkerShell(worker, commandSource).run();
      } catch (UsageError ue) {
        PrintStream out = ue.exitCode == 0 ? System.out : System.err;
        if (ue.getMessage() != null && ue.getMessage().length() > 0) {
          out.println(ue.getMessage());
          out.println();
        }

        Options.printUsage(out, ue.showSecretMenu);
        throw new TerminationException(ue.exitCode);
      } finally {
        if (worker != null) worker.shutdown();
      }
    } catch (TerminationException te) {
      if (te.getMessage() != null)
        (te.exitCode == 0 ? System.out : System.err).println(te.getMessage());

      System.exit(te.exitCode);
    }
  }

  /**
   * Runs the given Fabric program.
   *
   * @param mainClassName
   *          the unmangled name of the application's main class.
   * @param args
   *          arguments to be passed to the application.
   * @throws Throwable
   */
  public void runFabricApp(String mainClassName, final String[] args)
      throws Throwable {

    Method main = null;
    Object receiver = null;

    // Fabric compiler places static methods in the _Impl class
    Class<?> mainClass =
        getClassLoader().loadClass(NSUtil.toJavaImplName(mainClassName));
    for (Method m : mainClass.getMethods()) {
      if (m.getName().equals("main") && m.getParameterTypes().length == 1
          && m.getParameterTypes()[0].equals(ObjectArray.class)) {
        main = m;
        break;
      }
    }

    if (main == null) {
      // Support static methods defined in static impl class.
      mainClass = getClassLoader()
          .loadClass(NSUtil.toJavaStaticProxyName(mainClassName));
      for (Method m : mainClass.getMethods()) {
        if (m.getName().equals("main") && m.getParameterTypes().length == 1
            && m.getParameterTypes()[0].equals(ObjectArray.class)) {
          main = m;
          break;
        }
      }
      Field instanceField = mainClass.getField("$instance");
      receiver = (Object) instanceField.get(null);
    }

    final Store local = getLocalStore();
    final NodePrincipal workerPrincipal = getPrincipal();
    Object argsProxy = runInSubTransaction(new Code<Object>() {
      @Override
      public Object run() {
        ConfPolicy conf = LabelUtil._Impl.readerPolicy(local, workerPrincipal,
            workerPrincipal);
        IntegPolicy integ = LabelUtil._Impl.writerPolicy(local, workerPrincipal,
            workerPrincipal);
        Label label = LabelUtil._Impl.toLabel(local, conf, integ);
        return WrappedJavaInlineable.$wrap(local, label, conf, args);
      }
    });

    MainThread.invoke(main, receiver, argsProxy);
  }

  /**
   * Runs the given Java program.
   *
   * @param mainClassName
   *          the application's main class.
   * @param args
   *          arguments to be passed to the application.
   * @throws Throwable
   */
  public void runJavaApp(String mainClassName, final String[] args)
      throws Throwable {
    Class<?> mainClass = getClassLoader().loadClass(mainClassName);
    Method main = mainClass.getMethod("main", String[].class);
    MainThread.invoke(main, null, args);
  }

  /**
   * Executes the given code from within a new top-level Fabric transaction.
   * Should not be called by generated code. This is here to abstract away the
   * details of starting and finishing transactions.
   *
   * @param autoRetry
   *          whether the transaction should be automatically retried if it
   *          fails during commit
   */
  public static <T> T runInTopLevelTransaction(Code<T> code,
      boolean autoRetry) {
    return runInTransaction(null, code, autoRetry);
  }

  /**
   * Executes the given code from within a Fabric transaction. If the
   * transaction fails, it will be automatically retried until it succeeds.
   * Should not be called by generated code. This is here to abstract away the
   * details of starting and finishing transactions.
   *
   * @param tid
   *          The parent transaction for the subtransaction that will be
   *          created.
   */
  public static <T> T runInTransaction(TransactionID tid, Code<T> code) {
    return runInTransaction(tid, code, true);
  }

  /**
   * Executes the given code from within a Fabric transaction. Should not be
   * called by generated code. This is here to abstract away the details of
   * starting and finishing transactions.
   *
   * @param tid
   *          The parent transaction for the subtransaction that will be
   *          created.
   * @param autoRetry
   *          whether the transaction should be automatically retried if it
   *          fails during commit
   */
  private static <T> T runInTransaction(TransactionID tid, Code<T> code,
      boolean autoRetry) {
    TransactionManager tm = TransactionManager.getInstance();
    Log oldLog = tm.getCurrentLog();

    Log log = TransactionRegistry.getOrCreateInnermostLog(tid);
    tm.associateAndSyncLog(log, tid);

    try {
      return runInSubTransaction(code, autoRetry);
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
    return runInSubTransaction(code, true);
  }

  /**
   * Executes the given code from within a Fabric subtransaction of the current
   * transaction. Should not be called by generated code. This is here to
   * abstract away the details of starting and finishing transactions.
   *
   * @param autoRetry
   *          whether the transaction should be automatically retried if it
   *          fails during commit
   */
  private static <T> T runInSubTransaction(Code<T> code, boolean autoRetry) {
    TransactionManager tm = TransactionManager.getInstance();
    boolean backoffEnabled = getWorker().config.txRetryBackoff;

    // Indicating the transaction finished
    boolean success = false;
    // Indicating whether to retry if unsuccessful. Retry in nearly all cases.
    boolean retry = true;

    // Flag for triggering backoff on alternate retries.
    boolean doBackoff = true;

    int backoff = 1;
    while (!success) {
      if (backoffEnabled) {
        if (doBackoff) {
          if (backoff > 32) {
            while (true) {
              try {
                Thread.sleep(backoff);
                break;
              } catch (InterruptedException e) {
                Logging.logIgnoredInterruptedException(e);
              }
            }
          }

          if (backoff < 5000) backoff *= 2;
        }

        doBackoff = backoff <= 32 || !doBackoff;
      }

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

        if (currentTid.parent != null) {
          // Don't retry, kicking this up to a parent that needs to retry.
          retry = false;
          throw e;
        }

        throw new InternalError("Something is broken with transaction "
            + "management. Got a signal to restart a different transaction "
            + "than the one being managed.");
      } catch (Throwable e) {
        success = false;

        // Retry if the exception was a result of stale objects.
        if (tm.checkForStaleObjects()) continue;

        // Bad state, don't keep retrying.
        retry = false;
        throw new AbortException(e);
      } finally {
        if (success) {
          try {
            tm.commitTransaction();
          } catch (AbortException e) {
            success = false;
          } catch (TransactionRestartingException e) {
            success = false;

            if (!autoRetry) {
              throw new AbortException(
                  "Not retrying transaction that failed to commit (autoRetry=false)",
                  e);
            }
            // This is the TID for the parent of the transaction we just tried
            // to commit.
            TransactionID currentTid = tm.getCurrentTid();

            // Determine whether we need to restart an ancestor of the
            // transaction we just tried to commit.
            if (currentTid != null) {
              if (e.tid.equals(currentTid)
                  || !e.tid.isDescendantOf(currentTid)) {
                // Need to restart an ancestor of the transaction we just tried
                // to commit.
                throw e;
              }
            }

            // The transaction just we tried to commit will be restarted.
          }
        } else {
          tm.abortTransaction();
        }

        // If not successful and should retry, override control flow to run the
        // loop again.
        if (!success && retry) continue;
      }
    }

    throw new InternalError();
  }

  public static interface Code<T> {
    T run() throws Throwable;
  }

}
