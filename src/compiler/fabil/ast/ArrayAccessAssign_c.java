package fabil.ast;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import fabil.types.FabILTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class ArrayAccessAssign_c extends polyglot.ast.ArrayAccessAssign_c {

  @Deprecated
  public ArrayAccessAssign_c(Position pos, ArrayAccess left, Operator op,
      Expr right) {
    this(pos, left, op, right, null);
  }

  public ArrayAccessAssign_c(Position pos, ArrayAccess left, Operator op,
      Expr right, Ext ext) {
    super(pos, left, op, right, ext);
  }

  @Override
  public Type childExpectedType(Expr child, AscriptionVisitor av) {
    // fabric arrays of java inlineables expect fabric objects, not java objects
    if (child == right) {
      FabILTypeSystem ts = (FabILTypeSystem) av.typeSystem();
      Type array = ((ArrayAccess) left).array().type();
      Type base = array.toArray().ultimateBase();
      if (ts.isJavaInlineable(base) && ts.isFabricArray(array))
        return ts.FObject();
    }
    return super.childExpectedType(child, av);
  }

}
