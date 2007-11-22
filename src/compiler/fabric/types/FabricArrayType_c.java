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
    // Array types are subtypes of fabric.lang.Object.
    FabricTypeSystem ts = (FabricTypeSystem) this.ts;
    if (ancestor.isCanonical() && !ancestor.isNull()
        && !ts.typeEquals(this, ancestor) && ancestor.isReference()
        && ts.typeEquals(ancestor, ts.FObject())) return true;
    return super.descendsFromImpl(ancestor);
  }

}
