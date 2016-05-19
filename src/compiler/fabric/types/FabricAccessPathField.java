package fabric.types;

import jif.types.label.AccessPath;
import jif.types.label.AccessPathField;
import jif.types.label.AccessPathRoot;
import polyglot.types.FieldInstance;
import polyglot.util.Position;

public class FabricAccessPathField extends AccessPathField {

  public FabricAccessPathField(AccessPath path, FieldInstance fi,
      String fieldName, Position pos) {
    super(path, fi, fieldName, pos);
  }

  // Conversion constructor.
  private FabricAccessPathField(AccessPathField apf) {
    this(apf.path(), apf.fieldInstance(), apf.fieldName(), apf.position());
  }

  @Override
  public AccessPathField subst(AccessPathRoot r, AccessPath e) {
    return new FabricAccessPathField(super.subst(r, e));
  }

  @Override
  public String exprString() {
    String splitName = ((FabricFieldInstance) fi).splitClassName();
    if (splitName == null) return super.exprString();

    return path.exprString() + "." + splitName + "." + fieldName;
  }

  @Override
  public AccessPathField fieldInstance(FieldInstance fi) {
    return new FabricAccessPathField(super.fieldInstance(fi));
  }

}
