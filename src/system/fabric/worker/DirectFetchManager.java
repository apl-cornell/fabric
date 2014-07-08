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
package fabric.worker;

import java.util.Properties;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.FetchException;
import fabric.dissemination.FetchManager;

/**
 * This simple FetchManger always goes directly to the store.
 */
public class DirectFetchManager implements FetchManager {

  public DirectFetchManager(Worker worker, Properties dissemConfig) {
  }

  public ObjectGroup fetch(RemoteStore store, long onum) throws FetchException {
    return store.readObjectFromStore(onum);
  }

  public void destroy() {
  }

}
