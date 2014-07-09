/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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
package fabric.common.net.handshake;

import java.net.Socket;

import fabric.common.net.RemoteIdentity;
import fabric.net.RemoteNode;

/**
 * @param <Node> the type of node at the remote endpoint.
 */
public class ShakenSocket<Node extends RemoteNode<Node>> {
  /**
   * The name of the virtual server that received the connection.
   */
  public final String name;

  public final RemoteIdentity<Node> remoteIdentity;
  public final Socket sock;

  public ShakenSocket(String name, RemoteIdentity<Node> remoteIdentity,
      Socket sock) {
    this.name = name;
    this.remoteIdentity = remoteIdentity;
    this.sock = sock;
  }
}
