package fabric.store;

import java.net.SocketAddress;
import java.util.logging.Logger;

import fabric.common.AbstractConnectionHandler;
import fabric.lang.security.NodePrincipal;

/**
 * <p>
 * Manages a connection with a remote worker. The connection is used to receive
 * requests from and to send responses to the node.
 * </p>
 * <p>
 * XXX Assumes connections never get dropped.
 * </p>
 */
class ConnectionHandler extends
    AbstractConnectionHandler<Node.Store, SessionAttributes, MessageHandlerThread> {
  private final Node node;
  private static final Logger LOGGER = Logger.getLogger("fabric.store.worker");

  public ConnectionHandler(Node node) {
    super(node.opts.threadPool, new MessageHandlerThread.Factory());
    this.node = node;
  }

  @Override
  protected Node.Store getNodeByName(String name) {
    return node.getStore(name);
  }

  @Override
  protected SessionAttributes newAuthenticatedSession(Node.Store store,
      String workerName, String workerPrincipalName,
      NodePrincipal workerPrincipal) {
    return new SessionAttributes(store, workerName, workerPrincipalName,
        workerPrincipal);
  }

  @Override
  protected SessionAttributes newUnauthenticatedSession(Node.Store store,
      String workerName) {
    return new SessionAttributes(store, workerName);
  }

  @Override
  protected void logAuthenticationFailure() {
    LOGGER.info("Store rejected connection: worker failed authentication.");
  }

  @Override
  protected void logSession(SocketAddress remote, SessionAttributes session) {
    LOGGER.info("Store " + session.store.name + " accepted connection from "
        + remote);

    if (session.workerIsDissem) {
      LOGGER.info("Worker connected as dissemination node");
    } else {
      LOGGER.info("Worker principal is " + session.workerPrincipalName
          + (session.workerPrincipal == null ? " (acting as null)" : ""));
    }
  }

  @Override
  protected String getThreadName(SocketAddress remote, SessionAttributes session) {
    if (session.workerIsDissem) {
      return "Connection handler for dissemination node at " + remote
          + " talking to store " + session.store.name;
    }

    return "Connection handler for worker " + session.workerPrincipalName
        + " talking to store " + session.store.name;

  }
}
