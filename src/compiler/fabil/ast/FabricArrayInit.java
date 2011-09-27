package fabil.ast;

import java.util.List;

import polyglot.ast.Expr;

public interface FabricArrayInit extends Annotated, polyglot.ast.ArrayInit {
  @SuppressWarnings("rawtypes")
  FabricArrayInit elements(List elements);
  
  FabricArrayInit location(Expr location);
  FabricArrayInit label(Expr label);
}
