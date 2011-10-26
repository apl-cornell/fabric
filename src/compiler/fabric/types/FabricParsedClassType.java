package fabric.types;

import java.net.URI;

import jif.types.JifParsedPolyType;
import jif.types.label.Label;
import polyglot.types.MethodInstance;
import codebases.types.CodebaseClassType;

public interface FabricParsedClassType extends JifParsedPolyType,
    FabricClassType, CodebaseClassType {
  void removeMethod(MethodInstance mi);

  @Override
  public Label getFoldedAccessLabel();

  @Override
  public Label singleAccessLabel();

  void setCanonicalNamespace(URI ns);
}
