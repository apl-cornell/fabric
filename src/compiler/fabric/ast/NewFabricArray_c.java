package fabric.ast;

import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.NewArray_c;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

//XXX Should be replaced with extension
@Deprecated
public class NewFabricArray_c extends NewArray_c implements NewFabricArray {
  @Deprecated
  public NewFabricArray_c(Position pos, TypeNode baseType, List<Expr> dims,
      int addDims, ArrayInit init) {
    this(pos, baseType, dims, addDims, init, null);
  }

  public NewFabricArray_c(Position pos, TypeNode baseType, List<Expr> dims,
      int addDims, ArrayInit init, Ext ext) {
    super(pos, baseType, dims, addDims, init, ext);
  }
}
