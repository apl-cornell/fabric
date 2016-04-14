package fabric.types;

import jif.types.JifProcedureInstance;
import jif.types.label.Label;

/**
 * Fabric procedure instance. A wrapper of all the type information related to a
 * procedure.
 *
 * Extends <code>jif.types.JifProcedureInstance</code> to add support for
 * additional begin conflict label and end confidentiality label for non-leaky
 * transactions (work by Isaac Sheff and Tom Magrino in 2015).
 */
public interface FabricProcedureInstance extends JifProcedureInstance {
  /**
   * Lower bound for the conflict label on objects conflicted in the method.
   */
  Label beginConflictLabel();

  /**
   * Is the lower bound not explicitly specified?
   */
  boolean isDefaultBeginConflict();

  /**
   * Set lower bound for the conflict label on objects conflicted in the method.
   */
  void setBeginConflictLabel(Label p, boolean isDefault);

  /**
   * Upper bound on the update label of objects conflicted in the method.
   */
  Label endConflictLabel();

  /**
   * Is the upper bound not explicitly specified?
   */
  boolean isDefaultEndConflict();

  /**
   * Set upper bound on the update label of objects conflicted in the method.
   */
  void setEndConflictLabel(Label p, boolean isDefault);
}
