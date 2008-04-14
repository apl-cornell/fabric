package fabric.core;

import java.security.Principal;
import java.util.NoSuchElementException;

import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.util.LongKeyMap;
import fabric.core.store.StoreException;

public class TransactionManager {

  private static final int INITIAL_OBJECT_VERSION_NUMBER = 1;

  /**
   * The object store of the core for which we're managing transactions.
   */
  private final ObjectStore store;

  public TransactionManager(ObjectStore store) {
    this.store = store;
  }

  /**
   * Instruct the transaction manager that the given transaction is aborting
   */
  public synchronized void abortTransaction(Principal client, int transactionID) {
    try {
      store.rollback(client, transactionID);
    } catch (StoreException e) {}
  }

  /**
   * Execute the commit phase of two phase commit.
   */
  public synchronized void commitTransaction(Principal client, int transactionID)
      throws TransactionCommitFailedException {
    try {
      store.commit(client, transactionID);
    } catch (final StoreException e) {
      throw new TransactionCommitFailedException("Insufficient Authorization");
    } catch (final RuntimeException e) {
      throw new TransactionCommitFailedException("something went wrong");
    }
  }
  
  public ObjectStore getObjectStore() {
    return store;
  }
  
  /**
   * <p>
   * Execute the prepare phase of two phase commit. Validates the transaction to
   * make sure that no conflicts would occur if this transaction were committed.
   * Once prepare returns successfully, the corrseponding transaction can only
   * fail if the coordinator aborts it.
   * </p>
   * <p>
   * The prepare method must check a large number of conditions:
   * <ul>
   * <li>Neither creates, updates, nor reads can have pending commits, i.e.
   * none of them can contain objects for which other transactions have been
   * prepared but not committed or aborted.</li>
   * <li>The client has appropriate permissions to read/write/create</li>
   * <li>Created objects don't already exist</li>
   * <li>Modified and read objects do exist</li>
   * <li>Read objects are still valid (version numbers match)</li>
   * <li>TODO: duplicate objects within sets / between sets?</li>
   * </ul>
   * </p>
   * 
   * @param client
   *          The client requesting the prepare
   * @param creates
   *          The set of objects to be created in this transaction
   * @param updates
   *          The set of objects to be updated in this transaction
   * @param reads
   *          The set of objects that the transaction read
   * @return A locally unique transaction identifier
   * @throws TransactionPrepareFailedException
   *           If the transaction would cause a conflict or if the client is
   *           insufficiently priviledged to execute the transaction.
   */
  public int prepare(Principal client, PrepareRequest req)
      throws TransactionPrepareFailedException {
    try {
      synchronized (store) {

        // Check writes and update version numbers
        for (SerializedObject o : req.writes) {
          if (store.isPrepared(o.getOnum()))
            throw new TransactionPrepareFailedException(
                "Object " + o.getOnum() + " has been locked by an " +
                "uncommitted transaction");

          try {
            SerializedObject old = store.read(client, o.getOnum());
            o.setVersion(old.getVersion() + 1);
          } catch(NoSuchElementException exc) {
            throw new TransactionPrepareFailedException(
                "Object " + o.getOnum() + " does not exist.");
          } catch (StoreException e) {}
        }
        
        // Check creates and initial version numbers
        for (SerializedObject o : req.creates) {
          if (store.isPrepared(o.getOnum()))
            throw new TransactionPrepareFailedException(
                "Object " + o.getOnum() + " has been locked by an " +
                "uncommitted transaction");

          if (store.exists(o.getOnum()))
            throw new TransactionPrepareFailedException(
                "Object " + o.getOnum() + " already exists");
          
          o.setVersion(INITIAL_OBJECT_VERSION_NUMBER);
        }
        
        // Check reads
        for (LongKeyMap.Entry<Integer> entry : req.reads.entrySet()) {
          long onum    = entry.getKey();
          int  version = entry.getValue().intValue();

          if (store.isPrepared(onum))
            throw new TransactionPrepareFailedException(
                "Object " + onum + " has been locked by an " +
                "uncommitted transaction");

          SerializedObject obj = store.read(client, onum);
          if (obj.getVersion() != version)
            throw new TransactionPrepareFailedException(
                "Object " + onum + " is out of date");
        }
        
        return store.prepare(client, req);
      }
    } catch(final StoreException e) {
      throw new TransactionPrepareFailedException("Insufficient privileges");
    } catch(final NoSuchElementException e) {
      throw new TransactionPrepareFailedException("Object does not exist");
    }
  }

  public synchronized SerializedObject read(Principal client, long onum)
      throws StoreException {
    return store.read(client, onum);
  }

  public synchronized long[] newOIDs(Principal client, int num)
      throws StoreException {
    return store.newOnums(num);
  }
  
}
