package jif.types;

import java.util.*;

import jif.extension.LabelTypeCheckUtil;
import jif.types.hierarchy.LabelEnv;
import jif.types.label.*;
import polyglot.util.Enum;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** 
 * A <code>Constraint</code> is the superclass of label 
 * constraints and principals constraints, which 
 * are generated during type checking and label checking.
 * 
 */
public abstract class Constraint
{
    /** Kinds of constraint, either equality or inequality. */
    public static class Kind extends Enum {
        protected Kind(String name) { super(name); } 
    }

    protected final Param lhs;
    protected final Param rhs;
    protected final Kind kind;
    
    /**
     * The environment under which this constraint needs to be satisfied.
     */
    protected final LabelEnv env;

    protected final Position pos;
    
    /**
     * Error messages
     */
    protected final ConstraintMessage messages;

    /**
     * Do we want to report a violation of this constraint, or report the
     * error for a different constraint?
     */
    protected final boolean report;
    
    public Constraint(Param lhs, Kind kind, Param rhs, LabelEnv env,
              Position pos, ConstraintMessage msg, boolean report) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.kind = kind;
        this.env = env;
        this.pos = pos;
        this.messages = msg;
        this.report = report;
    }
    
    
    public Kind kind() {
        return kind;
    }
     
    public LabelEnv env() {
	return env;
    }
        
    public Position position() {
        return pos;
    }

    public boolean report() {
        return report;
    }
    
    public ConstraintMessage messages() {
        return messages;
    }

    /**
     * A message to display if this constraint is violated. This message should
     * be short, and explain without using typing rules what this constraint
     * represents. It should not refer to the names of labels (i.e., names
     * for <code>NamedLabel</code>s.
     */
    public String msg() {
        if (messages == null) return null;
        return messages.msg();
    }
    
    /**
     * A detailed message to display if this constraint is violated.
     * This message may consist of several sentences, and may refer to the
     * names of the labels, if <code>NamedLabel</code>s are used.
     */
    public String detailMsg() {
        if (messages == null) return null;
        return messages.detailMsg();
    }

    /**
     * A technical message to display if this constraint is violated. This
     * message can refer to typing rules to explain what the constraint 
     * represents, and to names of labels, if <code>NamedLabel</code>s are used.
     */
    public String technicalMsg() {
        if (messages == null) return null;
        return messages.technicalMsg();
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(lhs);
        sb.append(kind);
        sb.append(rhs);
        sb.append(" in environment ");
        sb.append(env);
        if (pos != null) sb.append("(" + pos.toString() + ")");
        
        return sb.toString();
    }
    
    
    /**
     * Produce a <code>Collection</code> of {@link Equation Equations} for this
     * constraint.
     */
    public abstract Collection getEquations();


    public boolean isCanonical() {
        return lhs.isCanonical() && rhs.isCanonical();
    }
    public abstract boolean hasVariables();
}
