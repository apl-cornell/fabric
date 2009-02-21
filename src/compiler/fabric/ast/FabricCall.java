package fabric.ast;

import polyglot.ast.Call;
import polyglot.ast.Expr;

public interface FabricCall extends Call {
  Expr remoteClient();
  FabricCall remoteClient(Expr remoteClient);
}
