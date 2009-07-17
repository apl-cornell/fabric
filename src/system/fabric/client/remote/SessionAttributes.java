package fabric.client.remote;

import fabric.common.AbstractWorkerThread;
import fabric.lang.NodePrincipal;

public final class SessionAttributes implements
    AbstractWorkerThread.SessionAttributes {

  /**
   * Whether this is a connection for dissemination updates.
   */
  final boolean isDissemConnection;

  /**
   * The name of the remote node that is being served.
   */
  final String remoteNodeName;
  
  /**
   * The name of the remote node principal.
   */
  final String remoteNodePrincipalName;

  /**
   * The remote node's principal object.
   */
  final NodePrincipal remotePrincipal;

  SessionAttributes(String remoteNodeName) {
    this.isDissemConnection = true;
    this.remoteNodeName = remoteNodeName;
    this.remoteNodePrincipalName = null;
    this.remotePrincipal = null;
  }

  SessionAttributes(String clientName, String clientPrincipalName, NodePrincipal client) {
    this.isDissemConnection = false;
    this.remoteNodeName = clientName;
    this.remoteNodePrincipalName = clientPrincipalName;
    this.remotePrincipal = client;
  }
}
