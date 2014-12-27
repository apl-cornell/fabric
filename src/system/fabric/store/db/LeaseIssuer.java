package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.STORE_DB_LOGGER;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import com.google.common.base.Supplier;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

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
   *
   * TODO: Consider longer K2 since this is less costly than leases.
   */
  private static final double K2 = 0.5;

  /**
   * The minimum read-to-write ratio. If the read-to-write ratio for an object
   * is below this threshold, then no leases will be issued for that object.
   */
  private static final double K3 = 2;

  /**
   * The number of samples to take after a lease period before issuing another
   * lease.
   */
  public static final int SAMPLE_SIZE = 3;

  // END TUNING PARAMETERS ///////////////////////////////////////////////////

  /**
   * The popularity cutoff. If the average read interval of an object is above
   * this threshold, then no leases will be issued for that object.
   */
  private static final double MAX_READ_PREP_INTERVAL = MAX_LEASE_LENGTH
      / (K2 * K3);

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
      reverseTable.put(defaultLease.expiry() >> 3, this);
    }

    synchronized void setLeaseIssued(V lease) {
      reverseTable.remove(leaseIssued.expiry() >> 3, this);
      this.leaseIssued = lease;
      reverseTable.put(lease.expiry() >> 3, this);
    }

    synchronized boolean replaceLease(V oldLease, V newLease) {
      boolean success = this.leaseIssued.equals(oldLease);
      if (success) {
        AccessMetrics<K>.Metrics metrics = getMetrics(key, false);
        if (metrics != null) {
          long expiry = newLease.expiry();
          synchronized (metrics) {
            boolean updated = metrics.updateTerm(expiry);
            if (updated) this.setLeaseIssued(newLease);
            return true;
          }
        }

        this.setLeaseIssued(newLease);
      }
      return success;
    }
  }

  private static int count = 0;

  private final ConcurrentMap<K, Entry> table;

  /**
   * Coarse grained reverse table, indexed by seconds since epoch (rounded down
   * to nearest whole second).
   */
  private final SetMultimap<Long, Entry> reverseTable;

  private final AccessMetrics<K> accessMetrics;

  protected LeaseIssuer(V defaultLease, AccessMetrics<K> accessMetrics) {
    this.table = new ConcurrentHashMap<>();
    this.reverseTable = Multimaps.synchronizedSetMultimap(
        Multimaps.newSetMultimap(
          new HashMap<Long, Collection<Entry>>(),
            new Supplier<Set<Entry>>() {
              @Override
              public Set<Entry> get() {
                return new HashSet<>();
              }
            }));
    this.defaultLease = defaultLease;
    this.accessMetrics = accessMetrics;
    new Collector().start();
  }

  private Entry getEntry(K key) {
    Entry existingEntry = table.get(key);
    if (existingEntry != null) return existingEntry;

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
    return getEntry(key).leaseIssued;
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
   * Suggests a lease-expiry time beyond the given expiry time.
   *
   * @return The suggested expiry.  Returns 0 if a warranty should be used
   * instead.
   */
  public long suggestLease(Principal worker, K key, long expiry) {
    // Snapshot state to avoid locking for too long.
    final long readInterval;
    final long writeInterval;
    final boolean isWritten;
    Oid writer;
    AccessMetrics<K>.Metrics m = getMetrics(key);
    synchronized (m) {
      // Only continue if we have enough samples since the last lease
      // period.
      if (m.getNumReadPrepares() < SAMPLE_SIZE) return expiry;

      writeInterval = m.getWriteInterval();
      readInterval = m.getReadInterval();
      writer = m.getWriter();
      isWritten = m.isWrittenSinceTerm();
    }

    final int curCount = count++;

    if (writer == null || !writer.equals(new Oid(worker))) {
      // If object isn't exclusively written by the requester, don't give a
      // lease
      if (curCount % 10000 == 0) {
        // onum, readInterval, actualReadInterval, writeInterval, lease
        HOTOS_LOGGER.info("lease #" + curCount + ": " + key + ","
            + readInterval + "," + writeInterval + ",no-exclusive-writer");
      }
      return 0;
    }

    if (readInterval > MAX_READ_PREP_INTERVAL) {
      // The object is too unpopular, only issue for the expiry needed.
      if (curCount % 10000 == 0) {
        // onum, readInterval, actualReadInterval, writeInterval, lease
        HOTOS_LOGGER.info("lease #" + curCount + ": " + key + ","
            + readInterval + "," + writeInterval + ",unpopular");
      }
      return expiry;
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

  /**
   * Clean Up thread to wipe away old warranties.
   *
   * Currently does the dead simple method of checking every second for things
   * to wipe.
   *
   * TODO: This is not thread safe due to how table is maintained, go back and
   * fix the other code to not produce data races.
   */
  private final class Collector extends Thread {
    private Collector() {
      super("Lease entry collector");
      setDaemon(true);
    }

    @Override
    public void run() {
      while (true) {
        try {
          // Every second, wipe away stale entries.
          Thread.sleep(1000);

          HashSet<Long> timeSlices;
          synchronized (reverseTable) {
            timeSlices = new HashSet<>(reverseTable.keySet());
          }
          for (Long timeSlice : timeSlices) {
            if (((timeSlice.longValue() + 1) << 3) < System.currentTimeMillis()) {
              Set<Entry> toWipe = reverseTable.removeAll(timeSlice);
              for (Entry candidate : toWipe) {
                if (candidate.leaseIssued.expired(false))
                  table.remove(candidate.key, candidate);
              }
            }
          }
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
      }
    }
  }
}
