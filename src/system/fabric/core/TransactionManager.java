package fabric.core;

import java.security.PrivateKey;
import java.util.*;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.AccessException;
import fabric.common.AuthorizationUtil;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.core.store.ObjectStore;
import fabric.dissemination.Glob;
import fabric.lang.Principal;
import fabric.lang.Statistics;

public class TransactionManager {

  private static final int INITIAL_OBJECT_VERSION_NUMBER = 1;
  private static final int MAX_GROUP_SIZE = 75;

  /**
   * The object store of the core for which we're managing transactions.
   */
  protected final ObjectStore store;
  protected final LongKeyMap<Statistics> objectStats;
  protected final ReadHistory            readHistory;
  protected static final Random rand = new Random();

  public TransactionManager(ObjectStore store) {
    this.store = store;
    this.objectStats = new LongKeyHashMap<Statistics>();
    this.readHistory = new ReadHistory();
  }

  /**
   * Instruct the transaction manager that the given transaction is aborting
   */
  public void abortTransaction(Principal client, long transactionID)
      throws AccessException {
    synchronized (store) {
      store.rollback(transactionID, client);
    }
  }

  /**
   * Execute the commit phase of two phase commit.
   */
  public void commitTransaction(Principal client, long transactionID)
      throws TransactionCommitFailedException {
    synchronized (store) {
      try {
        store.commit(transactionID, client);
      } catch (final AccessException e) {
        throw new TransactionCommitFailedException("Insufficient Authorization");
      } catch (final RuntimeException e) {
        throw new TransactionCommitFailedException("something went wrong", e);
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
   * @param creates
   *          The set of objects to be created in this transaction
   * @param updates
   *          The set of objects to be updated in this transaction
   * @param reads
   *          The set of objects that the transaction read
   * @throws TransactionPrepareFailedException
   *           If the transaction would cause a conflict or if the client is
   *           insufficiently privileged to execute the transaction.
   */
  public void prepare(Principal client, PrepareRequest req)
      throws TransactionPrepareFailedException {
    final long tid = req.tid;
    synchronized (store) {
      try {
        store.beginTransaction(tid, client);
      } catch (final AccessException e) {
        throw new TransactionPrepareFailedException("Insufficient privileges");
      }
    }

    try {
      // This will store the set of onums of objects that were out of date.
      Set<Long> versionConflicts = new HashSet<Long>();

      // Check writes and update version numbers
      for (SerializedObject o : req.writes) {
        // Make sure no one else is using the object and fetch the old copy from
        // the core.
        long onum = o.getOnum();
        SerializedObject coreCopy;
        synchronized (store) {
          if (store.isPrepared(onum, tid))
            throw new TransactionPrepareFailedException("Object " + onum
                + " has been locked by an uncommitted transaction");

          coreCopy = store.read(onum);

          // Register the update with the store.
          store.registerUpdate(tid, client, o);
        }

        // Make sure the object actually exists.
        if (coreCopy == null) {
          throw new TransactionPrepareFailedException("Object " + o.getOnum()
              + " does not exist.");
        }
        
        // check promises
        if (coreCopy.getExpiry() > req.commitTime) {
          throw new TransactionPrepareFailedException("Update to object" + onum +
              " violates an outstanding promise");
        }

        // Check write permissions
        if (!AuthorizationUtil.isWritePermitted(client, Client.getClient()
            .getCore(store.getName()), coreCopy.getLabelOnum())) {
          throw new TransactionPrepareFailedException("Insufficient privilege "
              + "to write object " + o.getOnum());
        }

        // Check version numbers.
        int coreVersion = coreCopy.getVersion();
        int clientVersion = o.getVersion();
        if (coreVersion != clientVersion) {
          versionConflicts.add(onum);
          continue;
        }
        
        // Check against read history
        if (!readHistory.check(onum, req.commitTime)) {
          throw new TransactionPrepareFailedException("Object " + onum +
              " has been read since it's proposed commit time.");
        }
        
        // Update promise statistics
        ensureStatistics(onum).commitWrote();
        
        // Update the version number on the prepared copy of the object.
        o.setVersion(coreVersion + 1);
      }

      // Check creates.
      synchronized (store) {
        for (SerializedObject o : req.creates) {
          long onum = o.getOnum();

          // Make sure no one else is using the object.
          if (store.isPrepared(onum, tid))
            throw new TransactionPrepareFailedException(versionConflicts,
                "Object " + onum + " has been locked by an "
                    + "uncommitted transaction");

          // Make sure the onum isn't already taken.
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
          SerializedObject obj = store.read(onum);
          int curVersion = obj.getVersion();
          if (curVersion != version) {
            versionConflicts.add(onum);
            continue;
          }

          // inform the object statistics of the read
          ensureStatistics(onum).commitRead();
          
          // Register the read with the store.
          store.registerRead(tid, client, onum);
        }
      }
      
      if (!versionConflicts.isEmpty()) {
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      readHistory.record(req);
      store.finishPrepare(tid, client);
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
   * Returns a Glob containing the specified object.
   * 
   * @param key
   *          The private key to use for signing the glob.
   * @param worker
   *          Used to track read statistics.
   */
  public Glob getGlob(long onum, PrivateKey key, Worker worker)
      throws AccessException {
    Glob glob;
    synchronized (store) {
      glob = store.getCachedGlob(onum);
    }
    if (glob != null) return glob;

    ObjectGroup group = readGroup(null, onum, true, null);
    if (group == null) throw new AccessException(store.getName(), onum);

    Core core = Client.getClient().getCore(store.getName());
    glob = new Glob(core, group, key);

    // Cache the glob.
    synchronized (store) {
      store.cacheGlob(group.objects().keySet(), glob);
    }

    worker.numGlobsCreated++;
    worker.numGlobbedObjects += group.objects().size();

    return glob;
  }

  /**
   * Reads a group of objects from the object store.
   * 
   * @param principal
   *          The principal performing the read. For dissemination reads, this
   *          is ignored.
   * @param onum
   *          The group's head object.
   * @param dissem
   *          Whether this is a dissemination read.
   * @param worker
   *          Used to track read statistics.
   */
  public ObjectGroup readGroup(Principal principal, long onum, boolean dissem,
      Worker worker) throws AccessException {
    if (dissem) principal = Client.getClient().getPrincipal();
    SerializedObject obj = checkRead(principal, onum);
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

        if (dissem) {
          // Ensure that the related object hasn't been globbed already.
          synchronized (store) {
            if (store.getCachedGlob(relatedOnum) != null) continue;
          }
        }

        SerializedObject related = read(principal, relatedOnum);
        if (related == null) continue;

        if (dissem) {
          // Ensure that the related object's label is the same as the head
          // object's label. We could be smarter here, but to avoid calling into
          // the client, let's hope pointer-equality is sufficient.
          long relatedLabelOnum = related.getLabelOnum();
          if (headLabelOnum != relatedLabelOnum) continue;
        }

        toVisit.add(related);
      }
    }

    return new ObjectGroup(group);
  }

  /**
   * @throws AccessException
   *           if the principal is not allowed to read the object.
   */
  public SerializedObject checkRead(Principal client, long onum)
      throws AccessException {
    SerializedObject result = read(client, onum);
    if (result == null)
      throw new AccessException(store.getName(), onum);
    return result;
  }

  private SerializedObject read(Principal client, long onum) {
    SerializedObject obj;
    synchronized (store) {
      obj = store.read(onum);
    }

    if (obj == null)
      return null;
    if (!AuthorizationUtil.isReadPermitted(client,
                                           Client.getClient().getCore(store.getName()),
                                           obj.getLabelOnum()))
      return null;
    
    // create promise if necessary.
    long now = System.currentTimeMillis();
    if (obj.getExpiry() < now) {
      Statistics history = getStatistics(onum);
      if (history != null) {
        int promise = history.generatePromise();
        if (promise > 0) {
          synchronized (store) {
            // create a promise
            
            if (store.isWritten(onum))
              // object has been written - no promise for you!
              return obj;
            
            // check to see if someone else has created a promise
            SerializedObject newObj = store.read(onum);
            long time = newObj.getExpiry();
            
            if (time < now + promise) try {
              // update the promise
              newObj.setExpiry(now + promise);
              long tid = rand.nextLong();
              store.beginTransaction(tid, client);
              store.registerUpdate(tid, client, newObj);
              store.finishPrepare(tid, client);
              store.commit(tid, client);
            } catch(AccessException exc) {
              // TODO: this should probably use the core principal instead of
              // the client principal, and AccessExceptions should be impossible
              return obj;
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
   * doesn't exist already.
   */
  private Statistics ensureStatistics(long onum) {
    synchronized (objectStats) {
      Statistics stats = getStatistics(onum);
      if (stats == null) {
        // TODO: potential deadlock
        Core local = Client.getClient().getCore(store.getName());
        fabric.lang.Object.$Proxy object = new fabric.lang.Object.$Proxy(local,onum);
        stats = object.createStatistics();
        objectStats.put(onum, stats);
      }
      return stats;
    }
  }

  /**
   * @throws AccessException
   *           if the principal is not allowed to create objects on this core.
   */
  public long[] newOnums(Principal client, int num) throws AccessException {
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
