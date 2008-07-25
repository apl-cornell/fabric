package fabil.extension;

import polyglot.ast.Expr;
import polyglot.ast.Node;

/**
 * This class provides common functionality to the New and NewArray for managing
 * a location field
 */
public abstract class LocatedExt_c extends ExprExt_c {
  public Expr location() {
    return location;
  }

  public Node location(Expr location) {
    LocatedExt_c result = (LocatedExt_c) copy();
    result.location = location;

    return node().ext(result);
  }

  protected Expr location;
}
