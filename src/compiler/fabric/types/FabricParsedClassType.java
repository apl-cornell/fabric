package fabric.types;

import polyglot.types.MethodInstance;
import jif.types.JifParsedPolyType;
import jif.types.label.Label;

public interface FabricParsedClassType extends JifParsedPolyType, FabricClassType {
  Label defaultFieldLabel();
  void removeMethod(MethodInstance mi);
}
