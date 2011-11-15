package jif.types.principal;

import java.util.List;

import jif.translate.DynamicPrincipalToJavaExpr_c;
import jif.translate.PrincipalToJavaExpr;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.PathMap;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathConstant;
import jif.visit.LabelChecker;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** An implementation of the <code>DynamicPrincipal</code> interface. 
 */
public class DynamicPrincipal_c extends Principal_c implements DynamicPrincipal {
    private final AccessPath path;

    public DynamicPrincipal_c(AccessPath path, JifTypeSystem ts, Position pos, PrincipalToJavaExpr toJava) {
        super(ts, pos, toJava);
        this.path = path;
        if (path instanceof AccessPathConstant) {
            throw new InternalCompilerError("Don't expect to get AccessPathConstants for dynamic labels");
        }
    }

    public AccessPath path() {
        return path;
    }

    public boolean isRuntimeRepresentable() { return true; }
    public boolean isCanonical() { return path.isCanonical(); }

    public String toString() {
        if (Report.should_report(Report.debug, 1)) { 
            return "<pr-dynamic " + path + ">";
        }
        return path().toString();
    }


    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        if (! (o instanceof DynamicPrincipal)) {
            return false;
        }

        DynamicPrincipal that = (DynamicPrincipal) o;
        return (this.path.equals(that.path()));
    }

    public int hashCode() {
        return path.hashCode();
    }

    public List throwTypes(TypeSystem ts) {
        return path.throwTypes(ts);
    }

    public Principal subst(LabelSubstitution substitution) throws SemanticException {
        AccessPath newPath = substitution.substAccessPath(path);
        if (newPath != path) {
            JifTypeSystem ts = (JifTypeSystem)typeSystem();
            Principal newDP = ts.pathToPrincipal(this.position(), newPath);
            return substitution.substPrincipal(newDP);
        }
        return substitution.substPrincipal(this);
    }
    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        return path.labelcheck(A, lc);
    }

}
