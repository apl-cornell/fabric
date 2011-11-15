package jif.ast;

import java.util.List;

import jif.types.JifTypeSystem;
import jif.types.Param;
import jif.types.principal.Principal;
import polyglot.ast.Expr_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;

/** An implementation of the <code>PrincipalNode</code> interface. 
 */
public abstract class PrincipalNode_c extends Expr_c implements PrincipalNode
{
    Principal principal;

    public PrincipalNode_c(Position pos) {
	super(pos);
    }

    public Principal principal() {
	return principal;
    }

    public PrincipalNode principal(Principal principal) {
	PrincipalNode_c n = (PrincipalNode_c) copy();
	n.principal = principal;
	return n;
    }

    public Param parameter() {
	return principal();
    }
    
    public String toString() {
	if (principal != null) {
	    return principal.toString();
	}
	else {
	    return "<unknown-principal>";
	}
    }
//    public Object constantValue() {
//        return principal;
//    }

    public boolean isDisambiguated() {
        return principal != null && principal.isCanonical() && super.isDisambiguated();
    }
    
    public List throwTypes(TypeSystem ts) {
        return principal().throwTypes(ts);
    }

    /** Type check the expression. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem)tc.typeSystem();
        return type(ts.Principal());
    }
}
