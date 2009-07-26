package fabric.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.logging.Logger;

import javax.net.ssl.*;

import jif.lang.ConfPolicy;
import jif.lang.IntegPolicy;
import jif.lang.Label;
import jif.lang.LabelUtil;
import fabric.client.debug.Timing;
import fabric.client.remote.RemoteCallManager;
import fabric.client.remote.RemoteClient;
import fabric.client.transaction.Log;
import fabric.client.transaction.TransactionManager;
import fabric.client.transaction.TransactionRegistry;
import fabric.common.*;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.TerminationException;
import fabric.common.exceptions.UsageError;
import fabric.dissemination.FetchManager;
import fabric.dissemination.Glob;
import fabric.dissemination.pastry.Cache;
import fabric.lang.NodePrincipal;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.arrays.ObjectArray;

/**
 * This is the main interface to the Fabric API. Applications wishing to use
 * Fabric must first initialize it by calling one of the <code>initialize</code>
 * methods, and can then use the singleton Client instance to access Cores and
 * Objects.
 */
public final class Client {
  public final String name;

  public final int port;

  // A map from core hostnames to Core objects
  protected final Map<String, RemoteCore> cores;

  // A map from client hostnames to RemoteClient objects.
  private final Map<String, RemoteClient> remoteClients;

  protected final LocalCore localCore;

  // A KeyStore holding cores' public key certificates.
  protected final KeyStore trustStore;

  // A socket factory for creating TLS connections.
  public final SSLSocketFactory sslSocketFactory;

  // The principal on whose behalf this client is running.
  protected final NodePrincipal principal;
  public final java.security.Principal javaPrincipal;

  // The logger
  public static final Logger log = Logger.getLogger("fabric.client");

  // The timeout (in milliseconds) to use whilst attempting to connect to a core
  // node.
  public final int timeout;

  // The number of times to retry connecting to each core node.
  public final int retries;

  // The name service to use.
  public final NameService nameService;

  // The manager to use for fetching objects from cores.
  protected final FetchManager fetchManager;

  // The collection of dissemination caches used by this client's dissemination
  // node.
  private final List<Cache> disseminationCaches;

  private final RemoteCallManager remoteCallManager;

  public static final Random RAND = new Random();
  private static final int DEFAULT_TIMEOUT = 2;

  // force Timing to load.
  @SuppressWarnings("unused")
  private static final Timing t = Timing.APP;

  /**
   * Initializes the Fabric <code>Client</code>. When connecting to a core, the
   * client will retry each core node the specified number of times before
   * failing. A negative retry-count is interpreted as an infinite retry-count.
   * 
   * @param keyStore
   *          The client's key store. Should contain the client's X509
   *          certificate.
   * @param passwd
   *          The password for unlocking the key store.
   * @param trustStore
   *          The trust store to use. If this value is null, then the default
   *          trust store will be used.
   * @param cacheSize
   *          The object cache size, in number of objects; must be positive.
   * @param maxConnections
   *          The maximum number of connections to core nodes to maintain; must
   *          be positive.
   * @param timeout
   *          The timeout value to be used in seconds; must be positive.
   * @param retries
   *          The number of times to retry before failing to connect.
   * @param useSSL
   *          Whether SSL encryption is desired. Used for debugging purposes.
   */
  public static Client initialize(String name, int port, String principalURL,
      KeyStore keyStore, char[] passwd, KeyStore trustStore,
      int maxConnections, int timeout, int retries, boolean useSSL,
      String fetcher, Map<String, RemoteCore> initCoreSet)
      throws InternalError, UnrecoverableKeyException, IllegalStateException,
      UsageError {

    if (instance != null)
      throw new IllegalStateException(
          "The Fabric client has already been initialized");

    log.info("Initializing Fabric client");
    log.config("maximum connections: " + maxConnections);
    log.config("timeout:             " + timeout);
    log.config("retries:             " + retries);
    log.config("use ssl:             " + useSSL);
    instance =
        new Client(name, port, principalURL, keyStore, passwd, trustStore,
            maxConnections, timeout, retries, useSSL, fetcher, initCoreSet);

    instance.remoteCallManager.start();
    instance.localCore.initialize();

    return instance;
  }

  /**
   * The singleton Client instance.
   */
  protected static Client instance;

