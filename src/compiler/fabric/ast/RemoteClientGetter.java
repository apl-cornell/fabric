package fabric.ast;

import polyglot.ast.Expr;

public interface RemoteClientGetter extends Expr {
  Expr remoteClientName();
  RemoteClientGetter remoteClientName(Expr expr);
}
