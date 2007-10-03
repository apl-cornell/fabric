package fabric.client;

import java.util.Collection;
import java.util.Map;

import fabric.common.AccessError;
import fabric.lang.Object.$Impl;

public interface Core {

  /**
   * Notifies the core that the transaction is entering the Prepare phase.
   * 
   * @return <code>true</code> iff the operation succeeded.
   */
  public void prepareTransaction(int transactionID, Collection<$Impl> toCreate,
      Map<Long, Integer> reads, Collection<$Impl> writes)
      throws UnreachableCoreException, TransactionPrepareFailedException;

  /**
   * Returns the requested $Impl object, fetching it from the Core if it is not
   * resident.
   * 
   * @param onum
   *          The identifier of the requested object
   * @return The requested object
   */
  public $Impl readObject(long onum) throws AccessError,
      UnreachableCoreException;

  /**
   * Notifies the core that the transaction is being Aborted.
   * 
   * @param transactionID
   *          the ID of the aborting transaction.
   */
  public void abortTransaction(int transactionID);

  /**
   * Notifies the Core that the transaction should be committed.
   * 
   * @param transactionID
   *          the ID of the transaction to commit
   * @throws UnreachableCoreException
   * @throws TransactionCommitFailedException
   */
  public void commitTransaction(int transactionID)
      throws UnreachableCoreException, TransactionCommitFailedException;

  /**
   * Notifies the Core that a new transaction is beginning.
   * 
   * @return a Core-specific identifier for the transaction
   */
  public int beginTransaction() throws UnreachableCoreException;

  /**
   * Obtains a new, unused object number from the Core.
   * 
   * @throws UnreachableCoreException
   */
  public long createOnum() throws UnreachableCoreException;
  
  /**
   * Returns the root map of the Core
   */
  public fabric.lang.Object getRoot() throws UnreachableCoreException;
}
