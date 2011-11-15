package fabric.types;

import java.net.URI;

import jif.types.JifSubst;
import jif.types.JifSubstClassType_c;
import jif.types.JifTypeSystem;
import jif.types.label.Label;
import polyglot.types.ClassType;
import polyglot.util.Position;
import codebases.types.CodebaseClassType;

public class FabricSubstClassType_c extends JifSubstClassType_c implements FabricSubstType {
  public FabricSubstClassType_c(JifTypeSystem ts, Position pos, ClassType base, JifSubst subst) {
    super(ts, pos, base, subst);
  }
  
  @Override
  public Label classUpdateLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.classUpdateLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.classUpdateLabel());
  }

  @Override
  public Label classAccessLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.classAccessLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.classAccessLabel());
  }

  @Override
  public Label providerFoldedClassAccessLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.providerFoldedClassAccessLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.providerFoldedClassAccessLabel());
  }

  @Override
  public URI canonicalNamespace() {
    return ((CodebaseClassType)base).canonicalNamespace();
  }

}
