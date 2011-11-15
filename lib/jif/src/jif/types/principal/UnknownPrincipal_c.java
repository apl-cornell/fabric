package jif.types.principal;

import jif.translate.JifToJavaRewriter;
import jif.types.JifTypeSystem;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** An implementation of the <code>UnknownPrincipal</code> interface. 
 */
public class UnknownPrincipal_c extends Principal_c
                               implements UnknownPrincipal
{
    public UnknownPrincipal_c(JifTypeSystem ts, Position pos) {
	super(ts, pos);
    }

    public boolean isRuntimeRepresentable() { return false; }
    public boolean isCanonical() { return false; }

    public String toString() { return "<unknown principal>"; }

    public Expr toJava(JifToJavaRewriter rw) throws SemanticException {
	throw new InternalCompilerError("Cannot translate an unknown principal.");
    }

    public boolean equalsImpl(TypeObject o) {
        return this == o;
    }

    public int hashCode() {
        return -572;
    }
}
