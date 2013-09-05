package fabric.store;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;
import static fabric.common.Logging.STORE_TRANSACTION_LOGGER;
import static fabric.store.db.ObjectDB.UpdateType.CREATE;
import static fabric.store.db.ObjectDB.UpdateType.WRITE;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fabric.common.AuthorizationUtil;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
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
import fabric.store.db.ObjectDB.ExtendWarrantyStatus;
import fabric.store.db.SemanticWarrantyTable;
import fabric.store.db.SemanticWarrantyTable.SemanticExtendStatus;
import fabric.worker.AbortException;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
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

  /**
   * Data for maintaining semantic warranties.
   */
  private final SemanticWarrantyTable semanticWarranties;

  public TransactionManager(ObjectDB database, PrivateKey signingKey) {
    this.database = database;
    this.sm = new SubscriptionManager(database.getName(), this, signingKey);
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
    semanticWarranties.abort(transactionID);
    STORE_TRANSACTION_LOGGER.fine("Aborted transaction " + transactionID);
  }

  /**
   * Executes the COMMIT phase of the three-phase commit.
   */
  public void commitTransaction(RemoteIdentity<RemoteWorker> workerIdentity,
      long transactionID, long commitTime)
      throws TransactionCommitFailedException {
    try {
      database.commit(transactionID, commitTime, workerIdentity, sm);
      semanticWarranties.commit(transactionID, commitTime);
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
      }

      // Prepare creates.
      for (SerializedObject o : req.creates) {
        database.registerUpdate(tid, worker, o, versionConflicts, CREATE);
      }

      if (!versionConflicts.isEmpty()) {
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      // Double check calls.
      long currentTime = System.currentTimeMillis();
      long currentProposedTime =
        (longestWarranty == null ? 0 : longestWarranty.expiry());
      SEMANTIC_WARRANTY_LOGGER.finest(
          String.format("Checking calls for %x that would delay longer than %d ms",
            tid, currentProposedTime - currentTime));
      Pair<SemanticWarranty, Set<SemanticWarrantyRequest>> callPrepareResp =
        semanticWarranties.prepareWrites(req.writes, req.creates, tid,
            currentProposedTime, database.getName());

      SemanticWarranty longestCallWarranty = callPrepareResp.first;
      Set<SemanticWarrantyRequest> additionalStuff = callPrepareResp.second;
      // TODO: Prepare additional creates and writes.  Send back additional
      // reads(/calls?) and their warranties.

      STORE_TRANSACTION_LOGGER.fine("Prepared writes for transaction " + tid);

      database.finishPrepareWrites(tid, worker);

      if (longestCallWarranty != null
          && (longestWarranty == null
            || longestCallWarranty.expiry() > longestWarranty.expiry())) {
        return longestCallWarranty.expiry();
      }

      return longestWarranty == null ? 0 : longestWarranty.expiry();
    } catch (TransactionPrepareFailedException e) {
      database.abortPrepareWrites(tid, worker);
      semanticWarranties.abort(tid);
      throw e;
    } catch (RuntimeException e) {
      e.printStackTrace();
      semanticWarranties.abort(tid);
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
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts =
          new LongKeyHashMap<Pair<SerializedObject, VersionWarranty>>();

      // This will store the warranties that will be sent back to the worker as
      // a result of the prepare.
      LongKeyMap<VersionWarranty> prepareResult =
          new LongKeyHashMap<VersionWarranty>();

      // This will store the new warranties we get.
      List<VersionWarranty.Binding> newWarranties =
          new ArrayList<VersionWarranty.Binding>();

      // Check reads
      for (LongKeyMap.Entry<Integer> entry : reads.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue().intValue();

        // Attempt to extend the object's warranty.
        try {
          Pair<ExtendWarrantyStatus, VersionWarranty> status =
              database.extendWarrantyForReadPrepare(worker, onum, version,
                  commitTime);
          switch (status.first) {
          case NEW:
            newWarranties.add(status.second.new Binding(onum, version));
            //$FALL-THROUGH$
          case OLD:
            prepareResult.put(onum, status.second);
            break;

          case BAD_VERSION:
            SerializedObject obj = database.read(onum);
            status = database.refreshWarranty(onum);
            versionConflicts
                .put(onum, new Pair<SerializedObject, VersionWarranty>(obj,
                    status.second));
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

      STORE_TRANSACTION_LOGGER.fine("Prepared transaction " + tid);
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
   * Executes the calls piece of the PREPARE_READS phase of the three-phase
   * commit.
   * 
   * @param worker
   *          The worker requesting the prepare
   * @throws TransactionPrepareFailedException
   *           If the transaction could not successfully extend the
   *           SemanticWarranty on any of the calls
   */
  public Map<CallInstance, SemanticWarranty> prepareCalls(Principal worker, long
      tid, Map<CallInstance, WarrantiedCallResult> calls, long commitTime)
    throws TransactionPrepareFailedException {
    try {
      Map<CallInstance, SemanticWarranty> updatedWars = new HashMap<CallInstance, SemanticWarranty>();
      Map<CallInstance, WarrantiedCallResult> conflictWars = new
        HashMap<CallInstance, WarrantiedCallResult>();
      Set<CallInstance> staleWars = new HashSet<CallInstance>();
      for (CallInstance call : calls.keySet()) {
        semanticWarranties.notifyReadPrepare(call, commitTime);
        Pair<SemanticExtendStatus, WarrantiedCallResult> extResult =
          semanticWarranties.extend(call, calls.get(call), commitTime);
        switch (extResult.first) {
          case OK:
            SEMANTIC_WARRANTY_LOGGER.finest("At risk call " + call
                + " extended for " +
                (extResult.second.warranty.expiry() - calls.get(call).warranty.expiry())
                + "ms");
            updatedWars.put(call, extResult.second.warranty);
            break;
          case BAD_VERSION:
            SEMANTIC_WARRANTY_LOGGER.finest("At risk call " + call
                + " had bad version!");
            if (extResult.second == null) {
              staleWars.addAll(semanticWarranties.getExpiredSubgraph(call));
            } else {
              conflictWars.put(call, extResult.second);
            }
            break;
          case DENIED:
            SEMANTIC_WARRANTY_LOGGER.finest("Prepare Calls failed due to inability to extend!");
            // Remove this call from the worker's cache, it's going to change.
            staleWars.add(call);
            throw new TransactionPrepareFailedException(conflictWars, staleWars,
                "Could not extend for " + call);
        }
      }
      if (conflictWars.size() + staleWars.size() > 0) {
        SEMANTIC_WARRANTY_LOGGER.finest("Prepare Calls failed due to conflicting or stale call values!");
        long currentTime = System.currentTimeMillis();
        String msg = "Prepare Calls failed due to " + conflictWars.size()
          + " conflicting and " + staleWars.size() + " stale call values:\n";
        msg += "Conflicting...\n";
        for (Map.Entry<CallInstance, WarrantiedCallResult> entry : conflictWars.entrySet())
          msg += "\t" + entry.getKey() + " = " + entry.getValue().value.$getOnum() + " (" +
            (entry.getValue().warranty.expiry() - currentTime) + ")\n";
        msg += "Stale...\n";
        for (CallInstance call : staleWars)
          msg += "\t" + call + "\n";
        throw new TransactionPrepareFailedException(conflictWars, staleWars,
            msg);
      }
      SEMANTIC_WARRANTY_LOGGER.finest("Calls prepared!");
      return updatedWars;
    } catch (TransactionPrepareFailedException e) {
      try {
        abortTransaction(worker, tid);
      } catch (AccessException ae) {
        throw new InternalError(ae);
      }
      throw e;
    }
  }

  /**
   * Executes the requests piece of the PREPARE_READS phase of the three-phase
   * commit.
   * 
   * @param worker
   *          The worker requesting the prepare
   */
  public Map<CallInstance, SemanticWarranty> prepareRequests(Principal worker,
      long tid, Set<SemanticWarrantyRequest> requests) throws
    TransactionPrepareFailedException {
    try {
      /* Create the associated warranties and add these calls to the warranties
       * table.
       */ 
      Map<CallInstance, SemanticWarranty> warranties =
        new HashMap<CallInstance, SemanticWarranty>();

      // Have to do a topologically sorted order of requests (so call dependencies
      // have warranties already).
      Map<CallInstance, Set<CallInstance>> simplifiedDepMap = new
        HashMap<CallInstance, Set<CallInstance>>();
      Map<CallInstance, SemanticWarrantyRequest> reqMap = new
        HashMap<CallInstance, SemanticWarrantyRequest>(requests.size());
      for (SemanticWarrantyRequest r : requests) {
        reqMap.put(r.call, r);
      }
      for (SemanticWarrantyRequest r : requests) {
        Set<CallInstance> depsInTable = new HashSet<CallInstance>();
        for (CallInstance c : r.calls.keySet())
          if (reqMap.containsKey(c))
            depsInTable.add(c);
        simplifiedDepMap.put(r.call, depsInTable);
      }

      LinkedList<CallInstance> fringe = new LinkedList<CallInstance>();
      Set<CallInstance> nonfringe = new HashSet<CallInstance>();
      for (CallInstance k : simplifiedDepMap.keySet())
        if (simplifiedDepMap.get(k).isEmpty())
          fringe.add(k);
        else
          nonfringe.add(k);

      while (!fringe.isEmpty()) {
        SemanticWarrantyRequest r = reqMap.get(fringe.poll());
        SEMANTIC_WARRANTY_LOGGER.finest(
            "Proposing SemanticWarranty for CallInstance " + r.call);

        // Get a proposal for a warranty
        SemanticWarranty proposed = semanticWarranties.requestWarranty(tid, r);
        SEMANTIC_WARRANTY_LOGGER.finer(r.call.toString()
            + " was proposed a warranty to expire in " + (proposed.expiry() -
              System.currentTimeMillis()));
        // Add it to the response set
        warranties.put(r.call, proposed);
        
        //Update fringe
        for (CallInstance c : new HashSet<CallInstance>(nonfringe)) {
          simplifiedDepMap.get(c).remove(r.call);
          if (simplifiedDepMap.get(c).isEmpty()) {
            nonfringe.remove(c);
            fringe.add(c);
          }
        }
      }

      return warranties;
    } catch (TransactionPrepareFailedException e) {
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
   * Returns a WarrantiedCallResult containing the specified call's value and
   * warranty.
   * 
   * @param principal
   *          The principal performing the read.
   * @param id
   *          The id for the call instance.
   */
  public Pair<WarrantiedCallResult, LongSet> getCall(Principal principal,
      CallInstance id) throws AccessException {
    Pair<WarrantiedCallResult, LongSet> result = 
      new Pair<WarrantiedCallResult, LongSet>(semanticWarranties.get(id),
          semanticWarranties.getCreates(id));
    if (result.first == null) throw new AccessException(
        "AccessDenied, could not find call id " + id.toString());
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
        new ArrayList<VersionWarranty.Binding>();

    for (Entry<Integer> entry : onumsToVersions.entrySet()) {
      long onum = entry.getKey();
      Pair<ExtendWarrantyStatus, VersionWarranty> refreshResult =
          database.refreshWarranty(onum);

      if (refreshResult.first == ExtendWarrantyStatus.NEW) {
        newWarranties.add(refreshResult.second.new Binding(onum, entry
            .getValue()));
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
  List<Pair<SerializedObject, VersionWarranty>> checkForStaleObjects(
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

    List<Pair<SerializedObject, VersionWarranty>> result =
        new ArrayList<Pair<SerializedObject, VersionWarranty>>();
    List<VersionWarranty.Binding> newWarranties =
        new ArrayList<VersionWarranty.Binding>();
    boolean success = false;

    try {
      for (LongKeyMap.Entry<Integer> entry : versions.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue();

        int curVersion = database.getVersion(onum);
        if (curVersion != version) {
          Pair<ExtendWarrantyStatus, VersionWarranty> refreshWarrantyResult =
              database.refreshWarranty(onum);
          SerializedObject obj = database.read(onum);

          result.add(new Pair<SerializedObject, VersionWarranty>(obj,
              refreshWarrantyResult.second));

          if (refreshWarrantyResult.first == ExtendWarrantyStatus.NEW) {
            newWarranties.add(refreshWarrantyResult.second.new Binding(onum,
                version));
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
