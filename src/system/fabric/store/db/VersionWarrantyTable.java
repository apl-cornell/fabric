package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import fabric.common.VersionWarranty;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;

/**
 * A table containing version warranties, keyed by onum, and supporting
 * concurrent accesses.
 */
public class VersionWarrantyTable {
  /**
   * The default warranty for onums that aren't yet in the table. All warranties
   * in the table should expire after the default warranty.
   */
  private volatile VersionWarranty defaultWarranty;

  private final ConcurrentLongKeyMap<VersionWarranty> table;

  private final Collector collector;

  /**
   * Reverse mapping: maps version warranties to corresponding onums.
   */
  private final ConcurrentMap<VersionWarranty, LongSet> reverseTable;

  VersionWarrantyTable() {
    defaultWarranty = new VersionWarranty(0);
    table = new ConcurrentLongKeyHashMap<VersionWarranty>();
    reverseTable = new ConcurrentHashMap<VersionWarranty, LongSet>();

    collector = new Collector();
    collector.start();
  }

  final VersionWarranty get(long onum) {
    VersionWarranty result = table.get(onum);
    if (result == null) return defaultWarranty;
    return result;
  }

  final void put(long onum, VersionWarranty warranty) {
    if (defaultWarranty.expiresAfter(warranty)) {
      throw new InternalError("Attempted to insert a warranty that expires "
          + "before the default warranty. This should not happen.");
    }

    long expiry = warranty.expiry();
    long length = expiry - System.currentTimeMillis();
    STORE_DB_LOGGER.finest("Adding warranty for onum " + onum + "; expiry="
        + expiry + " (in " + length + " ms)");

    table.put(onum, warranty);

    LongSet set = new LongHashSet();
    LongSet existingSet = reverseTable.putIfAbsent(warranty, set);
    if (existingSet != null) set = existingSet;
    synchronized (set) {
      set.add(onum);

      // Make sure the reverse table has an entry for the warranty, in case it
      // was removed by the Collector thread.
      reverseTable.put(warranty, set);
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
  final boolean extend(long onum, VersionWarranty oldWarranty,
      VersionWarranty newWarranty) {
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
      success = table.putIfAbsent(onum, newWarranty) == null;
    }

    if (!success) success = table.replace(onum, oldWarranty, newWarranty);

    if (success) {
      long expiry = newWarranty.expiry();
      long length = expiry - System.currentTimeMillis();
      STORE_DB_LOGGER.finest("Extended warranty for onum " + onum + "; expiry="
          + expiry + " (in " + length + " ms)");

      LongSet set = new LongHashSet();
      LongSet existingSet = reverseTable.putIfAbsent(newWarranty, set);
      if (existingSet != null) set = existingSet;
      synchronized (set) {
        set.add(onum);

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
   * Sets the default warranty for onums that aren't yet in the table.
   */
  void setDefaultWarranty(VersionWarranty warranty) {
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
        for (Iterator<Entry<VersionWarranty, LongSet>> it =
            reverseTable.entrySet().iterator(); it.hasNext();) {
          Entry<VersionWarranty, LongSet> entry = it.next();
          VersionWarranty warranty = entry.getKey();

          if (warranty.expiresAfter(now, true)) {
            // Warranty still valid. Update nextExpiryTime as necessary.
            long expiry = warranty.expiry();
            if (nextExpiryTime > expiry) nextExpiryTime = expiry;
          } else {
            // Warranty expired. Remove relevant entries from table.
            LongSet onums = entry.getValue();
            synchronized (onums) {
              for (LongIterator onumIt = onums.iterator(); onumIt.hasNext();) {
                table.remove(onumIt.next(), warranty);
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
