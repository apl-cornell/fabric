package fabric.client;

import fabric.common.FabricRuntimeException;

/**
 * An <code>UnreachableCoreException</code> is used to indicate that an
 * attempt to connect to a core has failed.
 */
public class UnreachableCoreException extends FabricRuntimeException {
  private Core core;

  public UnreachableCoreException(Core core) {
    this.core = core;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Throwable#getMessage()
   */
  @Override
  public String getMessage() {
    return "Unreachable core: " + core;
  }

}
