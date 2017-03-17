package bolt.ast;

import polyglot.ast.Expr;
import polyglot.ast.Term;

/**
 * Specifies the kind, length, and label of an array dimension.
 */
public interface ArrayDim extends Term {

  ArrayDimKind kind();

  ArrayDim kind(ArrayDimKind kind);

  Expr length();

  ArrayDim length(Expr length);

  Expr label();

  ArrayDim label(Expr label);

}
