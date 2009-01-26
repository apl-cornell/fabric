package fabric.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import javax.net.ssl.*;

import jif.lang.Label;
import fabric.client.transaction.TransactionManager;
import fabric.common.*;
import fabric.common.InternalError;
import fabric.dissemination.FetchManager;
import fabric.lang.Object;
import fabric.lang.Principal;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.arrays.ObjectArray;

/**
 * This is the main interface to the Fabric API. Applications wishing to use
 * Fabric must first initialize it by calling one of the <code>initialize</code>
 * methods, and can then use the singleton Client instance to access Cores and
 * Objects.
 */
public class Client {

  // A map from core hostnames to Core objects
  protected final Map<String, RemoteCore> cores;

  protected final LocalCore localCore;

  // A socket factory for creating TLS connections.
  protected final SSLSocketFactory sslSocketFactory;

  // The principal on whose behalf this client is running.
  protected final Principal principal;
  protected final java.security.Principal javaPrincipal;

  // Whether SSL encryption is desired.
  protected final boolean useSSL;

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

  protected final ThreadLocal<Label> label;

  public static final Random RAND = new Random();
  private static final int DEFAULT_TIMEOUT = 2;

  /**
   * Initializes the Fabric <code>Client</code>. When connecting to a core,
   * the client will retry each core node the specified number of times before
   * failing. A negative retry-count is interpreted as an infinite retry-count.
   * 
   * @param keyStore
   *                The client's key store. Should contain the client's X509
   *                certificate.
   * @param passwd
   *                The password for unlocking the key store.
   * @param trustStore
   *                The trust store to use. If this value is null, then the
   *                default trust store will be used.
   * @param cacheSize
   *                The object cache size, in number of objects; must be
   *                positive.
   * @param maxConnections
   *                The maximum number of connections to core nodes to maintain;
   *                must be positive.
   * @param timeout
   *                The timeout value to be used in seconds; must be positive.
   * @param retries
   *                The number of times to retry before failing to connect.
   * @param useSSL
   *                Whether SSL encryption is desired. Used for debugging
   *                purposes.
   */
  public static Client initialize(String name, String principalURL,
      KeyStore keyStore, char[] passwd, KeyStore trustStore,
      int maxConnections, int timeout, int retries, boolean useSSL,
      String fetcher, PostInitExec postInitExec) throws InternalError,
      UnrecoverableKeyException, IllegalStateException, UsageError {

    if (instance != null)
      throw new IllegalStateException(
          "The Fabric client has already been initialized");

    log.info("Initializing Fabric client");
    log.config("maximum connections: " + maxConnections);
    log.config("timeout:             " + timeout);
    log.config("retries:             " + retries);
    log.config("use ssl:             " + useSSL);
    instance =
        new Client(name, principalURL, keyStore, passwd, trustStore,
            maxConnections, timeout, retries, useSSL, fetcher);
    
    if (postInitExec != null) postInitExec.run(instance);
    
    return instance;
  }

  /**
   * The singleton Client instance.
   */
  protected static Client instance;

