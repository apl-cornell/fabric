package jif.types.principal;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import jif.translate.CannotPrincipalToJavaExpr_c;
import jif.translate.JifToJavaRewriter;
import jif.translate.PrincipalToJavaExpr;
import jif.types.*;
import jif.types.label.VariableGatherer;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** An abstract implementation of the <code>Principal</code> interface. 
 */
public abstract class Principal_c extends TypeObject_c implements Principal {
    PrincipalToJavaExpr toJava;

    protected Set variables = null; // memoized

    public Principal_c(JifTypeSystem ts, Position pos) {
        this(ts, pos, new CannotPrincipalToJavaExpr_c());
    }

    public Principal_c(JifTypeSystem ts, Position pos, PrincipalToJavaExpr toJava) {
	super(ts, pos);
        this.toJava = toJava;
    }

    public Expr toJava(JifToJavaRewriter rw) throws SemanticException {
        return toJava.toJava(this, rw);
    }

    public final boolean hasVariables() {
        return !variables().isEmpty();
    }

    public boolean isTopPrincipal() { return false; }
    public boolean isBottomPrincipal() { return false; }
    public abstract boolean isCanonical();
    public abstract boolean isRuntimeRepresentable();
    public abstract boolean equalsImpl(TypeObject o);
    public abstract int hashCode();
    
    public List throwTypes(TypeSystem ts) {
        return Collections.EMPTY_LIST;
    }

    public Principal subst(LabelSubstitution substitution) throws SemanticException {
        return substitution.substPrincipal(this);
    }

    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        JifTypeSystem ts = (JifTypeSystem)A.typeSystem();
        return ts.pathMap().N(A.pc()).NV(A.pc());
    }        
    
    public Set variables() {
        if (variables == null) {
            VariableGatherer lvg = new VariableGatherer();
            try {
                this.subst(lvg);
            }
            catch (SemanticException e) {
                throw new InternalCompilerError(e);
            }
            variables = lvg.variables;
        }
        return variables;
    }    
    
    public Principal simplify() {
        // XXX TODO implement in some of the subclasses.
        return this;        
    }
}
