package fabil.ast;

import java.util.List;

import polyglot.ast.Expr;

public interface FabricArrayInit extends Annotated, polyglot.ast.ArrayInit {
  @Override
  FabricArrayInit elements(List<Expr> elements);

  @Override
  FabricArrayInit location(Expr location);

  Expr updateLabel();

  Expr accessPolicy();

  FabricArrayInit updateLabel(Expr label);

  FabricArrayInit accessPolicy(Expr accesslabel);
}
