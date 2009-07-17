package fabil.ast;

import fabil.types.FabILTypeSystem;
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
    // fabric arrays of java inlineables expect fabric objects, not java objects
    if (child == right) {
      FabILTypeSystem ts = (FabILTypeSystem) av.typeSystem();
      Type array = ((ArrayAccess) left).array().type();
      Type base  = array.toArray().ultimateBase();
      if (ts.isJavaInlineable(base) && ts.isFabricArray(array))
        return ts.FObject();
    }
    return super.childExpectedType(child, av);
  }

}
