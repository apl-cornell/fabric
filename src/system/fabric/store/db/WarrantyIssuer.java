package fabric.store.db;

import java.util.concurrent.atomic.AtomicBoolean;

import fabric.common.Logging;
import fabric.common.util.LongKeyCache;

/**
 * A warranty issuer is notified of write events and read-prepares, and uses
 * this information to decide how long issued warranties should last.
 */
public class WarrantyIssuer {
  private class HistoryEntry {
    final long onum;

    /**
     * A bit vector indicating write activity. The bit in the 2^n position
     * indicates that a write-prepare was attempted between n and n+1 minutes
     * ago.
     */
    short writeHistory;

    /**
     * A timestamp on the write history. If this is N minutes old, then the
     * write history needs to be left-shifted by N bits to account for the
     * passage of time.
     */
    long writeHistoryTime;

    /**
     * An object for obtaining exclusive access to writeHistory.
     */
    final Object writeHistoryMutex;

    /**
     * The expiry time for the warranty last suggested.
     */
    long lastSuggestionExpiry;

    /**
     * The length of the warranty last suggested.
     */
    int lastSuggestionLength;

    /**
     * The length of the next warranty to be suggested.
     */
    int nextSuggestionLength;

    /**
     * Flag for signalling that the next suggestion length is already double the
     * current suggestion length.
     */
    final AtomicBoolean nextSuggestionDoubled;

    /**
     * Flag for signalling that a read-prepare notification is being processed.
     */
    final AtomicBoolean notifyReadPrepareFlag;

    public HistoryEntry(long onum) {
      this.onum = onum;
      this.writeHistoryTime = System.currentTimeMillis();
      this.writeHistory = 0;
      this.lastSuggestionExpiry = 0;
      this.lastSuggestionLength = minWarrantyLength;
      this.nextSuggestionLength = minWarrantyLength;
      this.nextSuggestionDoubled = new AtomicBoolean(false);
      this.notifyReadPrepareFlag = new AtomicBoolean(false);
      this.writeHistoryMutex = new Object();
    }

    /**
     * Updates the writeHistory as necessary to account for the passage of time.
     */
    private void updateWriteHistory() {
      synchronized (writeHistoryMutex) {
        long now = System.currentTimeMillis();
        int shiftAmount = (int) (now - writeHistoryTime) / 60000;
        if (shiftAmount == 0) return;

        writeHistory <<= shiftAmount;
        writeHistoryTime = now;
      }
    }

    /**
     * Notifies of a read-prepare event.
     */
    void notifyReadPrepare() {
      // Do nothing if the next suggestion has already been doubled or if some
      // other thread is already processing a read-prepare notification.
      if (nextSuggestionDoubled.get() || notifyReadPrepareFlag.getAndSet(true))
        return;

      synchronized (this) {
        Logging.HOTOS_LOGGER.info("read prepare @" + onum
            + " nextSuggestionLength=" + nextSuggestionLength
            + " maxWarrantyLength=" + maxWarrantyLength);
        // Ignore this if we're already issuing the maximum-length warranty.
        if (nextSuggestionLength >= maxWarrantyLength) return;

        // Ignore this if the last-suggested warranty hasn't expired.
        long now = System.currentTimeMillis();
        if (lastSuggestionExpiry > now) {
          Logging.HOTOS_LOGGER.info("last suggestion not yet expired for @"
              + onum);
          return;
        }

        // If this prepare could have been avoided if the last-suggested warranty
        // were twice as long, then double the suggestion length.
        if (lastSuggestionExpiry + lastSuggestionLength > now) {
          nextSuggestionLength = 2 * lastSuggestionLength;
          nextSuggestionDoubled.set(true);
          Logging.HOTOS_LOGGER.info("doubling next suggested length for @"
              + onum);
        } else {
          Logging.HOTOS_LOGGER.info("keeping next suggested length for @"
              + onum);
        }
      }

      notifyReadPrepareFlag.set(false);
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
      synchronized (writeHistoryMutex) {
        updateWriteHistory();
        writeHistory |= 0x0001;
        Logging.HOTOS_LOGGER.info("writing @" + onum);
      }
    }

