package fabric.client;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.*;
import java.util.*;
import java.util.logging.Logger;

import javax.net.ssl.*;

import fabric.common.AccessError;
import fabric.common.InternalError;
import fabric.common.Resources;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.Object.$Impl;
import fabric.lang.arrays.ObjectArray;

/**
 * This is the main interface to the Fabric API. Clients wishing to use Fabric
 * must first initialize it by calling one of the <code>initialize</code>
 * methods, and can then use the singleton Client instance to access Cores and
 * Objects.
 */
public class Client {
  // A map from 48-bit core IDs to Core objects
  protected Map<Long, RemoteCore> connections;

  protected LocalCore localCore;

  // A socket factory for creating TLS connections.
  protected SSLSocketFactory sslSocketFactory;

  // The timeout (in milliseconds) to use whilst attempting to connect to a core
  // node.
  public final int timeout;

  // The number of times to retry connecting to each core node.
  public final int retries;

  // The name service to use.
  public final NameService nameService;

  // The manager to use for fetching objects from cores.
  protected FetchManager fetchManager;

  public static final Random RAND = new Random();
  private static final int DEFAULT_TIMEOUT = 2;

  /**
   * Initializes the Fabric <code>Client</code>. When connecting to a core,
   * the client will retry each core node the specified number of times before
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
   */
  public static Client initialize(KeyStore keyStore, char[] passwd,
      KeyStore trustStore, int maxConnections, int timeout, int retries)
      throws InternalError, UnrecoverableKeyException, IllegalStateException {
    if (instance != null)
      throw new IllegalStateException(
          "The Fabric client has already been initialized");
    Logger.getLogger("fabric.client").info("Initializing Fabric client");
    instance =
        new Client(keyStore, passwd, trustStore, maxConnections, timeout,
            retries);
    return instance;
  }

  /**
   * The singleton Client instance.
   */
  protected static Client instance;

  protected Client(KeyStore keyStore, char[] passwd, KeyStore trustStore,
      int maxConnections, int timeout, int retries) throws InternalError,
      UnrecoverableKeyException {
    // Sanitise input.
    if (timeout < 1) timeout = DEFAULT_TIMEOUT;

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
    } catch (KeyManagementException e) {
      throw new InternalError("Unable to initialise key manager factory.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new InternalError(e);
    } catch (KeyStoreException e) {
      throw new InternalError("Unable to initialise key manager factory.", e);
    }

    this.timeout = 1000 * timeout;
    this.retries = retries;
    this.nameService = new NameService();
    this.connections = new HashMap<Long, RemoteCore>();
    this.localCore = new LocalCore();

    // TODO load specific fetch manager as user option
    this.fetchManager = new DirectFetchManager();
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
   * Returns a <code>Core</code> object representing the given coreID
   * 
   * @param coreID
   *          The 48-bit core identifier to fetch
   * @return The corresponding <code>Core</code> object.
   */
  public RemoteCore getCore(long coreID) {
    RemoteCore result = connections.get(coreID);
    if (result == null) {
      result = new RemoteCore(coreID);
      connections.put(coreID, result);
    }
    return result;
  }

  /**
   * Returns a <code>Core</code> object representing the given core.
   * 
   * @param name
   *          A human-readable name for the core.
   * @return The corresponding <code>Core</code> object.
   */
  public Core getCore(String name) {
    return getCore(nameService.lookupCore(name));
  }

  public LocalCore getLocalCore() {
    return localCore;
  }

  /**
   * Fetches the requested object using the current fetch manager.
   * 
   * @param c
   *          the core where the object resides.
   * @param onum
   *          the object identifier.
   * @return the requested object if successful.
   * @throws AccessError
   * @throws UnreachableCoreException
   */
  public $Impl fetchObject(Core c, long onum) throws AccessError,
      UnreachableCoreException {
    return fetchManager.fetch(c, onum);
  }

  // TODO: throws exception?
  public static void main(String[] args) throws Throwable {
    // Read in the Fabric properties file and update the System properties

    InputStream in = Resources.readFile("etc", "client.properties");
    Properties p = new Properties(System.getProperties());
    p.load(in);
    in.close();
    System.setProperties(p);

    KeyStore keyStore = KeyStore.getInstance("JKS");
    String passwd = System.getProperty("fabric.client.password");
    String filename =
        System.getProperty("fabric.client.keystore", "client.keystore");
    in = Resources.readFile("etc", filename);
    keyStore.load(in, passwd.toCharArray());
    in.close();

    KeyStore trustStore = KeyStore.getInstance("JKS");
    String trustPass = System.getProperty("fabric.client.trustpassword");
    String trustFile =
        System.getProperty("fabric.client.trustfilename", "trust.keystore");
    in = Resources.readFile("etc", trustFile);
    trustStore.load(in, trustPass.toCharArray());
    in.close();

    int maxConnections =
        Integer.parseInt(System.getProperty("fabric.client.maxConnections",
            "50"));
    int timeout =
        Integer.parseInt(System.getProperty("fabric.client.timeout", "2"));
    int retries =
        Integer.parseInt(System.getProperty("fabric.client.retries", "5"));

    initialize(keyStore, passwd.toCharArray(), trustStore, maxConnections,
        timeout, retries);

    // TODO: option overriding on command line?

    Class<?> mainClass = Class.forName(args[0] + "$$Impl");
    Method main =
        mainClass.getMethod("main", new Class[] { ObjectArray.class });
    String[] newArgs = new String[args.length - 1];
    for (int i = 0; i < newArgs.length; i++)
      newArgs[i] = args[i + 1];

    Client c = getClient();

    Core local = c.getLocalCore();
    TransactionManager.INSTANCE.startTransaction();
    Object argsProxy = WrappedJavaInlineable.$wrap(local, newArgs);
    TransactionManager.INSTANCE.commitTransaction();

    try {
      main.invoke(null, argsProxy);
    } catch (InvocationTargetException e) {
      Throwable cause = e.getCause();
      // Trim the stack trace to omit stuff dealing with the client framework.
      List<StackTraceElement> trace = new ArrayList<StackTraceElement>();
      for (StackTraceElement elt : cause.getStackTrace())
        trace.add(elt);

      for (ListIterator<StackTraceElement> it =
          trace.listIterator(trace.size()); it.hasPrevious();) {
        StackTraceElement elt = it.previous();
        if (elt.getClassName().equals(args[0] + "$$Impl")) break;
        it.remove();
      }

      StackTraceElement[] traceArray = new StackTraceElement[trace.size()];
      cause.setStackTrace(trace.toArray(traceArray));
      throw cause;
    }
  }
}
