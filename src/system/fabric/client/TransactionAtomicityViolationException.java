package fabric.client;

import java.util.Collections;
import java.util.List;

import fabric.common.InternalError;

public class TransactionAtomicityViolationException extends InternalError {
  public final List<Core> failed;
  public final List<Core> unreachable;

  public TransactionAtomicityViolationException(List<Core> failed,
      List<Core> unreachable) {
    this.failed = Collections.unmodifiableList(failed);
    this.unreachable = Collections.unmodifiableList(unreachable);
  }

}
