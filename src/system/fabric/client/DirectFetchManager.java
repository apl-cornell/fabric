package fabric.client;

import fabric.common.AccessError;
import fabric.dissemination.FetchManager;
import fabric.dissemination.Glob;

/**
 * This simple FetchManger always goes directly to the core.
 */
public class DirectFetchManager implements FetchManager {

  public Glob fetch(RemoteCore c, long onum) throws AccessError,
      UnreachableCoreException {
    return c.readObjectFromCore(onum);
  }
  
  public void destroy() {}

}
