package bolt.ast;

import polyglot.ast.Expr;

/**
 * An expression used as a label.
 */
public interface ExprLabel extends Label {
  Expr expr();

  Label expr(Expr expr);
}
