package fabric.dissemination;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.SemanticWarranty;
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
   * Fetches the glob identified by the given onum, located at the given store.
   * 
   * @param store
   *          the store.
   * @param onum
   *          the object identifier.
   * @return the requested glob if fetch was successful.
   * @throws AccessException
   * @throws UnreachableNodeException
   */
  public ObjectGroup fetch(RemoteStore store, long onum) throws AccessException;

  /**
   * Fetches the glob identified by the given callId, located at the given
   * store.
   * 
   * @param store
   *          the store.
   * @param callId
   *          the call identifier.
   * @return the requested glob if fetch was successful.
   * @throws AccessException
   * @throws UnreachableNodeException
   */
  public Pair<Object, SemanticWarranty> fetchCall(RemoteStore store, long callId) throws AccessException;

  /**
   * Called to destroy and clean up the fetch manager.
   */
  public void destroy();

}
