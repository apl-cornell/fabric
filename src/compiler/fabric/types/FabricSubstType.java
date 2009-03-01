package fabric.types;

import jif.types.JifSubstType;
import jif.types.label.Label;

public interface FabricSubstType extends JifSubstType, FabricClassType {
  Label defaultFieldLabel();
}
