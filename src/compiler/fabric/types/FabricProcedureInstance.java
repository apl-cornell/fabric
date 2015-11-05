package fabric.types;

import jif.types.JifProcedureInstance;
import jif.types.label.Label;

/**
 * Fabric procedure instance. A wrapper of all the type information related to a
 * procedure.
 *
 * Extends <code>jif.types.JifProcedureInstance</code> to add support for
 * additional begin access label and end confidentiality label for non-leaky
 * transactions (work by Isaac Sheff and Tom Magrino in 2015).
 */
public interface FabricProcedureInstance extends JifProcedureInstance {
  /**
   * Lower bound for the access label on objects accessed in the method.
   */
  Label beginAccessLabel();

  /**
   * Is the lower bound not explicitly specified?
   */
  boolean isDefaultBeginAccess();

  /**
   * Set lower bound for the access label on objects accessed in the method.
   */
  void setBeginAccessLabel(Label p, boolean isDefault);

  /**
   * Upper bound on the update label of objects accessed in the method.
   */
  Label endAccessLabel();

  /**
   * Is the upper bound not explicitly specified?
   */
  boolean isDefaultEndAccess();

  /**
   * Set upper bound on the update label of objects accessed in the method.
   */
  void setEndAccessLabel(Label p, boolean isDefault);
}
