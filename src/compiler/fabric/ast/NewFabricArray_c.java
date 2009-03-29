package fabric.ast;

import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.NewArray_c;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

public class NewFabricArray_c extends NewArray_c implements NewFabricArray {

  public NewFabricArray_c(Position pos, TypeNode baseType, List dims,
      int addDims, ArrayInit init) {
    super(pos, baseType, dims, addDims, init);
  }
}
