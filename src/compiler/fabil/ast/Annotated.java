package fabil.ast;

import polyglot.ast.Expr;

/**
 * Exprs that have label and location annotations should implement this.
 */
public interface Annotated extends Expr {
  Expr label();
  Expr accessLabel();
  Expr location();
  
  Annotated label(Expr label);
  Annotated accessLabel(Expr accesslabel);
  Annotated location(Expr location);
}
