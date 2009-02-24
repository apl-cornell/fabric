package fabric.ast;

import polyglot.ast.Ext;
import jif.ast.JifExtFactory;

/** Factory for extensions for all of the AST nodes in the fabric language.
 *  Note that the created extensions need not be <code>FabricExt</code>s. */
public interface FabricExtFactory extends JifExtFactory {
  Ext extAtomic();
  Ext extAbort();
  Ext extRetry();
  Ext extClient();
  Ext extRemoteClientGetter();
}
