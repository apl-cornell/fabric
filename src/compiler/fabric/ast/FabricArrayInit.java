package fabric.ast;

import java.util.List;

import polyglot.ast.Expr;

public interface FabricArrayInit extends polyglot.ast.ArrayInit {
  @Override
  @SuppressWarnings("rawtypes")
  FabricArrayInit elements(List elements);

  FabricArrayInit location(Expr location);

  FabricArrayInit label(Expr label);
}
