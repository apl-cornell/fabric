package jif.ast;


/** An immutable representation of the Jif <code>ActsFor constraint</code>. 
 *  <p>Grammar: <tt>actsFor (actor, granter)</tt></p>
 *  <p> The <tt>ActsFor constraint</tt> only appears in the <tt>where</tt> 
 *  clause of a procedure header. </p>
 */
public interface ActsForConstraintNode extends ConstraintNode
{
    /** Gets the actor principal. */
    PrincipalNode actor();

    /** Returns a copy of this node with the actor updated. */
    ActsForConstraintNode actor(PrincipalNode actor);

    /** Gets the granter principal. */
    PrincipalNode granter();

    /** Returns a copy of this node with the granter updated. */
    ActsForConstraintNode granter(PrincipalNode granter);
}
