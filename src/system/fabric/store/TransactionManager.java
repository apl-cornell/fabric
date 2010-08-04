package fabric.store;

import static fabric.common.Logging.STORE_TRANSACTION_LOGGER;

import java.security.PrivateKey;
import java.util.*;

import fabric.common.AuthorizationUtil;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.util.*;
import fabric.dissemination.Glob;
import fabric.lang.Statistics;
import fabric.lang.security.Label;
import fabric.lang.security.NodePrincipal;
import fabric.store.db.GroupContainer;
import fabric.store.db.ObjectDB;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.remote.RemoteWorker;

public class TransactionManager {

  private static final int INITIAL_OBJECT_VERSION_NUMBER = 1;
  private static final int MAX_GROUP_SIZE = 75;

  private static final Random rand = new Random();

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

  /**
   * The key to use when signing objects.
   */
  private final PrivateKey signingKey;

  private final LongKeyMap<Statistics> objectStats;

  public TransactionManager(ObjectDB database, PrivateKey signingKey) {
    this.database = database;
    this.signingKey = signingKey;
    this.objectStats = new LongKeyHashMap<Statistics>();
    // this.readHistory = new ReadHistory();

    this.sm = new SubscriptionManager(database.getName(), this);
  }

  /**
   * Instruct the transaction manager that the given transaction is aborting
   */
  public void abortTransaction(NodePrincipal worker, long transactionID)
      throws AccessException {
    synchronized (database) {
      database.rollback(transactionID, worker);
      STORE_TRANSACTION_LOGGER.fine("Aborted transaction "
          + transactionID);
    }
  }

