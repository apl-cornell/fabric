package fabric.client;

import java.util.Collections;
import java.util.List;

import fabric.common.InternalError;

public class TransactionAtomicityViolationException extends InternalError {
  public final List<RemoteNode> failed;
  public final List<RemoteNode> unreachable;

  public TransactionAtomicityViolationException(List<RemoteNode> failed,
      List<RemoteNode> unreachable) {
    this.failed = Collections.unmodifiableList(failed);
    this.unreachable = Collections.unmodifiableList(unreachable);
  }

  public TransactionAtomicityViolationException() {
    this.failed = Collections.emptyList();
    this.unreachable = Collections.emptyList();
  }

}
