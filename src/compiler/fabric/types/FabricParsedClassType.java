package fabric.types;

import java.net.URI;

import jif.types.JifParsedPolyType;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import polyglot.types.MethodInstance;
import codebases.types.CodebaseClassType;

public interface FabricParsedClassType extends JifParsedPolyType,
    FabricClassType, CodebaseClassType {
  void removeMethod(MethodInstance mi);

//  @Override
//  public Label providerFoldedClassAccessLabel();

  @Override
  public ConfPolicy classAccessPolicy();

  void setCanonicalNamespace(URI ns);
}
