package fabric.core;

import java.security.PrivateKey;

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
   * The name of the client that is being served.
   */
  final String clientName;

  /**
   * The client's principal object.
   */
  final NodePrincipal client;

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
  SessionAttributes(Node.Core core) {
    this.clientIsDissem = true;
    this.core = core;
    this.clientName = null;
    this.client = null;
    this.privateKey = core.privateKey;
  }

  /**
   * Constructs a SessionAttributes object corresponding to a client node.
   */
  SessionAttributes(Node.Core core, String clientName, NodePrincipal client) {
    this.clientIsDissem = false;
    this.core = core;
    this.clientName = clientName;
    this.client = client;
    this.privateKey = core.privateKey;
  }
}
