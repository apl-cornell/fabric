package fabil.extension;

import polyglot.ast.Cast;
import polyglot.ast.Eval;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import fabil.visit.ProxyRewriter;

public class EvalExt_c extends FabILExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.FabILExt_c#rewriteProxies(fabil.visit.ProxyRewriter)
   */
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    // Strip off any casts that may have been generated.
    Eval eval = node();
    Expr expr = eval.expr();
    while (expr instanceof Cast) {
      expr = ((Cast) expr).expr();
    }
    
    return eval.expr(expr);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public Eval node() {
    return (Eval) super.node();
  }
}
