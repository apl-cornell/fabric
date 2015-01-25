package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.Lease;
import fabric.common.exceptions.InternalError;
import fabric.common.util.Oid;
import fabric.lang.security.Principal;

/**
 * A lease issuer maintains the mapping from keys to leases on those keys and
 * uses access metrics for keys to suggest how long newly issued leases should
 * last. This class is thread-safe.
 */
public class LeaseIssuer<K, V extends Lease> {

  // BEGIN TUNING PARAMETERS /////////////////////////////////////////////////

  /**
   * The maximum length of time (in milliseconds) for which each issued lease
   * should be valid.
   */
  private static final int MAX_LEASE_LENGTH = 10000;

  /**
   * The amount by which write intervals are scaled to determine lease length.
   */
  private static final double K2 = 1;

  /**
   * The number of samples to take after a lease period before issuing another
   * lease.
   */
  public static final int SAMPLE_SIZE = 3;

  // END TUNING PARAMETERS ///////////////////////////////////////////////////

  /**
   * The default lease for keys that aren't yet in the table. All leases in the
   * table should expire after the default lease.
   */
  private volatile V defaultLease;

  private class Entry {
    final K key;
    volatile V leaseIssued;

    Entry(K key) {
      this.key = key;
      this.leaseIssued = defaultLease;
    }

    synchronized void setLeaseIssued(V lease) {
      this.leaseIssued = lease;
    }

    synchronized boolean replaceLease(V oldLease, V newLease) {
      boolean success = this.leaseIssued.equals(oldLease);
      if (success) {
        AccessMetrics<K>.Metrics metrics = getMetrics(key, false);
        if (metrics != null) {
          long expiry = newLease.expiry();
          synchronized (metrics) {
            boolean updated = metrics.updateTerm(expiry);
            if (updated) this.leaseIssued = newLease;
            return true;
          }
        }

        this.leaseIssued = newLease;
      }
      return success;
    }
  }

  private static int count = 0;

  private final ConcurrentMap<K, Entry> table;

  private final AccessMetrics<K> accessMetrics;

  protected LeaseIssuer(V defaultLease, AccessMetrics<K> accessMetrics) {
    this.table = new ConcurrentHashMap<K, Entry>();
    this.defaultLease = defaultLease;
    this.accessMetrics = accessMetrics;
  }

  private Entry getEntry(K key) {
    return getEntry(key, true);
  }

  private Entry getEntry(K key, boolean createIfAbsent) {
    Entry existingEntry = table.get(key);
    if (existingEntry != null) return existingEntry;

    if (!createIfAbsent) return null;

    Entry entry = new Entry(key);
    existingEntry = table.putIfAbsent(key, entry);
    return existingEntry == null ? entry : existingEntry;
  }

  private AccessMetrics<K>.Metrics getMetrics(K key, boolean createIfAbsent) {
    return accessMetrics.getMetrics(key, createIfAbsent);
  }

  private AccessMetrics<K>.Metrics getMetrics(K key) {
    return accessMetrics.getMetrics(key, true);
  }

  /**
   * @return the issued lease for the given key.
   */
  final V get(K key) {
    Entry e;
    return ((e = getEntry(key, false)) == null) ? defaultLease : e.leaseIssued;
  }

  /**
   * Replaces the lease for an onum only if it currently has a specific lease.
   *
   * @return true iff the lease was replaced.
   */
  final boolean replace(K key, V oldLease, V newLease) {
    if (defaultLease.expiresAfter(newLease)) {
      throw new InternalError("Attempted to insert a lease that expires "
          + "before the default lease. This should not happen.");
    }

    if (oldLease.expiresAfter(newLease)) {
      throw new InternalError(
          "Attempted to replace a lease with one that expires sooner");
    }

    boolean success = getEntry(key).replaceLease(oldLease, newLease);
    if (STORE_DB_LOGGER.isLoggable(Level.FINEST) && success) {
      long expiry = newLease.expiry();
      long length = expiry - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINEST, "Extended lease for {0}"
          + "; expiry={1} (in {2} ms)", key, expiry, length);
    }

    return success;
  }

  /**
   * For recovering lease state from stable storage. Sets the issued lease for
   * the given key.
   */
  final void put(K key, V lease) {
    if (defaultLease.expiresAfter(lease)) {
      throw new InternalError("Attempted to insert a lease that expires "
          + "before the default lease. This should not happen.");
    }

    if (STORE_DB_LOGGER.isLoggable(Level.FINEST)) {
      long expiry = lease.expiry();
      long length = expiry - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINEST,
          "Adding lease for {0}; expiry={1} (in {2} ms)", key, expiry, length);
    }

    getEntry(key).setLeaseIssued(lease);
  }

  /**
   * For recovering lease state from stable storage. Sets the default lease for
   * onums that don't yet have an entry in the issuer.
   */
  void setDefaultLease(V lease) {
    defaultLease = lease;
  }

  /**
   * Suggests a lease-expiry time.
   *
   * @param worker the worker requesting the lease.
   * @param key key the lease is for.
   */
  public Long suggestLease(Principal worker, K key) {
    return suggestLease(worker, key, System.currentTimeMillis());
  }

  /**
   * Suggests a lease-expiry time beyond the given expiry time.  Returns 0 if
   * a lease would be inappropriate for this object.
   *
   * @return The suggested expiry.
   */
  public long suggestLease(Principal worker, K key, long expiry) {
    // Snapshot state to avoid locking for too long.
    final long readInterval;
    final long writeInterval;
    final boolean isUsed;
    Oid client;
    AccessMetrics<K>.Metrics m = getMetrics(key);
    synchronized (m) {
      // Only continue if we have enough samples since the last lease
      // period.
      if (m.getNumReadPrepares() < SAMPLE_SIZE) return expiry;

      writeInterval = m.getWriteInterval();
      readInterval = m.getReadInterval();
      client = m.getClient();
      isUsed = m.isUsedSinceTerm();
    }

    final int curCount = count++;

    if ((client == null && isUsed)
        || (client != null && !client.equals(new Oid(worker)))) {
      // If object isn't exclusively used by the requester, don't give a
      // lease
      if (curCount % 10000 == 0) {
        // onum, readInterval, actualReadInterval, writeInterval, lease
        HOTOS_LOGGER.info("lease #" + curCount + ": " + key + ","
            + readInterval + "," + writeInterval + ",no-exclusive-client");
      }
      return 0;
    }

    // Issue lease with term as long as K2 * writeInterval
    if (curCount % 10000 == 0) {
      // onum, readInterval, actualReadInterval, writeInterval, lease
      HOTOS_LOGGER.info("lease #" + curCount + ": " + key + "," + readInterval
          + "," + writeInterval + "," + (K2 * writeInterval));
    }

    long leaseLength = Math.min((long) (K2 * writeInterval), MAX_LEASE_LENGTH);

    if (HOTOS_LOGGER.isLoggable(Level.FINE)) {
      if (key instanceof Number && ((Number) key).longValue() == 0) {
        Logging.log(HOTOS_LOGGER, Level.FINE, "onum = {0}, lease length = {1}",
            key, leaseLength);
      }

      if (writeInterval != Integer.MAX_VALUE) {
        Logging.log(HOTOS_LOGGER, Level.FINE, "onum = {0}, lease length = {1}",
            key, leaseLength);
      }
    }

    return Math.max(expiry, System.currentTimeMillis() + leaseLength);
  }
}
