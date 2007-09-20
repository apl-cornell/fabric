package fabric.ast;

import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

public class NewArray_c extends polyglot.ast.NewArray_c implements NewArray {

  private Expr location;

  /** NewArray constructor.  Simply calls super constructor and saves the location
   * 
   * @see polyglot.ast.NewArray_c#NewArray_c(Position, TypeNode, List, int, ArrayInit)
   * @param pos      Non-null
   * @param baseType Non-null
   * @param location May be null
   * @param dims     Non-null
   * @param addDims  Non-negative
   * @param init     May be null
   */
  public NewArray_c(Position pos, TypeNode baseType, Expr location,
                    List<Expr> dims, int addDims, ArrayInit init) {
    super(pos, baseType, dims, addDims, init);
    this.location = location;
  }

  public Expr location() {
    return this.location;
  }

  public NewArray location(Expr location) {
    NewArray_c result = (NewArray_c) copy();
    result.location = location;
    return result;
  }

}
