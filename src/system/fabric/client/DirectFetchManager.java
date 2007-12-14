package fabric.client;

import fabric.common.AccessError;
import fabric.lang.Object.$Impl;

/**
 * This simple FetchManger always goes directly to the core.
 */
public class DirectFetchManager implements FetchManager {

  public $Impl fetch(Core c, long onum) throws AccessError,
      UnreachableCoreException {
    return c.readObject(onum);
  }
  
  public void destroy() {}

}
