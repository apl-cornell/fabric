package fabric.ast;

import jif.types.principal.Principal;
import polyglot.ast.Call;
import polyglot.ast.Expr;

public interface FabricCall extends Call {
  Expr remoteWorker();

  FabricCall remoteWorker(Expr remoteWorker);

  Principal remoteWorkerPrincipal();

  FabricCall remoteWorkerPrincipal(Principal p);
}
