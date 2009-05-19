package fabric.ast;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.util.Position;
import jif.ast.AmbNewArray_c;

public class AmbNewFabricArray_c
     extends AmbNewArray_c
  implements AmbNewFabricArray {

  protected Expr loc;
  
  public AmbNewFabricArray_c(Position pos, TypeNode baseType, Expr loc, Object expr, List dims, int addDims) {
    super(pos, baseType, expr, dims, addDims);
    this.loc = loc;
  }

  
  public Expr location() {
    return this.loc;
  }
  
  public AmbNewFabricArray location(Expr loc) {
    AmbNewFabricArray_c result = (AmbNewFabricArray_c) copy();
    result.loc = loc;
    return result;
  }
}
