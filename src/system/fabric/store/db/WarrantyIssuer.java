package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.STORE_DB_LOGGER;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
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
  public static final int MAX_WARRANTY_LENGTH = (int) (2.698060 * 10000);

  /**
   * The decay rate for the exponential average when calculating the rate of
   * read and write prepares. Lower values result in slower adaptation to
   * changes in the prepare rates.
   */
  private static final double PREPARE_ALPHA = .466681;

  /**
   * The popularity cutoff. If the interval between consecutive read prepares is
   * above this threshold, then no warranty will be issued for the object.
   */
  private static final int MAX_READ_PREP_INTERVAL = 250;

  // END TUNING PARAMETERS ///////////////////////////////////////////////////

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
        long now = System.currentTimeMillis();
        if (!oldWarranty.expiresAfterStrict(now)) {
          // Old warranty is expired, so this is the start of a new warranty
          // period. Update the warranty start time.
          Metrics metrics = getMetrics(false);
          if (metrics != null) {
            synchronized (metrics) {
              metrics.foldWarrantyPeriod(now);
              metrics.warrantyStartTime = now;
              this.warrantyIssued = newWarranty;
              return true;
            }
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
       * The start of the current warranty period, in milliseconds since the
       * epoch.
       */
      long warrantyStartTime;

      /**
       * The number of read prepares observed during the current warranty period.
       */
      int numReadPrepares;

      /**
       * The time, in milliseconds since the epoch, of the last read prepare
       * that occurred outside of a warranty period.
       */
      long lastReadPrepareTime;

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

      long lastActualReadTime;
      int actualReadInterval;

      public Metrics() {
        final long now = System.currentTimeMillis();

        this.warrantyStartTime = now;

        this.numReadPrepares = 0;
        this.lastReadPrepareTime = now;
        this.readInterval = Integer.MAX_VALUE;
        this.lastWritePrepareTime = now;
        this.writeInterval = Integer.MAX_VALUE;

        this.lastActualReadTime = now;
        this.actualReadInterval = Integer.MAX_VALUE;
      }

      /**
       * If there have been no read prepares since the end of the last warranty
       * period, then the reads counted during the warranty period is folded
       * into readPrepareInterval, and the lastReadPrepareTime is set to the end
       * of the warranty period. This method does nothing if the warranty period
       * has not elapsed, if read prepares have been recorded since its end, or
       * if no events occurred during the warranty period.
       * 
       * @param now the current time.
       */
      synchronized void foldWarrantyPeriod(long now) {
        if (numReadPrepares == 0) return;

        long warrantyExpiry = warrantyIssued.expiry();
        if (warrantyExpiry > now) {
          // In warranty.
          return;
        }

        if (lastReadPrepareTime >= warrantyExpiry) {
          // Events have occurred since warranty expiry.
          return;
        }

        // Estimated average read interval during warranty period.
        // XXX this seems like it will always be tiny
        double curInterval = (double) NETWORK_LATENCY / numReadPrepares;

        // Fold into readPrepareInterval.
        if (readInterval == Integer.MAX_VALUE) {
          readInterval = (int) curInterval;
        } else {
          readInterval =
              (int) ((1.0 - PREPARE_ALPHA) * readInterval + PREPARE_ALPHA
                  * curInterval);
        }

        // Finish up.
        numReadPrepares = 0;
        lastReadPrepareTime = warrantyExpiry;
      }

      /**
       * Notifies of a read-prepare event.
       */
      synchronized void notifyReadPrepare() {
        long now = System.currentTimeMillis();
        long warrantyExpiry = warrantyIssued.expiry();
        foldWarrantyPeriod(now);

        {
          int curInterval = (int) (now - lastActualReadTime);
          lastActualReadTime = now;

          if (actualReadInterval == Integer.MAX_VALUE) {
            actualReadInterval = curInterval;
          } else {
            actualReadInterval =
                (int) ((1.0 - PREPARE_ALPHA) * actualReadInterval + PREPARE_ALPHA
                    * curInterval);
          }
        }

        if (now < warrantyExpiry) {
          // Warranty still in term. To account for long periods of inactivity
          // before the start of the warranty, the first event during the
          // warranty period counts as occurring outside the warranty.
          if (lastReadPrepareTime >= warrantyStartTime) {
            numReadPrepares++;
            return;
          }
        }

        // Not during warranty term. Roll directly into moving average.
        int curInterval = (int) (now - lastReadPrepareTime);
        lastReadPrepareTime = now;

        if (readInterval == Integer.MAX_VALUE) {
          readInterval = curInterval;
        } else {
          readInterval =
              (int) ((1.0 - PREPARE_ALPHA) * readInterval + PREPARE_ALPHA
                  * curInterval);
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
              (int) ((1.0 - PREPARE_ALPHA) * writeInterval + PREPARE_ALPHA
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
        final long actualReadInterval;
        synchronized (this) {
          writeInterval = this.writeInterval;
          actualReadInterval = this.actualReadInterval;

          // Fudge read interval in case it's been a while since the last read
          // prepare. Incorporate a phantom event if its interval is greater
          // than the current readInterval value. This event occurs now or, if
          // we're in a warranty period, at the start of the warranty.
          final long now = System.currentTimeMillis();
          final long phantomEventTime;
          if (warrantyIssued.expiresAfterStrict(now)) {
            phantomEventTime = warrantyStartTime;
          } else {
            phantomEventTime = now;
          }

          final long phantomEventInterval = now - phantomEventTime;

          if (this.readInterval == Integer.MAX_VALUE) {
            readInterval = phantomEventInterval;
          } else if (lastReadPrepareTime + this.readInterval < phantomEventTime) {
            readInterval =
                (long) ((1.0 - PREPARE_ALPHA) * this.readInterval + PREPARE_ALPHA
                    * phantomEventInterval);
          } else {
            readInterval = this.readInterval;
          }
        }

        if (readInterval > MAX_READ_PREP_INTERVAL) {
          // The object is too unpopular. Suggest the minimal expiry time.
          return expiry;
        }

        double ratio = writeInterval / (readInterval + Double.MIN_VALUE);

        int curCount = count.incrementAndGet();
        if (curCount % 10000 == 0) {
          HOTOS_LOGGER.info("warranty #" + curCount + ": 1. onum " + key);
          HOTOS_LOGGER.info("warranty #" + curCount + ": 2. readInterval = "
              + this.readInterval);
          HOTOS_LOGGER.info("warranty #" + curCount
              + ": 3. actualReadInterval = " + this.actualReadInterval);
          HOTOS_LOGGER.info("warranty #" + curCount + ": 4. L (est) = "
              + (long) Math.sqrt(TWO_P_N * ratio));
          HOTOS_LOGGER.info("warranty #"
              + curCount
              + ": 5. L (act) = "
              + (long) Math.sqrt(TWO_P_N * writeInterval
                  / (actualReadInterval + Double.MIN_VALUE)));
        }

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

      /**
       * XXX gross hack for nsdi deadline
       */
      public synchronized void notifyWarrantedReads(int count) {
        long now = System.currentTimeMillis();
        double curInterval = (double) (now - lastActualReadTime) / count;
        lastActualReadTime = now;

        if (actualReadInterval == Integer.MAX_VALUE) {
          actualReadInterval = (int) curInterval;
        } else {
          actualReadInterval =
              (int) ((1.0 - PREPARE_ALPHA) * actualReadInterval + PREPARE_ALPHA
                  * curInterval);
        }
      }
    }
  }

  static final AtomicInteger count = new AtomicInteger(0);

  private final ConcurrentMap<K, Entry> table;

  protected WarrantyIssuer(V defaultWarranty) {
    this.table = new ConcurrentHashMap<K, Entry>();
    this.defaultWarranty = defaultWarranty;
  }

  private Entry getEntry(K key) {
    Entry entry = new Entry(key);
    Entry existingEntry = table.putIfAbsent(key, entry);
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

  /**
   * XXX gross hack for nsdi deadline
   */
  void notifyWarrantedReads(K key, int count) {
    getMetrics(key).notifyWarrantedReads(count);
  }
}
