package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.Warranty;
import fabric.common.exceptions.InternalError;

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

  WarrantyTable(V defaultWarranty) {
    this.defaultWarranty = defaultWarranty;
    table = new ConcurrentHashMap<K, V>();
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
    Logging.log(STORE_DB_LOGGER, Level.FINEST,
        "Adding warranty for {0}; expiry={1} (in {2} ms)", key, expiry, length);

    table.put(key, warranty);
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
    }

    return success;
  }

  /**
   * Sets the default warranty for onums that aren't yet in the table.
   */
  void setDefaultWarranty(V warranty) {
    defaultWarranty = warranty;
  }
}
