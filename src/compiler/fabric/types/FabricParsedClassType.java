package fabric.types;

import jif.types.JifParsedPolyType;
import jif.types.label.Label;

public interface FabricParsedClassType extends JifParsedPolyType, FabricClassType {
  Label defaultFieldLabel();
}
