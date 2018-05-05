package fabric.store;

import static fabric.common.Logging.STORE_TRANSACTION_LOGGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.logging.Level;

import com.sleepycat.je.LockConflictException;

import fabric.common.AuthorizationUtil;
import fabric.common.Logging;
import fabric.common.ONumConstants;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.exceptions.AccessException;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.common.util.Triple;
import fabric.dissemination.ObjectGlob;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.metrics.contracts.Contract;
import fabric.store.db.GroupContainer;
import fabric.store.db.ObjectDB;
import fabric.worker.AbortException;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.metrics.ExpiryExtension;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.ImmutableSet;
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

  public TransactionManager(ObjectDB database) {
    this.database = database;
    this.sm = new SubscriptionManager(database.getName(), this);
    Threading.getPool().submit(extensionsRunner);
  }

  /**
   * Instruct the transaction manager that the given transaction is aborting
   */
  public void abortTransaction(Principal worker, long transactionID)
      throws AccessException {
    database.rollback(transactionID, worker);
    STORE_TRANSACTION_LOGGER.log(Level.FINE, "Aborted transaction {0}",
        transactionID);
  }

  /**
   * Execute the commit phase of two phase commit.
   */
  public void commitTransaction(RemoteIdentity<RemoteWorker> workerIdentity,
      long transactionID) throws TransactionCommitFailedException {
    try {
      database.commit(transactionID, workerIdentity, sm);
      STORE_TRANSACTION_LOGGER.log(Level.FINE, "Committed transaction {0}",
          transactionID);
    } catch (final AccessException e) {
      throw new TransactionCommitFailedException("Insufficient Authorization");
    } catch (final RuntimeException e) {
      throw new TransactionCommitFailedException(
          "something went wrong; store experienced a runtime exception during "
              + "commit: " + e.getMessage(),
          e);
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
   * @return a collection of onums and expiries for contracts that were prepared
   *         for extension or reading that were already longer lasting on the
   *         store.
   * @throws TransactionPrepareFailedException
   *           If the transaction would cause a conflict or if the worker is
   *           insufficiently privileged to execute the transaction.
   */
  public LongKeyMap<Long> prepare(Principal worker, PrepareRequest req)
      throws TransactionPrepareFailedException {
    return req.runPrepare(this, database, worker);
  }

  /**
   * Checks that the worker principal has permissions to read/write the given
   * objects. If it doesn't, an AccessException is thrown.
   */
  protected void checkPerms(final Principal worker, final LongSet reads,
      final Collection<SerializedObject> writes,
      final Collection<ExpiryExtension> extensions) throws AccessException {
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

        for (ExpiryExtension e : extensions) {
          long onum = e.onum;

          fabric.lang.Object storeCopy =
              new fabric.lang.Object._Proxy(store, onum);

          Label label = storeCopy.get$$updateLabel();

          // Check write permissions.
          if (!AuthorizationUtil.isReadAndWritePermitted(worker,
              label.$getStore(), label.$getOnum())) {
            return new AccessException("expiry extension", worker, storeCopy);
          }
        }

        for (SerializedObject o : writes) {
          long onum = o.getOnum();

          fabric.lang.Object storeCopy =
              new fabric.lang.Object._Proxy(store, onum);

          Label label = storeCopy.get$$updateLabel();

          // Check write permissions.
          if (!AuthorizationUtil.isReadAndWritePermitted(worker,
              label.$getStore(), label.$getOnum())) {
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
   * Returns a GroupContainer containing the specified object.
   */
  GroupContainer getGroupContainer(long onum) throws AccessException {
    return getGroupContainerAndSubscribe(onum, null,
        false /* this argument doesn't matter */);
  }

  /**
   * Returns the set of onums that observe the given onum that are on this
   * store.
   */
  LongSet getAssociatedOnums(long onum) throws AccessException {
    LongSet result = new LongHashSet();
    result.add(onum);
    getAssociatedOnums(onum, result);
    result.remove(onum);
    return result;
  }

  /**
   * Returns the set of onums that observe the given onum that are on this
   * store.  Does not include graph reached past the given set.
   */
  LongSet getAssociatedOnumsExcluded(long onum, LongSet excluded)
      throws AccessException {
    LongSet result = new LongHashSet();
    result.add(onum);
    result.addAll(excluded);
    getAssociatedOnums(onum, result);
    result.removeAll(excluded);
    result.remove(onum);
    return result;
  }

  private void getAssociatedOnums(long onum, LongSet explored)
      throws AccessException {
    SerializedObject obj = database.read(onum);
    if (obj == null) throw new AccessException(database.getName(), onum);

    Store store = Worker.getWorker().getStore(database.getName());
    ImmutableObserverSet set = obj.getObservers();
    if (set != null) {
      LongSet subSet = set.onumsForStore(store);
      if (subSet != null) {
        for (LongIterator iter = subSet.iterator(); iter.hasNext();) {
          long associate = iter.next();
          if (explored.contains(associate)) continue;
          explored.add(associate);
          getAssociatedOnums(associate, explored);
        }
      }
    }
    ImmutableSet set2 = obj.getAssociated();
    if (set2 != null) {
      LongSet subSet = set2.onumsForStore(store);
      if (subSet != null) {
        for (LongIterator iter = subSet.iterator(); iter.hasNext();) {
          long associate = iter.next();
          if (explored.contains(associate)) continue;
          explored.add(associate);
          getAssociatedOnums(associate, explored);
        }
      }
    }
  }

  /**
   * Unsubscribe the given worker from updates for the given
   *
   * @param client
   *          the {@link RemoteWorker} to be unsubscribed
   * @param unsubscribes
   *          the set of onums to be unsubscribed from.
   */
  void unsubscribe(RemoteWorker client, LongSet unsubscribes) {
    sm.unsubscribe(client, unsubscribes);
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
  GroupContainer getGroupContainerAndSubscribe(long onum,
      RemoteWorker subscriber, boolean dissemSubscribe) throws AccessException {
    GroupContainer result = database.readGroup(onum);
    if (result == null) throw new AccessException(database.getName(), onum);

    if (subscriber != null) sm.subscribe(onum, subscriber, dissemSubscribe);
    return result;
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
  public ObjectGlob getGlob(long onum, RemoteWorker subscriber)
      throws AccessException {
    return getGroupContainerAndSubscribe(onum, subscriber, true).getGlob();
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
  public ObjectGroup getGroup(Principal principal, RemoteWorker subscriber,
      long onum) throws AccessException {
    ObjectGroup group = getGroupContainerAndSubscribe(onum, subscriber, false)
        .getGroup(principal);
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
  List<SerializedObject> checkForStaleObjects(Principal worker,
      LongKeyMap<Pair<Integer, Long>> versionsAndExpiries)
      throws AccessException {
    // First, check read and write permissions.
    Store store = Worker.getWorker().getStore(database.getName());
    if (worker == null || worker.$getStore() != store
        || worker.$getOnum() != ONumConstants.STORE_PRINCIPAL) {
      checkPerms(worker, versionsAndExpiries.keySet(),
          Collections.<SerializedObject> emptyList(),
          Collections.<ExpiryExtension> emptyList());
    }

    List<SerializedObject> result = new ArrayList<>();
    for (LongKeyMap.Entry<Pair<Integer, Long>> entry : versionsAndExpiries
        .entrySet()) {
      long onum = entry.getKey();
      int version = entry.getValue().first;
      long expiry = entry.getValue().second;

      int curVersion = database.getVersion(onum);
      long curExpiry = database.getExpiry(onum);
      if (curVersion != version || curExpiry > expiry) {
        result.add(database.read(onum));
      }
    }

    return result;
  }

  /**
   * Checks if there are any existing extensions for the associated onum of each
   * {@link DelayedExtension}. If there isn't one, or if the existing one is for
   * a later time than the new request, the new {@link DelayedExtension} is
   * added to the queue, and the onum-request mapping is updated.
   */
  public void queueExtensions(LongSet extensions) {
    for (LongIterator it = extensions.iterator(); it.hasNext();) {
      long onum = it.next();
      queueExtension(onum);
    }
  }

  public void queueExtension(long onum) {
    long expiry = 0;
    try {
      expiry = database.getExpiry(onum);
    } catch (AccessException e) {
      // This shouldn't happen.
      throw new InternalError("Tried to extend a nonexistent onum!");
    }
    DelayedExtension de = new DelayedExtension(expiry - EXTENSION_WINDOW, onum);
    synchronized (de) {
      // Keep trying until we're sure we've consistently updated the extension
      // for this onum.
      while (true) {
        DelayedExtension existing =
            unresolvedExtensions.putIfAbsent(de.onum, de);
        if (existing == null) {
          waitingExtensions.add(de);
          break;
        } else {
          synchronized (existing) {
            // Don't do anything if there's an equivalent extension queued
            // already
            if (existing.compareTo(de) == 0) {
              break;
            }
            // Update to this event.  This would mean the old extension was
            // using an outdated expiration time.
            if (unresolvedExtensions.replace(de.onum, existing, de)) {
              waitingExtensions.remove(existing);
              waitingExtensions.add(de);
              break;
            }
          }
        }
      }
    }
  }

  /**
   * The currently queued or running extensions for each onum, so we may easily
   * replace the request with a new (to be handled earlier) request, if
   * necessary.
   */
  private final ConcurrentLongKeyMap<DelayedExtension> unresolvedExtensions =
      new ConcurrentLongKeyHashMap<>();

  /**
   * The extensions waiting to run.
   */
  private final DelayQueue<DelayedExtension> waitingExtensions =
      new DelayQueue<>();

  private final int EXTENSION_WINDOW = 1500;

  /**
   * A thread that goes through the extensions queue, waiting until the next
   * DelayedExtension's delay is 0, and updates the expiration time of the
   * DelayedExtension's associated onum.  It continually waits until the earliest
   * {@code DelayedExtension}'s time, handles the extension in a transaction,
   * then dequeues the extension.
   */
  private final Threading.NamedRunnable extensionsRunner =
      new Threading.NamedRunnable("Extensions runner") {
        @Override
        protected void runImpl() {
          try {
            while (true) {
              // Get the next delayed extension item.
              // XXX: In an ideal world, the extension item isn't taken off the
              // queue until we've synchronized on it.  However, this doesn't
              // hurt correctness although its a little inefficient.
              //
              // I'm not too worried about this because the stars would have to
              // align so that:
              //   - The second request comes in between those lines
              //   - The second request is marked to be handled before the
              //   current request
              // At worst, this causes two requests to be handled when on would
              // have been sufficient and that second request is unlikely to be
              // very expensive to process.
              final DelayedExtension extension = waitingExtensions.take();
              long curTime = System.currentTimeMillis();
              long exp = extension.time + EXTENSION_WINDOW;
              try {
                // Change to the actual expiry
                exp = database.getExpiry(extension.onum);
              } catch (AccessException ae) {
                // If this happens, it suggests we're trying to extend a
                // nonexistent value.
                System.err.println("Bad onum for extension! " + extension.onum);
                ae.printStackTrace();
                synchronized (extension) {
                  unresolvedExtensions.remove(extension.onum, extension);
                }
                continue;
              }
              if (exp - curTime <= EXTENSION_WINDOW
                  && exp >= curTime - EXTENSION_WINDOW) {
                Threading.getPool().submit(new Threading.NamedRunnable(
                    "Extension of " + extension.onum) {
                  @Override
                  protected void runImpl() {
                    // Don't want new extensions to walk away after this is
                    // done before we remove the mapping.
                    // Run a transaction handling updates
                    boolean success = true;
                    long start = System.currentTimeMillis();
                    Logging.METRICS_LOGGER.log(Level.INFO,
                        "STARTED EXTENSION OF {0}", extension.onum);
                    Triple<String, Long, Long> nameAndNewExpiry = null;
                    Logging.METRICS_LOGGER.log(Level.FINER,
                        "SYNCHRONIZED EXTENSION OF {0}", extension.onum);
                    try {
                      fabric.worker.transaction.TransactionManager
                          .getInstance().stats.reset();
                      nameAndNewExpiry = Worker.runInTopLevelTransaction(
                          new Code<Triple<String, Long, Long>>() {
                            @Override
                            public Triple<String, Long, Long> run() {
                              Store store = Worker.getWorker()
                                  .getStore(database.getName());
                              final Contract._Proxy target =
                                  new Contract._Proxy(store, extension.onum);
                              long oldExpiry = target.get$$expiry();
                              target.attemptExtension();
                              return new Triple<>(target.toString(), oldExpiry,
                                  target.get$$expiry());
                            }
                          }, true);
                    } catch (AbortException e) {
                      success = false;
                      // Clear out any leftover locking state
                      fabric.worker.transaction.TransactionManager.getInstance()
                          .clearLockObjectState();
                    } catch (LockConflictException e) {
                      success = false;
                      // Clear out any leftover locking state
                      fabric.worker.transaction.TransactionManager.getInstance()
                          .clearLockObjectState();
                    }
                    synchronized (extension) {
                      unresolvedExtensions.remove(extension.onum, extension);
                    }
                    if (nameAndNewExpiry != null) {
                      Logging.METRICS_LOGGER.log(Level.INFO,
                          "FINISHED EXTENSION OF {0} IN {1}ms from {4} to {5} {6} (success {2}) STATS: {3}",
                          new Object[] { Long.valueOf(extension.onum),
                              Long.valueOf(System.currentTimeMillis() - start),
                              success,
                              fabric.worker.transaction.TransactionManager
                                  .getInstance().stats,
                              nameAndNewExpiry.second, nameAndNewExpiry.third,
                              nameAndNewExpiry.first });
                    } else {
                      Logging.METRICS_LOGGER.log(Level.INFO,
                          "FINISHED EXTENSION OF {0} IN {1}ms (success {2}) STATS: {3}",
                          new Object[] { Long.valueOf(extension.onum),
                              Long.valueOf(System.currentTimeMillis() - start),
                              success,
                              fabric.worker.transaction.TransactionManager
                                  .getInstance().stats, });
                    }
                  }
                });
              } else {
                synchronized (extension) {
                  unresolvedExtensions.remove(extension.onum, extension);
                }
                // If too early, requeue it.
                if (exp > curTime)
                  queueExtension(extension.onum);
                else Logging.METRICS_LOGGER.log(Level.INFO,
                    "SKIPPED EXTENSION OF {0}", Long.valueOf(extension.onum));
              }
            }
          } catch (InterruptedException e) {
            System.err.println("Extension thread interrupted!");
            e.printStackTrace();
            Logging.logIgnoredInterruptedException(e);
          }
        }
      };

  /**
   * Wait for an update to occur to push the version for the given onum past the
   * given version.
   */
  public List<SerializedObject> waitForUpdates(
      LongKeyMap<Integer> onumsAndVersions) throws AccessException {
    List<SerializedObject> updates = new ArrayList<>(onumsAndVersions.size());
    for (LongKeyMap.Entry<Integer> e : onumsAndVersions.entrySet()) {
      updates.add(database.waitForUpdate(e.getKey(), e.getValue()));
    }
    return updates;
  }
}
