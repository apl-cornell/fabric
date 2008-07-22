package fabric.client;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import javax.net.ssl.*;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;

import fabric.client.transaction.TransactionManager;
import fabric.common.InternalError;
import fabric.common.Resources;
import fabric.dissemination.FetchManager;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.arrays.ObjectArray;
import fabric.lang.auth.Label;

/**
 * This is the main interface to the Fabric API. Clients wishing to use Fabric
 * must first initialize it by calling one of the <code>initialize</code>
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

  // Whether SSL encryption is desired.
  protected final boolean useSSL;

  // The logger
  public static Logger log;

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
  public static Client initialize(KeyStore keyStore, char[] passwd,
      KeyStore trustStore, int maxConnections, int timeout, int retries,
      boolean useSSL, String fetcher) throws InternalError,
      UnrecoverableKeyException, IllegalStateException {

    if (instance != null)
      throw new IllegalStateException(
          "The Fabric client has already been initialized");

    log = Logger.getLogger("fabric.client");

    log.info("Initializing Fabric client");
    log.config("maximum connections: " + maxConnections);
    log.config("timeout:             " + timeout);
    log.config("retries:             " + retries);
    log.config("use ssl:             " + useSSL);
    instance =
        new Client(keyStore, passwd, trustStore, maxConnections, timeout,
            retries, useSSL, fetcher);
    return instance;
  }

  /**
   * The singleton Client instance.
   */
  protected static Client instance;

  protected Client(KeyStore keyStore, char[] passwd, KeyStore trustStore,
      int maxConnections, int timeout, int retries, boolean useSSL,
      String fetcher) throws InternalError, UnrecoverableKeyException {
    // Sanitise input.
    if (timeout < 1) timeout = DEFAULT_TIMEOUT;

    // Set up the SSL socket factory.
    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
      kmf.init(keyStore, passwd);

      this.principal =
          ((X509KeyManager) kmf.getKeyManagers()[0])
              .getCertificateChain("client0")[0].getSubjectX500Principal();

      TrustManager[] tm = null;
      if (trustStore != null) {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
        tmf.init(trustStore);
        tm = tmf.getTrustManagers();
      }
      sslContext.init(kmf.getKeyManagers(), tm, null);
      this.sslSocketFactory = sslContext.getSocketFactory();
    } catch (KeyManagementException e) {
      throw new InternalError("Unable to initialise key manager factory.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    } catch (KeyStoreException e) {
      throw new InternalError("Unable to initialise key manager factory.", e);
    }

    this.timeout = 1000 * timeout;
    this.retries = retries;
    this.useSSL = useSSL;

    this.nameService = new NameService();
    this.cores = new HashMap<String, RemoteCore>();
    this.localCore = new LocalCore();

    this.label = new ThreadLocal<Label>();

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

  public Principal getPrincipal() {
    return principal;
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
    fetchManager.destroy();
    FabricSoftRef.destroy();
  }

  public static void initialize() throws IOException, KeyStoreException,
      NoSuchAlgorithmException, CertificateException,
      UnrecoverableKeyException, IllegalStateException, InternalError {
    initialize(null);
  }

  public static void initialize(String name) throws IOException,
      KeyStoreException, NoSuchAlgorithmException, CertificateException,
      UnrecoverableKeyException, IllegalStateException, InternalError {
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

    KeyStore keyStore = KeyStore.getInstance("JKS");
    String passwd = System.getProperty("fabric.client.password");
    String filename =
        p.getProperty("fabric.client.keystore", "client.keystore");
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

    initialize(keyStore, passwd.toCharArray(), trustStore, maxConnections,
        timeout, retries, useSSL, fetcher);
  }

  // TODO: throws exception?
  public static void main(String[] args) throws Throwable {
    Options opts = getOpts(args);
    initialize(opts.name);

    // log the command line
    StringBuilder cmd = new StringBuilder("Command Line: Client");
    for (String c : args) {
      cmd.append(" ");
      cmd.append(c);
    }
    log.config(cmd.toString());

    Class<?> mainClass = Class.forName(opts.main[0] + "$$Impl");
    Method main =
        mainClass.getMethod("main", new Class[] { ObjectArray.class });
    String[] newArgs = new String[opts.main.length - 1];
    for (int i = 0; i < newArgs.length; i++)
      newArgs[i] = opts.main[i + 1];

    Client c = getClient();

    Core local = c.getLocalCore();
    TransactionManager.getInstance().startTransaction();
    Object argsProxy = WrappedJavaInlineable.$wrap(local, newArgs);
    TransactionManager.getInstance().commitTransaction();

    try {
      MainThread.invoke(opts, main, argsProxy);
    } finally {
      c.shutdown();
    }
  }

  private static Options getOpts(String[] args) throws IllegalArgumentException {
    Options opts = new Options();
    GetOpt o = new GetOpt(args, Options.OPTS);

    try {
      for (int c = o.getNextOption(); c != -1; c = o.getNextOption()) {
        switch (c) {
        case 'n':
          opts.name = o.getOptionArg();
          break;
        }
      }

      opts.main = o.getCmdArgs();
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }

    return opts;
  }

  static class Options {

    public static final String OPTS = "n:";

    public String[] main;
    public String name;

  }

}
