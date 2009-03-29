package fabric.parse;

import jif.parse.Grm;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

public class Array extends jif.parse.Array {

  protected final boolean isNative;
  
  public Array(Grm parser, Position pos, TypeNode prefix) {
    this(parser, pos, prefix, false);
  }
  
  public Array(Grm parser, Position pos, TypeNode prefix, boolean isConst) {
    this(parser, pos, prefix, isConst, false);
  }
  
  public Array(Grm parser, Position pos, TypeNode prefix, boolean isConst, boolean isNative) {
    super(parser, pos, prefix, isConst);
    this.isNative = isNative;
  }
  
  public boolean isNative() {
    return isNative;
  }
}
