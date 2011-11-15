package jif.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.Throw;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;

/** Jif extension of the <code>Throw</code> node.
 *  
 *  @see polyglot.ast.Throw
 */
public class JifThrowDel extends JifJL_c
{
    public JifThrowDel() {
    }

    /**
     * This flag records whether the object thrown by this throw expression
     * is never null. This flag is by default false, but may be set to true by the
     * dataflow analysis performed by jif.visit.NotNullChecker
     */
    private boolean isThrownNeverNull = false;

    public void setThrownIsNeverNull() {
        isThrownNeverNull = true;
    }
    public boolean thrownIsNeverNull() {
        return isThrownNeverNull;
    }
    
    /** 
     *  List of Types of exceptions that might get thrown.
     * 
     * This differs from the method defined in Throw_c in that it does not
     * throw a null pointer exception if the thrown object is guaranteed to be 
     * non-null
     */
    public List throwTypes(TypeSystem ts) {
        Throw t = (Throw)node();

        // if the exception that a throw statement is given to throw is null,
        // then a NullPointerException will be thrown.
        if (!isThrownNeverNull && !ts.NullPointerException().equals(t.expr().type())) {
            return CollectionUtil.list(t.expr().type(), ts.NullPointerException());
        }
        return Collections.singletonList(t.expr().type());
    }
}
