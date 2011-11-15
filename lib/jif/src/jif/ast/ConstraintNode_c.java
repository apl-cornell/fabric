package jif.ast;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import jif.types.Assertion;
import polyglot.ast.Node_c;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.ExceptionChecker;
import polyglot.visit.NodeVisitor;

/** An implementation of the <code>ConstraintNode</code> interface. 
 */
public class ConstraintNode_c extends Node_c implements ConstraintNode
{
    protected Set constraints; // of Assertion 

    public ConstraintNode_c(Position pos) {
	super(pos);
    }

    public boolean isDisambiguated() {
        if (constraints == null) return false;
        for (Iterator iter = constraints.iterator(); iter.hasNext(); ) {
            Assertion a = (Assertion)iter.next();
            if (!a.isCanonical()) return false;
        }
        return super.isDisambiguated();
    }

    public Set constraints() {
	return constraints;
    }

    public ConstraintNode constraints(Set constraints) {
	ConstraintNode_c n = (ConstraintNode_c) copy();
	n.constraints = constraints;
	return n;
    }
    
    protected Assertion constraint() {
        if (constraints == null) return null;
        if (constraints.size() > 1)  throw new InternalCompilerError("Multiple constraints");
        return (Assertion)constraints.iterator().next();
    }

    protected ConstraintNode constraint(Assertion constraint) {
        ConstraintNode_c n = (ConstraintNode_c) copy();
        n.constraints = Collections.singleton(constraint);
        return n;
    }
    
    protected void setConstraint(Assertion constraint) {
        constraints = Collections.singleton(constraint);
    }
    
    /**
     * Bypass all children when performing an exception check. Constraints
     * aren't examined at runtime.
     */
    public NodeVisitor exceptionCheckEnter(ExceptionChecker ec)
    throws SemanticException
    {
        ec = (ExceptionChecker) super.exceptionCheckEnter(ec);
        return ec.bypassChildren(this);
    }

    
    public String toString() {
	if (constraints != null) {
	    return constraints.toString();
	}
	else {
	    return "<unknown-constraint>";
	}
    }
}
