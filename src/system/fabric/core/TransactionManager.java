package fabric.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jif.lang.Label;
import jif.lang.LabelUtil;
import fabric.client.Client;
import fabric.client.Core;
import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.AccessException;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.core.store.ObjectStore;
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
  public void abortTransaction(Principal client, int transactionID)
      throws AccessException {
    synchronized (store) {
      store.rollback(client, transactionID);
    }
  }

  /**
   * Execute the commit phase of two phase commit.
   */
  public void commitTransaction(Principal client, int transactionID)
      throws TransactionCommitFailedException {
    synchronized (store) {
      try {
        store.commit(client, transactionID);
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
      } catch (final AccessException e) {
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
    Core core = Client.getClient().getCore(store.getName());
    return new Label.$Proxy(core, labelOnum);
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
    SerializedObject obj = read(principal, onum);
    if (obj == null) return null;
    
    Label headLabel = getLabelByOnum(obj.getLabelOnum());

    // Traverse object graph and add more objects to the object group.
    LongKeyMap<SerializedObject> group = new LongKeyHashMap<SerializedObject>();
    for (Iterator<Long> it = obj.getIntracoreRefIterator(); it.hasNext();) {
      long relatedOnum = it.next();
      SerializedObject related = read(principal, relatedOnum, true);
      if (related != null) {
        // If this is a dissemination read, ensure that the related object's
        // label is at most as restrictive as the head object's label.
        if (dissem) {
          Label relatedLabel = getLabelByOnum(related.getLabelOnum());
          if (!relabelsTo(relatedLabel, headLabel)) continue;
        }
        
        group.put(relatedOnum, related);
        int count = 0;
        if (worker.numSendsByType.containsKey(related.getClassName()))
          count = worker.numSendsByType.get(related.getClassName());
        count++;
        worker.numSendsByType.put(related.getClassName(), count);
      }
    }

    worker.numObjectsSent += group.size() + 1;
    int count = 0;
    if (worker.numSendsByType.containsKey(obj.getClassName()))
      count = worker.numSendsByType.get(obj.getClassName());
    count++;
    worker.numSendsByType.put(obj.getClassName(), count);
    
    return new ObjectGroup(obj, group);
  }

  /**
   * This is the cache for checking whether one label relabels to another. We're
   * not using the caches in LabelUtil because the transaction management is too
   * slow (!!).
   */
  private static final LongKeyMap<LongKeyHashMap<Boolean>> cachedRelabelQueries =
      new LongKeyHashMap<LongKeyHashMap<Boolean>>();

  private static Boolean checkRelabellingCache(long labelFromOnum,
      long labelToOnum) {
    LongKeyMap<Boolean> submap;
    synchronized (cachedRelabelQueries) {
      submap = cachedRelabelQueries.get(labelFromOnum);
      if (submap == null) return null;
    }

    synchronized (submap) {
      return submap.get(labelToOnum);
    }
  }

  private static void cacheRelabelResult(long labelFromOnum, long labelToOnum,
      Boolean result) {
    LongKeyHashMap<Boolean> submap;
    synchronized (cachedRelabelQueries) {
      submap = cachedRelabelQueries.get(labelFromOnum);
      if (submap == null) {
        submap = new LongKeyHashMap<Boolean>();
        cachedRelabelQueries.put(labelFromOnum, submap);
      }
    }

    synchronized (submap) {
      submap.put(labelToOnum, result);
    }
  }

  /**
   * This assumes that the label references are intracore references.
   */
  private boolean relabelsTo(final Label from, final Label to) {
    if (from.$getOnum() == to.$getOnum()) return true;
    Boolean result = checkRelabellingCache(from.$getOnum(), to.$getOnum());
    if (result != null) return result;
    
    // Call into the Jif label framework to perform the relabelling check.
    result = Client.runInTransaction(new Client.Code<Boolean>() {
      public Boolean run() {
        return LabelUtil.$Impl.relabelsTo(from, to);
      }
    });

    cacheRelabelResult(from.$getOnum(), to.$getOnum(), result);

    return result;
  }

  /**
   * @throws AccessException
   *           if the principal is not allowed to read the object.
   */
  public SerializedObject read(Principal client, long onum)
      throws AccessException {
    return read(client, onum, false);
  }

  /**
   * @param denyWithNull
   *          If true, this method will return null instead of throwing an
   *          AccessException if the client has insufficient privileges.
   */
  private SerializedObject read(Principal client, long onum,
      boolean denyWithNull) throws AccessException {
    SerializedObject obj;
    synchronized (store) {
      obj = store.read(onum);
    }
    
    if (obj == null) return null;
    if (isReadPermitted(client, obj.getLabelOnum())) return obj;
    if (denyWithNull) return null;
    throw new AccessException();
  }

  /**
   * This is the cache for authorizing reads. The keys in this map are label
   * onums and principals. The values are booleans that specify whether the
   * principal is authorized to read according to the label. We're not using the
   * caches in LabelUtil because the transaction management is too slow (!!).
   */
  private static final LongKeyMap<Map<Principal, Boolean>> cachedReadAuthorizations =
      new LongKeyHashMap<Map<Principal, Boolean>>();

  private static Boolean checkAuthorizationCache(
      LongKeyMap<Map<Principal, Boolean>> cache, Principal principal,
      long labelOnum) {
    Map<Principal, Boolean> submap;
    synchronized (cache) {
      submap = cache.get(labelOnum);
      if (submap == null) return null;
    }

    synchronized (submap) {
      return submap.get(principal);
    }
  }

  private static void cacheAuthorization(
      LongKeyMap<Map<Principal, Boolean>> cache, Principal principal,
      long labelOnum, Boolean result) {
    Map<Principal, Boolean> submap;
    synchronized (cache) {
      submap = cache.get(labelOnum);
      if (submap == null) {
        submap = new HashMap<Principal, Boolean>();
        cache.put(labelOnum, submap);
      }
    }

    synchronized (submap) {
      submap.put(principal, result);
    }
  }

  /**
   * Determines whether the given principal is permitted to read according to
   * the label at the given onum.
   */
  private boolean isReadPermitted(final Principal principal, long labelOnum) {
    // Allow the core's client principal to do anything. We use pointer equality
    // here to avoid having to call into the client.
    if (principal == Client.getClient().getPrincipal()) return true;

    Boolean result =
        checkAuthorizationCache(cachedReadAuthorizations, principal, labelOnum);
    if (result != null) return result;

    // Call into the Jif label framework to perform the label check.
    final Label label = getLabelByOnum(labelOnum);
    result = Client.runInTransaction(new Client.Code<Boolean>() {
      public Boolean run() {
        return LabelUtil.$Impl.isReadableBy(label, principal);
      }
    });

    cacheAuthorization(cachedReadAuthorizations, principal, labelOnum, result);

    return result;
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
