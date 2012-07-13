package fabric.types;

import codebases.types.CodebaseContext;
import jif.types.JifContext;
import polyglot.ast.Expr;
import polyglot.types.Context;

public interface FabricContext extends JifContext, CodebaseContext {
  Context pushLocation(Expr location);

  Expr location();
}
