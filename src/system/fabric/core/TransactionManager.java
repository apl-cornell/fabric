package fabric.core;

import jif.lang.Label;
import jif.lang.LabelUtil;
import fabric.client.Client;
import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.SerializedObject;
import fabric.common.util.LongKeyMap;
import fabric.core.store.ObjectStore;
import fabric.core.store.StoreException;
import fabric.lang.Principal;

public class TransactionManager {

  private static final int INITIAL_OBJECT_VERSION_NUMBER = 1;

  /**
   * The object store of the core for which we're managing transactions.
   */
  protected final ObjectStore store;

  public TransactionManager(ObjectStore store) {
    this.store = store;
  }

  /**
   * Instruct the transaction manager that the given transaction is aborting
   */
  public synchronized void abortTransaction(Principal client, int transactionID) {
    try {
      store.rollback(client, transactionID);
    } catch (StoreException e) {
    }
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

  /**
   * <p>
   * Execute the prepare phase of two phase commit. Validates the transaction to
   * make sure that no conflicts would occur if this transaction were committed.
   * Once prepare returns successfully, the corresponding transaction can only
   * fail if the coordinator aborts it.
   * </p>
   * <p>
   * The prepare method must check a large number of conditions:
   * <ul>
   * <li>Neither creates, updates, nor reads can have pending commits, i.e. none
   * of them can contain objects for which other transactions have been prepared
   * but not committed or aborted.</li>
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
   *           insufficiently privileged to execute the transaction.
   */
  public int prepare(Principal client, PrepareRequest req)
      throws TransactionPrepareFailedException {
    int tid;
    synchronized (store) {
      try {
        tid = store.beginTransaction(client);
      } catch (final StoreException e) {
        throw new TransactionPrepareFailedException("Insufficient privileges");
      }
    }

    try {
      // Check writes and update version numbers
      for (SerializedObject o : req.writes) {
        // Make sure no one else is using the object and fetch the old copy from
        // the core.
        long onum = o.getOnum();
        SerializedObject coreCopy;
        synchronized (store) {
          if (store.isPrepared(onum))
            throw new TransactionPrepareFailedException("Object " + onum
                + " has been locked by an uncommitted transaction");

          coreCopy = store.read(onum);

          // Register the update with the store.
          store.registerUpdate(tid, o);
        }

        // Make sure the object actually exists.
        if (coreCopy == null) {
          throw new TransactionPrepareFailedException("Object " + o.getOnum()
              + " does not exist.");
        }

        // Check write permissions
        if (!isWritePermitted(client, coreCopy.getLabelOnum())) {
          throw new TransactionPrepareFailedException("Insufficient privilege "
              + "to write object " + o.getOnum());
        }

        // Check version numbers.
        int coreVersion = coreCopy.getVersion();
        int clientVersion = o.getVersion();
        if (coreVersion != clientVersion)
          versionError(onum, coreVersion, clientVersion);

        // Update the version number on the prepared copy of the object.
        o.setVersion(coreVersion + 1);
      }

      // Check creates.
      synchronized (store) {
        for (SerializedObject o : req.creates) {
          long onum = o.getOnum();

          // Make sure no one else is using the object.
          if (store.isPrepared(onum))
            throw new TransactionPrepareFailedException("Object " + onum
                + " has been locked by an " + "uncommitted transaction");

          // Make sure the onum isn't already taken.
          if (store.exists(onum))
            throw new TransactionPrepareFailedException("Object " + onum
                + " already exists");

          // Set the initial version number and register the update with the
          // store.
          o.setVersion(INITIAL_OBJECT_VERSION_NUMBER);
          store.registerUpdate(tid, o);
        }
      }

      // Check reads
      for (LongKeyMap.Entry<Integer> entry : req.reads.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue().intValue();

        synchronized (store) {
          // Make sure no one else is using the object.
          if (store.isWritten(onum))
            throw new TransactionPrepareFailedException("Object " + onum
                + " has been locked by an uncommitted transaction");

          // Check version numbers.
          SerializedObject obj = store.read(onum);
          int curVersion = obj.getVersion();
          if (curVersion != version) versionError(onum, curVersion, version);

          // Register the read with the store.
          store.registerRead(tid, onum);
        }
      }

      store.finishPrepare(tid);
      return tid;
    } catch (TransactionPrepareFailedException e) {
      synchronized (store) {
        store.abortPrepare(tid);
        throw e;
      }
    } catch (RuntimeException e) {
      synchronized (store) {
        e.printStackTrace();
        store.abortPrepare(tid);
        throw e;
      }
    }
  }

  private void versionError(long onum, int coreVersion, int clientVersion)
      throws TransactionPrepareFailedException {
    throw new TransactionPrepareFailedException("Object " + onum
        + " is out of date (client used v" + clientVersion + " but core has v"
        + coreVersion + ")");
  }

  /**
   * Returns the label at the given onum.
   */
  private Label getLabelByOnum(long labelOnum) {
    Principal corePrincipal = store.corePrincipal();
    return new Label.$Proxy(corePrincipal.$getCore(), labelOnum);
  }

  /**
   * Determines whether the given principal is permitted to write according to
   * the label at the given onum.
   */
  private boolean isWritePermitted(final Principal principal, long labelOnum) {
    // Allow the core's client principal to do anything. We use pointer equality
    // here to avoid having to call into the client.
    if (principal == Client.getClient().getPrincipal()) return true;

    // Call into the Jif label framework to perform the label check.
    final Label label = getLabelByOnum(labelOnum);
    return Client.runInTransaction(new Client.Code<Boolean>() {
      public Boolean run() {
        return LabelUtil.$Impl.isWritableBy(label, principal);
      }
    });
  }

  /**
   * @throws StoreException
   *           if the principal is not allowed to read the object.
   */
  public synchronized SerializedObject read(Principal client, long onum)
      throws StoreException {
    return store.read(onum);
  }

  /**
   * @throws StoreException
   *           if the principal is not allowed to create objects on this core.
   */
  public synchronized long[] newOnums(Principal client, int num)
      throws StoreException {
    return store.newOnums(num);
  }

  /**
   * Creates new onums, bypassing authorization. This is for internal use by the
   * core.
   */
  synchronized long[] newOnums(int num) {
    return store.newOnums(num);
  }

}
