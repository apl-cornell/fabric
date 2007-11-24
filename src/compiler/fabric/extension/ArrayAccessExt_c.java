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
    if (!base.isPrimitive() && !ts.isFabric(base)) return aa;

    Expr result = pr.qq().parseExpr("%E.get(%E)", array, aa.index());

    // Insert a cast if we have a pure Fabric type.
    if (ts.isFabric(base) && !ts.isJavaInlineable(base))
      result = pr.qq().parseExpr("(%T) %E", aa.type(), result);

    return result;
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
