package fabric.dissemination;

import fabric.common.ObjectGroup;
import fabric.common.WarrantyGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.util.Pair;
import fabric.net.UnreachableNodeException;
import fabric.worker.RemoteStore;

/**
 * A FetchManager is responsible for retrieving objects from Stores via a
 * dissemination layer. Workers may load different FetchManagers at run time to
 * make use of different dissemination networks.
 */
public interface FetchManager {

  /**
   * Fetches the group identified by the given onum, located at the given store.
   * 
   * @param store
   *          the store.
   * @param onum
   *          the object identifier.
   * @return the requested group if fetch was successful.
   * @throws AccessException
   * @throws UnreachableNodeException
   */
  public Pair<ObjectGroup, WarrantyGroup> fetch(RemoteStore store,
      long onum) throws AccessException;

  /**
   * Called to destroy and clean up the fetch manager.
   */
  public void destroy();

  /**
   * Updates the dissemination and worker caches with the given glob.
   *
   * @return true iff there was a dissemination-cache entry for the given oid.
   */
  public boolean updateCaches(RemoteStore store, long onum,
      AbstractGlob<?> update);

}
