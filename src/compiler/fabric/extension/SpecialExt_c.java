package fabric.extension;

import polyglot.ast.Expr;
import polyglot.ast.Special;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import fabric.visit.ProxyRewriter;

public class SpecialExt_c extends ExprExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  protected Expr rewriteProxiesImpl(ProxyRewriter pr) {
    Special special = node();
    TypeNode qualifier = special.qualifier();
    if (qualifier == null) return super.rewriteProxiesImpl(pr);

    // Tack on a ".$Impl" to the qualifier.
    QQ qq = pr.qq();
    return qq.parseExpr(qualifier + ".$Impl." + special.kind());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#node()
   */
  @Override
  public Special node() {
    return (Special) super.node();
  }

}
