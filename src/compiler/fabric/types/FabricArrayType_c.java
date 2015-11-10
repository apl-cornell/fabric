package fabric.types;

import jif.types.ConstArrayType_c;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import polyglot.types.FieldInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 * The only ArrayType class created by the FabricTypeSystem.
 *
 * @see FabricTypeSystem for further description
 * @author mdgeorge
 */
public class FabricArrayType_c extends ConstArrayType_c implements
FabricArrayType {
  protected ConfPolicy accessPolicy;
  protected boolean isNative;

  public FabricArrayType_c(FabricTypeSystem ts, Position pos, Type base,
      boolean isConst, boolean isNonConst, boolean isNative, ConfPolicy accessPolicy) {
    super(ts, pos, base, isConst, isNonConst);
    this.isNative = isNative;
    this.accessPolicy = accessPolicy;
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
  public ConfPolicy accessPolicy() throws SemanticException {
    // TODO: access policy of an array is just the confidentiality component of
    // the update label. Syntax needs to be extended to explicitly specify.
    if (accessPolicy == null) accessPolicy = defaultAccessPolicy();
    return accessPolicy;
  }

  private FabricTypeSystem ts() {
    return (FabricTypeSystem) ts;
  }

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

  protected ConfPolicy defaultAccessPolicy() {
    return ts().confProjection(updateLabel());
  }
}
