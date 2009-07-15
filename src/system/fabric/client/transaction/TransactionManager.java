package fabric.client.transaction;

import static fabric.client.transaction.Log.CommitState.Values.*;

import java.util.*;
import java.util.logging.Logger;

import jif.lang.Label;
import fabric.client.*;
import fabric.client.debug.Timing;
import fabric.client.remote.RemoteClient;
import fabric.client.remote.UpdateMap;
import fabric.common.FabricThread;
import fabric.common.TransactionID;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.core.InProcessCore;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;

/**
 * Holds transaction management information for a single thread. Each thread has
 * its own TransactionManager.
 * <p>
 * We say that a transaction has acquired a write lock on an object if any entry
 * in the object's <code>$history</code> list has <code>$writeLockHolder</code>
 * set to that transaction. @see fabric.lang.Object._Impl
 * </p>
 * <p>
 * We say that a transaction has acquired a read lock if it is in the
 * "read list" for that object. @see fabric.lang.Object._Impl.$readMapEntry
 * </p>
 * <p>
 * When a transaction acquires a read lock, we ensure that the <i>read
 * condition</i> holds: that the holder of the write lock is an ancestor of that
 * transaction. Before reading an object, we ensure that the transaction holds a
 * read lock and that the read condition still holds.
 * </p>
 * <p>
 * When a transaction acquires a write lock, we ensure that the <i>write
 * condition</i> holds: the holders of the read and write locks are all
 * ancestors of that transaction. Before writing an object, we ensure that the
 * transaction holds a write lock and that the write condition still holds.
 * </p>
 * <p>
 * Assumptions:
 * <ul>
 * <li>Once the top-level transaction within a thread aborts or commits, that
 * thread will terminate without performing further operations on the
 * transaction manager.</li>
 * <li>The fetch operation will not be invoked concurrently on the same object.</li>
 * </ul>
 * </p>
 * <p>
 * The following objects are used as condition variables:
 * <ul>
 * <li>Log.children - for signalling that all sub-transactions of a given
 * transaction have finished.</li>
 * <li>Impl objects - for signalling to readers and writers that a read or write
 * lock on that object has been released.
 * </ul>
 * </p>
 */
public final class TransactionManager {
  /**
   * The innermost running transaction for the thread being managed.
   */
  private Log current;

  static final Logger logger = Logger.getLogger("fabric.client.transaction");

  /**
   * A map from OIDs to a version number and a list of logs for transactions
   * that have read that version of the object. For each transaction tx, an
   * object o is in tx.reads exactly when tx is in readList[o]. A transaction
   * has acquired a read lock if its log is in this list. All entries in this
   * list have non-empty <code>readLocks</code> sets.
   */
  // Proxy objects aren't used here because doing so would result in calls to
  // hashcode() and equals() on such objects, resulting in fetching the
  // corresponding Impls from the core.
  static final OidKeyHashMap<ReadMapEntry> readMap =
      new OidKeyHashMap<ReadMapEntry>();

  public static ReadMapEntry getReadMapEntry(_Impl impl, long expiry) {
    FabricSoftRef ref = impl.$ref;

    // Optimization: if the impl lives on the local core, it will never be
    // evicted, so no need to store the entry in the read map.
    if (ref.core.isLocalCore()) return new ReadMapEntry(impl, expiry);

    while (true) {
      ReadMapEntry result;
      synchronized (readMap) {
        result = readMap.get(ref.core, ref.onum);
        if (result == null) {
          result = new ReadMapEntry(impl, expiry);
          readMap.put(ref.core, ref.onum, result);
          return result;
        }
      }

      synchronized (result) {
        synchronized (readMap) {
          // Make sure we still have the right entry.
          if (result != readMap.get(ref.core, ref.onum)) continue;

          result.obj = impl.$ref;
          result.pinCount++;
          result.promise = result.promise > expiry ? result.promise : expiry;
          int ver = impl.$getVersion();
          if (ver == result.versionNumber) return result;

          // Version numbers don't match. Abort all other transactions.
          // XXX What if we just read in an older copy of the object?
          for (Log reader : result.readLocks) {
            reader.flagAbort();
          }

          result.versionNumber = ver;
          return result;
        }
      }
    }
  }

