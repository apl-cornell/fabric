package fabric.client;

import fabric.common.AccessError;
import fabric.lang.Object.$Impl;

/**
 * A FetchManager is responsible for retrieving objects from Cores. Clients
 * may load different FetchManagers at run time to make use of different
 * dissemination networks.
 */
public interface FetchManager {
  
  /**
   * Fetches the object identified by onum located at core c.
   * 
   * @param c
   *                the core.
   * @param onum
   *                the object identifier.
   * @return the requested object if fetch was successful.
   * @throws AccessError
   * @throws UnreachableCoreException
   */
  public $Impl fetch(Core c, long onum) throws AccessError, 
      UnreachableCoreException;

}
