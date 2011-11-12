package fabil.ast;

import java.util.List;

import polyglot.ast.Expr;

public interface FabricArrayInit extends Annotated, polyglot.ast.ArrayInit {
  @Override
  @SuppressWarnings("rawtypes")
  FabricArrayInit elements(List elements);
  
  @Override
  FabricArrayInit location(Expr location);
  
  @Override
  FabricArrayInit label(Expr label);
}
