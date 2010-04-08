package fabric.worker.remote;

import java.net.SocketAddress;
import java.util.Collections;

import fabric.worker.Worker;
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
    MessageHandlerThread.logger.info("Worker rejected connection: authentication failed.");
  }

  @Override
  protected void logSession(SocketAddress remote, SessionAttributes session) {
    MessageHandlerThread.logger.info("Worker accepted connection from "
        + session.remoteNodeName + " at " + remote);
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
