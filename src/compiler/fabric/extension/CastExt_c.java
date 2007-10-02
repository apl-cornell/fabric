package fabric.extension;

import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.qq.QQ;
import fabric.visit.ProxyRewriter;

public class CastExt_c extends ExprExt_c {
  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    Cast cast = (Cast) node();
    QQ qq = pr.qq();

    return qq.parseExpr("(%T) ((fabric.lang.Object) %E).$getProxy()", cast
        .castType(), cast.expr());
  }
}
