package fabric.store.db;

import java.lang.ref.SoftReference;
import java.util.Arrays;

import fabric.common.util.LongKeyCache;

/**
 * A warranty issuer is notified of write events and uses this information to
 * decide how long issued warranties should last.
 */
public class WarrantyIssuer {
  private static class HistoryEntry {
    Long lastEventTime;
    long lastDefaultSuggestion;
    long lastDefaultSuggestionLength;

    SoftReference<PrepareIntervalHistory> history;
    final int maxHistoryLength;

    public HistoryEntry(int maxHistoryLength) {
      this.lastEventTime = null;
      this.lastDefaultSuggestion = 0;
      this.lastDefaultSuggestionLength = 0;

      this.history =
          new SoftReference<PrepareIntervalHistory>(new PrepareIntervalHistory(
              maxHistoryLength));
      this.maxHistoryLength = maxHistoryLength;
    }

    public PrepareIntervalHistory getHistory() {
      PrepareIntervalHistory result = history.get();
      if (result == null) {
        result = new PrepareIntervalHistory(maxHistoryLength);
        history = new SoftReference<PrepareIntervalHistory>(result);
      }
      return result;
    }
  }

  protected static class PrepareIntervalHistory {
    /**
     * Contains the actual interval history data.
     */
    private long[] data;

    /**
     * The number of entries in the history.
     */
    private int size;

    /**
     * Points to the oldest entry in the history. This is also the entry that
     * will be overwritten next.
     */
    private int oldest;

    PrepareIntervalHistory(int maxLength) {
      this.data = new long[maxLength];
      this.size = 0;
      this.oldest = 0;
    }

    public void add(long interval) {
      if (size < data.length) size++;
      data[oldest++] = interval;
      oldest %= data.length;
    }
  }

  /**
   * The maximum history length for each onum.
   */
  protected final int maxHistoryLength;

  /**
   * The minimum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  protected final long minWarrantyLength;

  /**
   * The maximum length of time (in milliseconds) for which each issued warranty
   * should be valid.
   */
  protected final long maxWarrantyLength;

  /**
   * The amount of time around a predicted write for which no warranties will
   * be issued.
   */
  protected final long writeWindowPadding;

  /**
   * Maps onums to a pair containing the time of the last notified event and
   * the history of prepare intervals. A prepare interval is the length of time
   * between a prepare and its immediately preceding reported event (either a
   * prepare or a commit).
   */
  protected final LongKeyCache<HistoryEntry> history;

  /**
   * @param historyLength
   *         the maximum history length for each onum.
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
  protected WarrantyIssuer(int historyLength, long minWarrantyLength,
      long maxWarrantyLength, long writeWindowPadding) {
    this.maxHistoryLength = historyLength;
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
      entry = new HistoryEntry(maxHistoryLength);
      history.put(onum, entry);
    }

    return entry;
  }

  /**
   * Notifies that a write has been committed to the database, allowing new
   * writes to be prepared.
   */
  public synchronized void notifyWriteCommit(long onum) {
    getEntry(onum).lastEventTime = System.currentTimeMillis();
  }

  /**
   * Notifies that a write has been prepared, preventing further writes from
   * being prepared until the corresponding transaction either commits or
   * aborts.
   */
  public synchronized void notifyWritePrepare(long onum) {
    HistoryEntry entry = getEntry(onum);
    long now = System.currentTimeMillis();

    if (entry.lastEventTime != null) {
      long interval = now - entry.lastEventTime;
      entry.getHistory().add(interval);
    }
    entry.lastEventTime = now;
  }

  /**
   * Suggests a warranty-expiry time, based on the time at which the next
   * write-prepare is predicted to arrive.
   */
  public Long suggestWarranty(long onum) {
    HistoryEntry entry;
    long[] predictedWrites;
    synchronized (this) {
      entry = getEntry(onum);
      PrepareIntervalHistory history = entry.getHistory();
      predictedWrites = new long[history.size];
      for (int i = 0; i < predictedWrites.length; i++) {
        predictedWrites[i] = entry.lastEventTime + history.data[i];
      }
    }
    Arrays.sort(predictedWrites);

    long now = System.currentTimeMillis();

    // Make sure we are not too close to a predicted write.
    for (long prediction : predictedWrites) {
      if (now + minWarrantyLength < prediction - writeWindowPadding) {
        // This prediction is far into the future, and so will be the remaining
        // predictions.
        break;
      }

      if (now <= prediction + writeWindowPadding) {
        // Too close to a predicted write.
        return null;
      }
    }

    // Find the next predicted write.
    for (long prediction : predictedWrites) {
      if (now < prediction) {
        // Have a predicted write that is in the future. Suggest a warranty.
        long latestPossible = now + maxWarrantyLength;
        long suggestion = prediction - writeWindowPadding;
        if (suggestion > latestPossible) suggestion = latestPossible;
        synchronized (entry) {
          entry.lastDefaultSuggestionLength = 0;
        }
        return suggestion;
      }
    }

    // No more predicted writes. Just issue the default warranty.
    synchronized (entry) {
      // If a previously issued default has yet to expire, keep it.
      if (now < entry.lastDefaultSuggestion) return null;

      long length = entry.lastDefaultSuggestionLength * 2;
      if (length < minWarrantyLength) length = minWarrantyLength;
      if (length > maxWarrantyLength) length = maxWarrantyLength;
      entry.lastDefaultSuggestionLength = length;
      return now + length;
    }
  }
}
