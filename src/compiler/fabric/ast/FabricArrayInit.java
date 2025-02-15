package fabric.ast;

import java.util.List;

import polyglot.ast.Expr;

public interface FabricArrayInit extends polyglot.ast.ArrayInit {
  @Override
  FabricArrayInit elements(List<Expr> elements);

  FabricArrayInit location(Expr location);

  Expr location();

  FabricArrayInit label(Expr label);

  Expr label();
}
