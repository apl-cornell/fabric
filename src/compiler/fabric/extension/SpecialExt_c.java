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
    QQ qq = pr.qq();
    if (qualifier != null) {
      // Tack on a ".$Impl" to the qualifier.
      special =
          (Special) qq.parseExpr(qualifier + ".$Impl." + special.kind()).type(
              special.type());
    }

    if (special.kind() != Special.THIS) return special;
    //return qq.parseExpr("(%T) %E.$getProxy()", special.type(), special);
    return special;
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
