package fabric.extension;

import java.util.Collections;

import polyglot.ast.*;
import fabric.visit.ProxyRewriter;

public class UnaryExt_c extends FabricExt_c {

  @Override
  public Node rewriteProxiesOverride(ProxyRewriter rewriter) {
    // Handle (pre/post)-(inc/dec)rement on fields.
    Unary unary = node();
    Expr expr = unary.expr();
    if (!(expr instanceof Field)) return null;

    Unary.Operator op = unary.operator();
    if (op != Unary.POST_DEC && op != Unary.POST_INC && op != Unary.PRE_DEC
        && op != Unary.PRE_INC) return null;

    Call getterCall = (Call) unary.visitChild(expr, rewriter);
    if (op.isPrefix()) {
      // XXX Hacky. Mangle the getter call to obtain a setter call.
      String name = getterCall.name();
      name = "set" + name.substring(3);

      Call setterCall = getterCall.name(name);
      Expr arg =
          rewriter.qq().parseExpr(
              "%E " + (op == Unary.PRE_DEC ? "-" : "+") + " 1", getterCall);
      return setterCall.arguments(Collections.singletonList(arg));
    }

    // XXX Hacky. Mangle the getter call to obtain a post-inc/dec call.
    String name = getterCall.name();
    name = (op == Unary.POST_DEC ? "postDec" : "postInc") + name.substring(3);
    return getterCall.name(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public Unary node() {
    return (Unary) super.node();
  }

}
