package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;
import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
  private static final int MAX_SEMANTIC_WARRANTY = 1000;

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
   * Table mapping from transactionIDs for pending transactions to updates for
   * semantic warranty dependencies.
   */
  private final ConcurrentLongKeyMap<Set<SemanticWarrantyRequest>> depUpdateMap;

  public SemanticWarrantyTable(ObjectDB database) {
    defaultWarranty = new SemanticWarranty(0);
    table = new ConcurrentHashMap<CallInstance, WarrantiedCallResult>();
    reverseTable = new ConcurrentHashMap<SemanticWarranty, Set<CallInstance>>();
    dependencyTable = new SemanticWarrantyDependencies();
    issuer = new WarrantyIssuer(250, MAX_SEMANTIC_WARRANTY, 250);
    depUpdateMap = new ConcurrentLongKeyHashMap<Set<SemanticWarrantyRequest>>();

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
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
          "Adding warranty of {0} for call {1}", warranty.expiry(), id);
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
    STORE_DB_LOGGER.finest("Adding warranty for call " + id + "; expiry="
        + expiry + " (in " + length + " ms)");

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
      throw new InternalError(
          "Attempted to extend a warranty with one that expires sooner.");
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
    issuer.notifyReadPrepare(call);
  }

  /**
   * Provides the longest SemanticWarranty that depended on any of the given
   * onums that is longer than the given commitTime.
   */
  public SemanticWarranty longestReadDependency(
      final Collection<SerializedObject> writes, long transactionID,
      long commitTime) {
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
      if (p.second.expiresBefore(System.currentTimeMillis(), false)) break;

      final CallInstance call = p.first;
      SEMANTIC_WARRANTY_LOGGER.finest("CHECKING CALL " + call);
      final fabric.lang.Object oldResult = get(call).value;
      //TODO: Time out call if it lasts longer than it's warranty.
      try {
        Worker.runInTopLevelTransaction(new Code<Void>() {
          @Override
          public Void run() {
            SEMANTIC_WARRANTY_LOGGER.finest("CHECKING CALL " + call);
            Store localStore = Worker.getWorker().getLocalStore();
            TransactionManager tm = TransactionManager.getInstance();
            for (SerializedObject obj : writes) {
              (new _Proxy(localStore,
                          obj.getOnum())).fetch().$copyAppStateFrom(obj.deserialize(localStore,
                          new VersionWarranty(0)));
            }
            if (!oldResult.equals(call.runCall()))
              throw new CallChangedException();
            // TODO: What if there's a write in the new evaluation and so
            // there's no request?
            //
            // I guess we cry.
            throw new CallUnchangedException(tm.getCurrentLog().getRequest(call));
          }
        }, false);
      } catch (CallChangedException e) {
        issuer.notifyWritePrepare(call);
        return p.second;
      } catch (CallUnchangedException e) {
        depUpdateMap.putIfAbsent(transactionID, new HashSet<SemanticWarrantyRequest>());
        depUpdateMap.get(transactionID).add(e.updatedReq);
      }
    }
    return null;
  }

  /**
   * Remove any state associated with the given transactionID due to a
   * transaction abort.
   */
  public void abort(long transactionID) {
    depUpdateMap.remove(transactionID);
  }

  /**
   * Commit any state associated with the given transactionID at the given
   * commitTime.
   */
  public void commit(long transactionID, long commitTime) {
    if (depUpdateMap.get(transactionID) != null)
      for (SemanticWarrantyRequest r : depUpdateMap.get(transactionID))
        putAt(commitTime, r, get(r.call).warranty);
    depUpdateMap.remove(transactionID);
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
