package jif.ast;

import java.util.List;

/** An authority constraint node. It represents an authority
 *  constraint of a method or a class. 
 *  <p>Grammar: <tt>authority(principal_list)</tt>
 */
public interface AuthConstraintNode extends ConstraintNode
{
    /** Gets the list of principal who grants their authorities. */
    List principals();
    
    /** Returns a copy of this node with the principal list updated. */
    AuthConstraintNode principals(List principals);
}
