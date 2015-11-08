package fabric.types;

import java.util.Map;

import jif.types.JifClassType;
import jif.types.JifSubst_c;
import jif.types.JifTypeSystem;
import jif.types.Param;
import jif.types.ParamInstance;
import polyglot.types.ClassType;
import polyglot.types.FieldInstance;

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

  @Override
  public <FI extends FieldInstance> FI substField(FI fi) {
    fi = super.substField(fi);
    if (fi instanceof FabricFieldInstance) {
      FabricFieldInstance ffi = (FabricFieldInstance) fi;
      if (ffi.accessPolicy() != null) {
        FabricTypeSystem fts = (FabricTypeSystem) ts;
        ffi.setAccessPolicy(substLabel(fts.toLabel(ffi.accessPolicy())).confProjection());
      }
    }
    return fi;
  }
}
