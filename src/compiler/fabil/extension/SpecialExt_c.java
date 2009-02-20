package fabil.extension;

import polyglot.ast.Expr;
import polyglot.ast.Special;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import fabil.visit.ProxyRewriter;

public class SpecialExt_c extends ExprExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ExprExt_c#rewriteProxiesImpl(fabil.visit.ProxyRewriter)
   */
  @Override
  protected Expr rewriteProxiesImpl(ProxyRewriter pr) {
    Special special = node();
    
    if (!pr.typeSystem().isFabricClass(special.type()))
      return special;
    
    TypeNode qualifier = special.qualifier();
    QQ qq = pr.qq();
    if (qualifier != null) {
      // Tack on a ".$Impl" to the qualifier.
      special =
          (Special) qq.parseExpr(qualifier + ".$Impl." + special.kind()).type(
              special.type());
    }

    if (special.kind() != Special.THIS) return special;
    return qq.parseExpr("(%T) %E.$getProxy()", special.type(), special);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ExprExt_c#node()
   */
  @Override
  public Special node() {
    return (Special) super.node();
  }

}
