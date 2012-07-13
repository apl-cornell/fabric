package fabil.extension;

import polyglot.ast.Expr;
import polyglot.ast.Special;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import fabil.visit.ProxyRewriter;

public class SpecialExt_c extends ExprExt_c {

  @Override
  protected Expr rewriteProxiesImpl(ProxyRewriter pr) {
    Special special = node();

    if (!pr.typeSystem().isFabricClass(special.type())) return special;

    TypeNode qualifier = special.qualifier();
    QQ qq = pr.qq();
    if (qualifier != null) {
      // Tack on a "._Impl" to the qualifier.
      special =
          (Special) qq.parseExpr(qualifier + "._Impl." + special.kind()).type(
              special.type());
    }

    if (special.kind() != Special.THIS) return special;
    return qq.parseExpr("(%T) %E.$getProxy()", special.type(), special);
  }

  @Override
  public Special node() {
    return (Special) super.node();
  }

}
