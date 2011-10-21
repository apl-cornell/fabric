package fabric.ast;

import polyglot.ast.Ext;
import jif.ast.JifExtFactory;

/** Factory for extensions for all of the AST nodes in the Fabric language.
 *  Note that the created extensions need not be <code>FabricExt</code>s. */
public interface FabricExtFactory extends JifExtFactory {
  Ext extAbortStmt();
  Ext extAtomic();
  Ext extAmbNewFabricArray();
  Ext extWorker();
  Ext extFabricArrayInit();
  Ext extFabricArrayTypeNode();
  Ext extNewFabricArray();
  Ext extRemoteWorkerGetter();
  Ext extRetryStmt();
  Ext extCodebaseNode();
  Ext extCodebaseDecl();
}
