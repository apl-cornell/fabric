package bolt.types;

import polyglot.types.ArrayType_c;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public class FabricArrayType_c extends ArrayType_c implements FabricArrayType {

  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricArrayType_c(TypeSystem ts, Position pos, Type base) {
    super(ts, pos, base);
  }

  private int FINISH_ME;
}
