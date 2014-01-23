package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.Warranty;
import fabric.common.util.Pair;

/**
 * A table containing version warranties, keyed by onum, and supporting
 * concurrent accesses.
 */
public class WarrantyTable<K, V extends Warranty> {
  /**
   * The default warranty for onums that aren't yet in the table. All warranties
   * in the table should expire after the default warranty.
   */
  private volatile V defaultWarranty;

  private final ConcurrentMap<K, V> table;

  private final Collector collector;

  /**
   * Priority-queue version of the table for ease of garbage collection.
   */
  private final PriorityBlockingQueue<Pair<K, V>> gcQueue;

  WarrantyTable(V defaultWarranty) {
    this.defaultWarranty = defaultWarranty;
    table = new ConcurrentHashMap<K, V>();
    gcQueue = new PriorityBlockingQueue<>(11, new Comparator<Pair<K, V>>() {
      @Override
      public int compare(Pair<K, V> o1, Pair<K, V> o2) {
        return Long.compare(o1.second.expiry(), o2.second.expiry());
      }
    });

    collector = new Collector();
    collector.start();
  }

  final V get(K key) {
    V result = table.get(key);
    if (result == null) return defaultWarranty;
    return result;
  }

  final void put(K key, V warranty) {
    if (defaultWarranty.expiresAfter(warranty)) {
      throw new InternalError("Attempted to insert a warranty that expires "
          + "before the default warranty. This should not happen.");
    }

    long expiry = warranty.expiry();
    long length = expiry - System.currentTimeMillis();
    STORE_DB_LOGGER.finest("Adding warranty for " + key + "; expiry=" + expiry
        + " (in " + length + " ms)");

    table.put(key, warranty);
    addGCEntry(key, warranty);
  }

  private final void addGCEntry(K key, V warranty) {
    gcQueue.add(new Pair<>(key, warranty));

    // Signal the collector thread that we have a new warranty.
    collector.signalNewWarranty();
  }

  /**
   * Extends the warranty for an onum only if it currently has a specific
   * warranty.
   * 
   * @return true iff the warranty was replaced.
   */
  final boolean extend(K key, V oldWarranty, V newWarranty) {
    if (defaultWarranty.expiresAfter(newWarranty)) {
      throw new InternalError("Attempted to insert a warranty that expires "
          + "before the default warranty. This should not happen.");
    }

    if (oldWarranty.expiresAfter(newWarranty)) {
      throw new InternalError(
          "Attempted to extend a warranty with one that expires sooner.");
    }

    boolean success = false;
    if (oldWarranty == defaultWarranty) {
      success = table.putIfAbsent(key, newWarranty) == null;
    }

    if (!success) success = table.replace(key, oldWarranty, newWarranty);

    if (success) {
      long expiry = newWarranty.expiry();
      long length = expiry - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINEST, "Extended warranty for {0}"
          + "; expiry={1} (in {2} ms)", key, expiry, length);

      addGCEntry(key, newWarranty);
    }

    return success;
  }

  /**
   * Sets the default warranty for onums that aren't yet in the table.
   */
  void setDefaultWarranty(V warranty) {
    defaultWarranty = warranty;
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
        long nextExpiryTime;
        // Loop until the GC queue is empty or there is an unexpired warranty at
        // its head. All expired warranties that we encounter are GCed.
        while (true) {
          Pair<K, V> gcQueueHead = gcQueue.peek();
          if (gcQueueHead == null || gcQueueHead.second.expired(false)) {
            nextExpiryTime =
                gcQueueHead == null ? Long.MAX_VALUE : gcQueueHead.second
                    .expiry();
            break;
          }

          // GC.
          gcQueueHead = gcQueue.poll();
          if (gcQueueHead == null) {
            throw new InternalError("Shouldn't happen.");
          } else if (gcQueueHead.second.expired(false)) {
            throw new InternalError("Shouldn't happen.");
          }

          table.remove(gcQueueHead.first, gcQueueHead.second);
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
