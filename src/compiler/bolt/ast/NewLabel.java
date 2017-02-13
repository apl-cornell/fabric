package bolt.ast;

import polyglot.ast.Expr;

/**
 * A label-creation expression.
 */
public interface NewLabel extends Expr {
  Label label();

  NewLabel label(Label label);
}
