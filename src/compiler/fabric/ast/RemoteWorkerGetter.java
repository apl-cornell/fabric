package fabric.ast;

import polyglot.ast.Expr;

public interface RemoteWorkerGetter extends Expr {
  Expr remoteWorkerName();

  RemoteWorkerGetter remoteWorkerName(Expr expr);
}