  private static final Map<Thread, TransactionManager> instanceMap =
      new WeakHashMap<Thread, TransactionManager>();

  public static TransactionManager getInstance() {
    Thread thread = Thread.currentThread();

    if (thread instanceof FabricThread) {
      FabricThread ft = (FabricThread) thread;
      TransactionManager result = ft.getTransactionManager();
      if (result == null) {
        result = new TransactionManager();
        ft.setTransactionManager(result);
      }
      return result;
    }

    synchronized (instanceMap) {
      TransactionManager result = instanceMap.get(thread);
      if (result == null) {
        result = new TransactionManager();
        instanceMap.put(thread, result);
      }

      return result;
    }
  }

  private TransactionManager() {
    this.current = null;
  }

  private void checkAbortSignal() {
    if (current.abortSignal != null) {
      synchronized (current) {
        if (!current.tid.isDescendantOf(current.abortSignal)) {
          current.abortSignal = null;
          return;
        }

        logger.finest(current + " got abort signal");
        // Abort the transaction.
        // TODO Provide a reason for the abort.
        throw new AbortException();
      }
    }
  }

  public void abortTransaction() {
    if (current.tid.depth == 0) {
      // Make sure no other thread is working on this transaction.
      synchronized (current.commitState) {
        while (current.commitState.value == PREPARING) {
          try {
            current.commitState.wait();
          } catch (InterruptedException e) {
          }
        }

        switch (current.commitState.value) {
        case UNPREPARED:
        case PREPARE_FAILED:
        case PREPARED:
          current.commitState.value = ABORTING;
          break;

        case PREPARING:
          // We should've taken care of this case already.
          throw new InternalError();

        case COMMITTING:
        case COMMITTED:
          // Too late to abort! We shouldn't really enter this situation.
          logger.fine("Ignoring attempt to abort a committed transaction.");
          return;

        case ABORTING:
        case ABORTED:
          return;
        }
      }
    }

    logger.warning(current + " aborting");
    // Assume only one thread will be executing this.

    // Set the abort flag in all our children.
    current.flagAbort();

    // Wait for all other threads to finish.
    current.waitForThreads();

    current.abort();
    logger.warning(current + " aborted");

    synchronized (current.commitState) {
      current.commitState.value = ABORTED;

      if (current.tid.parent == null || current.parent != null
          && current.parent.tid.equals(current.tid.parent)) {
        // The parent frame represents the parent transaction. Pop the stack.
        current = current.parent;
      } else {
        // Reuse the current frame for the parent transaction.
        current.tid = current.tid.parent;
      }
    }
  }

  /**
   * Commits the transaction if possible; otherwise, aborts the transaction.
   */
  public void commitTransaction() throws AbortException,
      TransactionAtomicityViolationException {
    Timing.COMMIT.begin();
    try {
      commitTransactionAt(System.currentTimeMillis());
    } finally {
      Timing.COMMIT.end();
    }
  }

  public void commitTransactionAt(long commitTime) {
    logger.finest(current + " attempting to commit");
    // Assume only one thread will be executing this.

    // XXX This is a long and ugly method. Refactor?

    // Wait for all sub-transactions to finish.
    current.waitForThreads();

    // Make sure we're not supposed to abort.
    try {
      checkAbortSignal();
    } catch (AbortException e) {
      abortTransaction();
      throw e;
    }

    logger.finest(current + " committing");

    Log parent = current.parent;
    if (current.tid.parent != null) {
      try {
        Timing.SUBTX.begin();
        // Update data structures to reflect the commit.
        current.commitNested();
        logger.finest(current + " committed");
        if (parent.tid.equals(current.tid.parent)) {
          // Parent frame represents parent transaction. Pop the stack.
          current = parent;
        } else {
          // Reuse the current frame for the parent transaction. Update its TID.
          current.tid = current.tid.parent;
        }
        return;
      } finally {
        Timing.SUBTX.end();
      }
    }

    // Commit top-level transaction.

    // Remove and unlock reads that have valid promises on them
    current.removePromisedReads(commitTime);

    // Go through the transaction log and figure out the cores we need to
    // contact.
    Set<Core> cores = current.coresToContact();
    List<RemoteClient> clients = current.clientsCalled;

    // Send prepare messages to our cohorts.
    Map<RemoteNode, TransactionPrepareFailedException> failures =
        sendPrepareMessages(commitTime, cores, clients);

    if (!failures.isEmpty()) {
      failures.remove(null);
      TransactionPrepareFailedException e =
          new TransactionPrepareFailedException(failures);
      logger.warning(current + " error committing: abort exception: " + e);
      abortTransaction();
      throw new AbortException(e);
    }

    // Send commit messages to our cohorts.
    sendCommitMessagesAndCleanUp(cores, clients);
  }

