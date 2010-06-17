package fabric.worker.remote;

import fabric.common.exceptions.NotImplementedException;
import fabric.lang.security.NodePrincipal;

public final class SessionAttributes  {

  /**
   * The name of the remote node that is being served.
   */
  public final String remoteNodeName;
  public final NodePrincipal remotePrincipal;
  
  SessionAttributes(String remoteNodeName) {
    throw new NotImplementedException();
  }

  SessionAttributes(String workerName, String workerPrincipalName, NodePrincipal worker) {
    throw new NotImplementedException();
  }
}