  @SuppressWarnings("unchecked")
  private Client(String name, int port, String principalURL, KeyStore keyStore,
      char[] passwd, KeyStore trustStore, int maxConnections, int timeout,
      int retries, boolean useSSL, String fetcher,
      Map<String, RemoteCore> initCoreSet) throws InternalError,
      UnrecoverableKeyException, UsageError {
    // Sanitise input.
    if (timeout < 1) timeout = DEFAULT_TIMEOUT;

    this.name = name;
    this.port = port;
    this.timeout = 1000 * timeout;
    this.retries = retries;
    fabric.common.Options.DEBUG_NO_SSL = !useSSL;

    this.nameService = new NameService();
    this.cores = new HashMap<String, RemoteCore>();
    if (initCoreSet != null) this.cores.putAll(initCoreSet);
    this.remoteClients = new HashMap<String, RemoteClient>();
    this.localCore = new LocalCore();
    this.trustStore = trustStore;

    // Set up the SSL socket factory.
    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
      kmf.init(keyStore, passwd);

      TrustManager[] tm = null;
      if (trustStore != null) {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
        tmf.init(trustStore);
        tm = tmf.getTrustManagers();
      }
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
        Core core = getCore(principalPath.getHost());
        long onum = Long.parseLong(principalPath.getPath().substring(1));
        this.principal = new NodePrincipal._Proxy(core, onum);
      } catch (URISyntaxException e) {
        throw new UsageError("Invalid principal URL specified.", 1);
      }
    } else {
      this.principal = null;
    }

    this.remoteCallManager = new RemoteCallManager();
    this.disseminationCaches = new ArrayList<Cache>(1);

    // Initialize the fetch manager. This MUST be the last thing done in the
    // constructor, or the fetch manager will not be properly shut down if
    // there's an error while initializing the client.
    try {
      Constructor<FetchManager> fetchManagerConstructor =
          (Constructor<FetchManager>) Class.forName(fetcher).getConstructor(
              Client.class);
      this.fetchManager = fetchManagerConstructor.newInstance(this);
    } catch (Exception e) {
      throw new InternalError("Unable to load fetch manager", e);
    }
  }

  /**
   * Returns the Singleton Client instance.
   * 
   * @return the Client instance
   * @throws IllegalStateException
   *           if the Fabric client is uninitialized
   */
  public static Client getClient() throws IllegalStateException {
    if (instance == null)
      throw new IllegalStateException(
          "The Fabric client is uninitialized.  Call Client.init(...)");
    return instance;
  }

  /**
   * Returns a <code>Core</code> object representing the given core.
   * 
   * @param name
   *          The core's host name.
   * @return The corresponding <code>Core</code> object.
   */
  public RemoteCore getCore(String name) {
    name = NameService.resolveAlias(name);

    if (name == null) {
      throw new NullPointerException();
    }

    RemoteCore result = cores.get(name);
    if (result == null) {
      try {
        Certificate cert = trustStore.getCertificate(name);
        PublicKey pubKey = cert.getPublicKey();
        result = new RemoteCore(name, pubKey);
        cores.put(name, result);
      } catch (GeneralSecurityException e) {
        throw new InternalError(e);
      }
    }
    return result;
  }

  /**
   * @return a <code>RemoteClient</code> object
   */
  public RemoteClient getClient(String name) {
    name = NameService.resolveAlias(name);

    if (name == null) throw new NullPointerException();

    RemoteClient result;
    synchronized (remoteClients) {
      result = remoteClients.get(name);
      if (result == null) {
        result = new RemoteClient(name);
        remoteClients.put(name, result);
      }
    }
    return result;
  }

  public LocalCore getLocalCore() {
    return localCore;
  }

  /**
   * @return a RemoteClient object representing the local client.
   */
  public RemoteClient getLocalClient() {
    return getClient(name);
  }

  /**
   * Returns the fetch manager.
   */
  public FetchManager fetchManager() {
    return fetchManager;
  }

  /**
   * Registers that a client has a new dissemination cache.
   */
  public void registerDisseminationCache(Cache cache) {
    this.disseminationCaches.add(cache);
  }

  /**
   * Updates the dissemination caches with the given object glob.
   * 
   * @return true iff there was a cache entry for the given oid.
   */
  public boolean updateDissemCaches(RemoteCore core, long onum, Glob update) {
    boolean result = false;

    for (Cache cache : disseminationCaches) {
      result |= cache.updateEntry(core, onum, update);
    }

    return result;
  }

  /**
   * Updates the client cache with the given object group.
   * 
   * @return true iff there was a cache entry for any object in the group.
   */
  public boolean updateCache(RemoteCore core, ObjectGroup group) {
    boolean result = false;
    for (SerializedObject obj : group.objects().values()) {
      result |= core.updateCache(obj);
    }

    return result;
  }

  /**
   * @return the Fabric notion of the client principal.
   */
  public NodePrincipal getPrincipal() {
    return principal;
  }

  /**
   * @return the Java notion of the client principal.
   */
  public java.security.Principal getJavaPrincipal() {
    return javaPrincipal;
  }

  /**
   * Called to shut down and clean up client.
   */
  public void shutdown() {
    shutdown_();
    remoteCallManager.shutdown();
    fetchManager.destroy();

    for (Core core : cores.values()) {
      if (core instanceof RemoteCore) {
        ((RemoteCore) core).cleanup();
      }
    }

    for (RemoteClient client : remoteClients.values()) {
      client.cleanup();
    }
  }

  /**
   * Called to shut down and clean up static client state.
   */
  private static void shutdown_() {
    FabricSoftRef.destroy();
  }

  public static void initialize() throws IOException, KeyStoreException,
      NoSuchAlgorithmException, CertificateException,
      UnrecoverableKeyException, IllegalStateException, InternalError,
      UsageError {
    initialize(null);
  }

  public static void initialize(String name) throws UnrecoverableKeyException,
      KeyStoreException, NoSuchAlgorithmException, CertificateException,
      IllegalStateException, IOException, InternalError, UsageError {
    initialize(name, null, null);
  }

  public static void initialize(String name, String principalURL,
      Map<String, RemoteCore> initCoreSet) throws IOException,
      KeyStoreException, NoSuchAlgorithmException, CertificateException,
      UnrecoverableKeyException, IllegalStateException, InternalError,
      UsageError {
    // Read in the Fabric properties file and update the System properties
    InputStream in = Resources.readFile("etc", "client.properties");
    Properties p = new Properties(System.getProperties());
    p.load(in);
    in.close();
    System.setProperties(p);

    if (name != null) {
      try {
        in = Resources.readFile("etc", "client", name + ".properties");
        p = new Properties(System.getProperties());
        p.load(in);
        in.close();
      } catch (IOException e) {
      }
    }

    if (principalURL == null)
      principalURL = p.getProperty("fabric.client.principal");

    KeyStore keyStore = KeyStore.getInstance("JKS");
    String passwd = p.getProperty("fabric.client.password");
    String filename =
        p.getProperty("fabric.client.keystore", name == null ? null
            : (name + ".keystore"));
    in = Resources.readFile("etc/keys", filename);
    keyStore.load(in, passwd.toCharArray());
    in.close();

    KeyStore trustStore = KeyStore.getInstance("JKS");
    String trustPass = p.getProperty("fabric.client.trustpassword");
    String trustFile =
        p.getProperty("fabric.client.trustfilename", "trust.keystore");
    in = Resources.readFile("etc/keys", trustFile);
    trustStore.load(in, trustPass.toCharArray());
    in.close();

    int port = Integer.parseInt(p.getProperty("fabric.client.port", "3373"));
    int maxConnections =
        Integer.parseInt(p.getProperty("fabric.client.maxConnections", "50"));
    int timeout = Integer.parseInt(p.getProperty("fabric.client.timeout", "2"));
    int retries = Integer.parseInt(p.getProperty("fabric.client.retries", "5"));

    String fetcher =
        p.getProperty("fabric.client.fetchmanager",
            "fabric.client.DirectFetchManager");

    boolean useSSL =
        Boolean.parseBoolean(p.getProperty("fabric.client.useSSL", "true"));

    initialize(name, port, principalURL, keyStore, passwd.toCharArray(),
        trustStore, maxConnections, timeout, retries, useSSL, fetcher,
        initCoreSet);
  }

  // TODO: throws exception?
  public static void main(String[] args) throws Throwable {
    log.info("Client node");
    log.config("Fabric version " + new Version());
    log.info("");

    // Parse the command-line options.
    Client client = null;
    final Options opts;
    try {
      try {
        opts = new Options(args);
        initialize(opts.name);

        client = getClient();
        if (client.getPrincipal() == null && opts.core == null
            && opts.app != null) {
          throw new UsageError(
              "No fabric.client.principal specified in the client "
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
      StringBuilder cmd = new StringBuilder("Command Line: Client");
      for (String s : args) {
        cmd.append(" ");
        cmd.append(s);
      }
      log.config(cmd.toString());

      if (opts.core != null) {
        // Create a principal object on the given core.
        final String name = client.getJavaPrincipal().getName();
        final Core core = client.getCore(opts.core);

        runInSubTransaction(new Code<Void>() {
          public Void run() {
            NodePrincipal principal = new NodePrincipal._Impl(core, null, name);
            principal.addDelegatesTo(core.getPrincipal());

            System.out.println("Client principal created:");
            System.out.println("fab://" + opts.core + "/"
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
      final NodePrincipal clientPrincipal = client.getPrincipal();
      runInSubTransaction(new Code<Void>() {
        public Void run() {
          log.config("Client principal is " + clientPrincipal);
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

      final Core local = client.getLocalCore();
      Object argsProxy = runInSubTransaction(new Code<Object>() {
        public Object run() {
          ConfPolicy conf =
              LabelUtil._Impl.readerPolicy(local, clientPrincipal,
                  clientPrincipal);
          IntegPolicy integ =
              LabelUtil._Impl.writerPolicy(local, clientPrincipal,
                  clientPrincipal);
          Label label = LabelUtil._Impl.toLabel(local, conf, integ);
          return WrappedJavaInlineable.$wrap(local, label, newArgs);
        }
      });

      MainThread.invoke(opts, main, argsProxy);
    } finally {
      if (client != null)
        client.shutdown();
      else shutdown_();
    }
  }

  /**
   * This is a closure for executing code after standard client initialization
   * is complete. It is used by, e.g., core.Node to establish a direct link
   * between the client and the core.
   */
  public static interface PostInitExec {
    void run(Client client);
  }

  public void setCore(String name, RemoteCore core) {
    cores.put(name, core);
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
      } catch (Throwable e) {
        success = false;
        throw new AbortException(e);
      } finally {
        if (success) {
          try {
            tm.commitTransaction();
          } catch (AbortException e) {
            success = false;
          }
        } else {
          tm.abortTransaction();
        }
      }
    }

    throw new InternalError();
  }

  public static interface Code<T> {
    T run();
  }

}
