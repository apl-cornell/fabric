package fabric.worker.remote;

import fabric.common.AbstractMessageHandlerThread;
import fabric.lang.NodePrincipal;

public final class SessionAttributes extends
    AbstractMessageHandlerThread.SessionAttributes {

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

  SessionAttributes(String workerName, String workerPrincipalName, NodePrincipal worker) {
    this.isDissemConnection = false;
    this.remoteNodeName = workerName;
    this.remoteNodePrincipalName = workerPrincipalName;
    this.remotePrincipal = worker;
  }
}
