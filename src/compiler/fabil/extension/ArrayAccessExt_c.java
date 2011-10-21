package fabil.extension;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Expr;
import polyglot.types.Type;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class ArrayAccessExt_c extends ExprExt_c {

  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    ArrayAccess aa = node();

    // Only rewrite Fabric arrays.
    FabILTypeSystem ts = pr.typeSystem();
    Expr array = aa.array();
    if (!ts.isFabricType(array.type())) return aa;

    Expr result = pr.qq().parseExpr("%E.get(%E)", array, aa.index());

    // Insert a cast if we have a pure Fabric base type.
    Type base = array.type().toArray().ultimateBase();
    if (ts.isFabricType(base) && !ts.isJavaInlineable(base)) {
      Type castType = aa.type();
      if (castType.isArray())
        castType = ts.fabricRuntimeArrayOf(castType.toArray().base());
      result = pr.qq().parseExpr("(%T) %E", castType, result);
    }

    return result;
  }

  @Override
  public ArrayAccess node() {
    return (ArrayAccess) super.node();
  }

}
