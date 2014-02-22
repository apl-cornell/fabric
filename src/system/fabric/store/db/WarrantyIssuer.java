package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.STORE_DB_LOGGER;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.Warranty;
import fabric.common.exceptions.InternalError;

/**
 * A warranty issuer is notified of write events and read-prepares, and uses
 * this information to decide how long issued warranties should last. It also
 * tracks the latest warranties that have been issued for each object. This
 * class is thread-safe.
 */
public class WarrantyIssuer<K, V extends Warranty> {

  // BEGIN TUNING PARAMETERS /////////////////////////////////////////////////

  /**
   * The base commit latency, in milliseconds.
   */
  private static final long BASE_COMMIT_LATENCY = 250;

  /**
   * The network delay, in milliseconds.
   */
  private static final int NETWORK_LATENCY = 5;

  private static final long TWO_P_N = 2 * BASE_COMMIT_LATENCY * NETWORK_LATENCY;

  /**
   * The minimum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  private static final int MIN_WARRANTY_LENGTH = 1000;

  /**
   * The maximum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  private static final int MAX_WARRANTY_LENGTH = 5000;

  /**
   * The decay rate for the exponential average when calculating the period of
   * read and write prepares. Higher values result in slower adaptation to
   * changes in the prepare rates.
   */
  private static final double PREPARE_ALPHA = .9;

  /**
   * The half life for determining the alpha to use for combining the first
   * prepare interval after a warranty period. The alpha value used is
   * determined by 2^(-L/HALF_LIFE).
   */
  private static final double HALF_LIFE = 5000;

  /**
   * The popularity cutoff. If the interval between consecutive read prepares is
   * above this threshold, then no warranty will be issued for the object.
   */
  private static final int MAX_READ_PREP_INTERVAL = 250;

  // END TUNING PARAMETERS ///////////////////////////////////////////////////

  private static final double NEG_DECAY_CONSTANT = -Math.log(2) / HALF_LIFE;

  /**
   * The default warranty for keys that aren't yet in the table. All warranties
   * in the table should expire after the default warranty.
   */
  private volatile V defaultWarranty;

  private class Entry {
    final K key;
    volatile V warrantyIssued;
    SoftReference<Metrics> metrics;

    Entry(K key) {
      this.key = key;
      this.warrantyIssued = defaultWarranty;
      this.metrics = new SoftReference<>(new Metrics());
    }

    synchronized void setWarrantyIssued(V warranty) {
      this.warrantyIssued = warranty;
    }

    synchronized boolean replaceWarranty(V oldWarranty, V newWarranty) {
      boolean success = this.warrantyIssued.equals(oldWarranty);
      if (success) {
        Metrics metrics = getMetrics(false);
        if (metrics != null) {
          long expiry = newWarranty.expiry();
          synchronized (metrics) {
            if (metrics.lastReadPrepareTime > expiry) return true;

            metrics.lastReadPrepareTime = expiry;
            metrics.numReadPrepares = 0;
            metrics.lastWarrantyLength = expiry - System.currentTimeMillis();
            this.warrantyIssued = newWarranty;
            return true;
          }
        }

        this.warrantyIssued = newWarranty;
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
       * that occurred outside of a warranty period, or of the expiry of the
       * warranty last issued, whichever is later.
       */
      long lastReadPrepareTime;

      /**
       * The length of the last warranty issued.
       */
      long lastWarrantyLength;

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
       * The number of read prepares occurred since the last warranty period.
       */
      int numReadPrepares;

      public Metrics() {
        final long now = System.currentTimeMillis();

        this.lastReadPrepareTime = now;
        this.lastWarrantyLength = 0;
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
          // Warranty still in term. Do nothing.
          return;
        }

        // Not during warranty term. Update moving average.
        int curInterval = (int) (now - lastReadPrepareTime);
        lastReadPrepareTime = now;
        numReadPrepares++;

        if (readInterval == Integer.MAX_VALUE) {
          readInterval = curInterval;
        } else {
          final double alpha;
          if (numReadPrepares == 1) {
            // First read prepare after a warranty period.
            alpha = Math.exp(NEG_DECAY_CONSTANT * lastWarrantyLength);
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
       * Suggests a warranty for the object beyond the given expiry time.
       * 
       * @return the time at which the warranty should expire.
       */
      long suggestWarranty(long expiry) {
        // Snapshot state to avoid locking for too long.
        final long readInterval;
        final long writeInterval;
        synchronized (this) {
          // Only continue if we have enough samples since the last warranty
          // period.
          if (numReadPrepares < 10) return expiry;

          writeInterval = this.writeInterval;
          readInterval = this.readInterval;
        }

        if (readInterval > MAX_READ_PREP_INTERVAL) {
          // The object is too unpopular. Suggest the minimal expiry time.
          return expiry;
        }

        double ratio = writeInterval / (readInterval + 1.0);

        long warrantyLength =
            Math.min((long) Math.sqrt(TWO_P_N * ratio), MAX_WARRANTY_LENGTH);

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

        if (warrantyLength < MIN_WARRANTY_LENGTH) return expiry;

        return Math.max(expiry, System.currentTimeMillis() + warrantyLength);
      }
    }
  }

  private final ConcurrentMap<K, Entry> table;

  protected WarrantyIssuer(V defaultWarranty) {
    this.table = new ConcurrentHashMap<K, Entry>();
    this.defaultWarranty = defaultWarranty;
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
   * @return the issued warranty for the given key.
   */
  final V get(K key) {
    return getEntry(key).warrantyIssued;
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

    boolean success = getEntry(key).replaceWarranty(oldWarranty, newWarranty);
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

    table.get(key).setWarrantyIssued(warranty);
  }

  /**
   * For recovering warranty state from stable storage. Sets the default
   * warranty for onums that don't yet have an entry in the issuer.
   */
  void setDefaultWarranty(V warranty) {
    defaultWarranty = warranty;
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
   * Suggests a warranty-expiry time.
   */
  public Long suggestWarranty(K key) {
    return suggestWarranty(key, System.currentTimeMillis());
  }

  /**
   * Suggests a warranty-expiry time beyond the given expiry time.
   */
  public long suggestWarranty(K key, long minExpiry) {
    return getEntry(key).getMetrics(true).suggestWarranty(minExpiry);
  }
}
