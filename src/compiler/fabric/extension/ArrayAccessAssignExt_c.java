package fabric.extension;

import polyglot.ast.ArrayAccess;
import polyglot.ast.ArrayAccessAssign;
import polyglot.ast.Expr;
import fabric.visit.ProxyRewriter;

public class ArrayAccessAssignExt_c extends ExprExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter rewriter) {
    ArrayAccessAssign assign = node();
    ArrayAccess left = (ArrayAccess) assign.left();
    Expr array = (Expr) left.visitChild(left.array(), rewriter);
    Expr index = (Expr) left.visitChild(left.index(), rewriter);
    Expr right = (Expr) assign.visitChild(assign.right(), rewriter);
    return rewriter.qq().parseExpr("%E.set(%E, %E)", array, index, right);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public ArrayAccessAssign node() {
    return (ArrayAccessAssign) super.node();
  }

}
