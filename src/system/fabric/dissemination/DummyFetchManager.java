/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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

import java.util.Properties;

import fabric.common.ObjectGroup;
import fabric.worker.RemoteStore;
import fabric.worker.Worker;

/**
 * This simple FetchManger always goes directly to the store.
 */
public class DummyFetchManager implements FetchManager {

  private Cache cache;

  public DummyFetchManager(Worker worker, Properties dissemConfig) {
    this.cache = new Cache();
  }

  @Override
  public ObjectGroup fetch(RemoteStore store, long onum) {
    return cache.get(store, onum, true).decrypt(store);
  }

  @Override
  public void destroy() {
  }

}
