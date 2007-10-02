package fabric.extension;

import polyglot.ast.*;
import fabric.visit.ProxyRewriter;

public class FieldAssignExt_c extends ExprExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter rewriter) {
    FieldAssign assign = node();
    Field field = (Field) assign.left();
    Receiver target = (Receiver) field.visitChild(field.target(), rewriter);
    String name = ((Id) field.visitChild(field.id(), rewriter)).id();
    Expr rhs = (Expr) field.visitChild(assign.right(), rewriter);
    return rewriter.qq().parseExpr("%E.set$" + name + "(%E)", target, rhs);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public FieldAssign node() {
    return (FieldAssign) super.node();
  }

}
