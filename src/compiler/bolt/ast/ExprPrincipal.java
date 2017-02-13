package bolt.ast;

import polyglot.ast.Expr;

/**
 * An expression used as a principal.
 */
public interface ExprPrincipal extends Principal {
  Expr expr();

  ExprPrincipal expr(Expr expr);
}
