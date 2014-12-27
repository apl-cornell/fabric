package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.Warranty;
import fabric.common.exceptions.InternalError;

/**
 * A warranty issuer maintains the mapping from keys to warranties on those keys
 * and uses access metrics for keys to suggest how long newly issued warranties
 * should last. This class is thread-safe.
 */
public class WarrantyIssuer<K, V extends Warranty> {

  // BEGIN TUNING PARAMETERS /////////////////////////////////////////////////

  /**
   * The maximum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  private static final int MAX_WARRANTY_LENGTH = 10000;

  /**
   * The amount by which write intervals are scaled to determine warranty
   * length.
   */
  private static final double K2 = 0.5;

  /**
   * The minimum read-to-write ratio. If the read-to-write ratio for an object
   * is below this threshold, then no warranties will be issued for that object.
   */
  private static final double K3 = 2;

  /**
   * The number of samples to take after a warranty period before issuing
   * another warranty.
   */
  public static final int SAMPLE_SIZE = 3;

  // END TUNING PARAMETERS ///////////////////////////////////////////////////

  /**
   * The popularity cutoff. If the average read interval of an object is above
   * this threshold, then no warranties will be issued for that object.
   */
  private static final double MAX_READ_PREP_INTERVAL = MAX_WARRANTY_LENGTH
      / (K2 * K3);

  /**
   * The default warranty for keys that aren't yet in the table. All warranties
   * in the table should expire after the default warranty.
   */
  private volatile V defaultWarranty;

  private static int count = 0;

  private final ConcurrentMap<K, V> table;

  private final AccessMetrics<K> accessMetrics;

  protected WarrantyIssuer(V defaultWarranty, AccessMetrics<K> accessMetrics) {
    //this.table = CacheBuilder.newBuilder().expireAfterWrite(MAX_WARRANTY_LENGTH,
        //TimeUnit.MILLISECONDS).<K, V>build().asMap();
    this.table = new ConcurrentHashMap<>();
    this.defaultWarranty = defaultWarranty;
    this.accessMetrics = accessMetrics;
  }

  private AccessMetrics<K>.Metrics getMetrics(K key) {
    return accessMetrics.getMetrics(key, true);
  }

  /**
   * @return the issued warranty for the given key.
   */
  final V get(K key) {
    V existingWarranty = table.get(key);
    if (existingWarranty != null) return existingWarranty;
    return defaultWarranty;
  }

  /**
   * Replaces the warranty for an onum only if it currently has a specific
   * warranty.
   * 
   * @return true iff the warranty was replaced.
   */
  final boolean replace(K key, V oldWarranty, V newWarranty) {
    if (defaultWarranty.expiresAfter(newWarranty)) {
      throw new InternalError("Attempted to insert a warranty that expires "
          + "before the default warranty. This should not happen.");
    }

    if (oldWarranty.expiresAfter(newWarranty)) {
      throw new InternalError(
          "Attempted to replace a warranty with one that expires sooner");
    }

    boolean success = false;

    AccessMetrics<K>.Metrics metrics = getMetrics(key);
    synchronized (metrics) {
      if (oldWarranty == defaultWarranty && !table.containsKey(key)) {
        success = true;
        table.put(key, newWarranty);
      } else {
        success = table.replace(key, oldWarranty, newWarranty);
      }
      if (success) metrics.updateTerm(newWarranty.expiry());
    }

    if (STORE_DB_LOGGER.isLoggable(Level.FINEST) && success) {
      long expiry = newWarranty.expiry();
      long length = expiry - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINEST, "Extended warranty for {0}"
          + "; expiry={1} (in {2} ms)", key, expiry, length);
    }

    return success;
  }

  /**
   * For recovering warranty state from stable storage. Sets the issued warranty
   * for the given key.
   */
  final void put(K key, V warranty) {
    if (defaultWarranty.expiresAfter(warranty)) {
      throw new InternalError("Attempted to insert a warranty that expires "
          + "before the default warranty. This should not happen.");
    }

    if (STORE_DB_LOGGER.isLoggable(Level.FINEST)) {
      long expiry = warranty.expiry();
      long length = expiry - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINEST,
          "Adding warranty for {0}; expiry={1} (in {2} ms)", key, expiry,
          length);
    }

    table.put(key, warranty);
  }

  /**
   * For recovering warranty state from stable storage. Sets the default
   * warranty for onums that don't yet have an entry in the issuer.
   */
  void setDefaultWarranty(V warranty) {
    defaultWarranty = warranty;
  }

  /**
   * Suggests a warranty-expiry time.
   */
  public Long suggestWarranty(K key) {
    return suggestWarranty(key, System.currentTimeMillis());
  }

  /**
   * Suggests a warranty-expiry time beyond the given expiry time.
   */
  public long suggestWarranty(K key, long expiry) {
    // Snapshot state to avoid locking for too long.
    final long readInterval;
    final long writeInterval;
    AccessMetrics<K>.Metrics m = getMetrics(key);
    synchronized (m) {
      // Only continue if we have enough samples since the last warranty
      // period.
      if (m.getNumReadPrepares() < SAMPLE_SIZE) return expiry;

      writeInterval = m.getWriteInterval();
      readInterval = m.getReadInterval();
    }

    final int curCount = count++;

    if (readInterval > MAX_READ_PREP_INTERVAL) {
      // The object is too unpopular. Suggest the minimal expiry time.
      if (curCount % 10000 == 0) {
        // onum, readInterval, actualReadInterval, writeInterval, warranty
        HOTOS_LOGGER.info("warranty #" + curCount + ": " + key + "," +
            readInterval + "," + writeInterval + ",unpopular");
      }
      return expiry;
    }

    double ratio = writeInterval / (readInterval + 1.0);

    if (ratio < K3) {
      // Not enough reads per write. Suggest the minimal expiry time.
      if (curCount % 10000 == 0) {
        // onum, readInterval, actualReadInterval, writeInterval, warranty
        HOTOS_LOGGER.info("warranty #" + curCount + ": " + key + "," +
            readInterval + "," + writeInterval + ",low read-write");
      }
      return expiry;
    }

    if (curCount % 10000 == 0) {
      // onum, readInterval, actualReadInterval, writeInterval, warranty
      HOTOS_LOGGER
          .info("warranty #" + curCount + ": " + key + "," + readInterval
              + "," + writeInterval + "," + (K2 * writeInterval));
    }

    long warrantyLength =
        Math.min((long) (K2 * writeInterval), MAX_WARRANTY_LENGTH);

    if (HOTOS_LOGGER.isLoggable(Level.FINE)) {
      if (key instanceof Number && ((Number) key).longValue() == 0) {
        Logging.log(HOTOS_LOGGER, Level.FINE,
            "onum = {0}, warranty length = {1}", key, warrantyLength);
      }

      if (writeInterval != Integer.MAX_VALUE) {
        Logging.log(HOTOS_LOGGER, Level.FINE,
            "onum = {0}, warranty length = {1}", key, warrantyLength);
      }
    }

    return Math.max(expiry, System.currentTimeMillis() + warrantyLength);
  }
}
