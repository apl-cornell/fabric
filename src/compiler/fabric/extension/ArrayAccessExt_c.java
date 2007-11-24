package fabric.extension;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Expr;
import polyglot.types.Type;
import fabric.types.FabricTypeSystem;
import fabric.visit.ProxyRewriter;

public class ArrayAccessExt_c extends ExprExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    ArrayAccess aa = node();

    // Only rewrite arrays of primitives and fabric.lang.Objects.
    FabricTypeSystem ts = pr.typeSystem();
    Expr array = aa.array();
    Type base = array.type().toArray().ultimateBase();
    if (base.isPrimitive() || ts.isFabric(base))
      return pr.qq().parseExpr("(%T) %E.get(%E)", aa.type(), array, aa.index());
    
    return aa;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public ArrayAccess node() {
    return (ArrayAccess) super.node();
  }

}
