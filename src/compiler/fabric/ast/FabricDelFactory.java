package fabric.ast;

import jif.ast.JifDelFactory;
import polyglot.ast.JL;

/** Factory for delegates for all of the AST nodes in the Fabric language. */
public interface FabricDelFactory extends JifDelFactory {
  JL delAbortStmt();

  JL delAtomic();

  JL delAmbNewFabricArray();

  JL delWorker();

  JL delFabricArrayInit();

  JL delFabricArrayTypeNode();

  JL delNewFabricArray();

  JL delRemoteWorkerGetter();

  JL delRetryStmt();

  JL delCodebaseNode();

  JL delCodebaseDecl();

  JL delPrincipalExpr();

}
