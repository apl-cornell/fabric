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
  private static final long NETWORK_LATENCY = 1;

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
   * The length of the time window over which to calculate the rate of
   * read and write prepares, in milliseconds.
   */
  private static final long PREPARE_WINDOW_LENGTH = (int) (.467751 * 100000);

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
    V warrantyIssued;
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
      if (success) this.warrantyIssued = newWarranty;
      return success;
    }

    Metrics getMetrics() {
      Metrics metrics = this.metrics.get();
      if (metrics != null) return metrics;

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
       * The start of the current time window over which the rate of read and
       * write prepares is being calculated, in milliseconds since the epoch.
       */
      long windowStartTime;

      /**
       * The number of read prepares observed during the current time window.
       */
      int numReadPrepares;

      /**
       * The accumulated read-prepare rate, as measured before the start of the
       * current window.
       */
      double readPrepareRate;

      /**
       * The number of reads during the current time window.
       */
      int numReads;

      /**
       * The accumulated read rate, as measured before the start of the
       * current window.
       */
      double readRate;

      /**
       * The number of writes during the current time window.
       */
      int numWrites;

      /**
       * The accumulated write rate, as measured before the start of the
       * current window.
       */
      double writeRate;

      long lastReadPrepareTime;

      /**
       * Interval between the last two read prepares.
       */
      long readPrepareInterval;

      public Metrics() {
        final long now = System.currentTimeMillis();

        this.windowStartTime = now;

        this.numReadPrepares = 0;
        this.readPrepareRate = 0.0;

        this.numReads = 0;
        this.readRate = 0.0;
        this.numWrites = 0;
        this.writeRate = 0.0;

        this.lastReadPrepareTime = now;
        this.readPrepareInterval = Long.MAX_VALUE;
      }

      /**
       * Notifies of a read-prepare event.
       */
      synchronized void notifyReadPrepare(long commitTime) {
        fixPrepareWindow();
        numReads++;
        numReadPrepares++;

        long now = System.currentTimeMillis();
        readPrepareInterval = now - lastReadPrepareTime;
        lastReadPrepareTime = now;
      }

      /**
       * Updates the time window over which the read-prepare rate is calculated.
       * 
       * @return the current time, in milliseconds since the epoch.
       */
      synchronized long fixPrepareWindow() {
        final long now = System.currentTimeMillis();

        final long timeElapsed = now - windowStartTime;
        if (timeElapsed < PREPARE_WINDOW_LENGTH) return now;

        // Need to start a new window.
        int numWindowsElapsed = (int) (timeElapsed / PREPARE_WINDOW_LENGTH);
        readPrepareRate =
            readPrepareRate * (1 - PREPARE_ALPHA)
                + ((double) numReadPrepares / PREPARE_WINDOW_LENGTH)
                * PREPARE_ALPHA;
        readRate =
            readRate * (1 - PREPARE_ALPHA)
                + ((double) numReads / PREPARE_WINDOW_LENGTH) * PREPARE_ALPHA;
        writeRate =
            writeRate * (1 - PREPARE_ALPHA)
                + ((double) numWrites / PREPARE_WINDOW_LENGTH) * PREPARE_ALPHA;

        // Account for possibility of more than one window having elapsed.
        for (int i = 1; i < numWindowsElapsed; i++) {
          readPrepareRate *= 1 - PREPARE_ALPHA;
          readRate *= 1 - PREPARE_ALPHA;
          writeRate *= 1 - PREPARE_ALPHA;
        }

        windowStartTime += numWindowsElapsed * PREPARE_WINDOW_LENGTH;
        numReadPrepares = 0;
        numReads = 0;
        numWrites = 0;
        return now;
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

        fixPrepareWindow();
        numWrites++;
      }

      synchronized double getReadWriteRatio() {
        long now = fixPrepareWindow();

        // Use a weighted average of the accumulated prepare rates and the
        // current prepare rates. The weight is PREPARE_ALPHA, adjusted by the
        // age of the current window.
        long windowAge = now - windowStartTime;
        if (windowAge == 0) windowAge = 1;
        double alpha = PREPARE_ALPHA * windowAge / PREPARE_WINDOW_LENGTH;
        double R = readRate * (1 - alpha) + alpha * numReads / windowAge;
        double W = writeRate * (1 - alpha) + alpha * numWrites / windowAge;

        double result = R / (W + Double.MIN_VALUE);

        int curCount = count.incrementAndGet();
        if (curCount % 10000 == 0) {
          double rho =
              readPrepareRate * (1 - alpha) + alpha * numReadPrepares
                  / windowAge;
          double ratio = rho / (W + Double.MIN_VALUE);

          HOTOS_LOGGER.info("warranty #" + curCount + ": 1. onum " + key);
          HOTOS_LOGGER.info("warranty #" + curCount + ": 2. R = " + R);
          HOTOS_LOGGER.info("warranty #" + curCount + ": 3. W = " + W);
          HOTOS_LOGGER.info("warranty #" + curCount + ": 4. rho = " + rho);
          HOTOS_LOGGER.info("warranty #" + curCount + ": 5. L (based on R) = "
              + (long) Math.sqrt(TWO_P_N * result));
          HOTOS_LOGGER.info("warranty #" + curCount
              + ": 6. L (based on rho) = "
              + (long) (2.0 * BASE_COMMIT_LATENCY * ratio));
        }

        return result;
      }

      /**
       * Suggests a warranty for the object beyond the given expiry time.
       * 
       * @return the time at which the warranty should expire.
       */
      long suggestWarranty(long expiry) {
        if (readPrepareInterval > MAX_READ_PREP_INTERVAL) {
          // The object is too unpopular. Suggest the minimal expiry time.
          return expiry;
        }

        double ratio = getReadWriteRatio();

        long warrantyLength =
            Math.min((long) Math.sqrt(TWO_P_N * ratio), MAX_WARRANTY_LENGTH);

        if (HOTOS_LOGGER.isLoggable(Level.FINE)) {
          if (key instanceof Number && ((Number) key).longValue() == 0) {
            Logging.log(HOTOS_LOGGER, Level.FINE,
                "onum = {0}, warranty length = {1}", key, warrantyLength);
          }

          if (writeRate > 0 || numWrites > 0) {
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
        fixPrepareWindow();
        numReads += count;
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
    return getEntry(key).getMetrics();
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
  public void notifyReadPrepare(K key, long commitTime) {
    getMetrics(key).notifyReadPrepare(commitTime);
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
    return getEntry(key).getMetrics().suggestWarranty(minExpiry);
  }

  /**
   * XXX gross hack for nsdi deadline
   */
  void notifyWarrantedReads(K key, int count) {
    getMetrics(key).notifyWarrantedReads(count);
  }
}
