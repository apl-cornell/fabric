package fabric.core.store;

import fabric.common.FabricException;

public class StoreException extends FabricException {
  
  public StoreException(Exception cause) {
    super(cause);
  }
  
  public StoreException() {}

}