  /**
   * Sends prepare messages to the cohorts. Also sends abort messages if any
   * cohort fails to prepare.
   */
  public Map<RemoteNode, TransactionPrepareFailedException> sendPrepareMessages(
      long commitTime) {
    return sendPrepareMessages(commitTime, current.coresToContact(),
        current.clientsCalled);
  }

  /**
   * Sends prepare messages to the given set of cores and clients. Also sends
   * abort messages if any of them fails to prepare.
   */
  private Map<RemoteNode, TransactionPrepareFailedException> sendPrepareMessages(
      final long commitTime, Set<Core> cores, List<RemoteClient> clients) {
    final Map<RemoteNode, TransactionPrepareFailedException> failures =
        Collections
            .synchronizedMap(new HashMap<RemoteNode, TransactionPrepareFailedException>());

    synchronized (current.commitState) {
      switch (current.commitState.value) {
      case UNPREPARED:
        current.commitState.value = PREPARING;
        break;
      case PREPARING:
      case PREPARED:
        return failures;
      case COMMITTING:
      case COMMITTED:
        logger.fine("Ignoring prepare request (transaction state = "
            + current.commitState.value + ")");
        return failures;
      case PREPARE_FAILED:
      case ABORTING:
      case ABORTED:
        // XXX HACK UGLY
        failures.put(null, null);
        return failures;
      }
    }

    List<Thread> threads = new ArrayList<Thread>(cores.size() + clients.size());

    // Go through each client and send prepare messages in parallel.
    for (final RemoteClient client : clients) {
      Thread thread = new Thread("client prepare to " + client.name()) {
        @Override
        public void run() {
          try {
            client.prepareTransaction(current.tid.topTid, commitTime);
          } catch (UnreachableNodeException e) {
            failures.put(client, new TransactionPrepareFailedException(
                "Unreachable client"));
          } catch (TransactionPrepareFailedException e) {
            failures.put(client, new TransactionPrepareFailedException(e
                .getMessage()));
          }
        }
      };
      thread.start();
      threads.add(thread);
    }

    // Go through each core and send prepare messages in parallel.
    final Client client = Client.getClient();
    for (Iterator<Core> coreIt = cores.iterator(); coreIt.hasNext();) {
      final Core core = coreIt.next();
      Runnable runnable = new Runnable() {
        public void run() {
          try {
            Collection<_Impl> creates = current.getCreatesForCore(core);
            LongKeyMap<Integer> reads = current.getReadsForCore(core);
            Collection<_Impl> writes = current.getWritesForCore(core);
            boolean subTransactionCreated =
                core.prepareTransaction(current.tid.topTid, commitTime,
                    creates, reads, writes);

            if (subTransactionCreated) {
              RemoteClient coreClient = client.getClient(core.name());
              synchronized (current.clientsCalled) {
                current.clientsCalled.add(coreClient);
              }
            }
          } catch (TransactionPrepareFailedException e) {
            failures.put((RemoteNode) core, e);
          } catch (UnreachableNodeException e) {
            failures.put((RemoteNode) core,
                new TransactionPrepareFailedException("Unreachable core"));
          }
        }
      };

      // Optimization: only start in a new thread if there are more cores to
      // contact and if it's a truly remote core (i.e., not in-process).
      if (!(core instanceof InProcessCore || core.isLocalCore())
          && coreIt.hasNext()) {
        Thread thread =
            new Thread(runnable, "client prepare to " + core.name());
        threads.add(thread);
        thread.start();
      } else {
        runnable.run();
      }
    }

    // Wait for replies.
    for (Thread thread : threads) {
      while (true) {
        try {
          thread.join();
          break;
        } catch (InterruptedException e) {
        }
      }
    }

    // Check for conflicts and unreachable cores/clients.
    if (!failures.isEmpty()) {
      String logMessage =
          "Transaction tid=" + current.tid.topTid + ":  prepare failed.";

      for (Map.Entry<RemoteNode, TransactionPrepareFailedException> entry : failures
          .entrySet()) {
        if (entry.getKey() instanceof RemoteCore) {
          // Remove old objects from our cache.
          Core core = (Core) entry.getKey();
          Set<Long> versionConflicts = entry.getValue().versionConflicts;
          if (versionConflicts != null) {
            for (long onum : versionConflicts)
              core.evict(onum);
          }
        }

        logMessage +=
            "\n\t" + entry.getKey() + ": " + entry.getValue().getMessage();
      }
      logger.fine(logMessage);

      sendAbortMessages(cores, clients, failures.keySet());
    }

    synchronized (current.commitState) {
      current.commitState.value =
          failures.isEmpty() ? PREPARED : PREPARE_FAILED;
      current.commitState.notifyAll();
    }

    return failures;
  }

