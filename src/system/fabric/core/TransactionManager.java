package fabric.core;

import java.security.PrivateKey;
import java.util.*;

import jif.lang.Label;
import fabric.client.*;
import fabric.client.Client.Code;
import fabric.client.remote.RemoteClient;
import fabric.common.AuthorizationUtil;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.util.*;
import fabric.core.store.GroupContainer;
import fabric.core.store.ObjectStore;
import fabric.dissemination.Glob;
import fabric.lang.NodePrincipal;
import fabric.lang.Statistics;

public class TransactionManager {

  private static final int INITIAL_OBJECT_VERSION_NUMBER = 1;
  private static final int MAX_GROUP_SIZE = 75;

  private static final Random rand = new Random();

  /**
   * The object store of the core for which we're managing transactions.
   */
  private final ObjectStore store;

  /**
   * The subscription manager associated with the core for which we're managing
   * transactions.
   */
  private final SubscriptionManager sm;

  // protected final ReadHistory readHistory;

  /**
   * The key to use when signing objects.
   */
  private final PrivateKey signingKey;

  private final LongKeyMap<Statistics> objectStats;

  public TransactionManager(ObjectStore store, PrivateKey signingKey) {
    this.store = store;
    this.signingKey = signingKey;
    this.objectStats = new LongKeyHashMap<Statistics>();
    // this.readHistory = new ReadHistory();

    this.sm = new SubscriptionManager(store.getName(), this);
  }

  /**
   * Instruct the transaction manager that the given transaction is aborting
   */
  public void abortTransaction(NodePrincipal client, long transactionID)
      throws AccessException {
    synchronized (store) {
      store.rollback(transactionID, client);
    }
  }

