package fabric.store;

import static fabric.common.Logging.STORE_TRANSACTION_LOGGER;
import static fabric.store.db.ObjectDB.UpdateType.CREATE;
import static fabric.store.db.ObjectDB.UpdateType.WRITE;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import fabric.common.AuthorizationUtil;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.VersionWarranty;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongKeyMap.Entry;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.dissemination.Glob;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.store.db.GroupContainer;
import fabric.store.db.ObjectDB;
import fabric.store.db.ObjectDB.ExtendWarrantyStatus;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;

public class TransactionManager {

  private static final int INITIAL_OBJECT_VERSION_NUMBER = 1;
  private static final int MIN_GROUP_SIZE = 75;

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
   * The key to use when signing objects.
   */
  private final PrivateKey signingKey;

  public TransactionManager(ObjectDB database, PrivateKey signingKey) {
    this.database = database;
    this.signingKey = signingKey;

    this.sm = new SubscriptionManager(database.getName(), this);
  }

  public final SubscriptionManager subscriptionManager() {
    return sm;
  }

  /**
   * Instructs the transaction manager that the given transaction is aborting
   */
  public void abortTransaction(Principal worker, long transactionID)
      throws AccessException {
    synchronized (database) {
      database.abort(transactionID, worker);
      STORE_TRANSACTION_LOGGER.fine("Aborted transaction " + transactionID);
    }
  }

  /**
   * Executes the COMMIT phase of the three-phase commit.
   */
  public void commitTransaction(Principal workerPrincipal, long transactionID,
      long commitTime) throws TransactionCommitFailedException {
    synchronized (database) {
      try {
        database.commit(transactionID, commitTime, workerPrincipal, sm);
        STORE_TRANSACTION_LOGGER.fine("Committed transaction " + transactionID);
      } catch (final RuntimeException e) {
        throw new TransactionCommitFailedException(
            "something went wrong; store experienced a runtime exception during "
                + "commit: " + e.getMessage(), e);
      }
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
      }
    }

    synchronized (database) {
      database.beginPrepareWrites(tid, worker);
    }

