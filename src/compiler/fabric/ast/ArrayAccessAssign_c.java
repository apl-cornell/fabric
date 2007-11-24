package fabric.ast;

import fabric.types.FabricTypeSystem;
import polyglot.ast.ArrayAccess;
import polyglot.ast.Expr;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;

public class ArrayAccessAssign_c extends polyglot.ast.ArrayAccessAssign_c {

  public ArrayAccessAssign_c(Position pos, ArrayAccess left, Operator op,
      Expr right) {
    super(pos, left, op, right);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Assign_c#childExpectedType(polyglot.ast.Expr,
   *      polyglot.visit.AscriptionVisitor)
   */
  @Override
  public Type childExpectedType(Expr child, AscriptionVisitor av) {
    if (child == right) {
      FabricTypeSystem ts = (FabricTypeSystem) av.typeSystem();
      Type base = ((ArrayAccess) left).array().type().toArray().ultimateBase();
      if (ts.isJavaInlineable(base)) return ts.FObject();
    }
    return super.childExpectedType(child, av);
  }

}
