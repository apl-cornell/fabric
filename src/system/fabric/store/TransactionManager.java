package fabric.store;

import static fabric.common.Logging.STORE_TRANSACTION_LOGGER;
import static fabric.store.db.ObjectDB.UpdateType.CREATE;
import static fabric.store.db.ObjectDB.UpdateType.WRITE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fabric.common.AuthorizationUtil;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.VersionWarranty;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.dissemination.Glob;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.store.db.GroupContainer;
import fabric.store.db.ObjectDB;
import fabric.store.db.ObjectDB.ExtendWarrantyStatus;
import fabric.store.db.SemanticWarrantyTable;
import fabric.worker.AbortException;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.CallResult;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;

/* TODO:
 *      - Change the defense mechanism for SemanticWarranties to check if the
 *      call value changed.
 *      - Double check that the re-use of a semantic warranty value has the same
 *      result as what's in the table.
 */
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

  /**
   * Data for maintaining semantic warranties.
   */
  private final SemanticWarrantyTable semanticWarranties;

  public TransactionManager(ObjectDB database) {
    this.database = database;
    this.sm = new SubscriptionManager(database.getName(), this);
    this.semanticWarranties = new SemanticWarrantyTable(database);
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
    STORE_TRANSACTION_LOGGER.fine("Aborted transaction " + transactionID);
  }

  /**
   * Executes the COMMIT phase of the three-phase commit.
   */
  public void commitTransaction(Principal workerPrincipal, long transactionID,
      long commitTime) throws TransactionCommitFailedException {
    try {
      database.commit(transactionID, commitTime, workerPrincipal, sm);
      STORE_TRANSACTION_LOGGER.fine("Committed transaction " + transactionID);
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
    // XXX - marker
    final long tid = req.tid;
    VersionWarranty longestWarranty = null;

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
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts =
          new LongKeyHashMap<Pair<SerializedObject, VersionWarranty>>();

      // Prepare writes.
      for (SerializedObject o : req.writes) {
        VersionWarranty warranty =
            database.registerUpdate(tid, worker, o, versionConflicts, WRITE);
        if (longestWarranty == null || warranty.expiresAfter(longestWarranty))
          longestWarranty = warranty;

        // Block all writes to objects read by an existing SemanticWarranty
        SemanticWarranty callWarranty =
          semanticWarranties.longestReadDependency(o.getOnum());
        if (callWarranty.expiresAfter(longestWarranty))
          longestWarranty = new VersionWarranty(callWarranty.expiry());
      }

      /*
      synchronized (database) {
        // Check writes and update version numbers.
        for (SerializedObject o : req.writes) {
          long onum = o.getOnum();
          // Make sure no one else has written to the object.
          if (database.isWritten(onum))
            throw new TransactionPrepareFailedException(versionConflicts,
                "Object " + onum + " has been locked by an uncommitted "
                    + "transaction");

          // Fetch old copy from store and check version numbers.
          SerializedObject storeCopy = database.read(onum);
          int storeVersion = storeCopy.getVersion();
          int workerVersion = o.getVersion();
          VersionWarranty warranty;
          if (storeVersion != workerVersion) {
            warranty = database.refreshWarranty(onum);
            versionConflicts
                .put(onum, new Pair<SerializedObject, VersionWarranty>(
                    storeCopy, warranty));
            continue;
          }

          // Register the update with the database.
          database.registerUpdate(tid, worker, o, WRITE);

          // Obtain existing warranty.
          warranty = database.getWarranty(onum);

          // Update the version number on the prepared copy of the object.
          o.setVersion(storeVersion + 1);

          if (longestWarranty == null || warranty.expiresAfter(longestWarranty))
            longestWarranty = warranty;
        }
        */

      // Prepare creates.
      for (SerializedObject o : req.creates) {
        database.registerUpdate(tid, worker, o, versionConflicts, CREATE);
      }

      /*
      for (SerializedObject o : req.creates) {
        long onum = o.getOnum();

        // Make sure no one else has claimed the object number in an
        // uncommitted transaction.
        if (database.isWritten(onum))
          throw new TransactionPrepareFailedException(versionConflicts,
              "Object " + onum + " has been locked by an "
                  + "uncommitted transaction");

        // Make sure the onum doesn't already exist in the database.
        if (database.exists(onum))
          throw new TransactionPrepareFailedException(versionConflicts,
              "Object " + onum + " already exists");

        // Set the initial version number and register the update with the
        // database.
        o.setVersion(INITIAL_OBJECT_VERSION_NUMBER);
        database.registerUpdate(tid, worker, o, CREATE);
      }
      }
      */

      if (!versionConflicts.isEmpty()) {
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      database.finishPrepareWrites(tid, worker);

      STORE_TRANSACTION_LOGGER.fine("Prepared writes for transaction " + tid);

      return longestWarranty == null ? 0 : longestWarranty.expiry();
    } catch (TransactionPrepareFailedException e) {
      synchronized (database) {
        database.abortPrepareWrites(tid, worker);
        throw e;
      }
    } catch (RuntimeException e) {
      synchronized (database) {
        e.printStackTrace();
        database.abortPrepareWrites(tid, worker);
        throw e;
      }
    }
  }

  /**
   * Executes the PREPARE_READS phase of the three-phase commit.
   * 
   * @param worker
   *          The worker requesting the prepare
   * @throws TransactionPrepareFailedException
   *           If the transaction would cause a conflict or if the worker is
   *           insufficiently privileged to execute the transaction.
   */
  public void prepareReads(Principal worker, long tid,
      LongKeyMap<Integer> reads, long commitTime)
      throws TransactionPrepareFailedException {
    // XXX - marker
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
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts =
          new LongKeyHashMap<Pair<SerializedObject, VersionWarranty>>();

      // Check reads
      for (LongKeyMap.Entry<Integer> entry : reads.entrySet()) {
        long onum = entry.getKey();
        database.notifyReadPrepare(onum);

        int version = entry.getValue().intValue();

        // Attempt to extend the object's warranty.
        try {
          ExtendWarrantyStatus status =
              database.extendWarranty(worker, onum, version, commitTime);
          switch (status) {
          case OK:
            break;

          case BAD_VERSION:
            SerializedObject obj = database.read(onum);
            VersionWarranty warranty = database.refreshWarranty(onum);
            versionConflicts.put(onum,
                new Pair<SerializedObject, VersionWarranty>(obj, warranty));
            continue;

          case DENIED:
            throw new TransactionPrepareFailedException(versionConflicts,
                "Unable to extend warranty for object " + onum);
          }
        } catch (AccessException e) {
          throw new TransactionPrepareFailedException(versionConflicts,
              e.getMessage());
        }
      }

      if (!versionConflicts.isEmpty()) {
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      STORE_TRANSACTION_LOGGER.fine("Prepared transaction " + tid);
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
   * Executes the calls piece of the PREPARE_READS phase of the three-phase
   * commit.
   * 
   * @param worker
   *          The worker requesting the prepare
   * @throws TransactionPrepareFailedException
   *           If the transaction could not successfully extend the
   *           SemanticWarranty on any of the calls
   */
  public void prepareCalls(Principal worker, long tid,
      LongSet calls, long commitTime)
      throws TransactionPrepareFailedException {
    LongIterator it = calls.iterator();
    while (it.hasNext()) {
      long callId = it.next();
      if (!semanticWarranties.extend(callId,
            semanticWarranties.get(callId).warranty,
            new SemanticWarranty(commitTime))) {
        throw new TransactionPrepareFailedException(
            "Could not extend warranty for call" + callId);
      }
    }
  }

  /**
   * Executes the requests piece of the PREPARE_READS phase of the three-phase
   * commit.
   * 
   * @param worker
   *          The worker requesting the prepare
   */
  public LongKeyMap<SemanticWarranty> prepareRequests(Principal worker,
      long tid, Set<SemanticWarrantyRequest> requests, long commitTime) {
    /* Create the associated warranties and add these calls to the warranties
     * table.
     */ 
    LongKeyMap<SemanticWarranty> warranties =
      new LongKeyHashMap<SemanticWarranty>();
    for (SemanticWarrantyRequest r : requests) {
      /* XXX: Should I be failing on some possible issue here? */
      warranties.put(r.call,
          semanticWarranties.put(r.call, r.reads, r.calls, r.value));
    }
    return warranties;
    /* TODO: Return the warranties created to the worker. */
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

          Label label = storeCopy.get$$updateLabel();

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
  GroupContainer getGroupContainerAndSubscribe(long onum)
      throws AccessException {
    GroupContainer container = database.readGroup(onum);
    if (container == null) throw new AccessException(database.getName(), onum);
    // if (subscriber != null) sm.subscribe(onum, subscriber, dissemSubscribe);

    container.refreshWarranties(this);
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
  public Glob getGlob(long onum) throws AccessException {
    return getGroupContainerAndSubscribe(onum).getGlob();
  }

  /**
   * Returns an CallResult containing the specified call's value and warranty.
   * 
   * @param principal
   *          The principal performing the read.
   * @param id
   *          The id for the call instance.
   */
  public CallResult getCall(Principal principal, long id)
      throws AccessException {
    CallResult result = semanticWarranties.get(id);
    if (result == null) throw new AccessException(database.getName(), id);
    return result;
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
  public ObjectGroup getGroup(Principal principal, long onum)
      throws AccessException {
    ObjectGroup group = getGroupContainerAndSubscribe(onum).getGroup(principal);
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
   * Refreshes the warranties on a group of objects.
   * @return
   *         the warranty in the group that will expire soonest.
   */
  public VersionWarranty refreshWarranties(
      Collection<Pair<SerializedObject, VersionWarranty>> objects) {
    // XXX - marker
    VersionWarranty result = null;
    for (Pair<SerializedObject, VersionWarranty> entry : objects) {
      VersionWarranty warranty =
          entry.second = database.refreshWarranty(entry.first.getOnum());
      if (result == null || result.expiresAfter(warranty)) result = warranty;
    }

    if (result == null) result = new VersionWarranty(0);
    return result;
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
  List<Pair<SerializedObject, VersionWarranty>> checkForStaleObjects(
      Principal worker, LongKeyMap<Integer> versions) throws AccessException {
    // First, check read and write permissions.
    Store store = Worker.getWorker().getStore(database.getName());
    if (worker == null || worker.$getStore() != store
        || worker.$getOnum() != ONumConstants.STORE_PRINCIPAL) {
      checkPerms(worker, versions.keySet(),
          Collections.<SerializedObject> emptyList());
    }

    List<Pair<SerializedObject, VersionWarranty>> result =
        new ArrayList<Pair<SerializedObject, VersionWarranty>>();
    synchronized (database) {
      for (LongKeyMap.Entry<Integer> entry : versions.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue();

        int curVersion = database.getVersion(onum);
        if (curVersion != version) {
          SerializedObject obj = database.read(onum);
          VersionWarranty warranty = database.refreshWarranty(onum);
          result
              .add(new Pair<SerializedObject, VersionWarranty>(obj, warranty));
        }
      }
    }

    return result;
  }

}
