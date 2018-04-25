package fabric.worker.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for easily tracking stats about app level transactions (as opposed to
 * individual attempts).
 */
public class TxnStats {
  private int txnAttempts = 0;
  private long tid = 0;
  private boolean coordinated = false;
  private int fetches = 0;
  private List<String> msgs = new ArrayList<>();
  private List<String> versionConflicts = new ArrayList<>();

  public TxnStats() {
  }

  public void reset() {
    txnAttempts = 0;
    tid = 0;
    coordinated = false;
    fetches = 0;
    msgs.clear();
    versionConflicts.clear();
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

  /**
   * Record a fetch.
   */
  public void markFetch() {
    fetches++;
  }

  /**
   * Add a custom message.
   */
  public void addMsg(String msg) {
    this.msgs.add(msg);
  }

  /**
   * Reset custom messages.
   */
  public void clearMsgs() {
    this.msgs.clear();
  }

  /**
   * Mark the version conflicts that occurred.
   */
  public void addConflicts(String conflicts) {
    versionConflicts.add(conflicts);
  }

  @Override
  public String toString() {
    return "[COORDINATED: " + coordinated + " WITH " + txnAttempts
        + " TXN ATTEMPTS" + " USING " + fetches + " FETCHES" + " MSGS: " + msgs
        + " CONFLICTS: " + versionConflicts + " IN " + Long.toHexString(tid)
        + "]";
  }
}
