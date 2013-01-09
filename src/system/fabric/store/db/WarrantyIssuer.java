package fabric.store.db;

import java.util.Arrays;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;

/**
 * A warranty issuer is notified of write events and uses this information to
 * decide how long issued warranties should last.
 */
public class WarrantyIssuer {
  private static class HistoryEntry extends Pair<Long, PrepareIntervalHistory> {
    public HistoryEntry(int maxHistoryLength) {
      super(null, new PrepareIntervalHistory(maxHistoryLength));
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
   * If there is insufficient data to make a prediction on the next write
   * prepare, the issued warranty will be valid for this amount of time (in
   * milliseconds).
   */
  protected final long defaultWarrantyLength;

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
  protected final LongKeyMap<HistoryEntry> history;

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
   * @param defaultWarrantyLength
   *         if there is insufficient data to make a prediction, the issued
   *         warranty will be valid for this amount of time (in milliseconds).
   *         
   * @param writeWindowPadding
   *         the amount of time around a predicted write for which no warranties
   *         will be issued.
   */
  protected WarrantyIssuer(int historyLength, long minWarrantyLength,
      long maxWarrantyLength, long defaultWarrantyLength,
      long writeWindowPadding) {
    this.maxHistoryLength = historyLength;
    this.minWarrantyLength = minWarrantyLength;
    this.maxWarrantyLength = maxWarrantyLength;
    this.defaultWarrantyLength = defaultWarrantyLength;
    this.writeWindowPadding = writeWindowPadding;
    this.history = new LongKeyHashMap<HistoryEntry>();

    // Sanity check.
    if (defaultWarrantyLength < minWarrantyLength)
      throw new InternalError("defaultWarrantyLength < minWarrantyLength");
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
    getEntry(onum).first = System.currentTimeMillis();
  }

  /**
   * Notifies that a write has been prepared, preventing further writes from
   * being prepared until the corresponding transaction either commits or
   * aborts.
   */
  public synchronized void notifyWritePrepare(long onum) {
    HistoryEntry entry = getEntry(onum);
    long now = System.currentTimeMillis();

    if (entry.first != null) {
      long interval = now - entry.first;
      entry.second.add(interval);
    }
    entry.first = now;
  }

  /**
   * Suggests a warranty-expiry time, based on the time at which the next
   * write-prepare is predicted to arrive.
   */
  public Long suggestWarranty(long onum) {
    long[] predictedWrites;
    synchronized (this) {
      HistoryEntry entry = getEntry(onum);
      predictedWrites = new long[entry.second.size];
      for (int i = 0; i < predictedWrites.length; i++) {
        predictedWrites[i] = entry.first + entry.second.data[i];
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
        return prediction - writeWindowPadding;
      }
    }

    // No more predicted writes. Just issue the default warranty.
    return now + defaultWarrantyLength;
  }
}
