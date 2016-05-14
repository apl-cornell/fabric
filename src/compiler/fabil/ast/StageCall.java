package fabil.ast;

import polyglot.ast.Expr;
import polyglot.ast.ProcedureCall;

/**
 * Special expr for staging (if the second argument is true) and then returning
 * the first argument.
 */
public interface StageCall extends ProcedureCall, Expr {

  /**
   * Get the first argument to the stage call.
   */
  public Expr origExpr();

  /**
   * Update the first argument to the stage call.
   */
  public StageCall origExpr(Expr newOrigExpr);

  /**
   * Get the second argument to the stage call (the boolean representing whether
   * to stage before returning the first argument).
   */
  public Expr flagExpr();

  /**
   * Update the second argument to the stage call.
   */
  public StageCall flagExpr(Expr newFlagExpr);

}
