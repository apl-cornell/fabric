package fabil.extension;

import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.qq.QQ;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class CastExt_c extends ExprExt_c {
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    FabILTypeSystem ts = pr.typeSystem();
    Cast cast = (Cast) node();
    if (!ts.isFabricReference(cast.castType())) return cast;

    // Get the exact proxy before we cast.
    QQ qq = pr.qq();
    return qq.parseExpr("(%T) fabric.lang.Object._Proxy.$getProxy(%E)", cast
        .castType(), cast.expr());
  }
}
