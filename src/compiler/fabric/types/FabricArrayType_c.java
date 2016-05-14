package fabric.types;

import jif.types.ConstArrayType_c;
import jif.types.JifFieldInstance;
import jif.types.JifTypeSystem;
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
      boolean isConst, boolean isNonConst, boolean isNative) {
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
    JifTypeSystem ts = (JifTypeSystem) this.typeSystem();

    super.init();

    if (fixField) {
      // Make the length field non-final.
      JifFieldInstance lengthField = (JifFieldInstance) lengthField();
      lengthField = (JifFieldInstance) lengthField.flags(lengthField.flags().clearFinal());
      // Make the length field label the same as the label of the entries (since
      // the length is non-final).
      lengthField.setLabel(ts.labelOfType(base()));
      fields.set(0, lengthField);
    }
  }

  protected ConfPolicy defaultAccessPolicy() {
    return ts().confProjection(updateLabel());
  }

  @Override
  protected FieldInstance createLengthFieldInstance() {
      FieldInstance fi =
              ts.fieldInstance(position(),
                               this,
                               ts.Public().Final(),
                               ts.Int(),
                               "length");
      fi.setNotConstant();
      return fi;
  }
}
