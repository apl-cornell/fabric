package fabric.ast;

import java.util.List;

import jif.ast.AmbNewArray_c;
import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

public class AmbNewFabricArray_c extends AmbNewArray_c implements
    AmbNewFabricArray {

  protected Expr loc;

  public AmbNewFabricArray_c(Position pos, TypeNode baseType, Expr loc,
      Object expr, List<Expr> dims, int addDims) {
    super(pos, baseType, expr, dims, addDims);
    this.loc = loc;
  }

  @Override
  public Expr location() {
    return this.loc;
  }

  @Override
  public AmbNewFabricArray location(Expr loc) {
    AmbNewFabricArray_c result = (AmbNewFabricArray_c) copy();
    result.loc = loc;
    return result;
  }
}
