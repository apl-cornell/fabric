package fabric.worker.metrics;

/**
 * Class for easily tracking stats about metrics transactions.
 */
public class TxnStats {
  private int txnAttempts = 0;
  private int lockAttempts = 0;
  private boolean coordinated = false;

  public TxnStats() {
  }

  public void reset() {
    txnAttempts = 0;
    lockAttempts = 0;
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
  public int getLockAttempts() {
    return lockAttempts;
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
   * Count a lock attempt.
   */
  public void markLockAttempt() {
    lockAttempts++;
  }

  @Override
  public String toString() {
    return "[COORDINATED: " + coordinated +
      " WITH " + txnAttempts + " TXN ATTEMPTS" +
      " AND " + lockAttempts + " LOCK ATTEMPTS]";
  }
}
