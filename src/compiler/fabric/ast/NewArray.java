package fabric.ast;

import polyglot.ast.Expr;

public interface NewArray extends polyglot.ast.NewArray {
  Expr location();
  NewArray location(Expr location);
}
