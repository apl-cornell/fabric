/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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
package fabric.worker.remote;

import fabric.common.AbstractMessageHandlerThread;
import fabric.lang.security.NodePrincipal;

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
