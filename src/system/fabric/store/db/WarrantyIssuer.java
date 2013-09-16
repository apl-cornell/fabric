package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;

import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.PIDController;
import fabric.common.Warranty;
import fabric.common.util.Cache;

/**
 * A warranty issuer is notified of write events and read-prepares, and uses
 * this information to decide how long issued warranties should last.
 */
public class WarrantyIssuer<K> {

  // BEGIN TUNING PARAMETERS /////////////////////////////////////////////////

  /**
   * Parameter for low-pass filtering the error derivatives in the PID
   * controller. Higher values result in more filtering.
   */
  private static final double PID_DERIV_ALPHA = 0;

  /**
   * Proportional gain for the PID controller.
   */
  private static final double PID_KP = 0.135132 * 100;

  /**
   * Integral gain for the PID controller.
   */
  private static final double PID_KI = 0 * 100;

  /**
   * Derivative gain for the PID controller.
   */
  private static final double PID_KD = 0 * 100;

  /**
   * The base commit latency, in milliseconds.
   */
  private static final long BASE_COMMIT_LATENCY = (int) (0.118652 * 125);

  /**
   * The minimum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  private static final int MIN_WARRANTY_LENGTH = 250;

  /**
   * The maximum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  private static final int MAX_WARRANTY_LENGTH = (int) (0.641968 * 10000);

  /**
   * The decay rate for the exponential average when calculating the rate of
   * read prepares. Lower values result in slower adaptation to changes in
   * the read-prepare rate.
   */
  private static final double READ_PREPARE_ALPHA = 0.149780;

  /**
   * The length of the time window over which to calculate the rate of
   * read prepares, in milliseconds.
   */
  private static final long READ_PREPARE_WINDOW_LENGTH = 1000;

  // END TUNING PARAMETERS ///////////////////////////////////////////////////

  private class Entry {
    final K key;

    /**
     * The time at which this object was created, in milliseconds since the
     * epoch.
     */
    final long createTime;

    /**
     * The total accumulated time spent in write delays, in milliseconds.
     */
    long accumWriteDelayTime;

    /**
     * Tracks the latest warranty-expiry time that delayed a write prepare.
     */
    long lastWriteDelayExpiryTime;

    /**
     * A mutex for manipulating the write-delay metrics.
     */
    final Object writeDelayMutex;

    /**
     * The start of the current time window over which the rate of read prepares
     * is being calculated, in milliseconds since the epoch.
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
     * A mutex for manipulating the read-prepare metrics.
     */
    final Object readPrepareMutex;

    /**
     * The PID controller for suggesting warranty lengths.
     */
    final PIDController pidController;

    public Entry(K key) {
      final long now = System.currentTimeMillis();

      this.key = key;
      this.createTime = now;
      this.accumWriteDelayTime = 0;
      this.lastWriteDelayExpiryTime = 0;
      this.writeDelayMutex = new Object();

      this.windowStartTime = now;
      this.numReadPrepares = 0;
      this.readPrepareRate = 0.0;
      this.readPrepareMutex = new Object();

      this.pidController = new PIDController(PID_KP, PID_KI, PID_KD);
      this.pidController.setAlpha(PID_DERIV_ALPHA);
      this.pidController.setOutputRange(0, MAX_WARRANTY_LENGTH);
      this.pidController.setSetpoint(BASE_COMMIT_LATENCY);
    }

    /**
     * Notifies of a read-prepare event.
     */
    void notifyReadPrepare(long commitTime) {
      synchronized (readPrepareMutex) {
        fixReadPrepareWindow();
        numReadPrepares++;
      }
    }

