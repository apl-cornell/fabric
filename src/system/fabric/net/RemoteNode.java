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

import java.io.IOException;
import java.io.Serializable;

import fabric.common.exceptions.InternalError;
import fabric.common.net.naming.SocketAddress;

/**
 * Abstracts remote stores and remote workers.
 */
public abstract class RemoteNode implements Serializable {
  /**
   * The DNS hostname of the node.
   */
  public final String name;

  /**
   * Whether the remote node supports unencrypted connections.
   */
  private transient final boolean supportsUnencrypted;

  /**
   * For communicating with the node over an SSL connection.
   */
  private transient CommManager sslCommManager;

  /**
   * For communicating with the node over an unencrypted connection.
   */
  private transient CommManager unencryptedCommManager;

  protected RemoteNode(String name, boolean supportsUnencrypted) {
    this.name = name;
    this.sslCommManager = null;
    this.unencryptedCommManager = null;
    this.supportsUnencrypted = supportsUnencrypted;
  }

  /**
   * @return
   */
  protected abstract SocketAddress lookup() throws IOException;
  
  /**
   * @return the node's hostname.
   */
  public final String name() {
    return name;
  }

  /**
   * @param useSSL
   *          Whether SSL is being used. This is ignored if the node type
   *          doesn't support non-SSL connections.
   * @return the data I/O stream pair to use for communicating with the node.
   */
  public final Stream openStream(
      boolean useSSL) {
    if (useSSL) {
      if (sslCommManager == null) sslCommManager = new CommManager(this, true);
      return sslCommManager.openStream();
    }

    if (!supportsUnencrypted)
      throw new InternalError(
          "Attempted to establish an unencrypted connection to a node that "
              + "does not support it.");

    if (unencryptedCommManager == null)
      unencryptedCommManager = new CommManager(this, false);
    return unencryptedCommManager.openStream();
  }

  public void cleanup() {
    if (sslCommManager != null) sslCommManager.shutdown();
    if (unencryptedCommManager != null) unencryptedCommManager.shutdown();
  }
}
