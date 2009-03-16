package fabil.extension;

import polyglot.ast.Expr;
import polyglot.ast.Instanceof;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class InstanceofExt_c extends ExprExt_c {

  @Override
  protected Expr rewriteProxiesImpl(ProxyRewriter pr) {
    FabILTypeSystem ts = pr.typeSystem();
    Instanceof node = node();

    TypeNode compareType = node.compareType();
    if (!ts.isFabricReference(compareType)) return node;

    // Get the exact proxy before checking instanceof.
    QQ qq = pr.qq();
    return qq.parseExpr(
        "fabric.lang.Object._Proxy.$getProxy(%E) instanceof %T", node.expr(),
        compareType);
  }

  @Override
  public Instanceof node() {
    return (Instanceof) super.node();
  }

}
