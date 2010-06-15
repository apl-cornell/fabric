package fabric.store;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import fabric.worker.Worker;
import fabric.worker.RemoteStore;
import fabric.common.ONumConstants;
import fabric.common.exceptions.InternalError;
import fabric.common.net.SubServerSocketFactory;
import fabric.common.net.handshake.HandshakeImpl;
import fabric.common.net.handshake.HandshakeProtocol;
import fabric.common.net.naming.NameService;
import fabric.common.net.naming.PropertyNameService;
import fabric.common.net.naming.PropertyNameService.PortType;

/**
 * The Node class encapsulates the shared resources for multiple stores and
 * workers that run in the same process. It also acts as a container for those
 * stores and workers. It is responsible for setting them up and tearing them
 * down.
 * 
 * @author mdgeorge
 */
public class Node {

  //
  // Note: Although this interface is designed for multiple stores, in the
  // current implementation we only allow a single store per process.
  //
  
  private final Store store;

  private final SubServerSocketFactory factory;

  public Node(Options opts) {
    try {
      this.store = new Store(this, opts.storeName);

      HandshakeProtocol handshake = new HandshakeImpl();
      NameService nameService = new PropertyNameService(PortType.STORE);

      this.factory = new SubServerSocketFactory(handshake, nameService);

    } catch (final IOException e) {
      throw new InternalError("Failed to intialize Node", e);
    }
  }

  /** Setup all workers and stores. */
  public void initialize() {
    try {
      // Initialize the worker before instantiating the stores in case their
      // object databases need initialization. (The initialization code will be
      // run on the worker.)
      Map<String, RemoteStore> initStoreSet =
          Collections.singletonMap(store.name,
              (RemoteStore) new InProcessStore(store.name, store));

      Worker.initialize(store.name, "fab://" + store.name + "/"
          + ONumConstants.STORE_PRINCIPAL, initStoreSet);

      // initialize the store
      store.initialize();

      // give some stdout output that scripts can use to know we're started.
      System.out.println("Store started");
    } catch (Exception e) {
      throw new InternalError(e);
    }
  }

  /** Launch all workers and stores. */
  public void run() {
    store.run();
  }

  /** Gracefully tear down all workers and stores. */
  public void shutdown() {
    store.shutdown();
  }

  //////////////////////////////////////////////////////////////////////////////
  // public accessors                                                         //
  //////////////////////////////////////////////////////////////////////////////
  
  /** Returns the store corresponding to the given name, or null if none exists. */
  public Store getStore(String name) {
    return name.equals(store.name) ? store : null;
  }

  /** Return the server socket factory associated with this node. */
  public SubServerSocketFactory getServerSocketFactory() {
    return this.factory;
  }

}
