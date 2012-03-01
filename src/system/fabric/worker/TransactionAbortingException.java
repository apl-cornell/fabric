package fabric.worker;

import fabric.common.exceptions.FabricRuntimeException;

/**
 * Indicates that the current transaction should be aborted.
 */
public final class TransactionAbortingException extends FabricRuntimeException {

  public TransactionAbortingException() {
  }

  public TransactionAbortingException(String message) {
    super(message);
  }

}
