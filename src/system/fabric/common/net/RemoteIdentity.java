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
package fabric.common.net;

import fabric.lang.security.Principal;
import fabric.net.RemoteNode;

/**
 * A pair consisting of a remote node and its authenticated principal, if any. 
 */
public class RemoteIdentity<Node extends RemoteNode<Node>> {
  /**
   * The remote node. Can be used to contact the remote endpoint.
   */
  public final Node node;

  /**
   * The principal under which the remote node is authenticated. Can be null if
   * the remote node was not authenticated.
   */
  public final Principal principal;

  public RemoteIdentity(Node node, Principal principal) {
    this.node = node;
    this.principal = principal;
  }

  @Override
  public String toString() {
    String principalName;
    if (principal == null) {
      principalName = "not authenticated";
    } else {
      principalName = principal.$getStore().name() + "/" + principal.$getOnum();
    }

    return node.name() + " (" + principalName + ")";
  }
}
