package fabric.types;

import fabil.Codebases;
import jif.types.JifClassType;
import jif.types.label.Label;

public interface FabricClassType extends JifClassType, Codebases {
  
  /**
   * Return the label associated with this class' fields, or null if this class
   * has no fields.
   */
  Label defaultFieldLabel();
  
  
  /**
   * Same behavior as above, except change the field labels
   * of Principal classes, so that they don't mention 'this'
   */
  Label defaultFabilFieldLabel();
  
}
