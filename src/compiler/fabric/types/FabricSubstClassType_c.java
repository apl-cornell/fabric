package fabric.types;

import fabric.lang.Codebase;
import polyglot.types.ClassType;
import polyglot.util.Position;
import jif.types.JifSubst;
import jif.types.JifSubstClassType_c;
import jif.types.JifTypeSystem;
import jif.types.label.Label;

public class FabricSubstClassType_c extends JifSubstClassType_c implements FabricSubstType {
  public FabricSubstClassType_c(JifTypeSystem ts, Position pos, ClassType base, JifSubst subst) {
    super(ts, pos, base, subst);
  }
  
  public Label singleFieldLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.singleFieldLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.singleFieldLabel());
  }

  public Label defaultFabilFieldLabel() {
    return singleFieldLabel();
  }

  public Codebase codebase() {
    return ((FabricParsedClassType) base).codebase();
  }

  public Label singleAccessLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.singleAccessLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.singleAccessLabel());
  }

  public Label getFoldedAccessLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.getFoldedAccessLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.getFoldedAccessLabel());
  }

}
