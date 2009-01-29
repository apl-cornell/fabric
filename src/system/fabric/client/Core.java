package fabric.client;

import java.util.Collection;

import jif.lang.ConfPolicy;
import jif.lang.IntegPolicy;
import jif.lang.Label;

import fabric.common.FetchException;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object.$Impl;

public interface Core {
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
  $Impl readObject(long onum) throws FetchException;
  
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
  public fabric.util.Map getRoot();
  
  /**
   * Returns the ConfPolicy object representing bottom confidentiality.
   */
  public ConfPolicy getBottomConfidPolicy();
  
  /**
   * Returns the IntegPolicy object representing top integrity.
   */
  public IntegPolicy getTopIntegPolicy();

  /**
   * Returns the Label object representing an empty label (which is the most
   * permissive, having bottom confidentiality and top integrity).
   */
  public Label getEmptyLabel();

  /**
   * Notifies this Core object that an $Impl has been evicted, so that it can
   * perform the necessary cache maintenance.
   * 
   * @param onum
   *          Onum of the object that was evicted.
   */
  public void notifyEvict(long onum);
}
