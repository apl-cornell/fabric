package fabric.store;

import fabric.common.exceptions.UsageError;

/**
 * A <code>DuplicateStoreException</code> is thrown when an attempt is made to
 * add a duplicate store to a store node.
 */
public class DuplicateStoreException extends UsageError {
  public DuplicateStoreException() {
    super("Tried to run multiple stores with the same name");
  }
}

