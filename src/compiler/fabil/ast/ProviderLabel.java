package fabil.ast;

import polyglot.ast.Expr;
import polyglot.ast.TypeNode;

/**
 * AST interface for representing the provider label for a class.
 */
public interface ProviderLabel extends Expr {
  TypeNode typeNode();
}