  /**
   * Execute the commit phase of two phase commit.
   */
  public void commitTransaction(NodePrincipal workerPrincipal, long transactionID)
       throws TransactionCommitFailedException {
    synchronized (database) {
      try {
        database.commit(transactionID, workerPrincipal, sm);
        STORE_TRANSACTION_LOGGER.fine("Committed transaction "
            + transactionID);
      } catch (final AccessException e) {
        throw new TransactionCommitFailedException("Insufficient Authorization");
      } catch (final RuntimeException e) {
        throw new TransactionCommitFailedException(
            "something went wrong; store experienced a runtime exception during "
                + "commit: " + e.getMessage(), e);
      }
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
   * <li>The worker has appropriate permissions to read/write/create</li>
   * <li>Created objects don't already exist</li>
   * <li>Updated objects cannot have been read since the proposed commit time.</li>
   * <li>Updated objects do not have valid outstanding promises</li>
   * <li>Modified and read objects do exist</li>
   * <li>Read objects are still valid (version numbers match)</li>
   * <li>TODO: duplicate objects within sets / between sets?</li>
   * </ul>
   * </p>
   * 
   * @param worker
   *          The worker requesting the prepare
   * @return whether a subtransaction was created for making Statistics objects.
   * @throws TransactionPrepareFailedException
   *           If the transaction would cause a conflict or if the worker is
   *           insufficiently privileged to execute the transaction.
   */
  public boolean prepare(NodePrincipal worker, PrepareRequest req)
      throws TransactionPrepareFailedException {
    final long tid = req.tid;
    boolean result = false;

    // First, check read and write permissions. We do this before we attempt to
    // do the actual prepare because we want to run the permissions check in a
    // transaction outside of the worker's transaction.
    Store store = Worker.getWorker().getStore(database.getName());
    if (worker == null || worker.$getStore() != store
        || worker.$getOnum() != ONumConstants.STORE_PRINCIPAL) {
      checkPerms(worker, req.reads.keySet(), req.writes);
    }

    synchronized (database) {
      try {
        database.beginTransaction(tid, worker);
      } catch (final AccessException e) {
        throw new TransactionPrepareFailedException("Insufficient privileges");
      }
    }

    try {
      // This will store the set of onums of objects that were out of date.
      LongKeyMap<SerializedObject> versionConflicts =
          new LongKeyHashMap<SerializedObject>();

      // Check writes and update version numbers
      for (SerializedObject o : req.writes) {
        // Make sure no one else has used the object and fetch the old copy
        // from the store.
        long onum = o.getOnum();
        SerializedObject storeCopy;
        synchronized (database) {
          if (database.isPrepared(onum, tid))
            throw new TransactionPrepareFailedException("Object " + onum
                + " has been locked by an uncommitted transaction");

          storeCopy = database.read(onum);

          // Register the update with the database.
          database.registerUpdate(tid, worker, o);
        }

        // check promises
        if (storeCopy.getExpiry() > req.commitTime) {
          throw new TransactionPrepareFailedException("Update to object" + onum
              + " violates an outstanding promise");
        }

        // Check version numbers.
        int storeVersion = storeCopy.getVersion();
        int workerVersion = o.getVersion();
        if (storeVersion != workerVersion) {
          versionConflicts.put(onum, storeCopy);
          continue;
        }

        // // Check against read history
        // if (!readHistory.check(onum, req.commitTime)) {
        // throw new TransactionPrepareFailedException("Object " + onum
        // + " has been read since it's proposed commit time.");
        // }

        // Update promise statistics
        Pair<Statistics, Boolean> pair = ensureStatistics(onum, req.tid);
        pair.first.commitWrote();
        result |= pair.second;

        // Update the version number on the prepared copy of the object.
        o.setVersion(storeVersion + 1);
      }

      // Check creates.
      synchronized (database) {
        for (SerializedObject o : req.creates) {
          long onum = o.getOnum();

          // Make sure no one else has claimed the object number in an
          // uncommitted transaction.
          if (database.isPrepared(onum, tid))
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
          database.registerUpdate(tid, worker, o);
        }
      }

      // Check reads
      for (LongKeyMap.Entry<Integer> entry : req.reads.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue().intValue();

        synchronized (database) {
          // Make sure no one else is using the object.
          if (database.isWritten(onum))
            throw new TransactionPrepareFailedException(versionConflicts,
                "Object " + onum + " has been locked by an uncommitted "
                    + "transaction");

          // Check version numbers.
          int curVersion;
          try {
            curVersion = database.getVersion(onum);
          } catch (AccessException e) {
            throw new TransactionPrepareFailedException(versionConflicts, e
                .getMessage());
          }
          if (curVersion != version) {
            versionConflicts.put(onum, database.read(onum));
            continue;
          }

          // inform the object statistics of the read
          Pair<Statistics, Boolean> pair = ensureStatistics(onum, req.tid);
          pair.first.commitRead();
          result |= pair.second;

          // Register the read with the database.
          database.registerRead(tid, worker, onum);
        }
      }

      if (!versionConflicts.isEmpty()) {
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      // readHistory.record(req);
      synchronized (database) {
        database.finishPrepare(tid, worker);
      }
      
      STORE_TRANSACTION_LOGGER.fine("Prepared transaction " + tid);

      return result;
    } catch (TransactionPrepareFailedException e) {
      synchronized (database) {
        database.abortPrepare(tid, worker);
        throw e;
      }
    } catch (RuntimeException e) {
      synchronized (database) {
        e.printStackTrace();
        database.abortPrepare(tid, worker);
        throw e;
      }
    }
  }

  /**
   * Checks that the worker principal has permissions to read/write the given
   * objects. If it doesn't, a TransactionPrepareFailedException is thrown.
   */
  private void checkPerms(final NodePrincipal worker, final LongSet reads,
      final Collection<SerializedObject> writes)
      throws TransactionPrepareFailedException {
    // The code that does the actual checking.
    Code<TransactionPrepareFailedException> checker =
        new Code<TransactionPrepareFailedException>() {
          public TransactionPrepareFailedException run() {
            Store store = Worker.getWorker().getStore(database.getName());

            for (LongIterator it = reads.iterator(); it.hasNext();) {
              long onum = it.next();

              fabric.lang.Object storeCopy =
                  new fabric.lang.Object._Proxy(store, onum);

              Label label = storeCopy.get$label();

              // Check read permissions.
              if (!AuthorizationUtil.isReadPermitted(worker, label.$getStore(),
                  label.$getOnum())) {
                return new TransactionPrepareFailedException("Insufficient "
                    + "privileges to read object " + onum);
              }
            }

            for (SerializedObject o : writes) {
              long onum = o.getOnum();

              fabric.lang.Object storeCopy =
                  new fabric.lang.Object._Proxy(store, onum);

              Label label = storeCopy.get$label();

              // Check write permissions.
              if (!AuthorizationUtil.isWritePermitted(worker, label.$getStore(),
                  label.$getOnum())) {
                return new TransactionPrepareFailedException("Insufficient "
                    + "privileges to write object " + onum);
              }
            }

            return null;
          }
        };

    TransactionPrepareFailedException failure =
        Worker.runInTransaction(null, checker);

    if (failure != null) throw failure;
  }

  /**
   * Returns a GroupContainer containing the specified object.
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
  GroupContainer getGroupContainerAndSubscribe(long onum,
      RemoteWorker subscriber, boolean dissemSubscribe, MessageHandlerThread handler)
      throws AccessException {
    GroupContainer container;
    synchronized (database) {
      container = database.getCachedGroupContainer(onum);
      if (container != null) {
        if (subscriber != null)
          sm.subscribe(onum, subscriber, dissemSubscribe);
        return container;
      }
    }

    // XXX Ideally, the subscription registration should happen atomically with
    // the read.
    if (subscriber != null) sm.subscribe(onum, subscriber, dissemSubscribe);
    ObjectGroup group = readGroup(onum, handler);
    if (group == null) throw new AccessException(database.getName(), onum);

    Store store = Worker.getWorker().getStore(database.getName());
    container = new GroupContainer(store, signingKey, group);

    // Cache the container.
    synchronized (database) {
      database.cacheGroupContainer(group.objects().keySet(), container);
    }

    return container;
  }

  /**
   * Returns a Glob containing the specified object.
   * 
   * @param subscriber
   *          If non-null, then the given worker will be subscribed to the
   *          object as a dissemination node.
   * @param handler
   *          Used to track read statistics.
   */
  public Glob getGlob(long onum, RemoteWorker subscriber,
      MessageHandlerThread handler) throws AccessException {
    return getGroupContainerAndSubscribe(onum, subscriber, true, handler)
        .getGlob();
  }

  /**
   * Returns an ObjectGroup containing the specified object.
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
  public ObjectGroup getGroup(NodePrincipal principal, RemoteWorker subscriber,
      long onum, MessageHandlerThread handler) throws AccessException {
    ObjectGroup group =
        getGroupContainerAndSubscribe(onum, subscriber, false, handler)
            .getGroup(principal);
    if (group == null) throw new AccessException(database.getName(), onum);
    return group;
  }

  /**
   * Reads a group of objects from the object database.
   * 
   * @param onum
   *          The group's head object.
   * @param handler
   *          Used to track read statistics.
   */
  private ObjectGroup readGroup(long onum, MessageHandlerThread handler) {
    SerializedObject obj = read(onum);
    if (obj == null) return null;

    long headLabelOnum = obj.getLabelOnum();

    LongKeyMap<SerializedObject> group =
        new LongKeyHashMap<SerializedObject>(MAX_GROUP_SIZE);
    Queue<SerializedObject> toVisit = new LinkedList<SerializedObject>();
    LongSet seen = new LongHashSet();

    // Do a breadth-first traversal and add objects to an object group.
    toVisit.add(obj);
    seen.add(onum);
    while (!toVisit.isEmpty()) {
      SerializedObject curObj = toVisit.remove();
      group.put(curObj.getOnum(), curObj);

      if (group.size() == MAX_GROUP_SIZE) break;

      for (Iterator<Long> it = curObj.getIntraStoreRefIterator(); it.hasNext();) {
        long relatedOnum = it.next();
        if (seen.contains(relatedOnum)) continue;
        seen.add(relatedOnum);

        // Ensure that the related object hasn't been globbed already.
        synchronized (database) {
          if (database.getCachedGroupContainer(relatedOnum) != null) continue;
        }

        SerializedObject related = read(relatedOnum);
        if (related == null) continue;

        // Ensure that the related object's label is the same as the head
        // object's label. We could be smarter here, but to avoid calling into
        // the worker, let's hope pointer-equality is sufficient.
        long relatedLabelOnum = related.getLabelOnum();
        if (headLabelOnum != relatedLabelOnum) continue;

        toVisit.add(related);
      }
    }

    return new ObjectGroup(group);
  }

  /**
   * Reads an object from the object database. No authorization checks are done
   * here.
   */
  SerializedObject read(long onum) {
    SerializedObject obj;
    synchronized (database) {
      obj = database.read(onum);
    }

    if (obj == null) return null;

    // create promise if necessary.
    long now = System.currentTimeMillis();
    if (obj.getExpiry() < now) {
      Statistics history = getStatistics(onum);
      if (history != null) {
        int promise = history.generatePromise();
        if (promise > 0) {
          NodePrincipal worker = Worker.getWorker().getPrincipal();
          synchronized (database) {
            // create a promise

            if (database.isWritten(onum))
            // object has been written - no promise for you!
              return obj;

            // check to see if someone else has created a promise
            SerializedObject newObj = database.read(onum);
            long time = newObj.getExpiry();

            if (time < now + promise) {
              try {
                // update the promise
                newObj.setExpiry(now + promise);
                long tid = rand.nextLong();
                database.beginTransaction(tid, worker);
                database.registerUpdate(tid, worker, newObj);
                database.finishPrepare(tid, worker);
                database.commit(tid, null, sm);
              } catch (AccessException exc) {
                // TODO: this should probably use the store principal instead of
                // the worker principal, and AccessExceptions should be
                // impossible
                return obj;
              }
            }
          }
        }
      }
    }

    return obj;
  }

  /**
   * Return the statistics object associated with onum, or null if there isn't
   * one
   */
  private Statistics getStatistics(long onum) {
    synchronized (objectStats) {
      return objectStats.get(onum);
    }
  }

  /**
   * Return the statistics object associated with onum, creating one if it
   * doesn't exist already. Also returns a boolean indicating whether the
   * Statistics object is freshly created.
   */
  private Pair<Statistics, Boolean> ensureStatistics(long onum, long tnum) {
    // Disabled statistics generation for now. -MJL
    return new Pair<Statistics, Boolean>(
        fabric.lang.DefaultStatistics.instance, false);

    // synchronized (objectStats) {
    // Statistics stats = getStatistics(onum);
    // boolean fresh = stats == null;
    // if (fresh) {
    // // set up to run as a sub-transaction of the current transaction.
    // TransactionID tid = new TransactionID(tnum);
    // Store local = Worker.getWorker().getStore(database.getName());
    // final fabric.lang.Object._Proxy object =
    // new fabric.lang.Object._Proxy(local, onum);
    // stats = Worker.runInTransaction(tid, new Worker.Code<Statistics>() {
    // public Statistics run() {
    // return object.createStatistics();
    // }
    // });
    // objectStats.put(onum, stats);
    // }
    // return new Pair<Statistics, Boolean>(stats, fresh);
    // }
  }

  /**
   * @throws AccessException
   *           if the principal is not allowed to create objects on this store.
   */
  public long[] newOnums(NodePrincipal worker, int num) throws AccessException {
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

}
