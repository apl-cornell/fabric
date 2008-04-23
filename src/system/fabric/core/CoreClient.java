package fabric.core;

import java.security.KeyStore;
import java.security.UnrecoverableKeyException;

import fabric.client.Client;
import fabric.client.RemoteCore;
import fabric.common.InternalError;
import fabric.core.Node.Core;

public class CoreClient extends Client {
  
  /** The node this CoreClient is attached to. */
  protected final Node node;

  protected CoreClient(KeyStore keyStore, char[] passwd, KeyStore trustStore,
      int maxConnections, int timeout, int retries, boolean useSSL, Node node)
      throws InternalError, UnrecoverableKeyException {
    super(keyStore, passwd, trustStore, maxConnections, timeout, retries, useSSL);
    
    this.node = node;
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
   */
  public static Client initialize(KeyStore keyStore, char[] passwd,
      KeyStore trustStore, int maxConnections, int timeout, int retries,
      boolean useSSL, Node node) throws InternalError, UnrecoverableKeyException,
      IllegalStateException {
    
    if (instance != null)
      throw new IllegalStateException(
          "The Fabric client has already been initialized");
    logger.info("Initializing Fabric client");
    instance =
        new CoreClient(keyStore, passwd, trustStore, maxConnections, timeout,
            retries, useSSL, node);
    return instance;
  }

  /**
   * Returns the core corresponding to the given name. It is however, an error
   * to request a core not on the node in which this CoreClient is running.
   * 
   * @param name
   *                The core's host name.
   * @return The corresponding <code>Core</code> object.
   */
  @Override
  public RemoteCore getCore(String name) {
    RemoteCore result = cores.get(name);
    
    if (result == null) {
      Core c = node.getCore(name);
      
      if (c != null) {
        result = new InProcessCore(name, c);
        cores.put(name, result);
      }
    }
    
    return result;
  }

}
