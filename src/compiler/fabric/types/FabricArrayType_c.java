package fabric.types;

import polyglot.types.FieldInstance;
import polyglot.types.Type;
import polyglot.util.Position;
import jif.types.ConstArrayType_c;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;

/** The only ArrayType class created by the FabricTypeSystem.
 * @see FabricTypeSystem for further description
 * @author mdgeorge
 *
 */
public class FabricArrayType_c
     extends ConstArrayType_c
  implements FabricArrayType {
  
  protected boolean isNative;
  
  public FabricArrayType_c(FabricTypeSystem ts, Position pos, Type base,
                           boolean isConst, boolean isNonConst,
                           boolean isNative) {
    super(ts, pos, base, isConst, isNonConst);
    this.isNative = isNative;
  }

  @Override
  public boolean isNative() {
    return isNative;
  }

  @Override
  public Label updateLabel() {
    return ts().labelOfType(base);
  }

  @Override
  public ConfPolicy accessPolicy() {
    // TODO: access policy of an array is just the confidentiality component of
    // the update label.
    return ts().confProjection(updateLabel());
  }
  
  private FabricTypeSystem ts() {
    return (FabricTypeSystem) ts;
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
}
