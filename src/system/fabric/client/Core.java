package fabric.client;

import java.util.Collection;

import fabric.common.FetchException;
import fabric.common.TransactionID;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object.$Impl;

public interface Core {
  /**
   * Returns this core's host name.
   */
  public String name();
  
  /**
   * Determines whether this is the local core.
   */
  public boolean isLocalCore();

  /**
   * Notifies the core that the transaction is entering the Prepare phase.
   */
  void prepareTransaction(long tid, Collection<$Impl> toCreate,
      LongKeyMap<Integer> reads, Collection<$Impl> writes)
      throws UnreachableNodeException, TransactionPrepareFailedException;

  /**
   * Returns the requested $Impl object. If the object is not resident, it is
   * fetched from the Core via dissemination.
   * 
   * @param onum
   *          The identifier of the requested object
   * @return The requested object
   */
  $Impl readObject(long onum) throws FetchException;

  /**
   * Returns the requested $Impl object, fetching it directly from the Core if
   * it is not resident.
   * 
   * @param onum
   *          The identifier of the requested object
   * @return The requested object
   */
  $Impl readObjectNoDissem(long onum) throws FetchException;

  /**
   * Returns the requested $Impl object if it exists in the object cache.
   * 
   * @param onum
   *          The identifier of the requested object.
   * @return The requested object if it exists in the object cache; otherwise,
   *         null.
   */
  $Impl readObjectFromCache(long onum);

  /**
   * Notifies the core that the transaction is being Aborted.
   * 
   * @param transactionID
   *          the ID of the aborting transaction. This is assumed to specify a
   *          top-level transaction.
   */
  void abortTransaction(TransactionID tid);

  /**
   * Notifies the Core that the transaction should be committed.
   * 
   * @param transactionID
   *          the ID of the transaction to commit
   * @throws UnreachableNodeException
   * @throws TransactionCommitFailedException
   */
  void commitTransaction(long transactionID) throws UnreachableNodeException,
      TransactionCommitFailedException;

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
   * Notifies this Core object that an $Impl has been evicted, so that it can
   * perform the necessary cache maintenance.
   * 
   * @param onum
   *          Onum of the object that was evicted.
   */
  public void notifyEvict(long onum);

  /**
   * Evicts the object with the given onum from cache.
   */
  public void evict(long onum);

  /**
   * Adds the given object to the cache.
   */
  public void cache($Impl impl);
}
