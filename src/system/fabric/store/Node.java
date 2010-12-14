package fabric.store;

import static fabric.common.Logging.STORE_LOGGER;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;

import fabric.common.ONumConstants;
import fabric.common.Version;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.TerminationException;
import fabric.common.exceptions.UsageError;
import fabric.common.net.SubServerSocketFactory;
import fabric.common.net.handshake.HandshakeImpl;
import fabric.common.net.handshake.HandshakeProtocol;
import fabric.common.net.naming.DefaultNameService;
import fabric.common.net.naming.DefaultNameService.PortType;
import fabric.common.net.naming.NameService;
import fabric.worker.RemoteStore;
import fabric.worker.Worker;

/**
 * The Node class encapsulates the shared resources for multiple stores and
 * workers that run in the same process. It also acts as a container for those
 * stores and workers. It is responsible for setting them up and tearing them
 * down.
 * 
 * @author mdgeorge
 */
public class Node {

  //////////////////////////////////////////////////////////////////////////////
  // store invocation                                                         //
  //////////////////////////////////////////////////////////////////////////////
  
  /** Main method.  Calls {@link start} and outputs errors nicely. */
  public static void main(String[] args) {
    try {
      start(args);
    } catch (TerminationException te) {
      if (te.getMessage() != null)
        (te.exitCode == 0 ? System.out : System.err).println(te.getMessage());

      System.exit(te.exitCode);
    }
  }

  
  /**
   * Main entry point for the store.  This method is useful for applications
   * that wish to embed a fabric store.
   * 
   * @throws TerminationException to indicate that the store is shutting down
   */
  public static void start(String args[]) throws TerminationException {
    STORE_LOGGER.info("Store node");
    STORE_LOGGER.log(Level.CONFIG, "Fabric version {0}", new Version());
    STORE_LOGGER.info("");

    // Parse the command-line options.
    Options opts;
    try {
      opts = new Options(args);
    } catch (UsageError ue) {
      printUsage(ue);
      throw new TerminationException(ue.exitCode);
    }
    
    // Start up store-node services.
    final Node node = new Node(opts);

    // initialize
    node.initialize();

    // register a hook to shut down gracefully.
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override public void run() { node.shutdown(); }
    });

    // run
    node.run();
  }
  
  private static void printUsage(UsageError ue) {
    PrintStream out = ue.exitCode == 0 ? System.out : System.err;
    if (ue.getMessage() != null && ue.getMessage().length() > 0) {
      out.println(ue.getMessage());
      out.println();
    }

    Options.usage(out);
  }

  //////////////////////////////////////////////////////////////////////////////
  // setup and shutdown                                                       //
  //////////////////////////////////////////////////////////////////////////////
  
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
      NameService nameService = new DefaultNameService(PortType.STORE);

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

  /** Return the server socket factory associated with this node. */
  public SubServerSocketFactory getServerSocketFactory() {
    return this.factory;
  }

}
