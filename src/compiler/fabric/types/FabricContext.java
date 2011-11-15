package fabric.types;

import polyglot.ast.Expr;
import polyglot.types.Context;
import jif.types.JifContext;

public interface FabricContext extends JifContext {
  Context pushLocation(Expr location);
  Expr location();
}
