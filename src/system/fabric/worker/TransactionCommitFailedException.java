package fabric.worker;

import fabric.common.exceptions.FabricException;

public class TransactionCommitFailedException extends FabricException {
  public TransactionCommitFailedException(String message) {
    super(message);
  }

  public TransactionCommitFailedException(String message, Throwable cause) {
    super(message, cause);
  }
}
