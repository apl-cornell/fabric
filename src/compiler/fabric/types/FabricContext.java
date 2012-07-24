package fabric.types;

import jif.types.JifContext;
import polyglot.ast.Expr;
import polyglot.types.Context;
import codebases.types.CodebaseContext;

public interface FabricContext extends JifContext, CodebaseContext {
  Context pushLocation(Expr location);

  Expr location();
}
