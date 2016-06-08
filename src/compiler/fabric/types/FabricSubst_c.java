package fabric.types;

import java.util.Map;

import jif.types.JifClassType;
import jif.types.JifSubst_c;
import jif.types.JifTypeSystem;
import jif.types.Param;
import jif.types.ParamInstance;
import polyglot.types.ClassType;
import polyglot.types.Type;

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
  public Type uncachedSubstType(Type t) {
    Type substituted = super.uncachedSubstType(t);
    if (!t.isArray()) return substituted;

    // A gross hack to get labels substituted in the "length" field of arrays,
    // because substitution in array fields doesn't seem to be fully supported.

    // First, get the array type and its length field instance.
    FabricArrayType arrayType = (FabricArrayType) substituted.toArray();
    FabricFieldInstance lengthFI =
        (FabricFieldInstance) arrayType.lengthField();

    // Next, perform substitution in the field instance. Calling substField()
    // seems to cause an infinite recursion, due to the substContainer() call in
    // Subst_c.substField(), so the rest of the code in that method and
    // JifSubst_c.substField() is copied here. (ugh)
    lengthFI = (FabricFieldInstance) lengthFI.copy();
    lengthFI.setType(substType(lengthFI.type()));
    lengthFI.setLabel(substLabel(lengthFI.label()));

    // Update the array type with the new length field instance.
    arrayType = arrayType.lengthField(lengthFI);

    FabricTypeSystem ts = (FabricTypeSystem) this.ts;
    if (!ts.isLabeled(substituted)) {
      return arrayType;
    }

    return ts.labeledType(substituted.position(), arrayType,
        ts.labelOfType(substituted));
  }
}
