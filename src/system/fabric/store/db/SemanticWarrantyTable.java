package fabric.store.db;

import java.util.concurrent.Future;

import static fabric.common.Logging.STORE_DB_LOGGER;
import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.VersionWarranty;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;

import fabric.lang.Object._Proxy;

import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.memoize.CallChangedException;
import fabric.worker.memoize.CallUnchangedException;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.worker.transaction.TransactionManager;

/*
 * TODO:
 *      - Get a more sane approach to assigning warranty lengths to new call
 *      entries.
 */
/**
 * A table containing semantic warranties, keyed by CallInstance id, and
 * supporting concurrent accesses.
 */
public class SemanticWarrantyTable {
  private static final int MAX_SEMANTIC_WARRANTY = 10000;

  /**
   * The default warranty for ids that aren't yet in the table. All warranties
   * in the table should expire after the default warranty.
   */
  private volatile SemanticWarranty defaultWarranty;

  private final Map<CallInstance, WarrantiedCallResult> table;

  private final Collector collector;

  /**
   * Reverse mapping: maps semantic warranties to corresponding ids.
   *
   * Primarily used for sweeping away expired entries.
   */
  private final ConcurrentMap<SemanticWarranty, Set<CallInstance>> reverseTable;

  /**
   * Table for looking up dependencies of calls on various reads and calls
   */
  private final SemanticWarrantyDependencies dependencyTable;

  /**
   * WarrantyIssuer for semantic warranties.
   */
  private final WarrantyIssuer issuer;

  /**
   * Table mapping from transactionIDs for pending transactions to
   * updates/additons for semantic warranty dependencies.
   */
  private final ConcurrentLongKeyMap<Map<CallInstance,
          Pair<SemanticWarrantyRequest, SemanticWarranty>>> pendingMap;

  public SemanticWarrantyTable(ObjectDB database) {
    defaultWarranty = new SemanticWarranty(0);
    table = new ConcurrentHashMap<CallInstance, WarrantiedCallResult>();
    reverseTable = new ConcurrentHashMap<SemanticWarranty, Set<CallInstance>>();
    dependencyTable = new SemanticWarrantyDependencies();
    issuer = new WarrantyIssuer(250, MAX_SEMANTIC_WARRANTY);
    pendingMap = new ConcurrentLongKeyHashMap<Map<CallInstance,
               Pair<SemanticWarrantyRequest, SemanticWarranty>>>();

    collector = new Collector();
    collector.start();
  }

  public final WarrantiedCallResult get(CallInstance id) {
    return table.get(id);
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
    putAt(commitTime, req.call, req.reads, req.calls, req.value, warranty);
  }

