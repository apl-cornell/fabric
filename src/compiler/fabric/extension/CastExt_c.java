package fabric.extension;

import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.qq.QQ;
import fabric.types.FabricTypeSystem;
import fabric.visit.ProxyRewriter;

public class CastExt_c extends ExprExt_c {
  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    FabricTypeSystem ts = pr.typeSystem();
    Cast cast = (Cast) node();
    if (!ts.isFabricReference(cast.castType())) return cast;

    // Get the exact proxy before we cast.
    QQ qq = pr.qq();
    return qq.parseExpr("(%T) fabric.lang.Object.$Proxy.$getProxy(%E)", cast
        .castType(), cast.expr());
  }
}
