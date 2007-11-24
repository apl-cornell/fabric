package fabric.extension;

import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.qq.QQ;
import polyglot.types.Type;
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
    if (!ts.isFabric(cast.castType())) return cast;

    Expr expr = cast.expr();
    Type type = expr.type();
    if (!ts.isFabric(type) || ts.isJavaInlineable(type)) return cast;

    // We have a pure Fabric object.  Get its exact proxy.
    QQ qq = pr.qq();
    return qq.parseExpr("(%T) ((fabric.lang.Object) %E).$getProxy()", cast
        .castType(), cast.expr());
  }
}
