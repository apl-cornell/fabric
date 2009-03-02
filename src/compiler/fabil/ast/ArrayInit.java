package fabil.ast;

import java.util.List;

import polyglot.ast.Expr;

public interface ArrayInit extends Annotated, polyglot.ast.ArrayInit {
  ArrayInit elements(List<Expr> elements);
  
  ArrayInit location(Expr location);
  ArrayInit label(Expr label);
}
