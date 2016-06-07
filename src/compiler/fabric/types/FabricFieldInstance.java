package fabric.types;

import jif.types.label.ConfPolicy;

/**
 * Fabric field instance. A wrapper of all the type information related to a
 * class field.
 */

public interface FabricFieldInstance extends jif.types.JifFieldInstance {
  ConfPolicy accessPolicy();

  void setAccessPolicy(ConfPolicy accessLabel);

  /**
   * @return the class name of the split fragment in which this field will
   *           reside after translation to FabIL. The split fragment will live
   *           as a class, nested in the class that declares this field. This
   *           method returns null if the field does not reside in a split
   *           fragment.
   */
  String splitClassName();
}