  private Client(String name, String principalURL, KeyStore keyStore,
      char[] passwd, KeyStore trustStore, int maxConnections, int timeout,
      int retries, boolean useSSL, String fetcher) throws InternalError,
      UnrecoverableKeyException, UsageError {
    // Sanitise input.
    if (timeout < 1) timeout = DEFAULT_TIMEOUT;

    this.timeout = 1000 * timeout;
    this.retries = retries;
    this.useSSL = useSSL;

    this.nameService = new NameService();
    this.cores = new HashMap<String, RemoteCore>();
    this.localCore = new LocalCore();

    this.label = new ThreadLocal<Label>();

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

      this.javaPrincipal =
          ((X509KeyManager) kmf.getKeyManagers()[0])
              .getCertificateChain(name)[0].getSubjectX500Principal();
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
        this.principal = new Principal.$Proxy(core, onum);
      } catch (URISyntaxException e) {
        throw new UsageError("Invalid principal URL specified.", 1);
      }
    } else {
      this.principal = null;
    }

    // Initialize the fetch manager. This MUST be the last thing done in the
    // constructor, or the fetch manager will not be properly shut down if
    // there's an error while initializing the client.
    try {
      this.fetchManager = (FetchManager) Class.forName(fetcher).newInstance();
    } catch (Exception e) {
      throw new InternalError("Unable to load fetch manager", e);
    }
  }

  /**
   * Returns the Singleton Client instance.
   * 
   * @return the Client instance
   * @throws IllegalStateException
   *                 if the Fabric client is uninitialized
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
   *                The core's host name.
   * @return The corresponding <code>Core</code> object.
   */
  public RemoteCore getCore(String name) {
    name = NameService.resolveAlias(name);
    
    if (name == null) {
      throw new NullPointerException();
    }

    RemoteCore result = cores.get(name);
    if (result == null) {
      result = new RemoteCore(name);
      cores.put(name, result);
    }
    return result;
  }

  public LocalCore getLocalCore() {
    return localCore;
  }

  /**
   * Returns the fetch manager.
   */
  public FetchManager fetchManager() {
    return fetchManager;
  }

  /**
   * @return the Fabric notion of the client principal.
   */
  public Principal getPrincipal() {
    return principal;
  }
  
  /**
   * @return the Java notion of the client principal.
   */
  public java.security.Principal getJavaPrincipal() {
    return javaPrincipal;
  }

  /**
   * Sets the principal of the currently executing thread.
   * 
   * @param p
   *                The principal to use.
   */
  public void setLabel(Label l) {
    label.set(l);
  }

  public Label getLabel() {
    return label.get();
  }

  /**
   * Called to shut down and clean up client.
   */
  public void shutdown() {
    shutdown_();
    fetchManager.destroy();
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
      PostInitExec postInitExec) throws IOException, KeyStoreException,
      NoSuchAlgorithmException,
      CertificateException, UnrecoverableKeyException, IllegalStateException,
      InternalError, UsageError {
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

    int maxConnections =
        Integer.parseInt(p.getProperty("fabric.client.maxConnections", "50"));
    int timeout = Integer.parseInt(p.getProperty("fabric.client.timeout", "2"));
    int retries = Integer.parseInt(p.getProperty("fabric.client.retries", "5"));

    String fetcher =
        p.getProperty("fabric.client.fetchmanager",
            "fabric.client.DirectFetchManager");

    boolean useSSL =
        Boolean.parseBoolean(p.getProperty("fabric.client.useSSL", "true"));

    initialize(name, principalURL, keyStore, passwd.toCharArray(), trustStore,
        maxConnections, timeout, retries, useSSL, fetcher, postInitExec);
  }

  // TODO: throws exception?
  public static void main(String[] args) throws Throwable {
    log.info("Client node");
    log.config("Fabric version " + new Version());
    log.info("");
    
    // Parse the command-line options.
    Client client = null;
    Options opts;
    try {
      try {
        opts = new Options(args);
        initialize(opts.name);

        client = getClient();
        if (client.getPrincipal() == null && opts.core == null) {
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
        String name = client.getJavaPrincipal().getName();
        Core core = client.getCore(opts.core);
        TransactionManager.getInstance().startTransaction();
        Principal principal = Principal.$Proxy.getInstance(core, name);
        TransactionManager.getInstance().commitTransaction();

        System.out.println("Client principal created:");
        System.out.println("fab://" + opts.core + "/" + principal.$getOnum());
        return;
      }
      
      // Attempt to read the principal object to ensure that it exists.
      TransactionManager.getInstance().startTransaction();
      log.config("Client principal is " + client.getPrincipal());
      TransactionManager.getInstance().commitTransaction();

      if (opts.app == null) {
        // Act as a dissemination node.
        while (true) {
          try {
            Thread.sleep(Long.MAX_VALUE);
          } catch (InterruptedException e) {
          }
        }
      }
      
      // Run the requested application.
      Class<?> mainClass = Class.forName(opts.app[0] + "$$Impl");
      Method main =
          mainClass.getMethod("main", new Class[] { ObjectArray.class });
      String[] newArgs = new String[opts.app.length - 1];
      for (int i = 0; i < newArgs.length; i++)
        newArgs[i] = opts.app[i + 1];

      Core local = client.getLocalCore();
      TransactionManager.getInstance().startTransaction();
      Object argsProxy = WrappedJavaInlineable.$wrap(local, newArgs);
      TransactionManager.getInstance().commitTransaction();

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
}
