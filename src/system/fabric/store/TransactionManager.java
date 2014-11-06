package fabric.store;

import static fabric.common.Logging.STORE_TRANSACTION_LOGGER;
import static fabric.store.db.ObjectDB.UpdateType.CREATE;
import static fabric.store.db.ObjectDB.UpdateType.WRITE;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import fabric.common.AuthorizationUtil;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.SerializedObjectAndTokens;
import fabric.common.VersionWarranty;
import fabric.common.WarrantyGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.RuntimeFetchException;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongKeyMap.Entry;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.store.db.GroupContainer;
import fabric.store.db.ObjectDB;
import fabric.store.db.ObjectDB.ExtendReadLockStatus;
import fabric.worker.AbortException;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.remote.RemoteWorker;

public class TransactionManager {

  // Hack hack hack
  private static final boolean ENABLE_WARRANTY_REFRESHES = false;

  @SuppressWarnings("all")
  private static class Ugh {
    static {
      if (TransactionManager.ENABLE_WARRANTY_REFRESHES != SubscriptionManager.ENABLE_WARRANTY_REFRESHES) {
        throw new InternalError();
      }
    }
  }

  /**
   * The object database of the store for which we're managing transactions.
   */
  private final ObjectDB database;

  /**
   * The subscription manager associated with the store for which we're managing
   * transactions.
   */
  private final SubscriptionManager sm;

  public TransactionManager(ObjectDB database, PrivateKey signingKey) {
    this.database = database;
    this.sm = new SubscriptionManager(database.getName(), this, signingKey);
  }

  public final SubscriptionManager subscriptionManager() {
    return sm;
  }

  /**
   * Instructs the transaction manager that the given transaction is aborting
   */
  public void abortTransaction(Principal worker, long transactionID)
      throws AccessException {
    database.abort(transactionID, worker);
    STORE_TRANSACTION_LOGGER.log(Level.FINE, "Aborted transaction {0}",
        transactionID);
  }

  /**
   * Executes the COMMIT phase of the three-phase commit.
   */
  public void commitTransaction(RemoteIdentity<RemoteWorker> workerIdentity,
      long transactionID, long commitTime)
      throws TransactionCommitFailedException {
    try {
      database.commit(transactionID, commitTime, workerIdentity, sm);
      if (STORE_TRANSACTION_LOGGER.isLoggable(Level.FINE)) {
        STORE_TRANSACTION_LOGGER.log(Level.FINE, "Committed transaction {0}",
            Long.toHexString(transactionID));
      }
    } catch (final RuntimeException e) {
      throw new TransactionCommitFailedException(
          "something went wrong; store experienced a runtime exception during "
              + "commit: " + e.getMessage(), e);
    }
  }

