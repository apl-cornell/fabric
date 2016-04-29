package fabric.store;

import static fabric.common.Logging.STORE_TRANSACTION_LOGGER;
import static fabric.store.db.ObjectDB.UpdateMode.CREATE;
import static fabric.store.db.ObjectDB.UpdateMode.WRITE;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import fabric.common.AuthorizationUtil;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.dissemination.ObjectGlob;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.store.db.GroupContainer;
import fabric.store.db.ObjectDB;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionStagingFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.remote.RemoteWorker;

public class TransactionManager {

  /**
   * The object database of the store for which we're managing transactions.
   */
  private final ObjectDB database;

  /**
   * The subscription manager associated with the store for which we're managing
   * transactions.
   */
  private final SubscriptionManager sm;

  // protected final ReadHistory readHistory;

  public TransactionManager(ObjectDB database) {
    this.database = database;
    this.sm = new SubscriptionManager(database.getName(), this);
  }

  /**
   * Instruct the transaction manager that the latest stage of the given
   * transaction is aborting
   */
  public void abortStage(Principal worker, long transactionID) {
    database.abortStage(transactionID, worker);
    STORE_TRANSACTION_LOGGER.log(Level.FINE, "Aborted transaction {0}",
        transactionID);
  }

  /**
   * Execute the commit phase of two phase commit.
   */
  public void commitTransaction(RemoteIdentity<RemoteWorker> workerIdentity,
      CommitRequest req) throws TransactionCommitFailedException {
    try {
      database.commit(workerIdentity, req, sm);
      STORE_TRANSACTION_LOGGER.log(Level.FINE, "Committed transaction {0}",
          req.tid);
    } catch (final AccessException e) {
      throw new TransactionCommitFailedException("Insufficient Authorization");
    } catch (final RuntimeException e) {
      throw new TransactionCommitFailedException(
          "something went wrong; store experienced a runtime exception during "
              + "commit: " + e.getMessage(),
          e);
    }
  }

  /**
   * <p>
   * Stages a transaction. The reads and writes made thus far in the transaction
   * are validated to ensure that no conflicts would occur if the transaction
   * were committed.
   * </p>
   * <p>
   * The following conditions are checked:
   * <ul>
   * <li>Updates cannot have pending commits, i.e., they cannot contain objects
   * for which other transaction have been staged, but not committed or
   * aborted.</li>
   * <li>The worker has appropriate permissions to read/write/create</li>
   * <li>Created objects don't already exist</li>
   * <li>Reads cannot have pending writes, i.e., they cannot be write-staged by
   * an uncommitted transaction</li>
   * <li>Modified and read objects do exist</li>
   * <li>Read objects are still valid (version numbers match)</li>
   * </ul>
   * </p>
   *
   * @param worker
   *          The worker requesting the prepare
   * @throws TransactionStagingFailedException
   *           If the transaction would cause a conflict or if the worker is
   *           insufficiently privileged to execute the transaction.
   */
  public void stageTransaction(Principal worker, long tid,
      LongKeyMap<Integer> reads, LongKeyMap<Integer> writes, LongSet creates)
      throws TransactionStagingFailedException {
    // First, check read and write permissions. We do this before we attempt to
    // do the actual staging because we want to run the permissions check in a
    // transaction outside of the worker's transaction.
    Store store = Worker.getWorker().getStore(database.getName());
    if (worker == null || worker.$getStore() != store
        || worker.$getOnum() != ONumConstants.STORE_PRINCIPAL) {
      try {
        checkPerms(worker, reads.keySet(), writes.keySet());
      } catch (AccessException e) {
        throw new TransactionStagingFailedException(e.getMessage());
      }
    }

    try {
      database.beginStage(tid, worker);
    } catch (final AccessException e) {
      throw new TransactionStagingFailedException("Insufficient privileges");
    }

    try {
      // This will store the set of onums of objects that were out of date.
      LongKeyMap<SerializedObject> versionConflicts = new LongKeyHashMap<>();

      // Prepare writes.
      for (LongKeyMap.Entry<Integer> entry : writes.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue();

        database.stageUpdate(tid, worker, onum, version, versionConflicts,
            WRITE);
      }

      // Prepare creates.
      for (LongIterator it = creates.iterator(); it.hasNext();) {
        database.stageUpdate(tid, worker, it.next(), 0, versionConflicts,
            CREATE);
      }

      // Prepare reads.
      for (LongKeyMap.Entry<Integer> entry : reads.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue();

        database.stageRead(tid, worker, onum, version, versionConflicts);
      }

      if (!versionConflicts.isEmpty()) {
        throw new TransactionStagingFailedException(versionConflicts);
      }

      database.finishStage(tid, worker);

      STORE_TRANSACTION_LOGGER.log(Level.FINE, "Staged transaction {0}", tid);
    } catch (TransactionStagingFailedException e) {
      database.abortStage(tid, worker);
      throw e;
    } catch (RuntimeException e) {
      e.printStackTrace();
      database.abortStage(tid, worker);
      throw e;
    }
  }

