package fabric.common.exceptions;

import fabric.worker.Core;

/**
 * An <code>AccessError</code> represents an authorisation error.
 */
public class AccessException extends FabricException {
  public AccessException(Core core, long onum) {
    this(core.name(), onum);
  }

  public AccessException(String coreName, long onum) {
    super("Error accessing fab://" + coreName + "/" + onum);
  }

  public AccessException(String message) {
    super(message);
  }
}
