package jif.extension;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.Assign;
import polyglot.types.TypeSystem;

/** The Jif extension of the <code>FieldAssign</code> node. 
 */
public class JifFieldAssignDel extends JifAssignDel
{
    public JifFieldAssignDel() {
    }

    /** 
     * This differs from the method defined in FieldAssign_c in that it does not
     * throw a null pointer exception if the receiver is guaranteed to be 
     * non-null
     */
    public List throwTypes(TypeSystem ts) {
        List l = super.throwTypes(ts);

        Assign a = (Assign)node();
        if (!((JifFieldDel)a.left().del()).targetIsNeverNull()) {
            l.add(ts.NullPointerException());
        }

        return l;
    }  

}
