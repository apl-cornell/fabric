package jif.types.label;

import java.util.List;
import java.util.Set;

import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.PathMap;
import jif.types.hierarchy.LabelEnv;
import jif.types.hierarchy.LabelEnv.SearchState;
import jif.visit.LabelChecker;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.Position;

/** The integrity projection of a (non meet, join or pair) label. 
 */
public class IntegProjectionPolicy_c extends Policy_c implements IntegPolicy {
    private final Label label;
    
    public IntegProjectionPolicy_c(Label label, JifTypeSystem ts, Position pos) {
        super(ts, pos); 
        this.label = label;
    }
    
    public Label label() {
        return label;
    }

    public boolean isSingleton() {
        return true;
    }
    public boolean isCanonical() {
        return label.isCanonical();
    }
    public boolean isRuntimeRepresentable() {
        return label.isRuntimeRepresentable();
    }

    protected Policy simplifyImpl() {
        return this;
    }
        
    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        if (o instanceof IntegProjectionPolicy_c) {
            IntegProjectionPolicy_c that = (IntegProjectionPolicy_c)o;
            return (this.label == that.label || 
                    (this.label != null && this.label.equals(that.label)));
        }
        return false;
    }
    
    public int hashCode() {
        return label.hashCode();
    }
    
    public boolean leq_(IntegPolicy p, LabelEnv env, SearchState state) {
        if (p instanceof IntegProjectionPolicy_c) {
            return env.leq(this.label(), ((IntegProjectionPolicy_c)p).label(), state);
        }
        if (p.isTopIntegrity()) return true;
        Label ub = env.findUpperBound(this.label);
        return env.leq(ub.integProjection(), p, state);
    }

    public String toString(Set printedLabels) {
        return "I(" + label.componentString(printedLabels) + ")";
    }
    
    public List throwTypes(TypeSystem ts) {
        return label.throwTypes(ts);
    }

    public Policy subst(LabelSubstitution substitution) throws SemanticException {

        Label newLabel = label.subst(substitution);
        if (newLabel == label) {
            return substitution.substPolicy(this).simplify();
        }

        JifTypeSystem ts = (JifTypeSystem)typeSystem();
        Policy newPolicy = ts.integProjection(this.label());
        return substitution.substPolicy(newPolicy).simplify();
    }
    
    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        return label.labelCheck(A, lc);
    }
    
    public boolean isBottomIntegrity() {
        return label.isBottom();
    }
    public boolean isTopIntegrity() {
        return label.isTop();
    }        
    public boolean isTop() {
        return isTopIntegrity();
    }
    public boolean isBottom() {
        return isBottomIntegrity();
    }
    public boolean hasWritersToReaders() {
        return label.hasWritersToReaders();        
    }    
    public boolean hasVariables() {
        return label.hasVariables();        
    }    
   public IntegPolicy meet(IntegPolicy p) {
        JifTypeSystem ts = (JifTypeSystem)this.ts;
        return ts.meet(this, p);
    }
    public IntegPolicy join(IntegPolicy p) {
        JifTypeSystem ts = (JifTypeSystem)this.ts;
        return ts.join(this, p);
    }    
}
