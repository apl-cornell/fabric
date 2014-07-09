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
package fabric.dissemination.pastry;

import java.io.IOException;
import java.util.Properties;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.dissemination.AbstractGlob;
import fabric.dissemination.Cache;
import fabric.dissemination.DummyFetchManager;
import fabric.dissemination.FetchManager;
import fabric.dissemination.ObjectGlob;
import fabric.worker.RemoteStore;
import fabric.worker.Worker;

/**
 * A PastryFetchManager performs object fetching by consulting a pastry
 * dissemination network to see if the object is available there. When an
 * instance of PastryFetchManager is created, it starts a pastry node. That node
 * will attempt to join a pastry network by contacting a bootstrap node. The
 * bootstrap node is configured by setting the property
 * fabric.dissemination.pastry.bootstrap in the Properties object given to the
 * constructor.
 */
public class PastryFetchManager implements FetchManager {

  private final Node node;
  private final DummyFetchManager fallback;

  public PastryFetchManager(Worker worker, Properties dissemConfig) {
    try {
      Cache cache = new Cache();
      this.fallback = new DummyFetchManager(worker, dissemConfig, cache);
      this.node = new Node(dissemConfig, cache); // start a new pastry node
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public ObjectGroup fetch(RemoteStore c, long onum) throws AccessException {
    ObjectGlob glob;
    try {
      glob = node.disseminator().fetch(c, onum);
    } catch (DisseminationTimeoutException e) {
      glob = null;
    }

    if (glob == null) {
      return fallback.fetch(c, onum);
    }

    return glob.decrypt();
  }

  @Override
  public void destroy() {
    node.destroy();
  }

  @Override
  public boolean updateCaches(RemoteStore store, long onum,
      AbstractGlob<?> update) {
    return node.disseminator.updateCaches(store, onum, update);
  }

}
