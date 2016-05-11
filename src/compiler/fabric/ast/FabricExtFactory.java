package fabric.ast;

import jif.ast.JifExtFactory;
import polyglot.ast.Ext;

/**
 * Factory for extensions for all of the AST nodes in the Fabric language. Note
 * that the created extensions need not be <code>FabricExt</code>s.
 */
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

  Ext extStageStmt();

  Ext extCodebaseNode();

  Ext extCodebaseDecl();

  Ext extStore();

  Ext extAccessPolicy();
}
