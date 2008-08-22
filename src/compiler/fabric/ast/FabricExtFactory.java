package fabric.ast;

import polyglot.ast.Ext;
import jif.ast.JifExtFactory;

/**
 * Classes implementing this interface can construct extension nodes for all of
 * the ast nodes defined by the fabric language.
 */
public interface FabricExtFactory extends JifExtFactory {
  Ext extAtomic();
}
