package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.Iterator;

import fabric.common.VersionWarranty;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongKeyMap.Entry;

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

  VersionWarrantyTable() {
    defaultWarranty = new VersionWarranty(0);
    table = new ConcurrentLongKeyHashMap<VersionWarranty>();

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
    private final long WAIT_TIME = 1000;

    public Collector() {
      super("Warranty GC");
      setDaemon(true);
    }

    @Override
    public void run() {
      while (true) {
        long now = System.currentTimeMillis();

        for (Iterator<Entry<VersionWarranty>> it = table.entrySet().iterator(); it
            .hasNext();) {
          Entry<VersionWarranty> entry = it.next();
          VersionWarranty warranty = entry.getValue();

          if (warranty.expiresBefore(now, false)) {
            // Warranty expired. Remove relevant entries from table.
            it.remove();
          }
        }

        // Wait until either the next warranty expires or we have more
        // warranties.
        try {
          Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
        }
      }
    }
  }
}