  /**
   * Checks that the worker principal has permissions to read/write the given
   * objects. If it doesn't, an AccessException is thrown.
   */
  private void checkPerms(final Principal worker, final LongSet reads,
      final LongSet writes) throws AccessException {
    // The code that does the actual checking.
    Code<AccessException> checker = new Code<AccessException>() {
      @Override
      public AccessException run() {
        Store store = Worker.getWorker().getStore(database.getName());

        for (LongIterator it = reads.iterator(); it.hasNext();) {
          long onum = it.next();

          fabric.lang.Object storeCopy =
              new fabric.lang.Object._Proxy(store, onum);

          Label label = storeCopy.get$$updateLabel();

          // Check read permissions.
          if (!AuthorizationUtil.isReadPermitted(worker, label.$getStore(),
              label.$getOnum())) {
            return new AccessException("read", worker, storeCopy);
          }
        }

        for (LongIterator it = writes.iterator(); it.hasNext();) {
          long onum = it.next();

          fabric.lang.Object storeCopy =
              new fabric.lang.Object._Proxy(store, onum);

          Label label = storeCopy.get$$updateLabel();

          // Check write permissions.
          if (!AuthorizationUtil.isWritePermitted(worker, label.$getStore(),
              label.$getOnum())) {
            return new AccessException("write", worker, storeCopy);
          }
        }

        return null;
      }
    };

    AccessException failure = Worker.runInTopLevelTransaction(checker, true);

    if (failure != null) throw failure;
  }

  /**
   * Returns a GroupContainer containing the specified object.
   */
  GroupContainer getGroupContainer(long onum) throws AccessException {
    return getGroupContainerAndSubscribe(onum, null,
        false /* this argument doesn't matter */);
  }

  /**
   * Returns a GroupContainer containing the specified object. All surrogates
   * referenced by any object in the group will also be in the group. This
   * ensures that the worker will not reveal information when dereferencing
   * surrogates.
   *
   * @param subscriber
   *          If non-null, then the given worker will be subscribed to the
   *          object.
   * @param dissemSubscribe
   *          True if the subscriber is a dissemination node; false if it's a
   *          worker.
   */
  GroupContainer getGroupContainerAndSubscribe(long onum,
      RemoteWorker subscriber, boolean dissemSubscribe) throws AccessException {
    GroupContainer result = database.readGroup(onum);
    if (result == null) throw new AccessException(database.getName(), onum);

    if (subscriber != null) sm.subscribe(onum, subscriber, dissemSubscribe);
    return result;
  }

  /**
   * Returns a Glob containing the specified object. All surrogates referenced
   * by any object in the group will also be in the group. This ensures that the
   * worker will not reveal information when dereferencing surrogates.
   *
   * @param subscriber
   *          If non-null, then the given worker will be subscribed to the
   *          object as a dissemination node.
   */
  public ObjectGlob getGlob(long onum, RemoteWorker subscriber)
      throws AccessException {
    return getGroupContainerAndSubscribe(onum, subscriber, true).getGlob();
  }

  /**
   * Returns an ObjectGroup containing the specified object. All surrogates
   * referenced by any object in the group will also be in the group. This
   * ensures that the worker will not reveal information when dereferencing
   * surrogates.
   *
   * @param principal
   *          The principal performing the read.
   * @param subscriber
   *          If non-null, then the given worker will be subscribed to the
   *          object as a worker.
   * @param onum
   *          The onum for an object that should be in the group.
   * @param handler
   *          Used to track read statistics.
   */
  public ObjectGroup getGroup(Principal principal, RemoteWorker subscriber,
      long onum) throws AccessException {
    ObjectGroup group = getGroupContainerAndSubscribe(onum, subscriber, false)
        .getGroup(principal);
    if (group == null) throw new AccessException(database.getName(), onum);
    return group;
  }

  /**
   * Reads an object from the object database. No authorization checks are done
   * here.
   */
  SerializedObject read(long onum) {
    return database.read(onum);
  }

  /**
   * @throws AccessException
   *           if the principal is not allowed to create objects on this store.
   */
  public long[] newOnums(Principal worker, int num) throws AccessException {
    return database.newOnums(num);
  }

  /**
   * Creates new onums, bypassing authorization. This is for internal use by the
   * store.
   */
  long[] newOnums(int num) {
    return database.newOnums(num);
  }

  /**
   * Checks the given set of objects for staleness and returns a list of updates
   * for any stale objects found.
   */
  List<SerializedObject> checkForStaleObjects(Principal worker,
      LongKeyMap<Integer> versions) throws AccessException {
    // First, check read and write permissions.
    Store store = Worker.getWorker().getStore(database.getName());
    if (worker == null || worker.$getStore() != store
        || worker.$getOnum() != ONumConstants.STORE_PRINCIPAL) {
      checkPerms(worker, versions.keySet(), new LongHashSet());
    }

    List<SerializedObject> result = new ArrayList<>();
    for (LongKeyMap.Entry<Integer> entry : versions.entrySet()) {
      long onum = entry.getKey();
      int version = entry.getValue();

      int curVersion = database.getVersion(onum);
      if (curVersion != version) {
        result.add(database.read(onum));
      }
    }

    return result;
  }

}
