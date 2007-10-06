package fabric.extension;

import polyglot.ast.*;
import polyglot.types.Flags;
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
    Flags flags = field.flags();
    Receiver target = (Receiver) field.visitChild(field.target(), rewriter);
    String name = ((Id) field.visitChild(field.id(), rewriter)).id();
    Expr rhs = (Expr) field.visitChild(assign.right(), rewriter);

    // If we're assigning to a final field, we must be in a constructor or an
    // initializer. Keep it as an assignment, since no setters will be
    // generated.
    if (flags.isFinal()) return assign.right(rhs);

    String quote = "%T";
    if (target instanceof Expr) quote = "%E";
    
    if (flags.isStatic()) quote += ".$static";
    
    return rewriter.qq()
        .parseExpr(quote + ".set$" + name + "(%E)", target, rhs);
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
