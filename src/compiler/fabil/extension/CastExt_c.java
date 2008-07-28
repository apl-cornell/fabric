package fabil.extension;

import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.qq.QQ;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class CastExt_c extends ExprExt_c {
  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ExprExt_c#rewriteProxiesImpl(fabil.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    FabILTypeSystem ts = pr.typeSystem();
    Cast cast = (Cast) node();
    if (!ts.isFabricReference(cast.castType())) return cast;

    // Get the exact proxy before we cast.
    QQ qq = pr.qq();
    return qq.parseExpr("(%T) fabric.lang.Object.$Proxy.$getProxy(%E)", cast
        .castType(), cast.expr());
  }
}
