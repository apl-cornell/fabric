package fabric.types;

import jif.types.label.ConfPolicy;

/** Fabric field instance. A wrapper of all the type information related
 *  to a class field.
 */

public interface FabricFieldInstance extends jif.types.JifFieldInstance {
  ConfPolicy accessLabel();
  void setAccessLabel(ConfPolicy accessLabel);
}