  /**
   * Schedule to perform a put with all the arguments at the given time.
   */
  public void putAt(long commitTime, final CallInstance id, final LongSet reads,
      final Set<CallInstance> calls, final fabric.lang.Object value,
      final SemanticWarranty warranty) {
    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        put(id, reads, calls, value, warranty);
      }
    });
  }

  /**
   * Add a new call, with the result and all the reads, calls, and creates, into
   * the table with the provided warranty.
   */
  public final void put(CallInstance id, LongSet reads, Set<CallInstance> calls,
      fabric.lang.Object value, SemanticWarranty warranty) {
    WarrantiedCallResult result = new WarrantiedCallResult(value, warranty);
    if (defaultWarranty.expiresAfter(warranty)) {
      throw new InternalError("Attempted to insert a warranty that expires "
          + "before the default warranty. This should not happen.");
    }

    long expiry = warranty.expiry();
    long length = expiry - System.currentTimeMillis();
    STORE_DB_LOGGER.finest("Adding warranty for call " + id + "=" + result
        + "; expiry=" + expiry + " (in " + length + " ms)");

    table.put(id, result);

    Set<CallInstance> set = new HashSet<CallInstance>();
    Set<CallInstance> existingSet = reverseTable.putIfAbsent(warranty, set);
    if (existingSet != null) set = existingSet;
    synchronized (set) {
      set.add(id);

      // Make sure the reverse table has an entry for the warranty, in case it
      // was removed by the Collector thread.
      reverseTable.put(warranty, set);
    }

    // Add the warranty dependencies to the dependencyTable
    dependencyTable.addCall(id, reads, calls);

    // Signal the collector thread that we have a new warranty.
    collector.signalNewWarranty();
  }

  /**
   * Extends the warranty for an id only if it currently has a specific
   * warranty.
   * 
   * @return true iff the warranty was replaced.
   */
  public final boolean extend(CallInstance id, SemanticWarranty oldWarranty,
      SemanticWarranty newWarranty) {
    if (defaultWarranty.expiresAfter(newWarranty)) {
      throw new InternalError("Attempted to insert a warranty that expires "
          + "before the default warranty. This should not happen.");
    }

    if (oldWarranty.expiresAfter(newWarranty)) {
      /*
      throw new InternalError(
          "Attempted to extend a warranty " + oldWarranty.expiry()
          + " with one that expires sooner " + newWarranty.expiry() + ".");
          */
      // Do nothing, as far as we're concerned, this is fine.
      return true;
    }

    /*
    boolean success = false;
    if (oldWarranty == defaultWarranty) {
      success = table.putIfAbsent(id, newWarranty) == null;
    }

    if (!success) success = table.replace(id, oldWarranty, newWarranty);
    */

    boolean success = false;
    WarrantiedCallResult oldEntry = table.get(id);
    if (oldEntry.warranty.equals(oldWarranty)) {
      table.put(id, new WarrantiedCallResult(oldEntry.value, newWarranty));
      success = true;
    }

    if (success) {
      long expiry = newWarranty.expiry();
      long length = expiry - System.currentTimeMillis();
      STORE_DB_LOGGER.finest("Extended warranty for id " + id + "; expiry="
          + expiry + " (in " + length + " ms)");

      Set<CallInstance> set = new HashSet<CallInstance>();
      Set<CallInstance> existingSet = reverseTable.putIfAbsent(newWarranty, set);
      if (existingSet != null) set = existingSet;
      synchronized (set) {
        set.add(id);

        // Make sure the reverse table has an entry for the warranty, in case it
        // was removed by the Collector thread.
        reverseTable.put(newWarranty, set);
      }

      // Signal the collector thread that we have a new warranty.
      collector.signalNewWarranty();
    }

    return success;
  }

  /**
   * Sets the default warranty for ids that aren't yet in the table.
   */
  public void setDefaultWarranty(SemanticWarranty warranty) {
    defaultWarranty = warranty;
  }

  /**
   * Notifies the issuer of a read prepare extending the Semantic Warranty for
   * the given call.
   */
  public void notifyReadPrepare(CallInstance call) {
    SEMANTIC_WARRANTY_LOGGER.finest("Notifying read prepare on " + call);
    issuer.notifyReadPrepare(call);
  }

  /**
   * Provides the longest SemanticWarranty that depended on any of the given
   * onums that is longer than the given commitTime.
   */
  public SemanticWarranty longestReadDependency(
      final Collection<SerializedObject> writes, long transactionID,
      long commitTime, final String storeName) {
    int initCapacity = writes.size() >= 1 ? writes.size() : 1;
    PriorityQueue<Pair<CallInstance, SemanticWarranty>> dependencies = new
      PriorityQueue<Pair<CallInstance, SemanticWarranty>>(initCapacity, new
          Comparator<Pair<CallInstance, SemanticWarranty>>() {
        @Override
        public int compare(Pair<CallInstance, SemanticWarranty> p1,
          Pair<CallInstance, SemanticWarranty> p2) {
          if (p1.second.expiry() == p2.second.expiry()) return 0;
          return p1.second.expiry() > p2.second.expiry() ? -1 : 1;
        }
      });

    for (SerializedObject obj : writes)
      for (CallInstance call : dependencyTable.getReaders(obj.getOnum()))
        if (get(call).warranty.expiresAfter(commitTime, true))
          dependencies.add(new Pair<CallInstance, SemanticWarranty>(call, get(call).warranty));

    while (!dependencies.isEmpty()) {
      Pair<CallInstance, SemanticWarranty> p = dependencies.poll();
      long currentTime = System.currentTimeMillis();
      if (p.second.expiresBefore(currentTime, false)) break;

      final CallInstance call = p.first;
      final fabric.lang.Object oldResult = get(call).value;
      Future<SemanticWarrantyRequest> checkHandler =
        Executors.newSingleThreadExecutor().submit((new
          Callable<SemanticWarrantyRequest>() {
            @Override
            public SemanticWarrantyRequest call() {
              try {
                Worker.runInTopLevelTransaction(new Code<Void>() {
                  @Override
                  public Void run() {
                    SEMANTIC_WARRANTY_LOGGER.finest("CHECKING CALL " + call);
                    Store localStore = Worker.getWorker().getStore(storeName);
                    TransactionManager tm = TransactionManager.getInstance();
                    for (SerializedObject obj : writes) {
                      (new _Proxy(localStore,
                                  obj.getOnum())).fetch().$copyAppStateFrom(obj.deserialize(localStore,
                                  new VersionWarranty(0)));
                    }
                    Object newResult = call.runCall();
                    if (!oldResult.equals(newResult)) {
                      SEMANTIC_WARRANTY_LOGGER.finest("DONE RECOMPUTING CALL " +
                        call + " which changed from " + oldResult + " to " +
                        newResult + "!");
                      throw new CallChangedException();
                    }
                    SEMANTIC_WARRANTY_LOGGER.finest("DONE RECOMPUTING CALL " + call + " which didn't change!");
                    // TODO: What if there's a write in the new evaluation and so
                    // there's no request?
                    //
                    // I guess we cry.
                    throw new CallUnchangedException(tm.getCurrentLog().getRequest(call));
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
        SemanticWarrantyRequest updatedReq = checkHandler.get(p.second.expiry()
            - currentTime, TimeUnit.MILLISECONDS);
        if (updatedReq == null) {
          SEMANTIC_WARRANTY_LOGGER.finest("Call " + call + " affected by " +
              transactionID);
          issuer.notifyWritePrepare(call);
          return p.second;
        }

        pendingMap.putIfAbsent(transactionID, new HashMap<CallInstance,
            Pair<SemanticWarrantyRequest, SemanticWarranty>>());
        pendingMap.get(transactionID).put(call, new
            Pair<SemanticWarrantyRequest, SemanticWarranty>(updatedReq,
              p.second));
        SEMANTIC_WARRANTY_LOGGER.finest("Call " + call + " unaffected by " +
            transactionID);
      } catch (ExecutionException e) {
        SEMANTIC_WARRANTY_LOGGER.finest("Call " + call
            + " had an error in check :(");
        SEMANTIC_WARRANTY_LOGGER.finest("\t" + e.getMessage());
        issuer.notifyWritePrepare(call);
        return p.second;
      } catch (InterruptedException e) {
        SEMANTIC_WARRANTY_LOGGER.finest("Call " + call
            + " had interrupted in check :(");
        issuer.notifyWritePrepare(call);
        return p.second;
      } catch (TimeoutException e) {
        SEMANTIC_WARRANTY_LOGGER.finest("Call " + call
            + " timed out in check :(");
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
    SEMANTIC_WARRANTY_LOGGER.finest(
        String.format("Aborting semantic warranty updates from %x",
          transactionID));
    pendingMap.remove(transactionID);
  }

  /**
   * Commit any state associated with the given transactionID at the given
   * commitTime.
   */
  public void commit(long transactionID, long commitTime) {
    SEMANTIC_WARRANTY_LOGGER.finest(
        String.format("Committing semantic warranty updates from %x",
          transactionID));
    if (pendingMap.get(transactionID) != null) {
      Map<CallInstance, Pair<SemanticWarrantyRequest, SemanticWarranty>> m =
        pendingMap.get(transactionID);
      for (CallInstance call : m.keySet()) {
        SEMANTIC_WARRANTY_LOGGER.finest("Adding " + call + ": " + m.get(call).first + ", " + m.get(call).second);
        putAt(commitTime, m.get(call).first, m.get(call).second);
      }
    }
    pendingMap.remove(transactionID);
  }

  /**
   * GC thread for expired warranties.
   */
  private final class Collector extends Thread {
    private final long MIN_WAIT_TIME = 1000;
    private final AtomicBoolean haveNewWarranty;

    public Collector() {
      super("Warranty GC");
      setDaemon(true);

      this.haveNewWarranty = new AtomicBoolean(false);
    }

    private void signalNewWarranty() {
      haveNewWarranty.set(true);
    }

    @Override
    public void run() {
      while (true) {
        long now = System.currentTimeMillis();

        long nextExpiryTime = Long.MAX_VALUE;
        for (Iterator<Entry<SemanticWarranty, Set<CallInstance>>> it =
            reverseTable.entrySet().iterator(); it.hasNext();) {
          Entry<SemanticWarranty, Set<CallInstance>> entry = it.next();
          SemanticWarranty warranty = entry.getKey();

          if (warranty.expiresAfter(now, true)) {
            // Warranty still valid. Update nextExpiryTime as necessary.
            long expiry = warranty.expiry();
            if (nextExpiryTime > expiry) nextExpiryTime = expiry;
          } else {
            // Warranty expired. Remove relevant entries from table.
            Set<CallInstance> ids = entry.getValue();
            synchronized (ids) {
              for (CallInstance call : ids) {
                table.remove(call);
                dependencyTable.removeCall(call);
              }

              it.remove();
            }
          }
        }

        // Wait until either the next warranty expires or we have more
        // warranties.
        long waitTime = nextExpiryTime - System.currentTimeMillis();
        for (int timeWaited = 0; timeWaited < waitTime; timeWaited +=
            MIN_WAIT_TIME) {
          try {
            Thread.sleep(MIN_WAIT_TIME);
          } catch (InterruptedException e) {
          }

          if (haveNewWarranty.getAndSet(false)) {
            break;
          }
        }
      }
    }
  }
}
