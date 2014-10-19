package fabric.extension;

import jif.ast.JifExt;
import polyglot.ast.Ext;

/**
 * This interface tags all fabric extension objects. All FabricExt objects
 * should be the second extension of an ast node, i.e. if <code>n</code> is a
 * node, then <code>n.ext.ext</code> refers to the FabricExt object (
 * <code>n.ext</code> should be a {@link JifExt}).
 *
 * @author mdgeorge
 */
public interface FabricExt extends Ext {

}
