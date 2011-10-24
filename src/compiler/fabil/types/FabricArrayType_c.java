package fabil.types;

import polyglot.types.ArrayType_c;
import polyglot.types.FieldInstance;
import polyglot.types.Type;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.Position;

public class FabricArrayType_c extends ArrayType_c implements FabricArrayType {

  /** Used for deserializing types. */
  protected FabricArrayType_c() {
  }

  public FabricArrayType_c(TypeSystem ts, Position pos, Type base) {
    super(ts, pos, base);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void init() {
    boolean fixField = fields == null;

    super.init();

    if (fixField) {
      // Make the length field non-final.
      FieldInstance lengthField = lengthField();
      lengthField = lengthField.flags(lengthField.flags().clearFinal());
      fields.set(0, lengthField);
    }
  }

  @Override
  public boolean descendsFromImpl(Type ancestor) {
    // Fabric arrays are subtypes of fabric.lang.Object.
    FabILTypeSystem ts = (FabILTypeSystem) this.ts;
    if (ancestor.isCanonical() && !ancestor.isNull()
        && !ts.typeEquals(this, ancestor) && ancestor.isReference()
        && ts.typeEquals(ancestor, ts.FObject()) && ts.isFabricArray(this))
      return true;
    return super.descendsFromImpl(ancestor);
  }

  @Override
  public boolean equalsImpl(TypeObject t) {
    return (t instanceof FabricArrayType) && super.equalsImpl(t);
  }

  @Override
  public boolean typeEqualsImpl(Type t) {
    return (t instanceof FabricArrayType) && super.typeEqualsImpl(t);
  }

  @Override
  public boolean isImplicitCastValidImpl(Type toType) {
    if (toType instanceof JavaArrayType) return false;

    return super.isImplicitCastValidImpl(toType);
  }

  @Override
  public boolean isCastValidImpl(Type toType) {
    if (toType instanceof JavaArrayType) return false;

    return super.isCastValidImpl(toType);
  }

}