  /**
   * Execute the commit phase of two phase commit.
   */
  public void commitTransaction(RemoteClient clientNode,
      NodePrincipal clientPrincipal, long transactionID)
      throws TransactionCommitFailedException {
    synchronized (store) {
      try {
        store.commit(transactionID, clientNode, clientPrincipal, sm);
      } catch (final AccessException e) {
        throw new TransactionCommitFailedException("Insufficient Authorization");
      } catch (final RuntimeException e) {
        throw new TransactionCommitFailedException(
            "something went wrong; core experienced a runtime exception during "
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
   * <li>The client has appropriate permissions to read/write/create</li>
   * <li>Created objects don't already exist</li>
   * <li>Updated objects cannot have been read since the proposed commit time.</li>
   * <li>Updated objects do not have valid outstanding promises</li>
   * <li>Modified and read objects do exist</li>
   * <li>Read objects are still valid (version numbers match)</li>
   * <li>TODO: duplicate objects within sets / between sets?</li>
   * </ul>
   * </p>
   * 
   * @param client
   *          The client requesting the prepare
   * @return whether a subtransaction was created for making Statistics objects.
   * @throws TransactionPrepareFailedException
   *           If the transaction would cause a conflict or if the client is
   *           insufficiently privileged to execute the transaction.
   */
  public boolean prepare(NodePrincipal client, PrepareRequest req)
      throws TransactionPrepareFailedException {
    final long tid = req.tid;
    boolean result = false;

    // First, check write permissions. We do this before we attempt to do the
    // actual prepare because we want to run the permissions check in a
    // transaction outside of the client's transaction.
    Core core = Client.getClient().getCore(store.getName());
    if (client == null || client.$getCore() != core
        || client.$getOnum() != ONumConstants.CORE_PRINCIPAL) {
      checkWritePerms(client, req.writes);
    }

    synchronized (store) {
      try {
        store.beginTransaction(tid, client);
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
        // Make sure no one else has written the object and fetch the old copy
        // from the core.
        long onum = o.getOnum();
        SerializedObject coreCopy;
        synchronized (store) {
          if (store.isWritten(onum))
            throw new TransactionPrepareFailedException("Object " + onum
                + " has been locked by an uncommitted transaction");

          coreCopy = store.read(onum);

          // Register the update with the store.
          store.registerUpdate(tid, client, o);
        }

        // check promises
        if (coreCopy.getExpiry() > req.commitTime) {
          throw new TransactionPrepareFailedException("Update to object" + onum
              + " violates an outstanding promise");
        }

        // Check version numbers.
        int coreVersion = coreCopy.getVersion();
        int clientVersion = o.getVersion();
        if (coreVersion != clientVersion) {
          versionConflicts.put(onum, coreCopy);
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
        o.setVersion(coreVersion + 1);
      }

      // Check creates.
      synchronized (store) {
        for (SerializedObject o : req.creates) {
          long onum = o.getOnum();

          // Make sure no one else has claimed the object number in an
          // uncommitted transaction.
          if (store.isPrepared(onum, tid))
            throw new TransactionPrepareFailedException(versionConflicts,
                "Object " + onum + " has been locked by an "
                    + "uncommitted transaction");

          // Make sure the onum doesn't already exist in the store.
          if (store.exists(onum))
            throw new TransactionPrepareFailedException(versionConflicts,
                "Object " + onum + " already exists");

          // Set the initial version number and register the update with the
          // store.
          o.setVersion(INITIAL_OBJECT_VERSION_NUMBER);
          store.registerUpdate(tid, client, o);
        }
      }

      // Check reads
      for (LongKeyMap.Entry<Integer> entry : req.reads.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue().intValue();

        synchronized (store) {
          // Make sure no one else is using the object.
          if (store.isWritten(onum))
            throw new TransactionPrepareFailedException(versionConflicts,
                "Object " + onum + " has been locked by an uncommitted "
                    + "transaction");

          // Check version numbers.
          int curVersion;
          try {
            curVersion = store.getVersion(onum);
          } catch (AccessException e) {
            throw new TransactionPrepareFailedException(versionConflicts, e
                .getMessage());
          }
          if (curVersion != version) {
            versionConflicts.put(onum, store.read(onum));
            continue;
          }

          // inform the object statistics of the read
          Pair<Statistics, Boolean> pair = ensureStatistics(onum, req.tid);
          pair.first.commitRead();
          result |= pair.second;

          // Register the read with the store.
          store.registerRead(tid, client, onum);
        }
      }

      if (!versionConflicts.isEmpty()) {
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      // readHistory.record(req);
      store.finishPrepare(tid, client);

      return result;
    } catch (TransactionPrepareFailedException e) {
      synchronized (store) {
        store.abortPrepare(tid, client);
        throw e;
      }
    } catch (RuntimeException e) {
      synchronized (store) {
        e.printStackTrace();
        store.abortPrepare(tid, client);
        throw e;
      }
    }
  }

  /**
   * Checks that the client principal has permissions to write the given
   * objects. If it doesn't, a TransactionPrepareFailedException is thrown.
   */
  private void checkWritePerms(final NodePrincipal client,
      final Collection<SerializedObject> writes)
      throws TransactionPrepareFailedException {
    // The code that does the actual checking.
    Code<TransactionPrepareFailedException> checker =
        new Code<TransactionPrepareFailedException>() {
          public TransactionPrepareFailedException run() {
            Core core = Client.getClient().getCore(store.getName());

            for (SerializedObject o : writes) {
              long onum = o.getOnum();

              fabric.lang.Object coreCopy =
                  new fabric.lang.Object._Proxy(core, onum);

              Label label = coreCopy.get$label();

              // Check write permissions.
              if (!AuthorizationUtil.isWritePermitted(client, label.$getCore(),
                  label.$getOnum())) {
                return new TransactionPrepareFailedException("Insufficient "
                    + "privileges to write object " + onum);
              }
            }

            return null;
          }
        };

    TransactionPrepareFailedException failure =
        Client.runInTransaction(null, checker);

    if (failure != null) throw failure;
  }

  /**
   * Returns a GroupContainer containing the specified object.
   * 
   * @param worker
   *          Used to track read statistics. Can be null.
   * @param subscriber
   *          If non-null, then the given client will be subscribed to the
   *          object.
   * @param dissemSubscribe
   *          True if the subscriber is a dissemination node; false if it's a
   *          client.
   */
  GroupContainer getGroupContainerAndSubscribe(long onum,
      RemoteClient subscriber, boolean dissemSubscribe, Worker worker)
      throws AccessException {
    GroupContainer container;
    synchronized (store) {
      container = store.getCachedGroupContainer(onum);
      if (container != null) {
        if (subscriber != null)
          sm.subscribe(onum, subscriber, dissemSubscribe);
        return container;
      }
    }

    // XXX Ideally, the subscription registration should happen atomically with
    // the read.
    if (subscriber != null) sm.subscribe(onum, subscriber, dissemSubscribe);
    ObjectGroup group = readGroup(onum, worker);
    if (group == null) throw new AccessException(store.getName(), onum);

    Core core = Client.getClient().getCore(store.getName());
    container = new GroupContainer(core, signingKey, group);

    // Cache the container.
    synchronized (store) {
      store.cacheGroupContainer(group.objects().keySet(), container);
    }

    if (worker != null) {
      worker.numGlobsCreated++;
      worker.numGlobbedObjects += group.objects().size();
    }

    return container;
  }

  /**
   * Returns a Glob containing the specified object.
   * 
   * @param subscriber
   *          If non-null, then the given client will be subscribed to the
   *          object as a dissemination node.
   * @param worker
   *          Used to track read statistics.
   */
  public Glob getGlob(long onum, RemoteClient subscriber, Worker worker)
      throws AccessException {
    return getGroupContainerAndSubscribe(onum, subscriber, true, worker)
        .getGlob();
  }

  /**
   * Returns an ObjectGroup containing the specified object.
   * 
   * @param principal
   *          The principal performing the read.
   * @param subscriber
   *          If non-null, then the given client will be subscribed to the
   *          object as a client.
   * @param onum
   *          The onum for an object that should be in the group.
   * @param worker
   *          Used to track read statistics.
   */
  public ObjectGroup getGroup(NodePrincipal principal, RemoteClient subscriber,
      long onum, Worker worker) throws AccessException {
    ObjectGroup group =
        getGroupContainerAndSubscribe(onum, subscriber, false, worker)
            .getGroup(principal);
    if (group == null) throw new AccessException(store.getName(), onum);
    return group;
  }

  /**
   * Reads a group of objects from the object store.
   * 
   * @param onum
   *          The group's head object.
   * @param worker
   *          Used to track read statistics.
   */
  private ObjectGroup readGroup(long onum, Worker worker) {
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

      if (worker != null) {
        int count = 0;
        worker.numObjectsSent++;
        if (worker.numSendsByType.containsKey(curObj.getClassName()))
          count = worker.numSendsByType.get(curObj.getClassName());
        count++;
        worker.numSendsByType.put(curObj.getClassName(), count);
      }

      if (group.size() == MAX_GROUP_SIZE) break;

      for (Iterator<Long> it = curObj.getIntracoreRefIterator(); it.hasNext();) {
        long relatedOnum = it.next();
        if (seen.contains(relatedOnum)) continue;
        seen.add(relatedOnum);

        // Ensure that the related object hasn't been globbed already.
        synchronized (store) {
          if (store.getCachedGroupContainer(relatedOnum) != null) continue;
        }

        SerializedObject related = read(relatedOnum);
        if (related == null) continue;

        // Ensure that the related object's label is the same as the head
        // object's label. We could be smarter here, but to avoid calling into
        // the client, let's hope pointer-equality is sufficient.
        long relatedLabelOnum = related.getLabelOnum();
        if (headLabelOnum != relatedLabelOnum) continue;

        toVisit.add(related);
      }
    }

    return new ObjectGroup(group);
  }

  /**
   * Reads an object from the object store. No authorization checks are done
   * here.
   */
  SerializedObject read(long onum) {
    SerializedObject obj;
    synchronized (store) {
      obj = store.read(onum);
    }

    if (obj == null) return null;

    // create promise if necessary.
    long now = System.currentTimeMillis();
    if (obj.getExpiry() < now) {
      Statistics history = getStatistics(onum);
      if (history != null) {
        int promise = history.generatePromise();
        if (promise > 0) {
          NodePrincipal client = Client.getClient().getPrincipal();
          synchronized (store) {
            // create a promise

            if (store.isWritten(onum))
            // object has been written - no promise for you!
              return obj;

            // check to see if someone else has created a promise
            SerializedObject newObj = store.read(onum);
            long time = newObj.getExpiry();

            if (time < now + promise) {
              try {
                // update the promise
                newObj.setExpiry(now + promise);
                long tid = rand.nextLong();
                store.beginTransaction(tid, client);
                store.registerUpdate(tid, client, newObj);
                store.finishPrepare(tid, client);
                store.commit(tid, null, client, sm);
              } catch (AccessException exc) {
                // TODO: this should probably use the core principal instead of
                // the client principal, and AccessExceptions should be
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
    // Core local = Client.getClient().getCore(store.getName());
    // final fabric.lang.Object._Proxy object =
    // new fabric.lang.Object._Proxy(local, onum);
    // stats = Client.runInTransaction(tid, new Client.Code<Statistics>() {
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
   *           if the principal is not allowed to create objects on this core.
   */
  public long[] newOnums(NodePrincipal client, int num) throws AccessException {
    synchronized (store) {
      return store.newOnums(num);
    }
  }

  /**
   * Creates new onums, bypassing authorization. This is for internal use by the
   * core.
   */
  long[] newOnums(int num) {
    synchronized (store) {
      return store.newOnums(num);
    }
  }

}
