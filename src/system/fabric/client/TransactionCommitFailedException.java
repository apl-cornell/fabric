package fabric.client;

import fabric.common.FabricException;

public class TransactionCommitFailedException extends FabricException {
  public TransactionCommitFailedException(String message) {
    super(message);
  }
}
