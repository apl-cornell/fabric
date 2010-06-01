package fabric.store;

import static fabric.common.Logging.NETWORK_CONNECTION_LOGGER;

import java.net.SocketAddress;
import java.util.logging.Level;

import fabric.lang.security.NodePrincipal;
import fabric.net.AbstractConnectionHandler;

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
    NETWORK_CONNECTION_LOGGER
        .info("Store rejected connection: remote worker failed authentication.");
  }

  @Override
  protected void logSession(SocketAddress remote, SessionAttributes session) {
    NETWORK_CONNECTION_LOGGER.log(Level.INFO, "Store " + session.store.name
        + " accepted connection from {0}", remote);

    if (session.workerIsDissem) {
      NETWORK_CONNECTION_LOGGER
          .info("Remote worker connected as dissemination node");
    } else {
      NETWORK_CONNECTION_LOGGER.info("Remote worker principal is "
          + session.workerPrincipalName
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