  /**
   * Sends commit messages to the cohorts.
   */
  public void sendCommitMessagesAndCleanUp()
      throws TransactionAtomicityViolationException {
    sendCommitMessagesAndCleanUp(current.coresToContact(),
        current.clientsCalled);
  }

  /**
   * Sends commit messages to the given set of cores and clients.
   */
  private void sendCommitMessagesAndCleanUp(Set<Core> cores,
      List<RemoteClient> clients) throws TransactionAtomicityViolationException {
    synchronized (current.commitState) {
      switch (current.commitState.value) {
      case UNPREPARED:
      case PREPARING:
        // This shouldn't happen.
        logger.fine("Ignoring commit request (transaction state = "
            + current.commitState.value + ")");
        return;
      case PREPARED:
        current.commitState.value = COMMITTING;
        break;
      case COMMITTING:
      case COMMITTED:
        return;
      case PREPARE_FAILED:
      case ABORTING:
      case ABORTED:
        throw new TransactionAtomicityViolationException();
      }
    }

    final List<RemoteNode> unreachable =
        Collections.synchronizedList(new ArrayList<RemoteNode>());
    final List<RemoteNode> failed =
        Collections.synchronizedList(new ArrayList<RemoteNode>());
    List<Thread> threads = new ArrayList<Thread>(cores.size() + clients.size());

    // Send commit messages to the clients in parallel.
    for (final RemoteClient client : clients) {
      Thread thread = new Thread("client commit to " + client) {
        @Override
        public void run() {
          try {
            client.commitTransaction(current.tid.topTid);
          } catch (UnreachableNodeException e) {
            unreachable.add(client);
          } catch (TransactionCommitFailedException e) {
            failed.add(client);
          }
        }
      };
      thread.start();
      threads.add(thread);
    }

    // Send commit messages to the cores in parallel.
    for (Iterator<Core> coreIt = cores.iterator(); coreIt.hasNext();) {
      final Core core = coreIt.next();
      Runnable runnable = new Runnable() {
        public void run() {
          try {
            core.commitTransaction(current.tid.topTid);
          } catch (TransactionCommitFailedException e) {
            failed.add((RemoteCore) core);
          } catch (UnreachableNodeException e) {
            unreachable.add((RemoteCore) core);
          }
        }
      };

      // Optimization: only start in a new thread if there are more cores to
      // contact and if it's a truly remote core (i.e., not in-process).
      if (!(core instanceof InProcessCore || core.isLocalCore())
          && coreIt.hasNext()) {
        Thread thread = new Thread(runnable, "client commit to " + core.name());
        threads.add(thread);
        thread.start();
      } else {
        runnable.run();
      }
    }

    // Wait for replies.
    for (Thread thread : threads) {
      while (true) {
        try {
          thread.join();
          break;
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    if (!(unreachable.isEmpty() && failed.isEmpty())) {
      logger.severe(current
          + " error committing: atomicity violation -- failed:" + failed
          + " unreachable:" + unreachable);
      throw new TransactionAtomicityViolationException(failed, unreachable);
    }

    // Update data structures to reflect successful commit.
    logger.finest(current + " committed at cores...updating data structures");
    current.commitTopLevel();
    logger.finest(current + " committed");

    synchronized (current.commitState) {
      current.commitState.value = COMMITTED;
    }

    TransactionRegistry.remove(current.tid.topTid);

    current = null;
  }

  /**
   * Sends abort messages to those nodes that haven't reported failures.
   * 
   * @param cores
   *          the set of cores involved in the transaction.
   * @param clients
   *          the set of clients involved in the transaction.
   * @param fails
   *          the set of nodes that have reported failure.
   */
  private void sendAbortMessages(Set<Core> cores, List<RemoteClient> clients,
      Set<RemoteNode> fails) {
    for (Core core : cores)
      if (!fails.contains(core)) core.abortTransaction(current.tid);

    for (RemoteClient client : clients)
      if (!fails.contains(client)) client.abortTransaction(current.tid);
  }

  public void registerCreate(_Impl obj) {
    Timing.TXLOG.begin();
    try {
      if (current == null)
        throw new InternalError("Cannot create objects outside a transaction");

      // Make sure we're not supposed to abort.
      checkAbortSignal();

      // Grab a write lock on the object.
      obj.$writer = current;
      obj.$writeLockHolder = current;

      // Own the object. The call to ensureOwnership is responsible for adding
      // the object to the set of created objects.
      ensureOwnership(obj);
      current.updateMap.put(obj.$getProxy(), obj.get$label());
    } finally {
      Timing.TXLOG.end();
    }
  }

  public void registerRead(_Impl obj) {
    synchronized (obj) {
      if (obj.$reader == current
          && obj.$updateMapVersion == current.updateMap.version) return;

      // Nothing to do if we're not in a transaction.
      if (current == null) return;

      Timing.TXLOG.begin();
      try {
        ensureReadLock(obj);
        ensureObjectUpToDate(obj);
      } finally {
        Timing.TXLOG.end();
      }
    }
  }

  /**
   * Ensures the current transaction has a read lock for the given object,
   * blocking if necessary. This method assumes we are synchronized on the
   * object.
   */
  private void ensureReadLock(_Impl obj) {
    if (obj.$reader == current) return;

    // Make sure we're not supposed to abort.
    checkAbortSignal();

    // Check read condition: wait until all writers are in our ancestry.
    boolean hadToWait = false;
    while (obj.$writeLockHolder != null
        && !current.isDescendantOf(obj.$writeLockHolder)) {
      try {
        logger.finest(current + " wants to read " + obj.$getCore() + "/"
            + obj.$getOnum() + " (" + obj.getClass() + "); waiting on writer "
            + obj.$writeLockHolder);
        hadToWait = true;
        obj.$numWaiting++;
        obj.wait();
      } catch (InterruptedException e) {
      }
      obj.$numWaiting--;

      // Make sure we weren't aborted while we were waiting.
      checkAbortSignal();
    }

    // Set the object's reader stamp to the current transaction.
    obj.$reader = current;

    // Reset the object's update-map version stamp.
    obj.$updateMapVersion = -1;

    current.acquireReadLock(obj);
    if (hadToWait) logger.finest(current + " got read lock");
  }

  /**
   * This should be called <i>before</i> the object is modified.
   * 
   * @return whether a new (top-level) transaction was created.
   */
  public boolean registerWrite(_Impl obj) {
    boolean needTransaction = (current == null);
    if (needTransaction) startTransaction();

    synchronized (obj) {
      if (obj.$writer == current
          && obj.$updateMapVersion == current.updateMap.version && obj.$isOwned)
        return needTransaction;

      try {
        Timing.TXLOG.begin();
        ensureWriteLock(obj);
        ensureObjectUpToDate(obj);
        ensureOwnership(obj);
      } finally {
        Timing.TXLOG.end();
      }
    }

    return needTransaction;

  }

  /**
   * Ensures the current transaction has a write lock for the given object,
   * blocking if necessary. This method assumes we are synchronized on the
   * object.
   */
  private void ensureWriteLock(_Impl obj) {
    // Nothing to do if the write stamp is us.
    if (obj.$writer == current) return;

    // Make sure we're not supposed to abort.
    checkAbortSignal();

    // Check write condition: wait until writer is in our ancestry and all
    // readers are in our ancestry.
    boolean hadToWait = false;
    while (true) {
      // Make sure writer is in our ancestry.
      if (obj.$writeLockHolder != null
          && !current.isDescendantOf(obj.$writeLockHolder)) {
        logger.finest(current + " wants to write " + obj.$getCore() + "/"
            + obj.$getOnum() + " (" + obj.getClass() + "); waiting on writer "
            + obj.$writeLockHolder);
        hadToWait = true;
      } else {
        // Abort any incompatible readers.
        ReadMapEntry readMapEntry = obj.$readMapEntry;
        if (readMapEntry != null) {
          synchronized (readMapEntry) {
            boolean allReadersInAncestry = true;
            for (Log lock : readMapEntry.readLocks) {
              if (!current.isDescendantOf(lock)) {
                logger.finest(current + " wants to write " + obj.$getCore()
                    + "/" + obj.$getOnum() + " (" + obj.getClass()
                    + "); aborting reader " + lock);
                lock.flagAbort();
                allReadersInAncestry = false;
              }
            }

            if (allReadersInAncestry) break;
          }
        }
      }

      try {
        obj.$numWaiting++;
        obj.wait();
      } catch (InterruptedException e) {
      }
      obj.$numWaiting--;

      // Make sure we weren't aborted while we were waiting.
      checkAbortSignal();
    }

    // Set the write stamp.
    obj.$writer = current;

    if (hadToWait) logger.finest(current + " got write lock");

    if (obj.$writeLockHolder == current) return;

    // Create a backup object, grab the write lock, and add the object to our
    // write set.
    obj.$history = obj.clone();
    obj.$writeLockHolder = current;

    if (obj.$getCore().isLocalCore()) {
      synchronized (current.localcoreWrites) {
        current.localcoreWrites.add(obj);
      }
    } else {
      synchronized (current.writes) {
        current.writes.add(obj);
      }
    }

    if (obj.$reader != current) {
      // Clear the read stamp -- the reader's read condition no longer holds.
      obj.$reader = Log.NO_READER;
    }
  }

  /**
   * Ensures the client has ownership of the object. This method assumes we are
   * synchronized on the object.
   */
  private void ensureOwnership(_Impl obj) {
    if (obj.$isOwned) return;

    // Check the update map to see if another client currently owns the object.
    RemoteClient owner = current.updateMap.getUpdate(obj.$getProxy());
    if (owner != null)
      owner.takeOwnership(current.tid, obj.$getCore(), obj.$getOnum());

    // We now own the object.
    obj.$isOwned = true;
    current.updateMap.put(obj.$getProxy(), Client.getClient().getLocalClient());

    // If the object is fresh, add it to our set of creates.
    if (obj.$version == 0) {
      if (obj.$getCore().isLocalCore()) {
        synchronized (current.localcoreCreates) {
          current.localcoreCreates.add(obj);
        }
      } else {
        synchronized (current.creates) {
          current.creates.add(obj);
        }
      }
    }
  }

  /**
   * Checks the update map and fetches from the object's owner as necessary.
   * This method assumes we are synchronized on the object.
   */
  private void ensureObjectUpToDate(_Impl obj) {
    // Check the object's update-map version stamp.
    if (obj.$updateMapVersion == current.updateMap.version) return;

    // Set the update-map version stamp on the object.
    obj.$updateMapVersion = current.updateMap.version;

    // Check the update map.
    RemoteClient owner = current.updateMap.getUpdate(obj.$getProxy());
    if (owner == null || owner == Client.getClient().getLocalClient()) return;

    // Need to fetch from the owner.
    ensureWriteLock(obj);
    owner.readObject(current.tid, obj);
  }

  /**
   * Starts a new transaction. The sub-transaction runs in the same thread as
   * the caller.
   */
  public void startTransaction() throws AbortException {
    startTransaction(null);
  }

  /**
   * Starts a new transaction with the given tid. The given tid is assumed to be
   * a valid descendant of the current tid. If the given tid is null, a random
   * tid is generated for the sub-transaction.
   */
  public void startTransaction(TransactionID tid) {
    if (current != null) checkAbortSignal();

    try {
      Timing.BEGIN.begin();
      current = new Log(current, tid);
      logger.finest(current.parent + " started subtx " + current
          + " in thread " + Thread.currentThread());
    } finally {
      Timing.BEGIN.end();
    }
  }

  /**
   * Starts the given thread, registering it as necessary.
   */
  public static void startThread(Thread thread) {
    if (!(thread instanceof FabricThread))
      getInstance().registerThread(thread);

    thread.start();
  }

  /**
   * Registers the given thread with the current transaction. This should be
   * called before the thread is started.
   */
  public void registerThread(Thread thread) {
    Timing.TXLOG.begin();
    try {
      // XXX Eventually, we will want to support threads in transactions.
      if (current != null)
        throw new InternalError("Cannot create threads within transactions");

      TransactionManager tm = new TransactionManager();

      if (thread instanceof FabricThread) {
        ((FabricThread) thread).setTransactionManager(tm);
      } else {
        synchronized (instanceMap) {
          instanceMap.put(thread, tm);
        }
      }
    } finally {
      Timing.TXLOG.end();
    }
  }

  /**
   * Registers that the given thread has finished.
   */
  public void deregisterThread(Thread thread) {
    if (!(thread instanceof FabricThread)) {
      synchronized (instanceMap) {
        instanceMap.remove(thread);
      }
    }
  }

  /**
   * Registers a remote call to the given client.
   */
  public void registerRemoteCall(RemoteClient client) {
    if (current != null) {
      if (!current.clientsCalled.contains(client))
        current.clientsCalled.add(client);
    }
  }

  /**
   * Associates the given transaction log with this transaction manager.
   */
  public void associateLog(Log log) {
    current = log;
  }

  public Log getCurrentLog() {
    return current;
  }

  public TransactionID getCurrentTid() {
    if (current == null) return null;
    return current.tid;
  }

  public UpdateMap getUpdateMap() {
    if (current == null) return null;
    return current.updateMap;
  }

  /**
   * @return the client on which the object resides. An object resides on a
   *         client if it is either on that client's local core, or if it was
   *         created by the current transaction and is owned by that client.
   */
  public RemoteClient getFetchClient(_Proxy proxy) {
    if (current == null || !current.updateMap.containsCreate(proxy))
      return null;
    Label label = current.updateMap.getCreate(proxy);

    return current.updateMap.getUpdate(proxy, label);
  }

  /**
   * Associates the given log with this worker's transaction manager and
   * synchronizes the log with the given tid.
   */
  public void associateAndSyncLog(Log log, TransactionID tid) {
    associateLog(log);

    if (log == null) {
      if (tid != null) startTransaction(tid);
      return;
    }

    // Do the commits that we've missed.
    TransactionID commonAncestor = log.getTid().getLowestCommonAncestor(tid);
    for (int i = log.getTid().depth; i > commonAncestor.depth; i--)
      commitTransaction();

    // Start new transactions if necessary.
    if (commonAncestor.depth != tid.depth) startTransaction(tid);
  }

}
