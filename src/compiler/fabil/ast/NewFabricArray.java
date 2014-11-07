package fabil.ast;

import polyglot.ast.Expr;

public interface NewFabricArray extends polyglot.ast.NewArray, Annotated {
  @Override
  FabricArrayInit init();

  @Override
  NewFabricArray init(polyglot.ast.ArrayInit init);

  Expr updateLabel();

  Expr accessPolicy();

  NewFabricArray updateLabel(Expr label);

  NewFabricArray accessPolicy(Expr accessPolicy);
}
