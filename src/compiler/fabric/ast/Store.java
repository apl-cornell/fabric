package fabric.ast;

import polyglot.ast.Expr;

/**
 *
 */
public interface Store extends Expr {

  /**
   * @param expr
   * @return
   */
  Expr expr(Expr expr);

  /**
   * @return
   */
  Expr expr();

  /**
   * @return
   */
  boolean exprIsNeverNull();

  /**
   * @return
   */
  boolean isLocalStore();

}
