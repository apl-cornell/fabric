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
package fabric.net;

import fabric.common.exceptions.FabricRuntimeException;

/**
 * An <code>UnreachableNodeException</code> is used to indicate that an attempt
 * to connect to a remote node has failed.
 */
public class UnreachableNodeException extends FabricRuntimeException {
  private RemoteNode node;

  public UnreachableNodeException(RemoteNode node) {
    this.node = node;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Throwable#getMessage()
   */
  @Override
  public String getMessage() {
    return "Unreachable node: " + node;
  }

}
