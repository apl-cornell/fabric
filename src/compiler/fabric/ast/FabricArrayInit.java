package fabric.ast;

import java.util.List;

import polyglot.ast.Expr;

public interface FabricArrayInit extends polyglot.ast.ArrayInit {
  FabricArrayInit elements(List<Expr> elements);
  
  FabricArrayInit location(Expr location);
  FabricArrayInit label(Expr label);
}
