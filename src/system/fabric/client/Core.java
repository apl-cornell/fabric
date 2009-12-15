package fabric.client;

import java.util.Collection;

import fabric.common.TransactionID;
import fabric.common.exceptions.FetchException;
import fabric.common.util.LongKeyMap;
import fabric.lang.NodePrincipal;
import fabric.lang.Object._Impl;
import fabric.net.UnreachableNodeException;

public interface Core {
  /**
   * Returns this core's host name.
   */
  public String name();

  /**
   * Returns the NodePrincipal associated with this core.
   */
  public NodePrincipal getPrincipal();

  /**
   * Determines whether this is the local core.
   */
  public boolean isLocalCore();

  /**
   * Notifies the core that the transaction is entering the Prepare phase.
   * 
   * @return whether a subtransaction was created on the core as a result of the
   *         prepare.
   */
  boolean prepareTransaction(boolean useAuthentication, long tid,
      long commitTime, Collection<_Impl> toCreate, LongKeyMap<Integer> reads,
      Collection<_Impl> writes) throws UnreachableNodeException,
      TransactionPrepareFailedException;

  /**
   * Returns the requested _Impl object. If the object is not resident, it is
   * fetched from the Core via dissemination.
   * 
   * @param onum
   *          The identifier of the requested object
   * @return The requested object
   */
  _Impl readObject(long onum) throws FetchException;

  /**
   * Returns the requested _Impl object, fetching it directly from the Core if
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
   * Notifies the core that the transaction is being Aborted.
   * 
   * @param useAuthentication
   * @param transactionID
   *          the ID of the aborting transaction. This is assumed to specify a
   *          top-level transaction.
   */
  void abortTransaction(boolean useAuthentication, TransactionID tid);

  /**
   * Notifies the Core that the transaction should be committed.
   * 
   * @param transactionID
   *          the ID of the transaction to commit
   * @throws UnreachableNodeException
   * @throws TransactionCommitFailedException
   */
  void commitTransaction(boolean useAuthentication, long transactionID)
      throws UnreachableNodeException, TransactionCommitFailedException;

  /**
   * Obtains a new, unused object number from the Core.
   * 
   * @throws UnreachableNodeException
   */
  long createOnum() throws UnreachableNodeException;

  /**
   * Returns the root map of the Core
   */
  public fabric.util.Map getRoot();

  /**
   * Notifies this Core object that an _Impl has been evicted, so that it can
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
