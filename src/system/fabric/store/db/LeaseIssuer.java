package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.STORE_DB_LOGGER;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import fabric.common.Lease;
import fabric.common.Logging;
import fabric.common.exceptions.InternalError;

/**
 * A lease issuer is notified of write events and read-prepares, and uses this
 * information to decide how long issued leases should last and who they should
 * be issued to. It also tracks the latest leases that have been issued for each
 * object. This class is thread-safe.
 *
 * TODO: Incorporate number of lessees and/or lessee specific metrics for
 * issuing decision logic.
 */
public class LeaseIssuer<K, V extends Lease> {

  // BEGIN TUNING PARAMETERS /////////////////////////////////////////////////

  /**
   * The maximum length of time (in milliseconds) for which each issued lease
   * should be valid.
   */
  private static final int MAX_LEASE_LENGTH = 10000;

  /**
   * The decay rate for the exponential average when calculating the period of
   * read and write prepares. Higher values result in slower adaptation to
   * changes in the prepare rates.
   */
  private static final double PREPARE_ALPHA = .9;

  /**
   * The half life for determining the alpha to use for combining the first
   * prepare interval after a lease term. The alpha value used is determined by
   * 2^(-L/HALF_LIFE).
   */
  private static final double HALF_LIFE = 5000;

  /**
   * The amount by which write intervals are scaled to determine term length.
   */
  private static final double K2 = 0.5;

  /**
   * The minimum read-to-write ratio. If the read-to-write ratio for an object
   * is below this threshold, then no lease will be issued for that object.
   *
   * TODO: Not sure this makes sense for leases.
   */
  private static final double K3 = 2;

  /**
   * The number of samples to take after a term before issuing another lease.
   */
  public static final int SAMPLE_SIZE = 3;

  // END TUNING PARAMETERS ///////////////////////////////////////////////////

  /**
   * The popularity cutoff. If the average read interval of an object is above
   * this threshold, then no lease will be issued for that object.
   */
  private static final double MAX_READ_PREP_INTERVAL = MAX_LEASE_LENGTH
    / (K2 * K3);

  private static final double NEG_DECAY_CONSTANT = -Math.log(2) / HALF_LIFE;

  /**
   * The default lease for keys that aren't yet in the table. All warranties in
   * the table should expire after the default lease.
   */
  private volatile V defaultLease;

  private class Entry {
    final K key;
    volatile V leaseIssued;
    SoftReference<Metrics> metrics;

    Entry(K key) {
      this.key = key;
      this.leaseIssued = defaultLease;
      this.metrics = new SoftReference<>(new Metrics());
    }

    synchronized void setLeaseIssued(V lease) {
      this.leaseIssued = lease;
    }

    synchronized boolean replaceLease(V oldLease, V newLease) {
      boolean success = this.leaseIssued.equals(oldLease);
      if (success) {
        Metrics metrics = getMetrics(false);
        if (metrics != null) {
          long expiry = newLease.expiry();
          synchronized (metrics) {
            if (metrics.lastReadPrepareTime > expiry) return true;

            metrics.lastReadPrepareTime = expiry;
            metrics.numReadPrepares = 0;
            metrics.lastLeaseLength = expiry - System.currentTimeMillis();
            this.leaseIssued = newLease;
            return true;
          }
        }

        this.leaseIssued = newLease;
      }
      return success;
    }

    Metrics getMetrics(boolean createIfAbsent) {
      Metrics metrics = this.metrics.get();
      if (metrics != null || !createIfAbsent) return metrics;

      synchronized (this) {
        metrics = this.metrics.get();
        if (metrics != null) return metrics;

        metrics = new Metrics();
        this.metrics = new SoftReference<>(metrics);
        return metrics;
      }
    }

    private class Metrics {
      /**
       * The time, in milliseconds since the epoch, of the last read prepare
       * that occurred outside of a term, or of the expiry of the lease last
       * issued, whichever is later.
       */
      long lastReadPrepareTime;

      /**
       * The length of the last lease issued.
       */
      long lastLeaseLength;

      /**
       * The moving average of (estimated) read intervals, in milliseconds.
       */
      int readInterval;

      /**
       * The time, in milliseconds since the epoch, of the last write prepare.
       */
      long lastWritePrepareTime;

      /**
       * The moving average of write-prepare intervals, in milliseconds.
       */
      int writeInterval;

      /**
       * The number of read prepares occurred since the last term.
       */
      int numReadPrepares;

      public Metrics() {
        final long now = System.currentTimeMillis();

        this.lastReadPrepareTime = now;
        this.lastLeaseLength = 0;
        this.readInterval = Integer.MAX_VALUE;
        this.lastWritePrepareTime = now;
        this.writeInterval = Integer.MAX_VALUE;
        this.numReadPrepares = 0;
      }

      /**
       * Notifies of a read-prepare event.
       */
      synchronized void notifyReadPrepare() {
        long now = System.currentTimeMillis();

        if (lastReadPrepareTime > now) {
          // still in term. Do nothing.
          return;
        }

        // Not during term. Update moving average.
        int curInterval = (int) (now - lastReadPrepareTime);
        lastReadPrepareTime = now;
        numReadPrepares++;

        if (readInterval == Integer.MAX_VALUE) {
          readInterval = curInterval;
        } else {
          final double alpha;
          if (numReadPrepares == 1) {
            // First read prepare after last term.
            alpha = Math.exp(NEG_DECAY_CONSTANT * lastLeaseLength);
          } else {
            alpha = PREPARE_ALPHA;
          }
          readInterval =
              (int) (alpha * readInterval + (1.0 - alpha) * curInterval);
        }
      }

