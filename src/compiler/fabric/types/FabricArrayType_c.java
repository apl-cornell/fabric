package fabric.types;

import polyglot.types.ArrayType_c;
import polyglot.types.Type;
import polyglot.util.Position;

public class FabricArrayType_c extends ArrayType_c implements FabricArrayType {
  public FabricArrayType_c(FabricTypeSystem ts, Position pos, Type base) {
    super(ts, pos, base);
  }
}
