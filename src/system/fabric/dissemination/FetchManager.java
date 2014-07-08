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
package fabric.dissemination;

import fabric.worker.RemoteStore;
import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FetchException;
import fabric.net.UnreachableNodeException;

/**
 * A FetchManager is responsible for retrieving objects from Stores. Workers
 * may load different FetchManagers at run time to make use of different
 * dissemination networks.
 */
public interface FetchManager {
  
  /**
   * Fetches the glob identified by the given onum, located at the given store.
   * 
   * @param store
   *                the store.
   * @param onum
   *                the object identifier.
   * @return the requested glob if fetch was successful.
   * @throws AccessException
   * @throws UnreachableNodeException
   */
  public ObjectGroup fetch(RemoteStore store, long onum) throws FetchException;

  /**
   * Called to destroy and clean up the fetch manager.
   */
  public void destroy();
  
}
