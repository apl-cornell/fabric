package jif.types;

import java.util.*;

import jif.types.hierarchy.LabelEnv;
import jif.types.label.Label;
import jif.types.principal.ConjunctivePrincipal;
import jif.types.principal.DisjunctivePrincipal;
import jif.types.principal.Principal;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** 
 * Principal equation derived from a principal constraint. A principal equation represents
 * an actsfor relation that must be satisfied, namely <code>lhs actsfor rhs</code>
 * in the environment <code>env</code>.
 * 
 * 
 * @see jif.types.PrincipalConstraint 
 */
public class PrincipalEquation extends Equation
{
    private Principal lhs;
    private Principal rhs;


    /**
     * Constructor
     */
    PrincipalEquation(Principal lhs, Principal rhs, PrincipalConstraint constraint) 
    {
        super(constraint);
        this.lhs = lhs;
        this.rhs = rhs.simplify();

        if (lhs instanceof DisjunctivePrincipal) {
            throw new InternalCompilerError(
            "LHS of equation must not be a disjunctive principal.");
        }
        if (rhs instanceof ConjunctivePrincipal) {
            throw new InternalCompilerError(
            "LHS of equation must not be a conjunctive principal.");
        }
    }


    public Principal lhs() {return lhs;}
    public Principal rhs() {return rhs;}
    public LabelEnv env() {return constraint().env();}
    public Position position() {return constraint().position();}

    public PrincipalConstraint principalConstraint() {
        return (PrincipalConstraint)constraint;
    }

    /**
     * Return a <code>Set</code> of variables that occur in either the 
     * left or right hand side.
     */
    public Set variables() {
        Set l = new LinkedHashSet();
        l.addAll(lhs.variables());
        l.addAll(rhs.variables());
        return l;
    }

    public int hashCode() { return lhs.hashCode() ^ rhs.hashCode(); }

    public boolean equals(Object o) {
        if (! (o instanceof PrincipalEquation)) {
            return false;
        } 

        PrincipalEquation eqn = (PrincipalEquation) o;
        
        if (this.constraint != eqn.constraint) return false;
        
        return lhs.equals(eqn.lhs) && rhs.equals(eqn.rhs);
    }

    public String toString() {
        return lhs.toString() + " actsfor " + rhs.toString() + " in environment " +
        env() + " (produced from " + 
        principalConstraint().lhsPrincipal() + principalConstraint().kind() + principalConstraint().rhsPrincipal() + ") " +
        position();
    }

    /**
     * Replace the <code>lhs</code> and <code>rhs</code> with the result of 
     * <code>lhs.subst(subst)</code> and <code>rhs.subst(subst)</code> 
     * respectively.
     */
    public void subst(LabelSubstitution subst) throws SemanticException {
        rhs = rhs.subst(subst);
        lhs = lhs.subst(subst);
    }
}
