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
public abstract class Equation
{
    protected Equation(Constraint constraint) {
        this.constraint = constraint;
    }
    protected final Constraint constraint;
    
    public LabelEnv env() {return constraint().env();}
    public Position position() {return constraint().position();}

    public Constraint constraint() {
        return constraint;
    }

    /**
     * Return a <code>Set</code> of variables that occur in either the 
     * left or right hand side.
     */
    public abstract Set variables();

    public abstract int hashCode();

    public abstract boolean equals(Object o);
    public abstract String toString();

    /**
     * Replace the <code>lhs</code> and <code>rhs</code> with the result of 
     * <code>lhs.subst(subst)</code> and <code>rhs.subst(subst)</code> 
     * respectively.
     */
    public abstract void subst(LabelSubstitution subst) throws SemanticException;
}
