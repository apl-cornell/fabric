package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import fabric.common.VersionWarranty;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;

/**
 * A thread-safe table containing version warranties, keyed by onum.
 */
public class VersionWarrantyTable {
  /**
   * The default warranty for onums that aren't yet in the table. All warranties
   * in the table should expire after the default warranty.
   */
  private VersionWarranty defaultWarranty;

  private final LongKeyMap<VersionWarranty> table;

  /**
   * Reverse mapping: maps version warranties to corresponding onums.
   */
  private final SortedMap<VersionWarranty, LongSet> reverseTable;

  VersionWarrantyTable() {
    defaultWarranty = new VersionWarranty(0);
    table = new LongKeyHashMap<VersionWarranty>();
    reverseTable = new TreeMap<VersionWarranty, LongSet>();

    new Collector().start();
  }

  final synchronized VersionWarranty get(long onum) {
    VersionWarranty result = table.get(onum);
    if (result == null) return defaultWarranty;
    return result;
  }

  final synchronized void put(long onum, VersionWarranty warranty) {
    if (defaultWarranty.expiresAfter(warranty)) {
      throw new InternalError("Attempted to insert a warranty that expires "
          + "before the default warranty. This should not happen.");
    }

    long expiry = warranty.expiry();
    long length = expiry - System.currentTimeMillis();
    STORE_DB_LOGGER.finest("Adding warranty for onum " + onum + "; expiry="
        + expiry + " (in " + length + " ms)");

    table.put(onum, warranty);

    LongSet set = reverseTable.get(warranty);
    if (set == null) {
      set = new LongHashSet();
      reverseTable.put(warranty, set);
    }
    set.add(onum);

    // Signal the collector thread that we have a new warranty.
    this.notifyAll();
  }

  /**
   * Sets the default warranty for onums that aren't yet in the table.
   */
  synchronized void setDefaultWarranty(VersionWarranty warranty) {
    defaultWarranty = warranty;
  }

  /**
   * GC thread for expired warranties.
   */
  private final class Collector extends Thread {
    private final long MIN_WAIT_TIME = 1000;

    public Collector() {
      super("Warranty GC");
      setDaemon(true);
    }

    @Override
    public void run() {
      synchronized (VersionWarrantyTable.this) {
        MAIN: while (true) {
          long now = System.currentTimeMillis();

          // Loop through warranties in ascending order of expiry.
          for (Iterator<Entry<VersionWarranty, LongSet>> it =
              reverseTable.entrySet().iterator(); it.hasNext();) {
            Entry<VersionWarranty, LongSet> entry = it.next();
            VersionWarranty warranty = entry.getKey();

            if (warranty.expiresAfter(now, true)) {
              // Warranty still valid. Wait until either it expires or we have
              // more warranties, and then start over.
              long waitTime = warranty.expiry() - now;
              if (waitTime < MIN_WAIT_TIME) waitTime = MIN_WAIT_TIME;
              try {
                VersionWarrantyTable.this.wait(waitTime);
              } catch (InterruptedException e) {
              }
              continue MAIN;
            }

            // Remove warranties.
            LongSet onums = entry.getValue();
            table.keySet().removeAll(onums);
            it.remove();
          }

          // No more warranties. Wait for more.
          try {
            VersionWarrantyTable.this.wait();
          } catch (InterruptedException e) {
          }
        }
      }
    }
  }
}
