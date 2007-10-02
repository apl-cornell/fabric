package fabric.extension;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Expr;
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
    return pr.qq().parseExpr("(%T) %E.get(%E)", aa.type(), aa.array(),
        aa.index());
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
