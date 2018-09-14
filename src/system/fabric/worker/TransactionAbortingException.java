package fabric.worker;

import fabric.common.TransactionID;
import fabric.common.exceptions.FabricRuntimeException;

/**
 * Indicates that the current transaction should be aborted.
 */
public final class TransactionAbortingException extends FabricRuntimeException {
  /**
   * Identifies the transaction that is to be aborted (null if it's the
   * top-level transaction, the default).
   */
  public final TransactionID tid;

  /**
   * Flag indicating whether the transaction should keep its read set and merge
   * with the parent transaction.
   */
  public final boolean keepReads;

  /**
   * @param tid
   *          identifies the transaction that is to be restarted.
   */
  public TransactionAbortingException(TransactionID tid, boolean keepReads) {
    super("restarting " + tid);
    this.tid = tid;
    this.keepReads = keepReads;
  }

  /**
   * @param tid
   *          identifies the transaction that is to be restarted.
   */
  public TransactionAbortingException(TransactionID tid) {
    super("restarting " + tid);
    this.tid = tid;
    this.keepReads = false;
  }

  public TransactionAbortingException() {
    this.tid = null;
    this.keepReads = false;
  }

  public TransactionAbortingException(String message) {
    super(message);
    this.tid = null;
    this.keepReads = false;
  }

}
