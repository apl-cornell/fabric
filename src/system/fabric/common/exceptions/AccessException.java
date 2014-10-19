package fabric.common.exceptions;

import fabric.lang.security.Principal;
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

  public AccessException(Principal accessor, fabric.lang.Object accessee) {
    this("access", accessor, accessee);
  }

  public AccessException(String accessType, Principal accessor,
      fabric.lang.Object accessee) {
    super(message(accessType, accessor, accessee));
  }

  private static String message(String accessType, Principal accessor,
      fabric.lang.Object accessee) {

    String msg = "Access Denied.  \n";
    msg += "Principal " + accessor.name();
    msg +=
        " is not trusted to " + accessType + " object " + accessee.$getOnum()
        + "@" + accessee.$getStore().name() + ".\n";
    msg += "The object's label is " + accessee.get$$updateLabel() + ".\n";
    msg += "The object's type is  " + accessee.getClass() + ".\n";
    return msg;
  }
}
