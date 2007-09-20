package fabric.ast;

import polyglot.ast.Expr;

/**
 * <code>New</code> nodes in Fabric contain information on where the object
 * should be located.
 */
public interface New extends polyglot.ast.New {
  Expr location();
  New location(Expr location);
}
