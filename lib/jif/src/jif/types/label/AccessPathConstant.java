package jif.types.label;

import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * Represents a final access path consisting of a constant label or principal,
 * that is, either a NewLabel expression or an External principal. For example,
 * "new label {Alice:}" or "Alice". These access paths only arise during method
 * instantiation. For example, given a method
 * 
 * <pre>
 * 
 *    int{*lb} m(label{} lb);
 *  
 * </pre>
 * 
 * and a call site
 * 
 * <pre>
 * 
 *    m(new label{Alice:});
 *  
 * </pre>
 * 
 * the type system needs to be precise enough to realize that the method call
 * returns an int with label {Alice:}. This is achieved through the use of
 * AccessPathConstants.
 * 
 * @see jif.types.label.AccessPath
 * @see jif.ast.JifUtil#exprToAccessPath(Expr, JifContext)
 */
public class AccessPathConstant extends AccessPathRoot {
    /**
     * Either a jif.types.label.Label or a jif.types.principal.Principal
     */
    private Object constantValue;

    private Type type;

    public AccessPathConstant(Label label, Type type, Position pos) {
        super(pos);
        this.constantValue = label;
        this.type = type;
    }

    public AccessPathConstant(Principal principal, Type type, Position pos) {
        super(pos);
        this.constantValue = principal;
        this.type = type;
    }

    public Object constantValue() {
        return constantValue;
    }

    public boolean isLabelConstant() {
        return ((JifTypeSystem)type.typeSystem()).isLabel(type);
    }
    public boolean isNeverNull() { return false; }

    public boolean isPrincipalConstant() {
        return ((JifTypeSystem)type.typeSystem()).isPrincipal(type);
    }

    public boolean isCanonical() {
        return true;
    }

    public AccessPath subst(AccessPathRoot r, AccessPath e) {
        throw new InternalCompilerError(
                "Shouldn't be calling subst on an AccessPathConstant! \"" + this + "\"");
    }

    public String toString() {
        return constantValue.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof AccessPathConstant) {
            AccessPathConstant that = (AccessPathConstant)o;
            return constantValue.equals(that.constantValue);
        }
        return false;
    }

    public int hashCode() {
        return constantValue.hashCode();
    }

    public Type type() {
        return type;
    }
}