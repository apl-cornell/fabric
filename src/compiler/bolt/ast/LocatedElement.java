package bolt.ast;

import polyglot.ast.Expr;
import polyglot.ast.Node;

/**
 * Interface for nodes that can have location annotations. This interface is
 * implemented by the Bolt extensions of these nodes.
 */
public interface LocatedElement {
  Expr location();

  Node location(Expr location);
}
