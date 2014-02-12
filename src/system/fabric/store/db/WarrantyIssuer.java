package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;

import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.Warranty;
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
  private static final long BASE_COMMIT_LATENCY = (int) (0.236450 * 1000);

  /**
   * The minimum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  private static final int MIN_WARRANTY_LENGTH = 250;

  /**
   * The maximum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  private static final int MAX_WARRANTY_LENGTH = (int) (0.683228 * 10000);

  /**
   * The decay rate for the exponential average when calculating the rate of
   * read and write prepares. Lower values result in slower adaptation to
   * changes in the prepare rates.
   */
  private static final double PREPARE_ALPHA = .698486;

  /**
   * The length of the time window over which to calculate the rate of
   * read and write prepares, in milliseconds.
   */
  private static final long PREPARE_WINDOW_LENGTH = (int) (0.848755 * 100000);

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

    long warrantyFreeWindowStartTime;
    int numWarrantyFreeReadPrepares;
    double warrantyFreeReadPrepareRate;

    public Entry(K key) {
      final long now = System.currentTimeMillis();

      this.key = key;

      this.windowStartTime = now;
      this.numReadPrepares = 0;
      this.readPrepareRate = 0.0;
      this.numWritePrepares = 0;
      this.writePrepareRate = 0.0;

      this.warrantyFreeWindowStartTime = now;
      this.numWarrantyFreeReadPrepares = 0;
      this.warrantyFreeReadPrepareRate = 0.0;
    }

    /**
     * Notifies of a read-prepare event.
     */
    synchronized void notifyReadPrepare(long commitTime) {
      fixPrepareWindow();
      numReadPrepares++;

      if (System.currentTimeMillis() >= warrantyFreeWindowStartTime)
        numWarrantyFreeReadPrepares++;
    }

    /**
     * Updates the time window over which the read-prepare rate is calculated.
     * 
     * @return the current time, in milliseconds since the epoch.
     */
    synchronized long fixPrepareWindow() {
      final long now = System.currentTimeMillis();
      fixWarrantyFreePrepareWindow(now);

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

    synchronized void fixWarrantyFreePrepareWindow(long now) {
      final long timeElapsed = now - warrantyFreeWindowStartTime;
      if (timeElapsed < PREPARE_WINDOW_LENGTH) return;

      // Need to start a new window.
      int numWindowsElapsed = (int) (timeElapsed / PREPARE_WINDOW_LENGTH);
      warrantyFreeReadPrepareRate =
          warrantyFreeReadPrepareRate * (1 - PREPARE_ALPHA)
              + ((double) numWarrantyFreeReadPrepares / PREPARE_WINDOW_LENGTH)
              * PREPARE_ALPHA;

      // Account for possibility of more than one window having elapsed.
      for (int i = 1; i < numWindowsElapsed; i++) {
        warrantyFreeReadPrepareRate *= 1 - PREPARE_ALPHA;
      }

      numWarrantyFreeReadPrepares = 0;
    }

    /**
     * Notifies of a commit event.
     */
    void notifyWriteCommit() {
    }

    /**
     * Notifies of a prepare event.
     */
    void notifyWritePrepare(Warranty warranty) {
      Logging.log(HOTOS_LOGGER, Level.FINER, "writing @{0}", key);
      if (HOTOS_LOGGER.isLoggable(Level.FINE)) {
        Logging.log(HOTOS_LOGGER, Level.FINE,
            "writing {0}, warranty expires in {1} ms", key, warranty.expiry()
                - System.currentTimeMillis());
      }

      synchronized (this) {
        fixPrepareWindow();
        numWritePrepares++;
      }
    }

    synchronized double getReadWritePrepareRatio() {
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
          writePrepareRate * (1 - alpha) + alpha * numWritePrepares / windowAge;
      return rho / (W + Double.MIN_VALUE);
    }

    /**
     * Suggests a warranty for the object beyond the given expiry time.
     * 
     * @return the time at which the warranty should expire.
     */
    long suggestWarranty(long expiry) {
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

      if (warrantyLength < MIN_WARRANTY_LENGTH) {
        setWarrantyFreeWindowStartTime(expiry);
        return expiry;
      }

      // Make sure the object is popular enough for a warranty.
      synchronized (this) {
        final long result;
        if (getWarrantyFreeReadPrepareRate() > 1.0) {
          result =
              Math.max(expiry, System.currentTimeMillis()) + warrantyLength;
        } else {
          result = expiry;
        }

        setWarrantyFreeWindowStartTime(result);
        return result;
      }
    }

    synchronized double getWarrantyFreeReadPrepareRate() {
      long now = System.currentTimeMillis();
      fixWarrantyFreePrepareWindow(now);

      // Use a weighted average of the accumulated prepare rate and the current
      // prepare rate. The weight is PREPARE_ALPHA, adjusted by the age of the
      // current window.
      long windowAge = now - warrantyFreeWindowStartTime;
      if (windowAge == 0) windowAge = 1;
      double alpha = PREPARE_ALPHA * windowAge / PREPARE_WINDOW_LENGTH;
      return warrantyFreeReadPrepareRate * (1 - alpha) + alpha
          * numWarrantyFreeReadPrepares / windowAge;
    }

    synchronized void setWarrantyFreeWindowStartTime(long time) {
      if (warrantyFreeWindowStartTime > time) return;

      warrantyFreeWindowStartTime = 0;
      numWarrantyFreeReadPrepares = 0;
      warrantyFreeReadPrepareRate = 0.0;
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
   * 
   * @param warranty the warranty that existed on the given key when the write
   *          was prepared.
   */
  public void notifyWritePrepare(K key, Warranty warranty) {
    getEntry(key).notifyWritePrepare(warranty);
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
}
