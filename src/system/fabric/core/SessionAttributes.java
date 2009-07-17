package fabric.core;

import java.security.PrivateKey;

import fabric.client.Client;
import fabric.client.remote.RemoteClient;
import fabric.common.AbstractWorkerThread;
import fabric.lang.NodePrincipal;

final class SessionAttributes implements AbstractWorkerThread.SessionAttributes {
  /**
   * Whether the client is a dissemination node.
   */
  final boolean clientIsDissem;

  /**
   * The core with which the client is interacting.
   */
  final Node.Core core;

  /**
   * The remote client node.
   */
  final RemoteClient remoteNode;

  /**
   * The name the remote principal.
   */
  final String clientPrincipalName;

  /**
   * The client's principal object.
   */
  final NodePrincipal clientPrincipal;

  /**
   * The private signing key for the core with which the client is interacting.
   * XXX This is currently the SSL private key. Should use the core's
   * NodePrincipal's key instead.
   */
  final PrivateKey privateKey;

  /**
   * Constructs a SessionAttributes object corresponding to a dissemination
   * node.
   */
  SessionAttributes(Node.Core core, String clientName) {
    this.clientIsDissem = true;
    this.core = core;
    this.remoteNode = Client.getClient().getClient(clientName);
    this.clientPrincipalName = null;
    this.clientPrincipal = null;
    this.privateKey = core.privateKey;
  }

  /**
   * Constructs a SessionAttributes object corresponding to a client node.
   */
  SessionAttributes(Node.Core core, String clientName,
      String clientPrincipalName, NodePrincipal client) {
    this.clientIsDissem = false;
    this.core = core;
    this.remoteNode = Client.getClient().getClient(clientName);
    this.clientPrincipalName = clientPrincipalName;
    this.clientPrincipal = client;
    this.privateKey = core.privateKey;
  }
}
