package fabric.types;

import java.util.Map;

import polyglot.types.ClassType;
import polyglot.types.Type;

import jif.types.JifClassType;
import jif.types.JifSubst_c;
import jif.types.JifTypeSystem;
import jif.types.Param;
import jif.types.ParamInstance;

public class FabricSubst_c extends JifSubst_c {
  public FabricSubst_c(JifTypeSystem ts, Map<ParamInstance, Param> subst,
      Map<Type, Type> cache) {
    super(ts, subst, cache);
  }

  @Override
  public ClassType substClassType(ClassType t) {
    // Don't bother trying to substitute into a non-Jif class.
    if (!(t instanceof JifClassType)) {
      return t;
    }

    return new FabricSubstClassType_c((JifTypeSystem) ts, t.position(),
        (JifClassType) t, this);
  }
}
