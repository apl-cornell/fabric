package fabric.store.db;

import fabric.common.util.LongKeyCache;

/**
 * A warranty issuer is notified of write events and uses this information to
 * decide how long issued warranties should last.
 */
public class WarrantyIssuer {
  private static class HistoryEntry {
    /**
     * Weight to use when calculating exponential moving average.
     */
    final double alpha;

    /**
     * Time of last commit or prepare request.
     */
    long lastEventTime;

    /**
     * Predicted interval between commit or prepare events.
     */
    int predictedInterval;

    int lastDefaultSuggestion;
    int lastDefaultSuggestionLength;

    /**
     * @param alpha weight for exponential moving average.
     */
    public HistoryEntry(double alpha) {
      this.alpha = alpha;
      this.lastEventTime = 0;
      this.predictedInterval = -1;
      this.lastDefaultSuggestion = 0;
      this.lastDefaultSuggestionLength = 0;
    }

    /**
     * Notifies of a commit event.
     */
    void notifyWriteCommit() {
      lastEventTime = System.currentTimeMillis();
    }

    /**
     * Notifies of a prepare event.
     */
    void notifyWritePrepare() {
      long now = System.currentTimeMillis();

      if (lastEventTime != 0) {
        int interval = (int) (now - lastEventTime);
        if (predictedInterval == -1) {
          predictedInterval = interval;
        } else {
          // Compute exponential moving average.
          predictedInterval =
              (int) (alpha * interval + (1.0 - alpha) * predictedInterval);
        }
      }

      lastEventTime = now;
    }

    long predictWrite() {
      if (predictedInterval == -1) return 0;

      return lastEventTime + predictedInterval;
    }
  }

  /**
   * Weight to use when calculating exponential moving average.
   */
  final double alpha;

  /**
   * The minimum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  protected final int minWarrantyLength;

  /**
   * The maximum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  protected final int maxWarrantyLength;

  /**
   * The amount of time around a predicted write for which no warranties will
   * be issued.
   */
  protected final int writeWindowPadding;

  /**
   * Maps onums to a pair containing the time of the last notified event and
   * the history of prepare intervals. A prepare interval is the length of time
   * between a prepare and its immediately preceding reported event (either a
   * prepare or a commit).
   */
  protected final LongKeyCache<HistoryEntry> history;

  /**
   * @param alpha
   *         predictions are made based on an exponential moving average of the
   *         elapsed time between events. Alpha ranges between 0 and 1, and is
   *         the weight to use when computing this average. Values closer to 1
   *         will bias predictions towards more recent observed behaviour.
   *         
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
  protected WarrantyIssuer(double alpha, int minWarrantyLength,
      int maxWarrantyLength, int writeWindowPadding) {
    this.alpha = alpha;
    this.minWarrantyLength = minWarrantyLength;
    this.maxWarrantyLength = maxWarrantyLength;
    this.writeWindowPadding = writeWindowPadding;
    this.history = new LongKeyCache<HistoryEntry>();

    // Sanity check.
    if (minWarrantyLength > maxWarrantyLength)
      throw new InternalError("minWarrantyLength > maxWarrantyLength");
  }

  private HistoryEntry getEntry(long onum) {
    HistoryEntry entry = history.get(onum);
    if (entry == null) {
      entry = new HistoryEntry(alpha);
      history.put(onum, entry);
    }

    return entry;
  }

  /**
   * Notifies that a write has been committed to the database, allowing new
   * writes to be prepared.
   */
  public synchronized void notifyWriteCommit(long onum) {
    getEntry(onum).notifyWriteCommit();
  }

  /**
   * Notifies that a write has been prepared, preventing further writes from
   * being prepared until the corresponding transaction either commits or
   * aborts.
   */
  public synchronized void notifyWritePrepare(long onum) {
    getEntry(onum).notifyWritePrepare();
  }

  /**
   * Suggests a warranty-expiry time, based on the time at which the next
   * write-prepare is predicted to arrive.
   */
  public Long suggestWarranty(long onum) {
    HistoryEntry entry;
    long now = System.currentTimeMillis();
    long predictedWrite;
    synchronized (this) {
      entry = getEntry(onum);
      predictedWrite = entry.predictWrite();
    }

    // Make sure we are not too close to a predicted write.
    if (now <= predictedWrite + writeWindowPadding) {
      return null;
    }

    if (now < predictedWrite) {
      // Have a predicted write that is in the future. Suggest a warranty.
      long latestPossible = now + maxWarrantyLength;
      long suggestion = predictedWrite - writeWindowPadding;
      if (suggestion > latestPossible) suggestion = latestPossible;
      synchronized (entry) {
        entry.lastDefaultSuggestionLength = 0;
      }
      return suggestion;
    }

    // Predicted write has already passed. Just issue the default warranty.
    synchronized (entry) {
      // If a previously issued default has yet to expire, keep it.
      if (now < entry.lastDefaultSuggestion) return null;

      int length = entry.lastDefaultSuggestionLength * 2;
      if (length < minWarrantyLength) length = minWarrantyLength;
      if (length > maxWarrantyLength) length = maxWarrantyLength;
      entry.lastDefaultSuggestionLength = length;
      return now + length;
    }
  }
}
