package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;

import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.util.Cache;
import fabric.common.util.Oid;
import fabric.lang.security.Principal;

/**
 * Table of read and write metrics for keys K.  This information is used for
 * issuing logic for optimizations like warranties. This class is thread-safe.
 */
public class AccessMetrics<K> {

  // BEGIN TUNING PARAMETERS /////////////////////////////////////////////////

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
   * The number of samples to take after a warranty period before issuing
   * another warranty.
   */
  public static final int SAMPLE_SIZE = 3;

  // END TUNING PARAMETERS ///////////////////////////////////////////////////

  private static final double NEG_DECAY_CONSTANT = -Math.log(2) / HALF_LIFE;

  public class Metrics {
    /**
     * The time, in milliseconds since the epoch, of the last read prepare
     * that occurred outside of a warranty period, or of the expiry of the
     * warranty last issued, whichever is later.
     */
    private long lastReadPrepareTime;

    /**
     * The length of the last warranty issued.
     */
    private long lastWarrantyLength;

    /**
     * The moving average of (estimated) read intervals, in milliseconds.
     */
    private int readInterval;

    /**
     * The time, in milliseconds since the epoch, of the last write prepare.
     */
    private long lastWritePrepareTime;

    /**
     * The moving average of write-prepare intervals, in milliseconds.
     */
    private int writeInterval;

    /**
     * The number of read prepares occurred since the last warranty period.
     */
    private int numReadPrepares;

    /**
     * The oid of the principal of the writer of this object, if there is only
     * one.  This is null if either there have been no writers or there is more
     * than one.
     */
    private Oid writer;

    /**
     * True if the object has been written since the beginning of the most
     * recent term.
     */
    private boolean writtenSinceTerm;

    public Metrics() {
      final long now = System.currentTimeMillis();

      this.lastReadPrepareTime = now;
      this.lastWarrantyLength = 0;
      this.readInterval = Integer.MAX_VALUE;
      this.lastWritePrepareTime = now;
      this.writeInterval = Integer.MAX_VALUE;
      this.numReadPrepares = 0;
      this.writer = null;
      this.writtenSinceTerm = false;
    }

    // Accessor methods.  Don't make the fields modifiable outside this class.

    /**
     * @return the lastReadPrepareTime
     */
    public long getLastReadPrepareTime() {
      return lastReadPrepareTime;
    }

    /**
     * @return the lastWarrantyLength
     */
    public long getLastWarrantyLength() {
      return lastWarrantyLength;
    }

    /**
     * @return the readInterval
     */
    public int getReadInterval() {
      return readInterval;
    }

    /**
     * @return the lastWritePrepareTime
     */
    public long getLastWritePrepareTime() {
      return lastWritePrepareTime;
    }

    /**
     * @return the writeInterval
     */
    public int getWriteInterval() {
      return writeInterval;
    }

    /**
     * @return the numReadPrepares
     */
    public int getNumReadPrepares() {
      return numReadPrepares;
    }

    /**
     * @return the writer
     */
    public Oid getWriter() {
      return writer;
    }

    /**
     * @return the writtenSinceTerm
     */
    public boolean isWrittenSinceTerm() {
      return writtenSinceTerm;
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
    synchronized void notifyWritePrepare(Principal client) {
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

      // Update writer tracking.
      Oid clientOid = new Oid(client);
      if (writtenSinceTerm && writer != clientOid) {
        // If it was already null, this is redundant.
        writer = null;
      } else if (!writtenSinceTerm) {
        writer = clientOid;
        writtenSinceTerm = true;
      }
    }

    synchronized void setReadPrepareTime() {
      lastReadPrepareTime = System.currentTimeMillis();
    }

    /**
     * Update the metrics given that there is a new term starting (so reads
     * won't be measured and will be estimated at the end).
     *
     * @param expiry Expiration time of the term in milliseconds since the
     * epoch.
     * @return True if the term was extended, false otherwise.
     */
    synchronized boolean updateTerm(long expiry) {
      // We already issued something for a longer term, nothing to do.
      if (lastReadPrepareTime > expiry) return false;

      if (numReadPrepares == 0 &&
          lastReadPrepareTime > System.currentTimeMillis()) {
        // We are extending a warranty term that is still active.
        long delta = expiry - lastReadPrepareTime;
        lastWarrantyLength += delta;
      } else {
        // Replacing an inactive warranty term.
        lastWarrantyLength = expiry - System.currentTimeMillis();
        // Reset writer checking.
        writtenSinceTerm = false;
        writer = null;
      }

      lastReadPrepareTime = expiry;
      numReadPrepares = 0;
      return true;
    }
  }

  private final Cache<K, Metrics> table;

  protected AccessMetrics() {
    this.table = new Cache<>();
  }

  /**
   * Get the metrics object for a given key.  Optionally create an entry if
   * there wasn't one found.
   *
   * @param key The item we are looking up the Metrics for.
   * @param createIfAbsent If true, make an entry for the key if there wasn't a
   * preexisting Metrics object.
   */
  public Metrics getMetrics(K key, boolean createIfAbsent) {
    if (createIfAbsent) table.putIfAbsent(key, new Metrics());
    return table.get(key);
  }

  /**
   * Get the metrics object for a given key.  Create an entry if there wasn't
   * one found.
   *
   * @param key The item we are looking up the Metrics for.
   */
  public Metrics getMetrics(K key) {
    return getMetrics(key, true);
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
  public void notifyWritePrepare(K key, Principal client) {
    Logging.log(HOTOS_LOGGER, Level.FINER, "writing @{0}", key);
    getMetrics(key).notifyWritePrepare(client);
  }

  /**
   * Updates the latest read-prepare time for the given object with the current
   * time.
   */
  public void setReadPrepareTime(K key) {
    Metrics metrics = getMetrics(key, false);
    if (metrics == null) return;
    metrics.setReadPrepareTime();
  }
}
