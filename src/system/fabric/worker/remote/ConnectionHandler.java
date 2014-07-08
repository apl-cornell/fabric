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

import static fabric.common.Logging.NETWORK_CONNECTION_LOGGER;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.logging.Level;

import fabric.common.AbstractConnectionHandler;
import fabric.common.Logging;
import fabric.lang.security.NodePrincipal;
import fabric.worker.Worker;

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
    AbstractConnectionHandler<Object, SessionAttributes, MessageHandlerThread> {

  private static final int POOL_SIZE = 10;

  public ConnectionHandler(RemoteCallManager rcm) {
    super(POOL_SIZE, new MessageHandlerThread.Factory(rcm));
  }

  @Override
  protected Object getNodeByName(String name) {
    // If worker exists, return a dummy object -- any will do.
    if (Worker.getWorker().name.equalsIgnoreCase(name))
      return Collections.EMPTY_LIST;

    return null;
  }

  @Override
  protected String getThreadName(SocketAddress remote, SessionAttributes session) {
    return "Connection handler for " + session.remoteNodeName
        + " talking to local worker";
  }

  @Override
  protected void logAuthenticationFailure() {
    NETWORK_CONNECTION_LOGGER
        .info("Worker rejected connection: authentication failed.");
  }

  @Override
  protected void logSession(SocketAddress remote, SessionAttributes session) {
    Logging.log(NETWORK_CONNECTION_LOGGER, Level.INFO,
        "Worker accepted connection from {0} at {1}", session.remoteNodeName,
        remote);
  }

  @Override
  protected SessionAttributes newAuthenticatedSession(Object node,
      String remoteNodeName, String remoteNodePrincipalName,
      NodePrincipal remoteNodePrincipal) {
    return new SessionAttributes(remoteNodeName, remoteNodePrincipalName,
        remoteNodePrincipal);
  }

  @Override
  protected SessionAttributes newUnauthenticatedSession(Object node,
      String remoteNodeName) {
    return new SessionAttributes(remoteNodeName);
  }
}
