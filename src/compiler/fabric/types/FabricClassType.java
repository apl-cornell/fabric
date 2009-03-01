package fabric.types;

import jif.types.JifClassType;
import jif.types.label.Label;

public interface FabricClassType extends JifClassType {
  Label defaultFieldLabel();
}
