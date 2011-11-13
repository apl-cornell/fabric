package fabil.ast;

import java.util.List;

import polyglot.ast.Expr;

public interface FabricArrayInit extends Annotated, polyglot.ast.ArrayInit {
  @Override
  @SuppressWarnings("rawtypes")
  FabricArrayInit elements(List elements);
  
  @Override
  FabricArrayInit location(Expr location);
  
  Expr updateLabel();
  Expr accessLabel();
  FabricArrayInit updateLabel(Expr label);
  FabricArrayInit accessLabel(Expr accesslabel);
}
