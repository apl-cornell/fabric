package fabric.store;

import fabric.lang.security.NodePrincipal;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

final class SessionAttributes  {
  /**
   * Whether the worker is a dissemination node.
   */
  final boolean workerIsDissem;

  /**
   * The store with which the worker is interacting.
   */
  final Store store;

  /**
   * The name of the remote principal.
   */
  final String workerPrincipalName;

  /**
   * The worker's principal object.
   */
  final NodePrincipal workerPrincipal;
  
  /**
   * The worker's node
   */
  final RemoteWorker remoteNode;

  /**
   * Constructs a SessionAttributes object corresponding to a dissemination
   * node.
   */
  SessionAttributes(Store store, String workerName) {
    this.workerIsDissem = true;
    this.store = store;
    this.workerPrincipalName = null;
    this.workerPrincipal = null;
    this.remoteNode      = Worker.getWorker().getWorker(workerName);

  }

  /**
   * Constructs a SessionAttributes object corresponding to a worker node.
   */
 SessionAttributes(Store store, String workerName,
      String workerPrincipalName, NodePrincipal worker) {
    this.workerIsDissem = false;
    this.store = store;
    this.workerPrincipalName = workerPrincipalName;
    this.workerPrincipal = worker;
    this.remoteNode      = Worker.getWorker().getWorker(workerName);

  }
}