      /**
       * Notifies of a commit event.
       */
      void notifyWriteCommit() {
      }

      /**
       * Notifies of a prepare event.
       */
      synchronized void notifyWritePrepare() {
        Logging.log(HOTOS_LOGGER, Level.FINER, "writing @{0}", key);

        long now = System.currentTimeMillis();
        int curInterval = (int) (now - lastWritePrepareTime);
        lastWritePrepareTime = now;

        if (writeInterval == Integer.MAX_VALUE) {
          writeInterval = curInterval;
        } else {
          writeInterval =
              (int) (PREPARE_ALPHA * writeInterval + (1.0 - PREPARE_ALPHA)
                  * curInterval);
        }
      }

      /**
       * Suggests a lease for the object beyond the given expiry time.
       * 
       * @return the time at which the term should expire.
       */
      long suggestLease(long expiry) {
        // Snapshot state to avoid locking for too long.
        final long readInterval;
        final long writeInterval;
        synchronized (this) {
          // Only continue if we have enough samples since the last lease
          // period.
          if (numReadPrepares < SAMPLE_SIZE) return expiry;

          writeInterval = this.writeInterval;
          readInterval = this.readInterval;
        }

        final int curCount = count++;

        if (readInterval > MAX_READ_PREP_INTERVAL) {
          // The object is too unpopular. Suggest the minimal expiry time.
          if (curCount % 10000 == 0) {
            // onum, readInterval, actualReadInterval, writeInterval, lease
            HOTOS_LOGGER.info("lease #" + curCount + ": " + key + "," +
                readInterval + "," + writeInterval + ",unpopular");
          }
          return expiry;
        }

        double ratio = writeInterval / (readInterval + 1.0);

        if (ratio < K3) {
          // Not enough reads per write. Suggest the minimal expiry time.
          if (curCount % 10000 == 0) {
            // onum, readInterval, actualReadInterval, writeInterval, lease
            HOTOS_LOGGER.info("lease #" + curCount + ": " + key + "," +
                readInterval + "," + writeInterval + ",low read-write");
          }
          return expiry;
        }

        if (curCount % 10000 == 0) {
          // onum, readInterval, actualReadInterval, writeInterval, lease
          HOTOS_LOGGER
              .info("lease #" + curCount + ": " + key + "," + readInterval + ","
                  + writeInterval + "," + (K2 * writeInterval));
        }

        long leaseLength =
            Math.min((long) (K2 * writeInterval), MAX_LEASE_LENGTH);

        if (HOTOS_LOGGER.isLoggable(Level.FINE)) {
          if (key instanceof Number && ((Number) key).longValue() == 0) {
            Logging.log(HOTOS_LOGGER, Level.FINE,
                "onum = {0}, lease length = {1}", key, leaseLength);
          }

          if (writeInterval != Integer.MAX_VALUE) {
            Logging.log(HOTOS_LOGGER, Level.FINE,
                "onum = {0}, lease length = {1}", key, leaseLength);
          }
        }

        return Math.max(expiry, System.currentTimeMillis() + leaseLength);
      }

      synchronized void setReadPrepareTime() {
        lastReadPrepareTime = System.currentTimeMillis();
      }
    }
  }

  private static int count = 0;

  private final ConcurrentMap<K, Entry> table;

  protected LeaseIssuer(V defaultLease) {
    this.table = new ConcurrentHashMap<K, Entry>();
    this.defaultLease = defaultLease;
  }

  private Entry getEntry(K key) {
    Entry existingEntry = table.get(key);
    if (existingEntry != null) return existingEntry;

    Entry entry = new Entry(key);
    existingEntry = table.putIfAbsent(key, entry);
    return existingEntry == null ? entry : existingEntry;
  }

  private Entry.Metrics getMetrics(K key) {
    return getEntry(key).getMetrics(true);
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
      throw new InternalError("Attempted to insert a lease that expires " +
          "before the default lease. This should not happen.");
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
          "Adding lease for {0}; expiry={1} (in {2} ms)", key, expiry,
          length);
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
   * Notifies that a read has been prepared, signalling that perhaps the
   * warranties issued should be longer.
   */
  public void notifyReadPrepare(K key) {
    getMetrics(key).notifyReadPrepare();
  }

  /**
   * Notifies that a write has been committed to the database, allowing new
   * writes to be prepared.
   */
  public void notifyWriteCommit(K key) {
    getMetrics(key).notifyWriteCommit();
  }

  /**
   * Notifies that a write has been prepared, preventing further writes from
   * being prepared until the corresponding transaction either commits or
   * aborts.
   */
  public void notifyWritePrepare(K key) {
    getMetrics(key).notifyWritePrepare();
  }

  /**
   * Suggests an expiry time.
   */
  public Long suggestLease(K key) {
    return suggestLease(key, System.currentTimeMillis());
  }

  /**
   * Suggests an expiry time beyond the given expiry time.
   */
  public long suggestLease(K key, long minExpiry) {
    return getMetrics(key).suggestLease(minExpiry);
  }

  /**
   * Updates the latest read-prepare time for the given object with the current
   * time.
   */
  public void setReadPrepareTime(K key) {
    Entry.Metrics metrics = getEntry(key).getMetrics(false);
    if (metrics == null) return;
    metrics.setReadPrepareTime();
  }
}
