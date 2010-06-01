package fabric.worker.remote;

import static fabric.common.Logging.NETWORK_CONNECTION_LOGGER;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.lang.security.NodePrincipal;
import fabric.net.AbstractConnectionHandler;
import fabric.worker.Worker;

/**
 * <p>
 * Manages a connection with a remote worker. The connection is used to receive
 * requests from and to send responses to the node.
 * </p>
 * <p>
 * XXX Assumes connections never get dropped.
 * </p>
 */
public class ConnectionHandler extends
    AbstractConnectionHandler<Object, SessionAttributes, MessageHandlerThread> {

  private static final int POOL_SIZE = 10;

  public ConnectionHandler(RemoteCallManager rcm) {
    super(POOL_SIZE, new MessageHandlerThread.Factory(rcm));
  }

  @Override
  protected Object getNodeByName(String name) {
    // If worker exists, return a dummy object -- any will do.
    if (Worker.getWorker().name.equalsIgnoreCase(name))
      return Collections.EMPTY_LIST;

    return null;
  }

  @Override
  protected String getThreadName(SocketAddress remote, SessionAttributes session) {
    return "Connection handler for " + session.remoteNodeName
        + " talking to local worker";
  }

  @Override
  protected void logAuthenticationFailure() {
    NETWORK_CONNECTION_LOGGER
        .info("Worker rejected connection: authentication failed.");
  }

  @Override
  protected void logSession(SocketAddress remote, SessionAttributes session) {
    Logging.log(NETWORK_CONNECTION_LOGGER, Level.INFO,
        "Worker accepted connection from {0} at {1}", session.remoteNodeName,
        remote);
  }

  @Override
  protected SessionAttributes newAuthenticatedSession(Object node,
      String remoteNodeName, String remoteNodePrincipalName,
      NodePrincipal remoteNodePrincipal) {
    return new SessionAttributes(remoteNodeName, remoteNodePrincipalName,
        remoteNodePrincipal);
  }

  @Override
  protected SessionAttributes newUnauthenticatedSession(Object node,
      String remoteNodeName) {
    return new SessionAttributes(remoteNodeName);
  }
}
