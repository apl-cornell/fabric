package fabric.types;

import codebases.types.CodebaseClassType;
import jif.types.JifClassType;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;

public interface FabricClassType extends JifClassType, CodebaseClassType {
  
  /**
   * Return the update label that objects of this type are enforced at. This
   * label is the join in the trust ordering of the update labels of every
   * field.
   */
  Label classUpdateLabel();
  
  
  /**
   * Return the access label that objects of this type are enforced at. This
   * label is the join in the trust ordering of the access labels of every
   * field and the confidentiality projection of every method's begin label.
   */
  ConfPolicy classAccessPolicy();
  

}
