package fabil.ast;

import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.TypeNode;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import fabil.types.FabILTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class Cast_c extends polyglot.ast.Cast_c {
  @Deprecated
  public Cast_c(Position pos, TypeNode castType, Expr expr) {
    this(pos, castType, expr, null);
  }

  public Cast_c(Position pos, TypeNode castType, Expr expr, Ext ext) {
    super(pos, castType, expr, ext);
  }

  @Override
  public Type childExpectedType(Expr child, AscriptionVisitor av) {
    if (child == expr) {
      FabILTypeSystem ts = (FabILTypeSystem) av.typeSystem();
      Type toType = castType.type();
      if (ts.isJavaInlineable(toType)) return ts.Object();
      if (ts.isFabricReference(toType)) return ts.FObject();
    }

    return super.childExpectedType(child, av);
  }

}
