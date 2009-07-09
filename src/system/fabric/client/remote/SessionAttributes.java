package fabric.client.remote;

import fabric.common.AbstractWorkerThread;
import fabric.lang.NodePrincipal;

public final class SessionAttributes implements
    AbstractWorkerThread.SessionAttributes {

  /**
   * The name of the client that is being served.
   */
  final String clientName;

  /**
   * The client's principal object.
   */
  final NodePrincipal client;

  SessionAttributes(String clientName, NodePrincipal client) {
    this.clientName = clientName;
    this.client = client;
  }
}
