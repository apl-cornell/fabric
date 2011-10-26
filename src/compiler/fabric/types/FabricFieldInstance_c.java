package fabric.types;

import jif.types.JifFieldInstance_c;
import jif.types.label.Label;
import polyglot.types.Flags;
import polyglot.types.ReferenceType;
import polyglot.types.Type;
import polyglot.util.Position;

public class FabricFieldInstance_c extends JifFieldInstance_c
                          implements FabricFieldInstance {

  public FabricFieldInstance_c(FabricTypeSystem ts, Position pos,
      ReferenceType container, Flags flags, Type type,
      Label accessLabel, String name) {
    super(ts, pos, container, flags, type, name);
    this.accessLabel = accessLabel;
  }
  protected Label accessLabel;
  
  @Override
  public Label accessLabel() {
    return accessLabel;
  }
  
  @Override
  public void setAccessLabel(Label accessLabel) {
    this.accessLabel = accessLabel;
  }
  
}