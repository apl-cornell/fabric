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
import fabric.common.Warranty;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;

import fabric.worker.AbortException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.memoize.CallInstance;
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
   * ObjectDB for which calls are reading items from.
   */
  private final ObjectDB database;

  public SemanticWarrantyTable(ObjectDB database) {
    defaultWarranty = new SemanticWarranty(0);
    table = new ConcurrentHashMap<CallInstance, WarrantiedCallResult>();
    reverseTable = new ConcurrentHashMap<SemanticWarranty, Set<CallInstance>>();
    dependencyTable = new SemanticWarrantyDependencies();
    this.database = database;

    collector = new Collector();
    collector.start();
  }

  public final WarrantiedCallResult get(CallInstance id) {
    return table.get(id);
  }

  /* Create a warranty with a suggested time for the given call with the
   * associated reads and calls.
   *
   * XXX THIS IS CURRENTLY NOT A VERY GOOD ALGORITHM AT _ALL_
   */
  public SemanticWarranty proposeWarranty(CallInstance id, LongSet reads, Set<CallInstance>
      calls, fabric.lang.Object value,
      Map<CallInstance, SemanticWarranty> relatedProposals) {
    Warranty minWarranty = new SemanticWarranty(Long.MAX_VALUE);
    LongIterator it1 = reads.iterator();
    while (it1.hasNext()) {
      Warranty readWarranty = database.refreshWarranty(it1.next());
      if (readWarranty.compareTo(minWarranty) < 0) {
        minWarranty = readWarranty;
      }
    }

    //XXX THIS IS ALMOST CERTAINLY WRONG IF WE MAKE MORE THAN ONE SEMANTIC
    //WARRANTY REQUEST IN A TRANSACTION AND ONE WAS NESTED IN THE OTHER.
    for (CallInstance call : calls) {
      if (relatedProposals.get(call) != null) {
        Warranty callWarranty = relatedProposals.get(call);
        if (callWarranty.compareTo(minWarranty) < 0) {
          minWarranty = callWarranty;
        }
      } else if (get(call) == null) {
          throw new InternalError("Attempted to propose a warranty without "
              + "warranties for dependency " + call.toString());
      } else {
        Warranty callWarranty = get(call).warranty;
        if (callWarranty.compareTo(minWarranty) < 0) {
          minWarranty = callWarranty;
        }
      }
    }

    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Suggesting SemanticWarranty of {0} for CallInstance {1}",
        minWarranty.expiry(), id);
    return new SemanticWarranty(minWarranty.expiry());
    //return new SemanticWarranty(System.currentTimeMillis() + 5000);
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
   * Provides the longest SemanticWarranty that depended on any of the given
   * onums that is longer than the given commitTime.
   */
  public SemanticWarranty longestReadDependency(final
      Collection<SerializedObject> objs, long commitTime) {
    PriorityQueue<Pair<CallInstance, SemanticWarranty>> dependencies = new
      PriorityQueue<Pair<CallInstance, SemanticWarranty>>(objs.size(), new
          Comparator<Pair<CallInstance, SemanticWarranty>>() {
        @Override
        public int compare(Pair<CallInstance, SemanticWarranty> p1,
          Pair<CallInstance, SemanticWarranty> p2) {
          if (p1.second.expiry() == p2.second.expiry()) return 0;
          return p1.second.expiry() > p2.second.expiry() ? -1 : 1;
        }
      });

    for (SerializedObject obj : objs)
      for (CallInstance call : dependencyTable.getReaders(obj.getOnum()))
        if (get(call).warranty.expiresAfter(commitTime, true))
          dependencies.add(new Pair<CallInstance, SemanticWarranty>(call, get(call).warranty));

    while (!dependencies.isEmpty()) {
      Pair<CallInstance, SemanticWarranty> p = dependencies.poll();
      final CallInstance call = p.first;
      final fabric.lang.Object oldResult = get(call).value;
      //TODO: Time out call if it lasts longer than it's warranty.
      try {
        Worker.runInTopLevelTransaction(new Code<Void>() {
              @Override
              public Void run() {
                for (SerializedObject obj : objs) {
                  TransactionManager.getInstance().registerWrite(obj.deserialize(Worker.getWorker().getLocalStore(),
                      new VersionWarranty(0)));
                }
                if (!oldResult.equals(call.runCall()))
                  throw new AbortException();
                return null;
              }
            }, false);
      } catch (AbortException e) {
        return p.second;
      }
    }
    return null;
  }

  /*
   * Provides the longest SemanticWarranty that called the given callId.
  public Pair<CallInstance, SemanticWarranty>
    longestCallDependency(CallInstance callId) {
    Pair<CallInstance, SemanticWarranty> longest = null;

    Set<CallInstance> callers = dependencyTable.getCallers(callId);
    for (CallInstance call : callers) {
      SemanticWarranty cur = get(call).warranty;
      if (cur.expiresAfter(longest.second)) {
        longest = new Pair<CallInstance, SemanticWarranty>(call, cur);
      }
    }

    return longest;
  }
   */

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
