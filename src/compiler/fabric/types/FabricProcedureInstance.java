package fabric.types;

import jif.types.JifProcedureInstance;
import jif.types.label.ConfPolicy;

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
   * Lower bound for the access policy on fields accessed in the method.
   */
  ConfPolicy beginAccessPolicy();

  /**
   * Is the lower bound not explicitly specified?
   */
  boolean isDefaultBeginAccess();

  /**
   * Set lower bound for the access policy on fields accessed in the method.
   */
  void setBeginAccessPolicy(ConfPolicy p, boolean isDefault);

  /**
   * Upper bound on the confidentiality policy of fields accessed in the method.
   */
  ConfPolicy endConfPolicy();

  /**
   * Is the upper bound not explicitly specified?
   */
  boolean isDefaultEndConf();

  /**
   * Set upper bound on the confidentiality policy of fields accessed in the method.
   */
  void setEndConfPolicy(ConfPolicy p, boolean isDefault);
}
