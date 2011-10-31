package fabric.worker.transaction;

import fabric.common.exceptions.FabricException;

public class TakeOwnershipFailedException extends FabricException {
  public TakeOwnershipFailedException(String msg) {
    super(msg);
  }
}
