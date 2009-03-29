package fabric.ast;

import polyglot.ast.Ext;
import jif.ast.JifExtFactory;

/** Factory for extensions for all of the AST nodes in the fabric language.
 *  Note that the created extensions need not be <code>FabricExt</code>s. */
public interface FabricExtFactory extends JifExtFactory {
  Ext extAbort();
  Ext extAtomic();
  Ext extClient();
  Ext extFabricArrayInit();
  Ext extFabricArrayTypeNode();
  Ext extNewFabricArray();
  Ext extRemoteClientGetter();
  Ext extRetry();
}
