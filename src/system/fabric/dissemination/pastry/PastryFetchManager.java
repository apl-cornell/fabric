package fabric.dissemination.pastry;

import java.io.IOException;

import fabric.client.RemoteCore;
import fabric.client.UnreachableCoreException;
import fabric.common.AccessError;
import fabric.common.InternalError;
import fabric.dissemination.FetchManager;
import fabric.dissemination.Glob;

public class PastryFetchManager implements FetchManager {
  
  private Node node;
  
  public PastryFetchManager() {
    try {
      node = new Node();
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  public Glob fetch(RemoteCore c, long onum) throws AccessError,
      UnreachableCoreException {
    try {
      return node.disseminator().fetch(c, onum);
    } catch (UnreachableCoreException e) {
      return c.readObjectFromCore(onum);
    }
  }
  
  public void destroy() {
    node.destroy();
  }

}
