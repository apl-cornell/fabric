package fabric.core;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;

import fabric.client.Client;
import fabric.common.InternalError;
import fabric.common.Resources;

public class CoreClient extends Client {
  
  /** The node this CoreClient is attached to. */
  protected final Node node;

  protected CoreClient(KeyStore keyStore, char[] passwd, KeyStore trustStore,
      int maxConnections, int timeout, int retries, boolean useSSL, Node node)
      throws InternalError, UnrecoverableKeyException {
    super(keyStore, passwd, trustStore, maxConnections, timeout, retries,
        useSSL, "fabric.client.DirectFetchManager");
    
    this.node = node;
    
    for (String s : node.cores.keySet()) {
      cores.put(s, new InProcessCore(s, node.cores.get(s)));
    }
  }

  public static void initialize(Node node) throws IOException, KeyStoreException,
      NoSuchAlgorithmException, CertificateException,
      UnrecoverableKeyException, IllegalStateException, InternalError {
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
    in = Resources.readFile("etc/keys", filename);
    keyStore.load(in, passwd.toCharArray());
    in.close();

    KeyStore trustStore = KeyStore.getInstance("JKS");
    String trustPass = System.getProperty("fabric.client.trustpassword");
    String trustFile =
        System.getProperty("fabric.client.trustfilename", "trust.keystore");
    in = Resources.readFile("etc/keys", trustFile);
    trustStore.load(in, trustPass.toCharArray());
    in.close();

    int maxConnections = Integer.parseInt(
        System.getProperty("fabric.client.maxConnections", "50"));
    int timeout = Integer.parseInt(
        System.getProperty("fabric.client.timeout", "2"));
    int retries = Integer.parseInt(
        System.getProperty("fabric.client.retries", "5"));

    boolean useSSL = Boolean.parseBoolean(
        System.getProperty("fabric.client.useSSL", "true"));

    initialize(keyStore, passwd.toCharArray(), trustStore, maxConnections,
        timeout, retries, useSSL, node);
  }

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
   * @param Node
   *                The node where this CoreClient is running.
   */
  public static Client initialize(KeyStore keyStore, char[] passwd,
      KeyStore trustStore, int maxConnections, int timeout, int retries,
      boolean useSSL, Node node) throws InternalError, UnrecoverableKeyException,
      IllegalStateException {
    
    if (instance != null)
      throw new IllegalStateException(
          "The Fabric client has already been initialized");
    log.info("Initializing Fabric client");
    instance =
        new CoreClient(keyStore, passwd, trustStore, maxConnections, timeout,
            retries, useSSL, node);
    return instance;
  }

}
