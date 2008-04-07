package fabric.client;

import java.util.Collection;

import fabric.common.AccessError;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object.$Impl;

public interface Core extends java.io.Serializable {
  /**
   * Returns this core's host name.
   */
  public String name();

  /**
   * Notifies the core that the transaction is entering the Prepare phase.
   * 
   * @return a core-specific transaction ID iff the operation succeeded.
   */
  int prepareTransaction(Collection<$Impl> toCreate,
      LongKeyMap<Integer> reads, Collection<$Impl> writes)
      throws UnreachableCoreException, TransactionPrepareFailedException;

  /**
   * Returns the requested $Impl object, fetching it from the Core if it is not
   * resident.
   * 
   * @param onum
   *          The identifier of the requested object
   * @return The requested object
   */
  $Impl readObject(long onum) throws AccessError,
      UnreachableCoreException;
  
  /**
   * Returns the requested $Impl object if it exists in the object cache.
   * 
   * @param onum The identifier of the requested object.
   * @return The requested object if it exists in the object cache; otherwise, null.
   */
  $Impl readObjectFromCache(long onum);

  /**
   * Notifies the core that the transaction is being Aborted.
   * 
   * @param transactionID
   *          the ID of the aborting transaction.
   */
  void abortTransaction(int transactionID);

  /**
   * Notifies the Core that the transaction should be committed.
   * 
   * @param transactionID
   *          the ID of the transaction to commit
   * @throws UnreachableCoreException
   * @throws TransactionCommitFailedException
   */
  void commitTransaction(int transactionID)
      throws UnreachableCoreException, TransactionCommitFailedException;

  /**
   * Obtains a new, unused object number from the Core.
   * 
   * @throws UnreachableCoreException
   */
  long createOnum() throws UnreachableCoreException;
  
  /**
   * Returns the root map of the Core
   */
  public fabric.lang.Object getRoot() throws UnreachableCoreException;
}
