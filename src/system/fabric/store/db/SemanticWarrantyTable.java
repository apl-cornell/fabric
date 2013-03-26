package fabric.store.db;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.VersionWarranty;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.lang.Object._Proxy;
import fabric.lang.WrappedJavaInlineable;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.memoize.CallChangedException;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.CallUnchangedException;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.worker.transaction.TransactionManager;

/**
 * A table containing semantic warranties, keyed by CallInstance id, and
 * supporting concurrent accesses.
 */
public class SemanticWarrantyTable {
  private static final int MIN_SEMANTIC_WARRANTY = 250;
  private static final int MAX_SEMANTIC_WARRANTY = 10000;

  /**
   * Table of objects to lock on for interacting with a certain call.
   */
  private final ConcurrentMap<CallInstance, Object> lockTable;

  /**
   * Table for looking up warranties of calls.
   */
  private final WarrantyTable<CallInstance, SemanticWarranty> warrantyTable;

  /**
   * Table for looking up call values.
   */
  private final ConcurrentMap<CallInstance, fabric.lang.Object> valueTable;
  
  /**
   * Table for looking up dependencies of calls on various reads and calls
   */
  private final SemanticWarrantyDependencies dependencyTable;

  /**
   * WarrantyIssuer for semantic warranties.
   */
  private final WarrantyIssuer<CallInstance> issuer;

  /**
   * Table mapping from transactionIDs for pending transactions to
   * updates/additons for semantic warranty dependencies.
   */
  private final ConcurrentLongKeyMap<Map<CallInstance, Pair<SemanticWarrantyRequest, SemanticWarranty>>> pendingMap;

  public SemanticWarrantyTable(ObjectDB database) {
    lockTable = new ConcurrentHashMap<CallInstance, Object>();
    warrantyTable = new WarrantyTable<CallInstance, SemanticWarranty>(new
        SemanticWarranty(0));
    valueTable = new ConcurrentHashMap<CallInstance, fabric.lang.Object>();
    dependencyTable = new SemanticWarrantyDependencies();
    issuer = new WarrantyIssuer<CallInstance>(MIN_SEMANTIC_WARRANTY,
        MAX_SEMANTIC_WARRANTY);
    pendingMap =
        new ConcurrentLongKeyHashMap<Map<CallInstance, Pair<SemanticWarrantyRequest, SemanticWarranty>>>();
  }

