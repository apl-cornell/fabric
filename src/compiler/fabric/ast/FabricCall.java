package fabric.ast;

import jif.types.principal.Principal;
import polyglot.ast.Call;
import polyglot.ast.Expr;

public interface FabricCall extends Call {
  Expr remoteClient();
  FabricCall remoteClient(Expr remoteClient);
  Principal remoteClientPrincipal();
  FabricCall remoteClientPrincipal(Principal p);
}
