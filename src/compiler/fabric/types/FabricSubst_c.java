package fabric.types;

import java.util.Map;

import jif.types.JifClassType;
import jif.types.JifSubst_c;
import jif.types.JifTypeSystem;
import jif.types.Param;
import jif.types.ParamInstance;
import polyglot.types.ClassType;

public class FabricSubst_c extends JifSubst_c {
  public FabricSubst_c(JifTypeSystem ts,
      Map<ParamInstance, ? extends Param> subst) {
    super(ts, subst);
  }

  @Override
  protected ClassType substClassTypeImpl(ClassType t) {
    // Don't bother trying to substitute into a non-Jif class.
    if (!(t instanceof JifClassType)) {
      return t;
    }

    return new FabricSubstClassType_c((JifTypeSystem) ts, t.position(),
        (JifClassType) t, this);
  }
}
