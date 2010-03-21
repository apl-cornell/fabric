package fabric.dissemination;

import fabric.worker.RemoteStore;
import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FetchException;
import fabric.net.UnreachableNodeException;

/**
 * A FetchManager is responsible for retrieving objects from Stores. Workers
 * may load different FetchManagers at run time to make use of different
 * dissemination networks.
 */
public interface FetchManager {
  
  /**
   * Fetches the glob identified by the given onum, located at the given store.
   * 
   * @param store
   *                the store.
   * @param onum
   *                the object identifier.
   * @return the requested glob if fetch was successful.
   * @throws AccessException
   * @throws UnreachableNodeException
   */
  public ObjectGroup fetch(RemoteStore store, long onum) throws FetchException;

  /**
   * Called to destroy and clean up the fetch manager.
   */
  public void destroy();
  
}
