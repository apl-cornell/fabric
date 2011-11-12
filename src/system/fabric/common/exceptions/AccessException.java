package fabric.common.exceptions;

import fabric.worker.Store;

/**
 * An <code>AccessError</code> represents an authorisation error.
 */
public class AccessException extends FabricException {
  public AccessException(Store store, long onum) {
    this(store.name(), onum);
  }

  public AccessException(String storeName, long onum) {
    super("Error accessing fab://" + storeName + "/" + onum);
  }

  public AccessException(String message) {
    super(message);
  }
}
