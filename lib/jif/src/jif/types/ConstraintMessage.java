package jif.types;

import jif.types.Constraint.Kind;
import polyglot.util.InternalCompilerError;


/** 
 * A <code>LabelConstraintMessage</code> provides error messages for 
 * label constraints.
 */
public class ConstraintMessage
{
    /**
     * A message to display if the constraint is violated. This message should
     * be short, and explain without using typing rules what this constraint
     * represents. It should not refer to the names of labels (i.e., names
     * for <code>NamedLabel</code>s.
     */
    public String msg() {
        return null;
    }
    
    /**
     * A detailed message to display if the constraint is violated.
     * This message may consist of several sentences, and may refer to the
     * names of the labels, if <code>NamedLabel</code>s are used.
     */
    public String detailMsg() {
        return msg();
    }

    /**
     * A technical message to display if the constraint is violated. This
     * message can refer to typing rules to explain what the constraint 
     * represents, and to names of labels, if <code>NamedLabel</code>s are used.
     */
    public String technicalMsg() {
        return msg();
    }        
    
    private Constraint constraint;
    public void setConstraint(Constraint c) {
        this.constraint = c;
    }
    
    public NamedLabel namedLhs() {
        if (constraint instanceof LabelConstraint) {
            return ((LabelConstraint)constraint).namedLhs();
        }
        throw new InternalCompilerError("Inappropriate call of namedLhs");
    }
    public NamedLabel namedRhs() {
        if (constraint instanceof LabelConstraint) {
            return ((LabelConstraint)constraint).namedRhs();
        }
        throw new InternalCompilerError("Inappropriate call of namedRhs");
    }
    public Kind kind() {
        return constraint.kind();        
    }
}
