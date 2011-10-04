package fabric.types;

import fabil.types.CodebaseClassType;
import polyglot.types.MethodInstance;
import jif.types.JifParsedPolyType;
import jif.types.label.Label;

public interface FabricParsedClassType extends JifParsedPolyType, FabricClassType, CodebaseClassType {
  void removeMethod(MethodInstance mi);
  public Label getFoldedAccessLabel();
  public Label singleAccessLabel();
  }