  /**
   * Executes the PREPARE_WRITES phase of the three-phase commit.
   *
   * @return a minimum commit time, specifying a time after which the warranties
   *     on all modified objects will expire.
   * @throws TransactionPrepareFailedException
   *           If the transaction would cause a conflict or if the worker is
   *           insufficiently privileged to execute the transaction.
   */
  public long prepareWrites(Principal worker, PrepareWritesRequest req)
      throws TransactionPrepareFailedException {
    final long tid = req.tid;
    long commitTime = System.currentTimeMillis();

    // First, check write permissions. We do this before we attempt to do the
    // actual prepare because we want to run the permissions check in a
    // transaction outside of the worker's transaction.
    Store store = Worker.getWorker().getStore(database.getName());
    if (worker == null || worker.$getStore() != store
        || worker.$getOnum() != ONumConstants.STORE_PRINCIPAL) {
      try {
        checkPerms(worker, LongSet.EMPTY, req.writes);
      } catch (AccessException e) {
        throw new TransactionPrepareFailedException(e.getMessage());
      } catch (AbortException e) {
        throw new TransactionPrepareFailedException(e.getMessage());
      }
    }

    database.beginPrepareWrites(tid, worker);

    try {
      // This will store the set of onums of objects that were out of date.
      LongKeyMap<SerializedObjectAndTokens> versionConflicts =
          new LongKeyHashMap<>();

      // Prepare writes.
      ObjectDB.ReadPrepareResult scratchObj =
          new ObjectDB.ReadPrepareResult(null, null, null);
      for (SerializedObject o : req.writes) {
        commitTime =
            Math.max(commitTime, database.registerUpdate(scratchObj, tid,
                worker, o, versionConflicts, WRITE));
      }

      // Prepare creates.
      for (SerializedObject o : req.creates) {
        database.registerUpdate(scratchObj, tid, worker, o, versionConflicts,
            CREATE);
      }

      if (!versionConflicts.isEmpty()) {
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      database.finishPrepareWrites(tid, worker);

      STORE_TRANSACTION_LOGGER.log(Level.FINE,
          "Prepared writes for transaction {0}", tid);

      return commitTime;
    } catch (TransactionPrepareFailedException e) {
      database.abortPrepareWrites(tid, worker);
      throw e;
    } catch (RuntimeException e) {
      e.printStackTrace();
      database.abortPrepareWrites(tid, worker);
      throw e;
    }
  }

  /**
   * Executes the PREPARE_READS phase of the three-phase commit.
   *
   * @param workerIdentity
   *          The worker requesting the prepare
   * @throws TransactionPrepareFailedException
   *           If the transaction would cause a conflict or if the worker is
   *           insufficiently privileged to execute the transaction.
   */
  public LongKeyMap<VersionWarranty> prepareReads(
      RemoteIdentity<RemoteWorker> workerIdentity, long tid,
      LongKeyMap<Integer> reads, long commitTime)
      throws TransactionPrepareFailedException {

    Principal worker = workerIdentity.principal;

    try {
      // First, check read permissions. We do this before we attempt to do the
      // actual prepare because we want to run the permissions check in a
      // transaction outside of the worker's transaction.
      Store store = Worker.getWorker().getStore(database.getName());
      if (worker == null || worker.$getStore() != store
          || worker.$getOnum() != ONumConstants.STORE_PRINCIPAL) {
        try {
          checkPerms(worker, reads.keySet(),
              Collections.<SerializedObject> emptyList());
        } catch (AccessException e) {
          throw new TransactionPrepareFailedException(e.getMessage());
        }
      }

      // This will store the set of onums of objects that were out of date.
      LongKeyMap<SerializedObjectAndTokens> versionConflicts =
          new LongKeyHashMap<>();

      // This will store the warranties that will be sent back to the worker as
      // a result of the prepare.
      LongKeyMap<VersionWarranty> prepareResult = new LongKeyHashMap<>();

      // This will store the new warranties we get.
      List<VersionWarranty.Binding> newWarranties =
          ENABLE_WARRANTY_REFRESHES ? new ArrayList<VersionWarranty.Binding>()
              : null;

      // Check reads
      final ObjectDB.ReadPrepareResult resultObj =
          new ObjectDB.ReadPrepareResult(null, null, null);
      for (LongKeyMap.Entry<Integer> entry : reads.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue().intValue();

        // Attempt to extend the object's warranty.
        try {
          ObjectDB.ReadPrepareResult result =
              database.lockForReadPrepare(resultObj, worker, onum, version,
                  commitTime);
          switch (result.getStatus()) {
          case NEW:
            if (ENABLE_WARRANTY_REFRESHES) {
              newWarranties
                  .add(result.getWarranty().new Binding(onum, version));
            }
            //$FALL-THROUGH$
          case OLD:
            prepareResult.put(onum, result.getWarranty());
            break;

          case BAD_VERSION:
            SerializedObject obj = database.read(onum);
            result = database.refreshRead(worker, resultObj, onum);
            versionConflicts.put(onum, new SerializedObjectAndTokens(obj,
                result.getWarranty(), result.getLease()));
            continue;

          case DENIED:
            sm.notifyNewWarranties(newWarranties, null);
            throw new TransactionPrepareFailedException(versionConflicts,
                "Unable to extend warranty for object " + onum);
          }
        } catch (AccessException e) {
          sm.notifyNewWarranties(newWarranties, null);
          throw new TransactionPrepareFailedException(versionConflicts,
              e.getMessage());
        }
      }

      if (!versionConflicts.isEmpty()) {
        sm.notifyNewWarranties(newWarranties, null);
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      STORE_TRANSACTION_LOGGER.log(Level.FINE, "Prepared transaction {0}", tid);
      sm.notifyNewWarranties(newWarranties, workerIdentity.node);
      return prepareResult;
    } catch (TransactionPrepareFailedException e) {
      // Roll back the transaction.
      try {
        abortTransaction(worker, tid);
      } catch (AccessException ae) {
        throw new InternalError(ae);
      }
      throw e;
    }
  }

  /**
   * Checks that the worker principal has permissions to read/write the given
   * objects. If it doesn't, an AccessException is thrown.
   */
  private void checkPerms(final Principal worker, final LongSet reads,
      final Collection<SerializedObject> writes) throws AccessException {
    // The code that does the actual checking.
    Code<AccessException> checker = new Code<AccessException>() {
      @Override
      public AccessException run() {
        Store store = Worker.getWorker().getStore(database.getName());

        for (LongIterator it = reads.iterator(); it.hasNext();) {
          long onum = it.next();

          fabric.lang.Object storeCopy =
              new fabric.lang.Object._Proxy(store, onum);

          Label label;
          try {
            label = storeCopy.get$$updateLabel();
          } catch (RuntimeFetchException e) {
            return new AccessException("Object at onum " + onum
                + " doesn't exist.");
          }

          // Check read permissions.
          if (!AuthorizationUtil.isReadPermitted(worker, label.$getStore(),
              label.$getOnum())) {
            return new AccessException("read", worker, storeCopy);
          }
        }

        for (SerializedObject o : writes) {
          long onum = o.getOnum();

          fabric.lang.Object storeCopy =
              new fabric.lang.Object._Proxy(store, onum);

          Label label;
          try {
            label = storeCopy.get$$updateLabel();
          } catch (RuntimeFetchException e) {
            return new AccessException("Object at onum " + onum
                + " doesn't exist.");
          }

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
   * Returns a GroupContainer containing the specified object, without
   * refreshing warranties.
   */
  GroupContainer getGroupContainer(long onum) throws AccessException {
    return getGroupContainerAndSubscribe(onum, null, false /* this argument doesn't matter */);
  }

  /**
   * Returns a GroupContainer containing the specified object. All surrogates
   * referenced by any object in the group will also be in the group. This
   * ensures that the worker will not reveal information when dereferencing
   * surrogates.
   *
   * @param subscriber
   *          If non-null, then the given worker will be subscribed to the
   *          object and the object's group's warranties will be refreshed.
   * @param dissemSubscribe
   *          True if the subscriber is a dissemination node; false if it's a
   *          worker.
   */
  GroupContainer getGroupContainerAndSubscribe(long onum,
      RemoteWorker subscriber, boolean dissemSubscribe) throws AccessException {
    GroupContainer container = database.readGroup(onum);
    if (container == null) throw new AccessException(database.getName(), onum);

    if (subscriber != null) {
      sm.subscribe(onum, subscriber, dissemSubscribe);
      container.refreshWarranties(this);
    }

    return container;
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
  public Pair<ObjectGlob, WarrantyGlob> getGlobs(long onum,
      RemoteWorker subscriber) throws AccessException {
    return getGroupContainerAndSubscribe(onum, subscriber, true).getGlobs();
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
  public Pair<ObjectGroup, WarrantyGroup> getGroup(Principal principal,
      RemoteWorker subscriber, long onum) throws AccessException {
    Pair<ObjectGroup, WarrantyGroup> group =
        getGroupContainerAndSubscribe(onum, subscriber, false).getGroups(
            principal);
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
   * Refreshes the warranties on a group of objects, represented by a mapping
   * from onums to version numbers. The refresh is done by creating new
   * warranties for any objects whose warranty has expired.
   */
  public void refreshWarranties(LongKeyMap<Integer> onumsToVersions) {
    List<VersionWarranty.Binding> newWarranties =
        ENABLE_WARRANTY_REFRESHES ? new ArrayList<VersionWarranty.Binding>()
            : null;

    ObjectDB.ReadPrepareResult resultObj =
        new ObjectDB.ReadPrepareResult(null, null, null);
    for (Entry<Integer> entry : onumsToVersions.entrySet()) {
      long onum = entry.getKey();
      ObjectDB.ReadPrepareResult refreshResult =
          database.refreshRead(null, resultObj, onum);

      if (ENABLE_WARRANTY_REFRESHES) {
        if (refreshResult.getStatus() == ExtendReadLockStatus.NEW) {
          newWarranties.add(refreshResult.getWarranty().new Binding(onum, entry
              .getValue()));
        }
      }
    }

    sm.notifyNewWarranties(newWarranties, null);
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
  List<SerializedObjectAndTokens> checkForStaleObjects(
      RemoteIdentity<RemoteWorker> workerIdentity, LongKeyMap<Integer> versions)
      throws AccessException {
    Principal worker = workerIdentity.principal;

    // First, check read and write permissions.
    Store store = Worker.getWorker().getStore(database.getName());
    if (worker == null || worker.$getStore() != store
        || worker.$getOnum() != ONumConstants.STORE_PRINCIPAL) {
      checkPerms(worker, versions.keySet(),
          Collections.<SerializedObject> emptyList());
    }

    List<SerializedObjectAndTokens> result = new ArrayList<>();
    List<VersionWarranty.Binding> newWarranties =
        ENABLE_WARRANTY_REFRESHES ? new ArrayList<VersionWarranty.Binding>()
            : null;
    boolean success = false;

    try {
      ObjectDB.ReadPrepareResult resultObj =
          new ObjectDB.ReadPrepareResult(null, null, null);
      for (LongKeyMap.Entry<Integer> entry : versions.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue();

        int curVersion = database.getVersion(onum);
        if (curVersion != version) {
          ObjectDB.ReadPrepareResult refreshReadResult =
              database.refreshRead(worker, resultObj, onum);
          SerializedObject obj = database.read(onum);

          result.add(new SerializedObjectAndTokens(obj, refreshReadResult
              .getWarranty(), refreshReadResult.getLease()));

          if (ENABLE_WARRANTY_REFRESHES) {
            if (refreshReadResult.getStatus() == ExtendReadLockStatus.NEW) {
              newWarranties.add(refreshReadResult.getWarranty().new Binding(
                  onum, version));
            }
          }
        }
      }
      success = true;
    } finally {
      sm.notifyNewWarranties(newWarranties,
          success ? (RemoteWorker) workerIdentity.node : null);
    }

    return result;
  }

}
