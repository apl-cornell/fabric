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
  public Label singleFieldLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.singleFieldLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.singleFieldLabel());
  }

  @Override
  public Label singleFabilFieldLabel() {
    return singleFieldLabel();
  }
  
  @Override
  public Label singleFabilAccessLabel() {
    return singleAccessLabel();
  }

  @Override
  public Label singleAccessLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.singleAccessLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.singleAccessLabel());
  }

  @Override
  public Label getFoldedAccessLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.getFoldedAccessLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.getFoldedAccessLabel());
  }

  @Override
  public URI canonicalNamespace() {
    return ((CodebaseClassType)base).canonicalNamespace();
  }

}
