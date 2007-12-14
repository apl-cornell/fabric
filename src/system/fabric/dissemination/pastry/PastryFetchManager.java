package fabric.dissemination.pastry;

import java.io.IOException;

import fabric.client.Core;
import fabric.client.FetchManager;
import fabric.client.UnreachableCoreException;
import fabric.common.AccessError;
import fabric.lang.Object.$Impl;

public class PastryFetchManager implements FetchManager {
  
  private Node node;
  
  public PastryFetchManager() {
    try {
      node = new Node();
    } catch (IOException e) {}
  }

  public $Impl fetch(Core c, long onum) throws AccessError,
      UnreachableCoreException {
    return node.disseminator().fetch(c, onum);
  }
  
  public void destroy() {
    node.destroy();
  }

}
