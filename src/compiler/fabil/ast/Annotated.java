package fabil.ast;

import polyglot.ast.Expr;

/**
 * Exprs that have label and location annotations should implement this.
 */
public interface Annotated extends Expr {
  Expr location();

  Annotated location(Expr location);
}
