package jif.types.label;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jif.translate.CannotLabelToJavaExpr_c;
import jif.translate.JifToJavaRewriter;
import jif.translate.LabelToJavaExpr;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.PathMap;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.types.TypeObject_c;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * An abstract implementation of the <code>Label</code> interface.
 */
public abstract class Label_c extends TypeObject_c implements Label {
    protected String description;

    protected LabelToJavaExpr toJava;
    
    protected Set variables = null; // memoized

    protected Label_c() {
        super();
    }

    public Label_c(JifTypeSystem ts, Position pos, LabelToJavaExpr toJava) {
        super(ts, pos);
        this.toJava = toJava;
    }

    public Label_c(JifTypeSystem ts, Position pos) {
        this(ts, pos, new CannotLabelToJavaExpr_c());
    }
    public Object copy() {
        Label_c l = (Label_c)super.copy();
        l.variables = null;
        l.simplified = null;
        return l;
    }

    public String description() {
        return description;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public boolean hasVariableComponents() {
        return !variableComponents().isEmpty();
    }

    public final boolean hasVariables() {
        return !variables().isEmpty();
    }

    public boolean hasWritersToReaders() {
        return false;
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

    public Expr toJava(JifToJavaRewriter rw) throws SemanticException {
        return toJava.toJava(this, rw);
    }

    //
    /**
     * By default, a label is not Bottom
     */
    public boolean isBottom() {
        return false;
    }

    public boolean isTop() {
        return false;
    }

    public boolean isInvariant() {
        return !isCovariant();
    }
    
    /**
     * Check if the label is disambiguated, without recursing into child labels.
     */
    protected abstract boolean isDisambiguatedImpl();
    
    public final boolean isDisambiguated() {
        final boolean[] result = new boolean[1];
        result[0] = true;
        try {
            this.subst(new LabelSubstitution() {
                public Label substLabel(Label L) throws SemanticException {
                    if (result[0] && L instanceof Label_c) {
                        result[0] = ((Label_c)L).isDisambiguatedImpl();
                    }
                    return L;
                }
                public Principal substPrincipal(Principal p) throws SemanticException {
                    return p;
                }    
      });
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Unexpected semantic exception", e);
        }
        return result[0];
    }

    public String toString() {
        return "{" + componentString(new HashSet()) + "}";
    }

    public String toString(Set printedLabels) {
        return "{" + componentString(printedLabels) + "}";
    }

    public String componentString() {
        return componentString(new HashSet());
    }

    abstract public String componentString(Set printedLabels);

    public abstract boolean equalsImpl(TypeObject t);

    private Label simplified = null;
    public final Label simplify() {
        // memoize the result
        if (simplified == null) {
            simplified = ((Label_c)this.normalize()).simplifyImpl();
        }
        return simplified;
    }
    protected Label simplifyImpl() {
        return this;
    }
    
    public Label normalize() {
        return this;
    }

    public ConfPolicy confProjection() {
        return ((JifTypeSystem)ts).confProjection(this);
    }

    public IntegPolicy integProjection() {
        return ((JifTypeSystem)ts).integProjection(this);
    }

    public List throwTypes(TypeSystem ts) {
        return Collections.EMPTY_LIST;
    }

    public Label subst(LabelSubstitution substitution) throws SemanticException {
        return substitution.substLabel(this);
    }

    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        JifTypeSystem ts = (JifTypeSystem)A.typeSystem();
        return ts.pathMap().N(A.pc()).NV(A.pc());
    }

    public Set variableComponents() {
        return Collections.EMPTY_SET;
    }
    
}