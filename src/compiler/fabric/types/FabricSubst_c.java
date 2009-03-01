package fabric.types;

import java.util.Map;

import polyglot.types.ClassType;

import jif.types.JifClassType;
import jif.types.JifSubst_c;
import jif.types.JifTypeSystem;

public class FabricSubst_c extends JifSubst_c {
  public FabricSubst_c(JifTypeSystem ts, Map subst, Map cache) {
    super(ts, subst, cache);
  }
  
  @Override
  public ClassType substClassType(ClassType t) {
    // Don't bother trying to substitute into a non-Jif class.
    if (! (t instanceof JifClassType)) {
        return t;
    }

    return new FabricSubstClassType_c((JifTypeSystem) ts, t.position(),
                                      (JifClassType) t, this);
  }
}