    /**
     * Suggests a warranty for the object beyond the given expiry time.
     * 
     * @return the time at which the warranty should expire.
     */
    synchronized Long suggestWarranty(long expiry) {
      int writeHistory;
      synchronized (writeHistoryMutex) {
        updateWriteHistory();
        writeHistory = this.writeHistory;
      }

      // Use the writeHistory's Hamming weight to determine the maximum length
      // of the suggested warranty.
      int weight = Integer.bitCount(writeHistory & 0xffff);
      Logging.HOTOS_LOGGER.info("@" + onum + " writeHistory=" + writeHistory
          + " weight=" + weight);
      int maxSuggestionLength = maxWarrantyLength >>> weight;
      Logging.HOTOS_LOGGER.info("@" + onum + " maxSuggestionLength="
          + maxSuggestionLength);
      if (maxSuggestionLength < minWarrantyLength) {
        // Writes occurring too frequently.
        lastSuggestionExpiry = 0;
        lastSuggestionLength = minWarrantyLength;
        nextSuggestionLength = minWarrantyLength;
        nextSuggestionDoubled.set(false);
        return null;
      }

      lastSuggestionLength =
          (nextSuggestionLength < maxSuggestionLength) ? nextSuggestionLength
              : maxSuggestionLength;
      Logging.HOTOS_LOGGER.info("@" + onum + " suggesting "
          + lastSuggestionLength);
      lastSuggestionExpiry = expiry + lastSuggestionLength;
      nextSuggestionDoubled.set(false);
      return lastSuggestionExpiry;
    }
  }

  /**
   * The minimum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  private final int minWarrantyLength;

  /**
   * The maximum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  private final int maxWarrantyLength;

  /**
   * Maps onums to <code>HistoryEntry</code>s.
   */
  private final LongKeyCache<HistoryEntry> history;

  /**
   * @param minWarrantyLength
   *         the minimum length of time (in milliseconds) for which each issued
   *         warranty should be valid.
   *         
   * @param maxWarrantyLength
   *         the maximum length of time (in milliseconds) for which each issued
   *         warranty should be valid.
   *         
   * @param writeWindowPadding
   *         the amount of time around a predicted write for which no warranties
   *         will be issued.
   */
  protected WarrantyIssuer(int minWarrantyLength, int maxWarrantyLength,
      int writeWindowPadding) {
    this.minWarrantyLength = minWarrantyLength;
    this.maxWarrantyLength = maxWarrantyLength;
    this.history = new LongKeyCache<HistoryEntry>();

    // Sanity check.
    if (minWarrantyLength > maxWarrantyLength)
      throw new InternalError("minWarrantyLength > maxWarrantyLength");
  }

  private HistoryEntry getEntry(long onum) {
    HistoryEntry entry = new HistoryEntry(onum);
    HistoryEntry existingEntry = history.putIfAbsent(onum, entry);
    return existingEntry == null ? entry : existingEntry;
  }

  /**
   * Notifies that a read has been prepared, signalling that perhaps the
   * warranties issued should be longer.
   */
  public void notifyReadPrepare(long onum) {
    getEntry(onum).notifyReadPrepare();
  }

  /**
   * Notifies that a write has been committed to the database, allowing new
   * writes to be prepared.
   */
  public void notifyWriteCommit(long onum) {
    getEntry(onum).notifyWriteCommit();
  }

  /**
   * Notifies that a write has been prepared, preventing further writes from
   * being prepared until the corresponding transaction either commits or
   * aborts.
   */
  public void notifyWritePrepare(long onum) {
    getEntry(onum).notifyWritePrepare();
  }

  /**
   * Suggests a warranty-expiry time.
   */
  public Long suggestWarranty(long onum) {
    return suggestWarranty(onum, System.currentTimeMillis());
  }

  /**
   * Suggests a warranty-expiry time beyond the given expiry time.
   */
  public long suggestWarranty(long onum, long minExpiry) {
    Long suggestion = getEntry(onum).suggestWarranty(minExpiry);
    return suggestion == null ? minExpiry : suggestion;
  }
}
