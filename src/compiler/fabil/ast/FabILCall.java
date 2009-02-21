package fabil.ast;

import polyglot.ast.Call;
import polyglot.ast.Expr;

public interface FabILCall extends Call {
  Expr remoteClient();
  FabILCall remoteClient(Expr remoteClient);
}
