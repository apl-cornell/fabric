package fabil.ast;

import fabil.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;

public class Cast_c extends polyglot.ast.Cast_c {

  public Cast_c(Position pos, TypeNode castType, Expr expr) {
    super(pos, castType, expr);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Cast_c#childExpectedType(polyglot.ast.Expr,
   *      polyglot.visit.AscriptionVisitor)
   */
  @Override
  public Type childExpectedType(Expr child, AscriptionVisitor av) {
    if (child == expr) {
      FabricTypeSystem ts = (FabricTypeSystem) av.typeSystem();
      Type toType = castType.type();
      if (ts.isJavaInlineable(toType)) return ts.Object();
      if (ts.isFabricReference(toType)) return ts.FObject();
    }
    
    return super.childExpectedType(child, av);
  }

}
