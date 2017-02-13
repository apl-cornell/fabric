package bolt.ast;

import polyglot.ast.Expr;

/**
 * A principal-creation expression.
 */
public interface NewPrincipal extends Expr {
  Principal principal();

  NewPrincipal principal(Principal principal);
}
