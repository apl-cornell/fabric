package fabric.dissemination.pastry;

import java.io.IOException;

import fabric.client.RemoteCore;
import fabric.common.FetchException;
import fabric.common.InternalError;
import fabric.common.ObjectGroup;
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
  
  public PastryFetchManager() {
    try {
      node = new Node();  // start a new pastry node
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  public ObjectGroup fetch(RemoteCore c, long onum) throws FetchException {
    Glob glob;
    try {
      glob = node.disseminator().fetch(c, onum);
    } catch (DisseminationTimeoutException e) {
      glob = null;
    }
    
    if (glob == null) return c.readObjectFromCore(onum);
    
    return glob.decrypt(c);
  }
  
  public void destroy() {
    node.destroy();
  }

}