    /**
     * Updates the time window over which the read-prepare rate is calculated.
     * 
     * @return the current time, in milliseconds since the epoch.
     */
    long fixReadPrepareWindow() {
      synchronized (readPrepareMutex) {
        final long now = System.currentTimeMillis();

        final long timeElapsed = now - windowStartTime;
        if (timeElapsed < READ_PREPARE_WINDOW_LENGTH) return now;

        // Need to start a new window.
        int numWindowsElapsed =
            (int) (timeElapsed / READ_PREPARE_WINDOW_LENGTH);
        readPrepareRate =
            readPrepareRate * (1 - READ_PREPARE_ALPHA)
                + ((double) numReadPrepares / READ_PREPARE_WINDOW_LENGTH)
                * READ_PREPARE_ALPHA;

        // Account for possibility of more than one window having elapsed.
        for (int i = 1; i < numWindowsElapsed; i++) {
          readPrepareRate *= 1 - READ_PREPARE_ALPHA;
        }

        windowStartTime += numWindowsElapsed * READ_PREPARE_WINDOW_LENGTH;
        numReadPrepares = 0;
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
    void notifyWritePrepare(Warranty warranty) {
      Logging.log(HOTOS_LOGGER, Level.FINER, "writing @{0}", key);
      HOTOS_LOGGER.log(Level.INFO, "writing {0}, warranty expires in {1} ms",
          new Object[] { key, warranty.expiry() - System.currentTimeMillis() });

      synchronized (writeDelayMutex) {
        long warrantyExpiry = warranty.expiry();
        if (warrantyExpiry <= lastWriteDelayExpiryTime) return;

        long now = System.currentTimeMillis();

        // Calculate accumulated write-delay time.
        if (lastWriteDelayExpiryTime <= now) {
          accumWriteDelayTime += Math.max(0, warrantyExpiry - now);
        } else {
          // Previous warranty hasn't expired yet. Assume previous prepare was
          // aborted and just add the extra delay incurred by the newer
          // warranty.
          accumWriteDelayTime += warrantyExpiry - lastWriteDelayExpiryTime;
        }

        lastWriteDelayExpiryTime = warrantyExpiry;
      }
    }

    double getReadPrepareRate() {
      synchronized (readPrepareMutex) {
        long now = fixReadPrepareWindow();

        // Return a weighted average of the accumulated read-prepare rate and
        // the current read-prepare rate. The weight is READ_PREPARE_ALPHA,
        // adjusted by the age of the current window.
        long windowAge = now - windowStartTime;
        if (windowAge == 0) windowAge = 1;
        double alpha =
            READ_PREPARE_ALPHA * windowAge / READ_PREPARE_WINDOW_LENGTH;
        return readPrepareRate * (1 - alpha) + alpha * numReadPrepares
            / windowAge;
      }
    }

    double getWriteDelayFactor() {
      synchronized (writeDelayMutex) {
        long now = System.currentTimeMillis();
        if (now == createTime) return 0;

        return (double) accumWriteDelayTime / (now - createTime);
      }
    }

    /**
     * Suggests a warranty for the object beyond the given expiry time.
     * 
     * @return the time at which the warranty should expire.
     */
    Long suggestWarranty(long expiry) {
      double rho = getReadPrepareRate() + Double.MIN_VALUE;
      double omega = getWriteDelayFactor();

      double input = omega / rho;
      long warrantyLength = (long) pidController.setInput(input);

      if (key instanceof Number && ((Number) key).longValue() == 0) {
        HOTOS_LOGGER.log(Level.INFO,
            "onum = {0}, pid output = {2}, pid input = {1}", new Object[] {
                key, input, warrantyLength });
      }

      if (accumWriteDelayTime > 0)
        HOTOS_LOGGER.log(Level.INFO,
            "onum = {0}, pid output = {2}, pid input = {1}", new Object[] {
                key, input, warrantyLength });

      if (warrantyLength < MIN_WARRANTY_LENGTH) return null;

      return Math.max(expiry, System.currentTimeMillis()) + warrantyLength;
    }
  }

  /**
   * Maps keys to <code>HistoryEntry</code>s.
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
    Long suggestion = getEntry(key).suggestWarranty(minExpiry);
    return suggestion == null ? minExpiry : suggestion;
  }
}
