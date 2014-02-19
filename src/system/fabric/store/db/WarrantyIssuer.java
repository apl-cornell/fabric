package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;

import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.util.Cache;

/**
 * A warranty issuer is notified of write events and read-prepares, and uses
 * this information to decide how long issued warranties should last.
 */
public class WarrantyIssuer<K> {

  // BEGIN TUNING PARAMETERS /////////////////////////////////////////////////

  /**
   * The base commit latency, in milliseconds.
   */
  private static final long BASE_COMMIT_LATENCY = 250;

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

  private class Entry {
    final K key;

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
     * The number of write prepares observed during the current time window.
     */
    int numWritePrepares;

    /**
     * The accumulated write-prepare rate, as measured before the start of the
     * current window.
     */
    double writePrepareRate;

    long lastReadPrepareTime;

    /**
     * Interval between the last two read prepares.
     */
    long readPrepareInterval;

    /**
     * A mutex for manipulating the read- and write-prepare metrics.
     */
    final Object prepareMutex;

    public Entry(K key) {
      final long now = System.currentTimeMillis();

      this.key = key;

      this.windowStartTime = now;
      this.numReadPrepares = 0;
      this.readPrepareRate = 0.0;
      this.numWritePrepares = 0;
      this.writePrepareRate = 0.0;
      this.prepareMutex = new Object();

      this.lastReadPrepareTime = now;
      this.readPrepareInterval = Long.MAX_VALUE;
    }

    /**
     * Notifies of a read-prepare event.
     */
    void notifyReadPrepare(long commitTime) {
      synchronized (prepareMutex) {
        fixPrepareWindow();
        numReadPrepares++;

        long now = System.currentTimeMillis();
        readPrepareInterval = now - lastReadPrepareTime;
        lastReadPrepareTime = now;
      }
    }

    /**
     * Updates the time window over which the read-prepare rate is calculated.
     * 
     * @return the current time, in milliseconds since the epoch.
     */
    long fixPrepareWindow() {
      synchronized (prepareMutex) {
        final long now = System.currentTimeMillis();

        final long timeElapsed = now - windowStartTime;
        if (timeElapsed < PREPARE_WINDOW_LENGTH) return now;

        // Need to start a new window.
        int numWindowsElapsed = (int) (timeElapsed / PREPARE_WINDOW_LENGTH);
        readPrepareRate =
            readPrepareRate * (1 - PREPARE_ALPHA)
                + ((double) numReadPrepares / PREPARE_WINDOW_LENGTH)
                * PREPARE_ALPHA;
        writePrepareRate =
            writePrepareRate * (1 - PREPARE_ALPHA)
                + ((double) numWritePrepares / PREPARE_WINDOW_LENGTH)
                * PREPARE_ALPHA;

        // Account for possibility of more than one window having elapsed.
        for (int i = 1; i < numWindowsElapsed; i++) {
          readPrepareRate *= 1 - PREPARE_ALPHA;
          writePrepareRate *= 1 - PREPARE_ALPHA;
        }

        windowStartTime += numWindowsElapsed * PREPARE_WINDOW_LENGTH;
        numReadPrepares = 0;
        numWritePrepares = 0;
        return now;
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
    void notifyWritePrepare() {
      Logging.log(HOTOS_LOGGER, Level.FINER, "writing @{0}", key);

      synchronized (prepareMutex) {
        fixPrepareWindow();
        numWritePrepares++;
      }
    }

    double getReadWritePrepareRatio() {
      synchronized (prepareMutex) {
        long now = fixPrepareWindow();

        // Use a weighted average of the accumulated prepare rates and the
        // current prepare rates. The weight is PREPARE_ALPHA, adjusted by the
        // age of the current window.
        long windowAge = now - windowStartTime;
        if (windowAge == 0) windowAge = 1;
        double alpha = PREPARE_ALPHA * windowAge / PREPARE_WINDOW_LENGTH;
        double rho =
            readPrepareRate * (1 - alpha) + alpha * numReadPrepares / windowAge;
        double W =
            writePrepareRate * (1 - alpha) + alpha * numWritePrepares
                / windowAge;
        return rho / (W + Double.MIN_VALUE);
      }
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

      double ratio = getReadWritePrepareRatio();

      long warrantyLength =
          Math.min((long) (2.0 * BASE_COMMIT_LATENCY * ratio),
              MAX_WARRANTY_LENGTH);

      if (HOTOS_LOGGER.isLoggable(Level.FINE)) {
        if (key instanceof Number && ((Number) key).longValue() == 0) {
          Logging.log(HOTOS_LOGGER, Level.FINE,
              "onum = {0}, warranty length = {1}", key, warrantyLength);
        }

        if (writePrepareRate > 0 || numWritePrepares > 0) {
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
    public void notifyWarrantedReads(int count) {
      numReadPrepares += count;
    }
  }

  /**
   * Maps onums to <code>HistoryEntry</code>s.
   */
  private final Cache<K, Entry> history;

  protected WarrantyIssuer() {
    this.history = new Cache<K, Entry>();
  }

  private Entry getEntry(K key) {
    Entry entry = new Entry(key);
    Entry existingEntry = history.putIfAbsent(key, entry);
    return existingEntry == null ? entry : existingEntry;
  }

  /**
   * Notifies that a read has been prepared, signalling that perhaps the
   * warranties issued should be longer.
   */
  public void notifyReadPrepare(K key, long commitTime) {
    getEntry(key).notifyReadPrepare(commitTime);
  }

  /**
   * Notifies that a write has been committed to the database, allowing new
   * writes to be prepared.
   */
  public void notifyWriteCommit(K key) {
    getEntry(key).notifyWriteCommit();
  }

  /**
   * Notifies that a write has been prepared, preventing further writes from
   * being prepared until the corresponding transaction either commits or
   * aborts.
   */
  public void notifyWritePrepare(K key) {
    getEntry(key).notifyWritePrepare();
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
    return getEntry(key).suggestWarranty(minExpiry);
  }

  /**
   * XXX gross hack for nsdi deadline
   */
  void notifyWarrantedReads(K key, int count) {
    getEntry(key).notifyWarrantedReads(count);
  }
}
