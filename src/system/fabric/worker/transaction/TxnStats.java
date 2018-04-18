package fabric.worker.transaction;

/**
 * Class for easily tracking stats about app level transactions (as opposed to
 * individual attempts).
 */
public class TxnStats {
  private int txnAttempts = 0;
  private int lockAttempts = 0;
  private long tid = 0;
  private boolean coordinated = false;
  private boolean locksUsed = false;
  private int fetches = 0;

  public TxnStats() {
  }

  public void reset() {
    txnAttempts = 0;
    lockAttempts = 0;
    tid = 0;
    coordinated = false;
    locksUsed = false;
    fetches = 0;
  }

  /**
   * @return the txnAttempts count
   */
  public int getTxnAttempts() {
    return txnAttempts;
  }

  /**
   * @return the last tid
   */
  public int getLockAttempts() {
    return lockAttempts;
  }

  /**
   * @return the lockAttempts count
   */
  public long getTid() {
    return tid;
  }

  /**
   * @return the coordinated flag
   */
  public boolean isCoordinated() {
    return coordinated;
  }

  /**
   * @return the locksUsed flag
   */
  public boolean isLocksUsed() {
    return locksUsed;
  }

  /**
   * @return the fetches
   */
  public int getFetches() {
    return fetches;
  }

  /**
   * Mark the final attempt as a coordination.
   */
  public void markCoordination() {
    coordinated = true;
  }

  /**
   * Mark the final attempt as using locks.
   */
  public void markLocksUsed() {
    locksUsed = true;
  }

  /**
   * Count a transaction attempt.
   */
  public void markTxnAttempt() {
    txnAttempts++;
  }

  /**
   * Count a lock attempt.
   */
  public void markLockAttempt() {
    lockAttempts++;
  }

  /**
   * Record the final tid.
   */
  public void recordTid(long tid) {
    this.tid = tid;
  }

  /**
   * Record a fetch.
   */
  public void markFetch() {
    fetches++;
  }

  @Override
  public String toString() {
    return "[COORDINATED: " + coordinated +
      " WITH " + txnAttempts + " TXN ATTEMPTS" +
      " AND " + lockAttempts + " LOCK ATTEMPTS AND LOCKS USED: " + locksUsed +
      " USING " + fetches + " FETCHES" +
      " IN " + Long.toHexString(tid) + "]";
  }
}
