package fabric.worker.transaction;

/**
 * Class for easily tracking stats about app level transactions (as opposed to
 * individual attempts).
 */
public class TxnStats {
  private int txnAttempts = 0;
  private long tid = 0;
  private boolean coordinated = false;

  public TxnStats() {
  }

  public void reset() {
    txnAttempts = 0;
    tid = 0;
    coordinated = false;
  }

  /**
   * @return the txnAttempts count
   */
  public int getTxnAttempts() {
    return txnAttempts;
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
   * Mark the final attempt as a coordination.
   */
  public void markCoordination() {
    coordinated = true;
  }

  /**
   * Count a transaction attempt.
   */
  public void markTxnAttempt() {
    txnAttempts++;
  }

  /**
   * Record the final tid.
   */
  public void recordTid(long tid) {
    this.tid = tid;
  }

  @Override
  public String toString() {
    return "[COORDINATED: " + coordinated +
      " WITH " + txnAttempts + " TXN ATTEMPTS" +
      " IN " + Long.toHexString(tid) + "]";
  }
}
