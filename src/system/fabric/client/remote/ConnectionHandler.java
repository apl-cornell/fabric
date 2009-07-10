package fabric.client.remote;

import java.net.SocketAddress;
import java.util.Collections;

import fabric.client.Client;
import fabric.common.AbstractConnectionHandler;
import fabric.lang.NodePrincipal;

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
    AbstractConnectionHandler<Object, SessionAttributes, Worker> {

  private static final int POOL_SIZE = 10;

  public ConnectionHandler(RemoteCallManager rcm) {
    super(POOL_SIZE, new Worker.Factory(rcm));
  }

  @Override
  protected Object getNodeByName(String name) {
    // If client exists, return a dummy object -- any will do.
    if (Client.getClient().name.equalsIgnoreCase(name))
      return Collections.EMPTY_LIST;
    
    return null;
  }

  @Override
  protected String getThreadName(SocketAddress remote, SessionAttributes session) {
    return "Connection handler for " + session.clientName
        + " talking to local client";
  }

  @Override
  protected void logAuthenticationFailure() {
    Worker.logger.info("Client rejected connection: authentication failed.");
  }

  @Override
  protected void logSession(SocketAddress remote, SessionAttributes session) {
    Worker.logger.info("Client accepted connection from " + session.clientName
        + " at " + remote);
  }

  @Override
  protected SessionAttributes newAuthenticatedSession(Object node,
      String remoteNodeName, NodePrincipal remoteNodePrincipal) {
    return new SessionAttributes(remoteNodeName, remoteNodePrincipal);
  }
}
