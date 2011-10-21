package fabric.dissemination.pastry;

import java.io.IOException;
import java.util.Properties;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.dissemination.FetchManager;
import fabric.dissemination.Glob;
import fabric.worker.RemoteStore;
import fabric.worker.Worker;

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

  @Override
  public ObjectGroup fetch(RemoteStore c, long onum) throws AccessException {
    Glob glob;
    try {
      glob = node.disseminator().fetch(c, onum);
    } catch (DisseminationTimeoutException e) {
      glob = null;
    }
    
    if (glob == null) return c.readObjectFromStore(onum);
    
    return glob.decrypt(c);
  }
  
  @Override
  public void destroy() {
    node.destroy();
  }

}
