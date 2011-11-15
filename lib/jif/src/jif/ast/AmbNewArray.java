package jif.ast;

import java.util.List;

import polyglot.ast.*;

/** An ambiguous new array expression.
 *  The ambiguity arises because in <code>new T.a[e][m]</code>, <code>e</code>
 *  may be either a dimension expression or a label/principal parameter. 
 */
public interface AmbNewArray extends Expr, Ambiguous
{
    /** Gets the base type T.a from an AmbNewArray representing T.a[e][m]. */
    TypeNode baseType();
    
    /** Returns a copy of this node with the base type updated. */
    AmbNewArray baseType(TypeNode baseType);

    /** Gets the expr e from an AmbNewArray representing T.a[e][m].
     *  @return either an Expr or a String
     */
    Object expr();
    
    /** Gets the additional expression dimensions m from an AmbNewArray representing T.a[e][m].  */
    List dims();
    
    /** Returns a copy of this node with the additional expression dimensions updated.  */
    AmbNewArray dims(List dims);
    
    /** Gets the number of unspecified dimensions [] from an AmbNewArray representing T.a[e][m][][]...[] */
    int additionalDims();
    
    /** Returns a copy of this node with the number of unspecified dimensions updated. */
    AmbNewArray additionalDims(int addDims);
}
