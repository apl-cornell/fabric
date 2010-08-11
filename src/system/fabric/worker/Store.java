package fabric.worker;

import java.util.Collection;

import fabric.common.TransactionID;
import fabric.common.exceptions.FetchException;
import fabric.common.util.LongKeyMap;
import fabric.lang.security.NodePrincipal;
import fabric.lang.Object._Impl;
import fabric.net.UnreachableNodeException;

public interface Store {
  /**
   * Returns this store's host name.
   */
  public String name();

  /**
   * Returns the NodePrincipal associated with this store.
   */
  public NodePrincipal getPrincipal();

  /**
   * Determines whether this is the local store.
   */
  public boolean isLocalStore();

  /**
   * Notifies the store that the transaction is entering the Prepare phase.
   * 
   * @return whether a subtransaction was created on the store as a result of
   *         the prepare.
   */
  boolean prepareTransaction(boolean useAuthentication, long tid,
      long commitTime, Collection<_Impl> toCreate, LongKeyMap<Integer> reads,
      Collection<_Impl> writes) throws UnreachableNodeException,
      TransactionPrepareFailedException;

  /**
   * Returns the requested _Impl object. If the object is not resident, it is
   * fetched from the Store via dissemination.
   * 
   * @param onum
   *          The identifier of the requested object
   * @return The requested object
   */
  _Impl readObject(long onum) throws FetchException;

  /**
   * Returns the requested _Impl object, fetching it directly from the Store if
   * it is not resident.
   * 
   * @param onum
   *          The identifier of the requested object
   * @return The requested object
   */
  _Impl readObjectNoDissem(long onum) throws FetchException;

  /**
   * Returns the requested _Impl object if it exists in the object cache.
   * 
   * @param onum
   *          The identifier of the requested object.
   * @return The requested object if it exists in the object cache; otherwise,
   *         null.
   */
  _Impl readObjectFromCache(long onum);

  /**
   * Notifies the store that the transaction is being Aborted.
   * 
   * @param useAuthentication
   * @param tid
   *          the ID of the aborting transaction. This is assumed to specify a
   *          top-level transaction.
   */
  void abortTransaction(boolean useAuthentication, TransactionID tid);

  /**
   * Notifies the Store that the transaction should be committed.
   * 
   * @param transactionID
   *          the ID of the transaction to commit
   * @throws UnreachableNodeException
   * @throws TransactionCommitFailedException
   */
  void commitTransaction(boolean useAuthentication, long transactionID)
      throws UnreachableNodeException, TransactionCommitFailedException;

  /**
   * Determines whether the given set of objects are stale.
   * 
   * @return true iff stale objects were found.
   */
  boolean checkForStaleObjects(LongKeyMap<Integer> reads);

  /**
   * Obtains a new, unused object number from the Store.
   * 
   * @throws UnreachableNodeException
   */
  long createOnum() throws UnreachableNodeException;

  /**
   * Returns the root map of the Store
   */
  public fabric.util.Map getRoot();

  /**
   * Notifies this Store object that an _Impl has been evicted, so that it can
   * perform the necessary cache maintenance.
   * 
   * @param onum
   *          Onum of the object that was evicted.
   * @return true iff the onum was found in cache.
   */
  public boolean notifyEvict(long onum);

  /**
   * Evicts the object with the given onum from cache.
   * 
   * @return true iff the onum was found in cache.
   */
  public boolean evict(long onum);

  /**
   * Adds the given object to the cache.
   */
  public void cache(_Impl impl);
}
