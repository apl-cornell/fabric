package fabric.types;

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
  
  public Label defaultFieldLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.defaultFieldLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.defaultFieldLabel());
  }

  public Label defaultFabilFieldLabel() {
    // TODO Auto-generated method stub
    return defaultFieldLabel();
  }
}
