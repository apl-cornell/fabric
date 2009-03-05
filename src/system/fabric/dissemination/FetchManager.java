package fabric.dissemination;

import fabric.client.RemoteCore;
import fabric.client.UnreachableNodeException;
import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FetchException;

/**
 * A FetchManager is responsible for retrieving objects from Cores. Clients
 * may load different FetchManagers at run time to make use of different
 * dissemination networks.
 */
public interface FetchManager {
  
  /**
   * Fetches the glob identified by onum located at core c.
   * 
   * @param c
   *                the core.
   * @param onum
   *                the object identifier.
   * @return the requested glob if fetch was successful.
   * @throws AccessException
   * @throws UnreachableNodeException
   */
  public ObjectGroup fetch(RemoteCore c, long onum) throws FetchException;

  /**
   * Called to destroy and clean up the fetch manager.
   */
  public void destroy();
  
}
