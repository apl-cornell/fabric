package fabric.common.exceptions;

import java.security.GeneralSecurityException;

/**
 * A wrapper for java.security.GeneralSecurityException. This is here to convey
 * GeneralSecurityExceptions over the network.
 */
public class FabricGeneralSecurityException extends FabricException {
  public FabricGeneralSecurityException(GeneralSecurityException cause) {
    super(cause);
  }
  
  @Override
  public GeneralSecurityException getCause() {
    return (GeneralSecurityException) super.getCause();
  }
}
