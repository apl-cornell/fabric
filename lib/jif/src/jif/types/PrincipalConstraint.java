package jif.types;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import jif.types.hierarchy.LabelEnv;
import jif.types.label.Variable;
import jif.types.principal.ConjunctivePrincipal;
import jif.types.principal.DisjunctivePrincipal;
import jif.types.principal.Principal;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** 
 * A <code>PrincipalConstraint</code> represents a constraint on principals, which 
 * may either be an actsfor or an equivalence constraint. 
 * <code>PrincipalConstraint</code>s are generated during type checking and label checking.
 * <code>PrincipalConstraint</code>s in turn produce {@link Equation Equations}
 * which are what the {@link Solver Solver} will use to find a satisfying 
 * assignment for {@link Variable Variables}.
 * 
 */
public class PrincipalConstraint extends Constraint
{
    /**
     * An equivalence kind of constraint. That is, the constraint requires that 
     * lhs actsfor rhs and rhs actsfor lhs. 
     */
    public static final Kind EQUIV = new Kind(" equiv ");
    
    /**
     * An actsfor kind of constraint. That is, the constraint requires that
     * lhs actsfor rhs.
     */
    public static final Kind ACTSFOR = new Kind(" actsfor "); 
    
    public PrincipalConstraint(Principal lhs, Kind kind, Principal rhs, LabelEnv env,
              Position pos, ConstraintMessage msg, boolean report) {
        super(lhs, kind, rhs, env, pos, msg, report);
    }
    
    public Principal lhsPrincipal() {
	return (Principal)lhs;
    }
    
    
    public Principal rhsPrincipal() {
	return (Principal)rhs;
    }
                

    /**
     * Produce a <code>Collection</code> of {@link Equation Equations} for this
     * constraint.
     */
    public Collection getEquations() {
        Collection eqns = new LinkedList();
        
        if (kind == ACTSFOR) {
            addActsforEqns(eqns, lhsPrincipal(), rhsPrincipal());
        }
        else if (kind == EQUIV) {
            addActsforEqns(eqns, lhsPrincipal(), rhsPrincipal());
            addActsforEqns(eqns, rhsPrincipal(), lhsPrincipal());
        }
        else {
            throw new InternalCompilerError("Inappropriate kind of equation: " + kind);
        }
        
        return eqns;
        
    }
    
    /**
     * Produce equations that require <code>left</code> to act for <code>right</code>, 
     * and add them to <code>eqns</code>.
     */
    protected void addActsforEqns(Collection eqns, Principal left, Principal right) {
        left = left.simplify();
        right = right.simplify();
        if (left instanceof DisjunctivePrincipal) {
            for (Iterator i = ((DisjunctivePrincipal)left).disjuncts().iterator(); i.hasNext(); ) {
                Principal jc = (Principal) i.next();
                addActsforEqns(eqns, jc, right);                
            }            
        }
        else if (right instanceof ConjunctivePrincipal) {
            for (Iterator i = ((ConjunctivePrincipal)right).conjuncts().iterator(); i.hasNext(); ) {
                Principal mc = (Principal) i.next();
                addActsforEqns(eqns, left, mc);                
            }                        
        }
        else {
            Equation eqn = new PrincipalEquation(left, right, this);
            eqns.add(eqn);            
        }
    }
    public boolean hasVariables() {
        return lhsPrincipal().hasVariables() || rhsPrincipal().hasVariables();
    } 
    
}
