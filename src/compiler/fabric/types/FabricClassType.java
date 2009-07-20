package fabric.types;

import jif.types.JifClassType;
import jif.types.label.Label;

public interface FabricClassType extends JifClassType {
  
  /**
   * Return the label assiciated with this class' fields, or null if ths class
   * has no fields.
   */
  Label defaultFieldLabel();
}
