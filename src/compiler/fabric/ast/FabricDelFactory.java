package fabric.ast;

import jif.ast.JifDelFactory;
import polyglot.ast.JLDel;

/** Factory for delegates for all of the AST nodes in the Fabric language. */
public interface FabricDelFactory extends JifDelFactory {
  JLDel delAbortStmt();

  JLDel delAtomic();

  JLDel delAmbNewFabricArray();

  JLDel delWorker();

  JLDel delFabricArrayInit();

  JLDel delFabricArrayTypeNode();

  JLDel delNewFabricArray();

  JLDel delRemoteWorkerGetter();

  JLDel delRetryStmt();

  JLDel delCodebaseNode();

  JLDel delCodebaseDecl();

  JLDel delPrincipalExpr();

  /**
   * @return
   */
  JLDel delStore();

  /**
   * @return
   */
  JLDel delAccessPolicy();

}
