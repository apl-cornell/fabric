package bolt.ast;

import polyglot.ast.Expr;
import polyglot.ast.Term;

/**
 * An array dimension expression paired with an array dimension kind.
 */
public interface ArrayDimExpr extends Term {
  ArrayDimKind kind();

  ArrayDimExpr kind(ArrayDimKind kind);

  Expr expr();

  ArrayDimExpr expr(Expr expr);
}
