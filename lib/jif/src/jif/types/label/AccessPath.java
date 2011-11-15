package jif.types.label;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.hierarchy.LabelEnv;
import jif.visit.LabelChecker;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Copy;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * An AccessPath represents a final access path. A final access path is of the
 * form "R.f1. ... .fn", where "R" is one of
 * <ul>
 * <li>a final local variable
 * <li>the special expression "this"
 * <li>a class type
 * </ul>
 * and each "fi" is a final field dereference.
 * 
 * <p>
 * AccessPaths are used in the Jif type system to represent dynamic principals
 * and dynamic labels.
 * 
 * <p>
 * For technical reasons, there are two subclasses of AccessPath that do not
 * directly represent final access paths.
 * {@link jif.types.label.AccessPathConstant AccessPathConstant}is used to
 * represent label or principal expressions, such as "new label {Alice:}" or
 * "Alice". (@link jif.types.label.AccessPathUninterpreted AccessPathUninterpreted}
 * is used to represent non-final access paths, for example "this.m()".
 *  
 */
public abstract class AccessPath implements Serializable, Copy {
    private Position position;

    protected AccessPath(Position pos) {
        this.position = pos;
    }

    public abstract boolean equals(Object o);
    public abstract boolean isCanonical();
    public abstract boolean isUninterpreted();

    /**
     * Return the result of substituting the root r with the access path
     * e. For example, given a field access path "this.f", substituting
     * the access path root "this" for the access path "o.g" will result
     * in the access path "o.g.f". This method is used to instantiate
     * procedures for calls, and labels of fields. 
     */
    public abstract AccessPath subst(AccessPathRoot r, AccessPath e);

    /**
     * The type of the access path (when the access path is regarded
     * as an expression).
     */
    public abstract Type type();

    public abstract int hashCode();

    public abstract boolean isNeverNull();

    public abstract PathMap labelcheck(JifContext A, LabelChecker lc);

    public abstract AccessPathRoot root();
    
    public final Position position() {
        return position;
    }

    /**
     * Go through the path, check that all the type information is set
     * correctly, and check that every field access is to a final field,
     * and any local used is final 
     * 
     * @param A
     */
    public void verify(JifContext A) throws SemanticException {
    }

    public String exprString() {
        return toString();
    }

    /**
     * Return a list of types that may be thrown as a result of the runtime
     * evaluation of this path.
     */
    public List throwTypes(TypeSystem ts) {
        return Collections.EMPTY_LIST;
    }
    
    public Object copy() {
        try {
            AccessPath ap = (AccessPath) super.clone();

            return ap;
        }
        catch (CloneNotSupportedException e) {
            throw new InternalCompilerError("Java clone() weirdness.");
        }
    }

    public abstract boolean equivalentTo(AccessPath p, LabelEnv env);
}