  public final WarrantiedCallResult get(CallInstance id) {
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      if (valueTable.get(id) == null) return null;
      // Don't necessarily issue a new warranty until the transaction attempts
      // committing.
      return new WarrantiedCallResult(valueTable.get(id),
          warrantyTable.get(id));
    }
  }

  /* Create a warranty with a suggested time for the given call with the
   * associated reads and calls.
   */
  public SemanticWarranty proposeWarranty(CallInstance id) {
    return new SemanticWarranty(issuer.suggestWarranty(id));
  }

  /**
   * Adds a proposal to be inserted at commitTime.
   */
  public void addPendingWarranty(long commitTime, SemanticWarrantyRequest req,
      SemanticWarranty war, long transactionID) {
    pendingMap.putIfAbsent(transactionID, new HashMap<CallInstance,
        Pair<SemanticWarrantyRequest, SemanticWarranty>>());
    pendingMap.get(transactionID).put(req.call, new
        Pair<SemanticWarrantyRequest, SemanticWarranty>(req, war));
  }

  /**
   * Wrapper of the "more arguments" version of putAt.
   */
  public void putAt(long commitTime, SemanticWarrantyRequest req,
      SemanticWarranty warranty) {
    putAt(commitTime, req.call, req.readOnums, req.createOnums, req.calls,
        req.value, warranty);
  }

  /**
   * Schedule to perform a put with all the arguments at the given time.
   */
  public void putAt(long commitTime, final CallInstance id,
      final LongSet reads, final LongSet creates,
      final Set<CallInstance> calls, final fabric.lang.Object value,
      final SemanticWarranty warranty) {
    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        put(id, reads, creates, calls, value, warranty);
      }
    });
  }

  /**
   * Add a new call, with the result and all the reads, calls, and creates, into
   * the table with the provided warranty.
   */
  public final void put(CallInstance id, LongSet reads, LongSet creates,
      Set<CallInstance> calls, fabric.lang.Object value,
      SemanticWarranty warranty) {
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      // Add warranty to warranty table
      warrantyTable.put(id, warranty);

      // Add result to value table
      valueTable.put(id, value);

      // Add the warranty dependencies to the dependencyTable
      LongSet deps = new LongHashSet(reads);
      deps.addAll(creates);
      if (!(value instanceof WrappedJavaInlineable)) deps.add(value.$getOnum());
      dependencyTable.addCall(id, deps, calls);
    }
  }

  public static enum SemanticExtendStatus {
    OK, BAD_VERSION, DENIED
  }

  /**
   * Extends the warranty for an id only if it currently has a specific
   * warranty.
   * 
   * @return true iff the warranty was replaced.
   */
  public final Pair<SemanticExtendStatus, WarrantiedCallResult> extend(CallInstance
      id, WarrantiedCallResult oldValue, long newTime) {
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      if (oldValue.value.equals(valueTable.get(id))) {
        SemanticWarranty newWarranty = new SemanticWarranty(newTime);
        if (!oldValue.warranty.expired(true)) {
          if (warrantyTable.extend(id, oldValue.warranty, newWarranty)) {
            return new Pair<SemanticExtendStatus,
                   WarrantiedCallResult>(SemanticExtendStatus.OK, new
                       WarrantiedCallResult(oldValue.value,
                         warrantyTable.get(id)));
          } else {
            return new Pair<SemanticExtendStatus,
                   WarrantiedCallResult>(SemanticExtendStatus.DENIED, null);
          }
        } else {
          warrantyTable.put(id, newWarranty);
          return new Pair<SemanticExtendStatus,
                 WarrantiedCallResult>(SemanticExtendStatus.OK, new
                     WarrantiedCallResult(oldValue.value,
                       warrantyTable.get(id)));
        }
      }
      if (valueTable.get(id) == null) {
        return new Pair<SemanticExtendStatus,
               WarrantiedCallResult>(SemanticExtendStatus.BAD_VERSION, null);
      } else {
        return new Pair<SemanticExtendStatus,
               WarrantiedCallResult>(SemanticExtendStatus.BAD_VERSION, new
                   WarrantiedCallResult(valueTable.get(id),
                     warrantyTable.get(id)));
      }
    }
  }

  /**
   * Remove the call's associated value from the table.
   */
  public final void remove(CallInstance call) {
    lockTable.putIfAbsent(call, new Object());
    synchronized (lockTable.get(call)) {
      // We let the warranty die a natural death
      // Remove the associated value
      valueTable.remove(call);
      // Remove the dependencies
      dependencyTable.removeCall(call);
    }
  }

  /**
   * Notifies the issuer of a read prepare extending the Semantic Warranty for
   * the given call.
   */
  public void notifyReadPrepare(CallInstance call, long commitTime) {
    SEMANTIC_WARRANTY_LOGGER.finest("Notifying read prepare on " + call);
    issuer.notifyReadPrepare(call, commitTime);
  }

  /**
   * Provides the longest SemanticWarranty that depended on any of the given
   * onums that is longer than the given commitTime.  Also performs any
   * bookkeeping associated with write events (like removing stale call values).
   */
  public SemanticWarranty prepareWrites(
      final Collection<SerializedObject> writes, long transactionID,
      long commitTime, final String storeName) {
    int initCapacity = writes.size() >= 1 ? writes.size() : 1;
    PriorityQueue<Pair<CallInstance, SemanticWarranty>> dependencies =
        new PriorityQueue<Pair<CallInstance, SemanticWarranty>>(initCapacity,
            new Comparator<Pair<CallInstance, SemanticWarranty>>() {
              @Override
              public int compare(Pair<CallInstance, SemanticWarranty> p1,
                  Pair<CallInstance, SemanticWarranty> p2) {
                if (p1.second.expiry() == p2.second.expiry()) return 0;
                return p1.second.expiry() > p2.second.expiry() ? -1 : 1;
              }
            });

    for (SerializedObject obj : writes) {
      for (CallInstance call : new HashSet<CallInstance>(dependencyTable.getReaders(obj.getOnum()))) {
        SemanticWarranty dependentWarranty = get(call).warranty;
        if (dependentWarranty.expiresAfter(commitTime, true)) {
          dependencies.add(new Pair<CallInstance, SemanticWarranty>(call,
                dependentWarranty));
        } else if (dependentWarranty.expired(true)) {
          // ZZZZAP, value is (probably) out of date.
          // XXX notify write prepare here?
          remove(call);
        }
      }
    }

    while (!dependencies.isEmpty()) {
      Pair<CallInstance, SemanticWarranty> p = dependencies.poll();
      long currentTime = System.currentTimeMillis();
      if (p.second.expiresBefore(currentTime, false)) break;

      final CallInstance call = p.first;
      final fabric.lang.Object oldResult = get(call).value;
      Future<SemanticWarrantyRequest> checkHandler =
          Executors.newSingleThreadExecutor().submit(
              (new Callable<SemanticWarrantyRequest>() {
                @Override
                public SemanticWarrantyRequest call() {
                  try {
                    Worker.runInTopLevelTransaction(new Code<Void>() {
                      @Override
                      public Void run() {
                        SEMANTIC_WARRANTY_LOGGER
                            .finest("CHECKING CALL " + call);
                        Store localStore =
                            Worker.getWorker().getStore(storeName);
                        TransactionManager tm =
                            TransactionManager.getInstance();
                        for (SerializedObject obj : writes) {
                          (new _Proxy(localStore, obj.getOnum())).fetch()
                              .$copyAppStateFrom(
                                  obj.deserialize(localStore,
                                      new VersionWarranty(0)));
                        }
                        Object newResult = call.runCall();
                        if (!oldResult.equals(newResult)) {
                          SEMANTIC_WARRANTY_LOGGER
                              .finest("DONE RECOMPUTING CALL " + call
                                  + " which changed from " + oldResult + " to "
                                  + newResult + "!");
                          throw new CallChangedException();
                        }
                        SEMANTIC_WARRANTY_LOGGER
                            .finest("DONE RECOMPUTING CALL " + call
                                + " which didn't change!");
                        // TODO: What if there's a write in the new evaluation and so
                        // there's no request?
                        //
                        // I guess we cry.
                        throw new CallUnchangedException(tm.getCurrentLog()
                            .getRequest(call));
                      }
                    }, false);
                  } catch (CallChangedException e) {
                  } catch (CallUnchangedException e) {
                    return e.updatedReq;
                  }
                  return null;
                }
              }));
      try {
        SemanticWarrantyRequest updatedReq =
            checkHandler.get(p.second.expiry() - currentTime,
                TimeUnit.MILLISECONDS);
        if (updatedReq == null) {
          SEMANTIC_WARRANTY_LOGGER.finest("Call " + call + " affected by "
              + transactionID);
          issuer.notifyWritePrepare(call);
          return p.second;
        }

        pendingMap
            .putIfAbsent(
                transactionID,
                new HashMap<CallInstance, Pair<SemanticWarrantyRequest, SemanticWarranty>>());
        pendingMap.get(transactionID).put(
            call,
            new Pair<SemanticWarrantyRequest, SemanticWarranty>(updatedReq,
                p.second));
        SEMANTIC_WARRANTY_LOGGER.finest("Call " + call + " unaffected by "
            + transactionID);
      } catch (ExecutionException e) {
        SEMANTIC_WARRANTY_LOGGER.finest("Call " + call
            + " had an error in check");
        SEMANTIC_WARRANTY_LOGGER.finest("\t" + e.getMessage());
        issuer.notifyWritePrepare(call);
        return p.second;
      } catch (InterruptedException e) {
        SEMANTIC_WARRANTY_LOGGER.finest("Call " + call
            + " had interrupted in check");
        issuer.notifyWritePrepare(call);
        return p.second;
      } catch (TimeoutException e) {
        SEMANTIC_WARRANTY_LOGGER.finest("Call " + call
            + " timed out in check");
        issuer.notifyWritePrepare(call);
        return p.second;
      }
    }
    return null;
  }

  /**
   * Remove any state associated with the given transactionID due to a
   * transaction abort.
   */
  public void abort(long transactionID) {
    SEMANTIC_WARRANTY_LOGGER.finest(String.format(
        "Aborting semantic warranty updates from %x", transactionID));
    pendingMap.remove(transactionID);
  }

  /**
   * Commit any state associated with the given transactionID at the given
   * commitTime.
   */
  public void commit(long transactionID, long commitTime) {
    SEMANTIC_WARRANTY_LOGGER.finest(String.format(
        "Committing semantic warranty updates from %x", transactionID));
    if (pendingMap.get(transactionID) != null) {
      Map<CallInstance, Pair<SemanticWarrantyRequest, SemanticWarranty>> m =
          pendingMap.get(transactionID);
      for (CallInstance call : m.keySet()) {
        SEMANTIC_WARRANTY_LOGGER.finest("Adding " + call + ": "
            + m.get(call).first + ", " + m.get(call).second);
        putAt(commitTime, m.get(call).first, m.get(call).second);
      }
    }
    pendingMap.remove(transactionID);
  }
}
