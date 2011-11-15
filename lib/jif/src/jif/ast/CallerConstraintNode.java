package jif.ast;

import java.util.List;

/** A caller constraint node. It requests the caller of
 *  a method are granted certain authorities. 
 *  <p>Grammar: <tt>caller(principal_list)</tt>
 */
public interface CallerConstraintNode extends ConstraintNode
{
    /**Gets the list of principals who need to grant their
     * authorities to the caller. */
    List principals();
    
    /**Returns a copy of this node with the principal list updated. */
    CallerConstraintNode principals(List principals);
}