    try {
      // This will store the set of onums of objects that were out of date.
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts =
          new LongKeyHashMap<Pair<SerializedObject, VersionWarranty>>();

      // Check writes and update version numbers.
      synchronized (database) {
        try {
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
              versionConflicts.put(onum,
                  new Pair<SerializedObject, VersionWarranty>(storeCopy,
                      warranty));
              continue;
            }

            // Register the update with the database.
            database.registerUpdate(tid, worker, o, WRITE);

            // Obtain existing warranty.
            warranty = database.getWarranty(onum);

            // Update the version number on the prepared copy of the object.
            o.setVersion(storeVersion + 1);

            if (longestWarranty == null
                || warranty.expiresAfter(longestWarranty))
              longestWarranty = warranty;
          }
        } finally {
          database.flushWarranties();
        }
      }

      // Check creates.
      synchronized (database) {
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

      if (!versionConflicts.isEmpty()) {
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      synchronized (database) {
        database.finishPrepareWrites(tid, worker);
      }

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

    try {
      // This will store the set of onums of objects that were out of date.
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts =
          new LongKeyHashMap<Pair<SerializedObject, VersionWarranty>>();

      // Check reads
      synchronized (database) {
        try {
          for (LongKeyMap.Entry<Integer> entry : reads.entrySet()) {
            long onum = entry.getKey();
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
        } finally {
          database.flushWarranties();
        }
      }

      if (!versionConflicts.isEmpty()) {
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      STORE_TRANSACTION_LOGGER.fine("Prepared transaction " + tid);
    } catch (RuntimeException e) {
      e.printStackTrace();
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
   * @param handler
   *          Used to track read statistics. Can be null.
   * @param subscriber
   *          If non-null, then the given worker will be subscribed to the
   *          object.
   * @param dissemSubscribe
   *          True if the subscriber is a dissemination node; false if it's a
   *          worker.
   */
  GroupContainer getGroupContainerAndSubscribe(long onum)
      throws AccessException {
    GroupContainer container;
    synchronized (database) {
      container = database.getCachedGroupContainer(onum);
      if (container != null) {
        // if (subscriber != null)
        // sm.subscribe(onum, subscriber, dissemSubscribe);

        container.refreshWarranties(this);
        return container;
      }

      // if (subscriber != null) sm.subscribe(onum, subscriber, dissemSubscribe);
      GroupContainer group = makeGroup(onum);
      if (group == null) throw new AccessException(database.getName(), onum);

      group.refreshWarranties(this);
      return group;
    }
  }

  /**
   * Returns a Glob containing the specified object. All surrogates referenced
   * by any object in the group will also be in the group. This ensures that the
   * worker will not reveal information when dereferencing surrogates.
   * 
   * @param subscriber
   *          If non-null, then the given worker will be subscribed to the
   *          object as a dissemination node.
   * @param handler
   *          Used to track read statistics.
   */
  public Glob getGlob(long onum) throws AccessException {
    return getGroupContainerAndSubscribe(onum).getGlob();
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
   * Creates a group of objects from the object database. All surrogates
   * referenced by any object in the group will also be in the group. This
   * ensures that the worker will not reveal information when dereferencing
   * surrogates.
   * 
   * @param onum
   *          The group's head object.
   * @param handler
   *          Used to track read statistics.
   */
  private GroupContainer makeGroup(long onum) {
    // Obtain a partial group for the object.
    PartialObjectGroup partialGroup = readPartialGroup(onum);

    for (Entry<SerializedObject> entry : partialGroup.frontier.entrySet()) {
      long frontierOnum = entry.getKey();
      if (database.getCachedGroupContainer(frontierOnum) != null) continue;

      SerializedObject frontierObj = entry.getValue();
      PartialObjectGroup frontierGroup =
          readPartialGroup(frontierOnum, frontierObj);
      if (frontierGroup.size() >= MIN_GROUP_SIZE) continue;

      // Group on the frontier isn't big enough. Coalesce it with the group
      // we're creating.
      database.coalescePartialGroups(frontierGroup, partialGroup);
    }

    return database.promotePartialGroup(signingKey, partialGroup);
  }

  /**
   * Returns a partial group of objects. A partial group is a group containing
   * at most MIN_GROUP_SIZE objects, but may need to be grown because the object
   * sub-graphs on the group's frontier may not be large enough to fill an
   * entire group.
   */
  PartialObjectGroup readPartialGroup(long onum) {
    return readPartialGroup(onum, null);
  }

  /**
   * Returns a partial group of objects. A partial group is a group containing
   * at most MIN_GROUP_SIZE objects, but may need to be grown because the object
   * sub-graphs on the group's frontier may not be large enough to fill an
   * entire group.
   * 
   * @param obj
   *          The object corresponding to the given onum. If null, the object
   *          will be read from the database.
   */
  private PartialObjectGroup readPartialGroup(long onum, SerializedObject obj) {
    PartialObjectGroup partialGroup = database.getCachedPartialGroup(onum);
    if (partialGroup != null) return partialGroup;

    if (obj == null) obj = read(onum);
    if (obj == null) return null;

    long headLabelOnum = obj.getUpdateLabelOnum();

    LongKeyMap<SerializedObject> group =
        new LongKeyHashMap<SerializedObject>(MIN_GROUP_SIZE);
    LongKeyMap<SerializedObject> frontier =
        new LongKeyHashMap<SerializedObject>();

    // Number of non-surrogate objects in the group.
    int groupSize = 0;

    Queue<SerializedObject> toVisit = new LinkedList<SerializedObject>();
    LongSet seen = new LongHashSet();

    // Do a breadth-first traversal and add objects to the group.
    toVisit.add(obj);
    seen.add(onum);
    while (!toVisit.isEmpty()) {
      SerializedObject curObject = toVisit.remove();
      long curOnum = curObject.getOnum();

      // Always add surrogates.
      if (curObject.isSurrogate()) {
        group.put(curOnum, curObject);
        continue;
      }

      // Ensure that the object hasn't been grouped already.
      if (database.getCachedGroupID(curOnum) != null) continue;

      if (groupSize >= MIN_GROUP_SIZE) {
        // Partial group is full. Add the object to the partial group's
        // frontier.
        frontier.put(curOnum, curObject);
        continue;
      }

      // Add object to partial group.
      group.put(curOnum, curObject);
      groupSize++;

      // Visit links outgoing from the object.
      for (Iterator<Long> it = curObject.getIntraStoreRefIterator(); it
          .hasNext();) {
        long relatedOnum = it.next();
        if (seen.contains(relatedOnum)) continue;
        seen.add(relatedOnum);

        SerializedObject related = read(relatedOnum);
        if (related == null) continue;

        // Ensure that the related object's label is the same as the head
        // object's label. We could be smarter here, but to avoid calling into
        // the worker, let's hope pointer-equality is sufficient.
        long relatedLabelOnum = related.getUpdateLabelOnum();
        if (headLabelOnum != relatedLabelOnum) continue;

        toVisit.add(related);
      }
    }

    PartialObjectGroup result =
        new PartialObjectGroup(groupSize, group, frontier);
    database.cachePartialGroup(result);
    return result;
  }

  /**
   * Reads an object from the object database. No authorization checks are done
   * here.
   */
  SerializedObject read(long onum) {
    synchronized (database) {
      return database.read(onum);
    }
  }

  /**
   * Refreshes the warranties on a group of objects.
   * @return
   *         the warranty in the group that will expire soonest.
   */
  public VersionWarranty refreshWarranties(
      Collection<Pair<SerializedObject, VersionWarranty>> objects) {
    synchronized (database) {
      try {
        VersionWarranty result = null;
        for (Pair<SerializedObject, VersionWarranty> entry : objects) {
          VersionWarranty warranty =
              entry.second = database.refreshWarranty(entry.first.getOnum());
          if (result == null || result.expiresAfter(warranty))
            result = warranty;
        }

        if (result == null) result = new VersionWarranty(0);
        return result;
      } finally {
        database.flushWarranties();
      }
    }
  }

  /**
   * @throws AccessException
   *           if the principal is not allowed to create objects on this store.
   */
  public long[] newOnums(Principal worker, int num) throws AccessException {
    synchronized (database) {
      return database.newOnums(num);
    }
  }

  /**
   * Creates new onums, bypassing authorization. This is for internal use by the
   * store.
   */
  long[] newOnums(int num) {
    synchronized (database) {
      return database.newOnums(num);
    }
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
      try {
        for (LongKeyMap.Entry<Integer> entry : versions.entrySet()) {
          long onum = entry.getKey();
          int version = entry.getValue();

          int curVersion = database.getVersion(onum);
          if (curVersion != version) {
            SerializedObject obj = database.read(onum);
            VersionWarranty warranty = database.refreshWarranty(onum);
            result.add(new Pair<SerializedObject, VersionWarranty>(obj,
                warranty));
          }
        }
      } finally {
        database.flushWarranties();
      }
    }

    return result;
  }

}
