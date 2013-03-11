package fabil.extension;

import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class CastExt_c extends ExprExt_c {
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    Cast cast = (Cast) node();
    // Only Fabric classes have proxies
    if (!hasProxy(pr, cast.castType())) return cast;
    // Get the exact proxy before we cast.
    QQ qq = pr.qq();
    return qq.parseExpr("(%T) fabric.lang.Object._Proxy.$getProxy(%E)",
        cast.castType(), cast.expr());
  }

  private boolean hasProxy(ProxyRewriter pr, TypeNode type) {
    FabILTypeSystem ts = pr.typeSystem();
    return ts.isFabricReference(type) && !ts.isJavaInlineable(type);
  }
}
