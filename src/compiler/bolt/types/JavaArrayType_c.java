package bolt.types;

import polyglot.ext.jl5.types.JL5ArrayType_c;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public class JavaArrayType_c extends JL5ArrayType_c implements JavaArrayType {

  private static final long serialVersionUID = SerialVersionUID.generate();

  public JavaArrayType_c(TypeSystem ts, Position pos, Type base,
      boolean isVarArgs) {
    super(ts, pos, base, isVarArgs);
  }

  private int FINISH_ME;

}
