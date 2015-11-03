package fabric.types;

import jif.types.JifFieldInstance_c;
import jif.types.VarMap;
import jif.types.label.ConfPolicy;

import polyglot.types.Flags;
import polyglot.types.ReferenceType;
import polyglot.types.Type;
import polyglot.util.Position;

public class FabricFieldInstance_c extends JifFieldInstance_c implements
FabricFieldInstance {

  public FabricFieldInstance_c(FabricTypeSystem ts, Position pos,
      ReferenceType container, Flags flags, Type type, ConfPolicy accessLabel,
      String name) {
    super(ts, pos, container, flags, type, name);
    this.accessLabel = accessLabel;
  }

  protected ConfPolicy accessLabel;

  @Override
  public ConfPolicy accessPolicy() {
    return accessLabel;
  }

  @Override
  public void setAccessPolicy(ConfPolicy accessLabel) {
    this.accessLabel = accessLabel;
  }

  @Override
  public void subst(VarMap bounds) {
    super.subst(bounds);
    this.setAccessPolicy((ConfPolicy) bounds.applyTo(accessLabel));
  }

  @Override
  public String toString() {
      return super.toString() + "@{" + accessPolicy() + "}";
  }
}
