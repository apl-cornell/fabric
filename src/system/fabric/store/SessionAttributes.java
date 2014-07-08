/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.store;

import fabric.lang.security.NodePrincipal;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

final class SessionAttributes {
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
    this.remoteNode = Worker.getWorker().getWorker(workerName);

  }

  /**
   * Constructs a SessionAttributes object corresponding to a worker node.
   */
  SessionAttributes(Store store, String workerName, String workerPrincipalName,
      NodePrincipal worker) {
    this.workerIsDissem = false;
    this.store = store;
    this.workerPrincipalName = workerPrincipalName;
    this.workerPrincipal = worker;
    this.remoteNode = Worker.getWorker().getWorker(workerName);

  }
}
