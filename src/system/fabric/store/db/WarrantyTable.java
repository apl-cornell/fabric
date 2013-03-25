package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import fabric.common.Warranty;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongKeyMap.Entry;

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
   * Reverse mapping: maps version warranty expiry times (in buckets according
   * to REVERSE_TABLE_BUCKET_SIZE) to corresponding onums.
   */
  private final ConcurrentLongKeyMap<Set<K>> reverseTable;
  private final int REVERSE_TABLE_BUCKET_SIZE = 5000;

  WarrantyTable(V defaultWarranty) {
    this.defaultWarranty = defaultWarranty;
    table = new ConcurrentHashMap<K, V>();
    reverseTable = new ConcurrentLongKeyHashMap<Set<K>>();

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
    addReverseEntry(key, warranty);
  }

  private final void addReverseEntry(K key, V warranty) {
    long warrantyBucket = warranty.expiry();
    warrantyBucket +=
        REVERSE_TABLE_BUCKET_SIZE - warrantyBucket % REVERSE_TABLE_BUCKET_SIZE;

    Set<K> set = reverseTable.get(warrantyBucket);
    if (set == null) {
      set = new HashSet<K>();
      Set<K> existingSet = reverseTable.putIfAbsent(warrantyBucket, set);
      if (existingSet != null) set = existingSet;
    }

    synchronized (set) {
      set.add(key);

      // Make sure the reverse table has an entry for the warranty, in case it
      // was removed by the Collector thread.
      reverseTable.put(warrantyBucket, set);
    }

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
      STORE_DB_LOGGER.finest("Extended warranty for " + key + "; expiry="
          + expiry + " (in " + length + " ms)");

      addReverseEntry(key, newWarranty);
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
        long now = System.currentTimeMillis();

        long nextExpiryTime = Long.MAX_VALUE;
        for (Iterator<Entry<Set<K>>> it = reverseTable.entrySet().iterator(); it
            .hasNext();) {
          Entry<Set<K>> entry = it.next();
          long expiry = entry.getKey();

          if (Warranty.isAfter(expiry, now, true)) {
            // Warranty still valid. Update nextExpiryTime as necessary.
            if (nextExpiryTime > expiry) nextExpiryTime = expiry;
          } else {
            // Warranty expired. Remove relevant entries from table.
            Set<K> keys = entry.getValue();
            synchronized (keys) {
              for (K key : keys) {
                Warranty warranty = table.get(key);
                if (warranty == null || !warranty.expired(false)) continue;
                table.remove(key, warranty);
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
