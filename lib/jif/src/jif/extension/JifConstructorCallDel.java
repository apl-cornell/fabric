package jif.extension;

import jif.types.JifContext;
import polyglot.types.Context;


/** The Jif extension of the <code>ConstructorCall</code> node. 
 * 
 *  @see polyglot.ast.ConstructorCall
 */
public class JifConstructorCallDel extends JifJL_c
{
    public JifConstructorCallDel() {
    }
    
    /* (non-Javadoc)
     * @see polyglot.ast.NodeOps#enterScope(polyglot.types.Context)
     */
    public Context enterScope(Context c) {
        return ((JifContext)c).pushConstructorCall();
    }
}
