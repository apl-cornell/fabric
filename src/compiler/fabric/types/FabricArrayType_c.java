package fabric.types;

import polyglot.types.ArrayType_c;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;

public class FabricArrayType_c extends ArrayType_c {

  protected FabricArrayType_c() {
  }

  public FabricArrayType_c(TypeSystem ts, Position pos, Type base) {
    super(ts, pos, base);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.ReferenceType_c#descendsFromImpl(polyglot.types.Type)
   */
  @Override
  public boolean descendsFromImpl(Type ancestor) {
    // Arrays of primitives and arrays of Fabric objects are subtypes of
    // fabric.lang.Object.
    FabricTypeSystem ts = (FabricTypeSystem) this.ts;
    Type base = ultimateBase();
    if (ancestor.isCanonical() && !ancestor.isNull()
        && !ts.typeEquals(this, ancestor) && ancestor.isReference()
        && ts.typeEquals(ancestor, ts.FObject())
        && (base.isPrimitive() || ts.isFabric(base))) return true;
    return super.descendsFromImpl(ancestor);
  }

}
