package fabric.client;

import fabric.common.FetchException;
import fabric.common.ObjectGroup;
import fabric.dissemination.FetchManager;

/**
 * This simple FetchManger always goes directly to the core.
 */
public class DirectFetchManager implements FetchManager {

  public ObjectGroup fetch(RemoteCore c, long onum) throws FetchException {
    return c.readObjectFromCore(onum);
  }
  
  public void destroy() {}

}
