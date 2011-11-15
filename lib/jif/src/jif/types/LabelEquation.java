package jif.types;

import java.util.*;

import jif.types.hierarchy.LabelEnv;
import jif.types.label.JoinLabel;
import jif.types.label.Label;
import jif.types.label.MeetLabel;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** 
 * Label equation derived from a label constraint. A label equation represents
 * an inequality that must be satisfied, namely <code>lhs <= rhs</code>
 * in the environment <code>env</code>.
 * 
 * 
 * @see jif.types.LabelConstraint 
 */
public class LabelEquation extends Equation
{
    private Label lhs;
    private Label rhs;

    /**
     * Constructor
     */
    LabelEquation(Label lhs, Label rhs, LabelConstraint constraint) 
    {
        super(constraint);
        this.lhs = lhs;
        this.rhs = rhs.simplify();

        if (lhs instanceof JoinLabel) {
            throw new InternalCompilerError(
            "LHS of equation must not be a join label.");
        }
        if (rhs instanceof MeetLabel) {
            throw new InternalCompilerError(
            "LHS of equation must not be a meet label.");
        }
    }


    public Label lhs() {return lhs;}
    public Label rhs() {return rhs;}
    public LabelEnv env() {return constraint().env();}
    public Position position() {return constraint().position();}

    public LabelConstraint labelConstraint() {
        return (LabelConstraint)constraint;
    }
    /**
     * Return a <code>List</code> of variable components that occur in either the 
     * left or right hand side.
     */
    public List variableComponents() {
        List l = new LinkedList();
        l.addAll(lhs.variableComponents());
        l.addAll(rhs.variableComponents());
        return l;
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
        if (! (o instanceof LabelEquation)) {
            return false;
        } 

        LabelEquation eqn = (LabelEquation) o;
        
        if (this.constraint != eqn.constraint) return false;
        
        return lhs.equals(eqn.lhs) && rhs.equals(eqn.rhs);
    }

    public String toString() {
        return lhs.toString() + " <= " + rhs.toString() + " in environment " +
        env() + " (produced from " + 
        labelConstraint().lhsLabel() + labelConstraint().kind() + labelConstraint().rhsLabel() + ") " +
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
