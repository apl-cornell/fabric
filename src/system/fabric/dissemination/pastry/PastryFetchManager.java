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
package fabric.dissemination.pastry;

import java.io.IOException;
import java.util.Properties;

import fabric.worker.Worker;
import fabric.worker.RemoteStore;
import fabric.common.ObjectGroup;
import fabric.common.exceptions.FetchException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.Cache;
import fabric.dissemination.FetchManager;
import fabric.dissemination.Glob;

/**
 * A PastryFetchManager performs object fetching by consulting a pastry
 * dissemination network to see if the object is available there. When an
 * instance of PastryFetchManager is created, it starts a pastry node. That
 * node will attempt to join a pastry network by contacting a bootstrap node.
 * This is set in the pastry configuration file (by default etc/pastry.params).
 */
public class PastryFetchManager implements FetchManager {
  
  private Node node;
  
  public PastryFetchManager(Worker worker, Properties dissemConfig) {
    try {
      node = new Node(dissemConfig);  // start a new pastry node
      worker.registerDisseminationCache(node.disseminator.cache);
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  public ObjectGroup fetch(RemoteStore c, long onum) throws FetchException {
    Glob glob;
    try {
      glob = node.disseminator().fetch(c, onum);
    } catch (DisseminationTimeoutException e) {
      glob = null;
    }
    
    if (glob == null) return c.readObjectFromStore(onum);
    
    return glob.decrypt(c);
  }
  
  public void destroy() {
    node.destroy();
    Cache.Collector.shutdown();
  }

}
