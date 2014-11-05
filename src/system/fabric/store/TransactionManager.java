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
import java.util.logging.Level;

import fabric.common.AuthorizationUtil;
import fabric.common.Logging;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SemanticWarranty;
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
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.lang.Object._Impl;
import fabric.lang.WrappedJavaInlineable;
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
import fabric.worker.transaction.ReadMap;
import fabric.worker.RemoteStore;
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
   * on all modified objects will expire, and the additional reads and
   * warrantied calls the transaction needs to use to support any call updates
   * this transaction will trigger.
   * @throws TransactionPrepareFailedException
   *           If the transaction would cause a conflict or if the worker is
   *           insufficiently privileged to execute the transaction.
   */
  public long prepareWrites(RemoteIdentity<RemoteWorker> workerIdentity,
      PrepareWritesRequest req) throws TransactionPrepareFailedException {
    final long tid = req.tid;
    VersionWarranty longestWarranty = null;

    Principal worker = workerIdentity.principal;

    // First, check write permissions. We do this before we attempt to do the
    // actual prepare because we want to run the permissions check in a
    // transaction outside of the worker's transaction.
    RemoteStore store = Worker.getWorker().getStore(database.getName());
    if (worker == null || worker.$getStore() != store
        || worker.$getOnum() != ONumConstants.STORE_PRINCIPAL) {
      try {
        checkPerms(worker, LongSet.EMPTY, req.writes);
      } catch (AccessException e) {
        semanticWarranties.abort(tid);
        throw new TransactionPrepareFailedException(e.getMessage());
      } catch (AbortException e) {
        semanticWarranties.abort(tid);
        throw new TransactionPrepareFailedException(e.getMessage());
      }
    }

    database.beginPrepareWrites(tid, worker);

    long longest = 0;
    OidKeyHashMap<Integer> addedReads = new OidKeyHashMap<>();
    Map<CallInstance, WarrantiedCallResult> addedCalls = new HashMap<>();

    try {
      // This will store the set of onums of objects that were out of date.
      LongKeyMap<SerializedObjectAndTokens> versionConflicts =
          new LongKeyHashMap<>();

      // Prepare writes.
      Pair<ExtendWarrantyStatus, VersionWarranty> scratchObj =
          new Pair<>(null, null);
      for (SerializedObject o : req.writes) {
        VersionWarranty warranty =
            database.registerUpdate(scratchObj, tid, worker, o,
                versionConflicts, WRITE);
        if (longestWarranty == null || warranty.expiresAfter(longestWarranty))
          longestWarranty = warranty;
      }

      // Prepare creates.
      for (SerializedObject o : req.creates) {
        database.registerUpdate(scratchObj, tid, worker, o, versionConflicts,
            CREATE);
      }

      // Double check calls.
      long currentTime = System.currentTimeMillis();
      long currentProposedTime =
          (longestWarranty == null ? 0 : longestWarranty.expiry());
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
          "Checking calls for {0} that would delay longer than {1} ms",
          Long.toHexString(tid), currentProposedTime - currentTime);
      Pair<SemanticWarranty, Pair<Map<CallInstance, SemanticWarrantyRequest>, Map<CallInstance, SemanticWarrantyRequest>>> callPrepareResp =
          semanticWarranties.prepareWrites(req.writes, req.creates, tid,
              currentProposedTime, database.getName());

      SemanticWarranty longestCallWarranty = callPrepareResp.first;
      Map<CallInstance, SemanticWarrantyRequest> updates =
          callPrepareResp.second.first;
      Map<CallInstance, SemanticWarrantyRequest> newCalls =
          callPrepareResp.second.second;
      updates.putAll(newCalls);

      OidKeyHashMap<Store> addedCreates = new OidKeyHashMap<>();

      for (SemanticWarrantyRequest update : updates.values()) {
        // Register additional creates
        for (LongKeyMap<_Impl> submap : update.creates) {
          for (_Impl create : submap.values()) {
            database.registerUpdate(scratchObj, tid, worker,
                new SerializedObject(create), versionConflicts, CREATE);
            addedCreates.put(create.$getStore(), create.$getOnum(),
                create.$getStore());
          }
        }

        // Collect reads and their warranties for the worker
        for (LongKeyMap<ReadMap.Entry> submap : update.reads) {
          for (ReadMap.Entry read : submap.values()) {
            addedReads.put(store, read.getRef().onum, read.getVersionNumber());
          }
        }

        // Collect calls and their warranties for the worker
        addedCalls.putAll(update.calls);
      }

      // Remove added reads on objects we created during call updates
      for (LongKeyMap<Store> submap : addedCreates) {
        for (LongKeyMap.Entry<Store> entry : submap.entrySet()) {
          addedReads.remove(entry.getValue(), entry.getKey());
        }
      }

      // Remove added reads on objects we wrote or created in this
      // transaction...
      for (SerializedObject o : req.writes) {
        addedReads.remove(store, o.getOnum());
      }
      for (SerializedObject o : req.creates) {
        addedReads.remove(store, o.getOnum());
      }

      if (!versionConflicts.isEmpty()) {
        throw new TransactionPrepareFailedException(versionConflicts);
      }

      // Don't worry about calls we're updating or requesting.
      for (CallInstance updatingCall : updates.keySet()) {
        addedCalls.remove(updatingCall);
      }

      // Don't worry about calls we're requesting in this prepare request
      for (SemanticWarrantyRequest callRequest : req.calls) {
        addedCalls.remove(callRequest.call);
      }

      // Ugh this is ugly.
      longest = longestWarranty == null ? 0 : longestWarranty.expiry();
      if (longestCallWarranty != null
          && (longestCallWarranty.expiresAfter(longest, true))) {
        longest = longestCallWarranty.expiry();
      }

      database.finishPrepareWrites(tid, worker);
    } catch (TransactionPrepareFailedException e) {
      database.abortPrepareWrites(tid, worker);
      semanticWarranties.abort(tid);
      throw e;
    } catch (RuntimeException e) {
      e.printStackTrace();
      database.abortPrepareWrites(tid, worker);
      semanticWarranties.abort(tid);
      throw e;
    }

    try {
      if (addedCalls.size() != 0)
        prepareCalls(worker, tid, addedCalls, longest);
      // Don't bother if there were no reads.
      if (addedReads.get(store) != null)
        prepareReads(workerIdentity, tid, addedReads.get(store), longest);
    } catch (TransactionPrepareFailedException tpfe) {
      // Don't need to worry about calls because inprocess store doesn't
      // maintain a cache.  Need to update local cache of objects, however.
      for (SerializedObjectAndTokens conflict : tpfe.versionConflicts.values())
        store.updateCache(conflict);
      throw new TransactionPrepareFailedException(
          "Had problems with reads from call updates!");
    }

    STORE_TRANSACTION_LOGGER.log(Level.FINE,
        "Prepared writes for transaction {0}", tid);

    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Transaction {0} prepared writes to be done in {1} ms.",
        Long.toHexString(tid), longest - System.currentTimeMillis());

    return longest;
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

    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Transaction {0} preparing reads to be extended for {1} ms.",
        Long.toHexString(tid), commitTime - System.currentTimeMillis());

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
      final Pair<ExtendWarrantyStatus, VersionWarranty> resultObj =
          new Pair<>(null, null);
      for (LongKeyMap.Entry<Integer> entry : reads.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue().intValue();

        // Attempt to extend the object's warranty.
        try {
          Pair<ExtendWarrantyStatus, VersionWarranty> status =
              database.extendWarrantyForReadPrepare(resultObj, worker, onum,
                  version, commitTime);
          switch (status.first) {
          case NEW:
            if (ENABLE_WARRANTY_REFRESHES) {
              newWarranties.add(status.second.new Binding(onum, version));
            }
            //$FALL-THROUGH$
          case OLD:
            prepareResult.put(onum, status.second);
            break;

          case BAD_VERSION:
            SerializedObject obj = database.read(onum);
            status = database.refreshWarranty(resultObj, onum);
            versionConflicts.put(onum, new SerializedObjectAndTokens(obj,
                status.second));
            Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
                "BAD VERSION ON {0} FOR {1}: DB: {2}, US: {3}", onum,
                Long.toHexString(tid), obj.getVersion(), version);
            continue;

          case DENIED:
            sm.notifyNewWarranties(newWarranties, null);
            Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
                "DENIED EXTENSION ON {0} FOR {1}", onum, Long.toHexString(tid));
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
   * Executes the calls piece of the PREPARE_READS phase of the three-phase
   * commit.
   *
   * @param worker
   *          The worker requesting the prepare
   * @throws TransactionPrepareFailedException
   *           If the transaction could not successfully extend the
   *           SemanticWarranty on any of the calls
   */
  public Map<CallInstance, WarrantiedCallResult> prepareCalls(Principal worker,
      long tid, Map<CallInstance, WarrantiedCallResult> calls, long commitTime)
      throws TransactionPrepareFailedException {
    try {
      Map<CallInstance, WarrantiedCallResult> updatedWars =
          new HashMap<CallInstance, WarrantiedCallResult>();
      Map<CallInstance, WarrantiedCallResult> conflictWars =
          new HashMap<CallInstance, WarrantiedCallResult>();
      for (CallInstance call : calls.keySet()) {
        Pair<SemanticExtendStatus, WarrantiedCallResult> extResult =
            semanticWarranties.extendForReadPrepare(call, calls.get(call),
                commitTime, tid);
        switch (extResult.first) {
        case OK:
          updatedWars.put(call, extResult.second);
          break;
        case BAD_VERSION:
          conflictWars.put(call, extResult.second);
          break;
        case DENIED:
          throw new TransactionPrepareFailedException(conflictWars,
              "Could not extend for " + call);
        }
      }
      if (conflictWars.size() > 0) {
        SEMANTIC_WARRANTY_LOGGER
            .finest("Prepare Calls failed due to conflicting or stale call values!");
        long currentTime = System.currentTimeMillis();
        String msg =
            "Prepare Calls failed due to " + conflictWars.size()
                + " conflicting values:\n";
        msg += "Conflicting...\n";
        for (Map.Entry<CallInstance, WarrantiedCallResult> entry : conflictWars
            .entrySet()) {
          if (entry.getValue().getValue() instanceof WrappedJavaInlineable) {
            msg +=
                "\t" + entry.getKey() + " = "
                    + entry.getValue().getValue().$unwrap() + " ("
                    + (entry.getValue().getWarranty().expiry() - currentTime)
                    + ")\n";
          } else {
            msg +=
                "\t" + entry.getKey() + " = "
                    + entry.getValue().getValue().$getOnum() + " ("
                    + (entry.getValue().getWarranty().expiry() - currentTime)
                    + ")\n";
          }
        }
        throw new TransactionPrepareFailedException(conflictWars, msg);
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
      long tid, Set<SemanticWarrantyRequest> requests) {
    /* Create the associated warranties and add these calls to the warranties
     * table.
     */
    Map<CallInstance, SemanticWarranty> warranties =
        new HashMap<CallInstance, SemanticWarranty>();

    // Have to do a topologically sorted order of requests (so call dependencies
    // have warranties already).
    Map<CallInstance, Set<CallInstance>> simplifiedDepMap =
        new HashMap<CallInstance, Set<CallInstance>>();
    Map<CallInstance, SemanticWarrantyRequest> reqMap =
        new HashMap<CallInstance, SemanticWarrantyRequest>(requests.size());
    for (SemanticWarrantyRequest r : requests) {
      reqMap.put(r.call, r);
    }
    for (SemanticWarrantyRequest r : requests) {
      Set<CallInstance> depsInTable = new HashSet<CallInstance>();
      for (CallInstance c : r.calls.keySet())
        if (reqMap.containsKey(c)) depsInTable.add(c);
      simplifiedDepMap.put(r.call, depsInTable);
    }

    LinkedList<CallInstance> fringe = new LinkedList<CallInstance>();
    Set<CallInstance> nonfringe = new HashSet<CallInstance>();
    for (CallInstance k : simplifiedDepMap.keySet())
      if (simplifiedDepMap.get(k).isEmpty())
        fringe.add(k);
      else nonfringe.add(k);

    while (!fringe.isEmpty()) {
      SemanticWarrantyRequest r = reqMap.get(fringe.poll());
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
          "Proposing SemanticWarranty for CallInstance {0}", r.call);

      // Get a proposal for a warranty
      SemanticWarranty proposed =
          semanticWarranties.requestWarranty(tid, r, true);
      if (proposed != null) {
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
            "{0} was proposed a warranty to expire in {1}", r.call.toString(),
            (proposed.expiry() - System.currentTimeMillis()));
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
      } else {
        // We couldn't get that warranty... so don't even bother with other
        // warranties that used it.  Oh well.
        List<CallInstance> callsToDrop = new ArrayList<CallInstance>();
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
            "{0} could not make a warranty.", r.call);
        callsToDrop.add(r.call);
        // Recursively remove stuff that used this.
        while (!callsToDrop.isEmpty()) {
          CallInstance callToDrop = callsToDrop.remove(0);
          for (CallInstance c : new HashSet<CallInstance>(nonfringe)) {
            if (simplifiedDepMap.get(c).contains(callToDrop)) {
              nonfringe.remove(c);
              Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
                  "{0} could not make a warranty.", c);
              callsToDrop.add(c);
            }
          }
        }
      }
    }

    return warranties;
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
  public WarrantiedCallResult getCall(Principal principal, CallInstance id)
      throws AccessException {
    WarrantiedCallResult result = semanticWarranties.fetchForWorker(id);
    if (result == null)
      throw new AccessException("AccessDenied, could not find call id "
          + id.toString());
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
        ENABLE_WARRANTY_REFRESHES ? new ArrayList<VersionWarranty.Binding>()
            : null;

    Pair<ExtendWarrantyStatus, VersionWarranty> resultObj =
        new Pair<>(null, null);
    for (Entry<Integer> entry : onumsToVersions.entrySet()) {
      long onum = entry.getKey();
      Pair<ExtendWarrantyStatus, VersionWarranty> refreshResult =
          database.refreshWarranty(resultObj, onum);

      if (ENABLE_WARRANTY_REFRESHES) {
        if (refreshResult.first == ExtendWarrantyStatus.NEW) {
          newWarranties.add(refreshResult.second.new Binding(onum, entry
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
      Pair<ExtendWarrantyStatus, VersionWarranty> resultObj =
          new Pair<>(null, null);
      for (LongKeyMap.Entry<Integer> entry : versions.entrySet()) {
        long onum = entry.getKey();
        int version = entry.getValue();

        int curVersion = database.getVersion(onum);
        if (curVersion != version) {
          Pair<ExtendWarrantyStatus, VersionWarranty> refreshWarrantyResult =
              database.refreshWarranty(resultObj, onum);
          SerializedObject obj = database.read(onum);

          result.add(new SerializedObjectAndTokens(obj,
              refreshWarrantyResult.second));

          if (ENABLE_WARRANTY_REFRESHES) {
            if (refreshWarrantyResult.first == ExtendWarrantyStatus.NEW) {
              newWarranties.add(refreshWarrantyResult.second.new Binding(onum,
                  version));
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
