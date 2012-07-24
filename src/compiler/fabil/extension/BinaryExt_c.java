package fabil.extension;

import polyglot.ast.Binary;
import polyglot.ast.Expr;
import polyglot.qq.QQ;
import fabil.visit.ProxyRewriter;

public class BinaryExt_c extends ExprExt_c {
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    // rewrite == and != to invoke isEquals as appropriate
    Binary node = node();
    QQ qq = pr.qq();

    if (node.left().type().isPrimitive() || node.right().type().isPrimitive())
      return node;

    if (node.operator().equals(Binary.EQ))
      return qq.parseExpr(" fabric.lang.Object._Proxy.idEquals(%E, %E)",
          node.left(), node.right());
    else if (node.operator().equals(Binary.NE))
      return qq.parseExpr("!fabric.lang.Object._Proxy.idEquals(%E, %E)",
          node.left(), node.right());
    else return node;
  }

  @Override
  public Binary node() {
    return (Binary) super.node();
  }
}
