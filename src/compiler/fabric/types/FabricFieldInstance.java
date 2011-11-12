package fabric.types;

import jif.types.label.Label;

/** Fabric field instance. A wrapper of all the type information related
 *  to a class field.
 */

public interface FabricFieldInstance extends jif.types.JifFieldInstance {
  Label accessLabel();
  void setAccessLabel(Label accessLabel);
}