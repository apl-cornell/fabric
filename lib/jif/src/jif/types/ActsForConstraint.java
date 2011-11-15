package jif.types;

import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.types.principal.Principal;

/** The acts-for constraint. 
 */
public interface ActsForConstraint extends Assertion {
    public Principal actor();
    public ActsForConstraint actor(Principal actor);

    public Principal granter();
    public ActsForConstraint granter(Principal granter);
    
    public boolean isEquiv();
    public Expr toJava(JifToJavaRewriter rw) throws SemanticException;
}
