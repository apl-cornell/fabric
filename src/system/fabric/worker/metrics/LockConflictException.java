package fabric.worker.metrics;

import fabric.common.TransactionID;
import fabric.common.exceptions.FabricRuntimeException;
import fabric.worker.transaction.TransactionManager;

/**
 * Indicates that the current transaction conflicted on a lock object.
 */
public final class LockConflictException extends FabricRuntimeException {

  public final TransactionID tid;

  /**
   * @param tid
   *          identifies the transaction that had the conflict.
   */
  public LockConflictException(TransactionID tid) {
    super("lock conflict in " + tid);
    this.tid = tid;
    // XXX TODO Ugh this is a really unintuitive place to put this but I don't
    // want to deal with regenerating the DB at this point.
    TransactionManager.getInstance().registerLockConflict();
  }
}
