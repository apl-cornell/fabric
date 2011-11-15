package jif.types.label;

import java.util.*;

import jif.types.*;
import jif.types.hierarchy.LabelEnv;
import jif.types.hierarchy.LabelEnv.SearchState;
import jif.visit.LabelChecker;
import polyglot.types.*;
import polyglot.util.Position;

/** The confidentiality projection of a (non meet, join or pair) label. 
 */
public class ConfProjectionPolicy_c extends Policy_c implements ConfPolicy {
    private final Label label;
    
    public ConfProjectionPolicy_c(Label label, JifTypeSystem ts, Position pos) {
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
        if (o instanceof ConfProjectionPolicy_c) {
            ConfProjectionPolicy_c that = (ConfProjectionPolicy_c)o;
            return (this.label == that.label || 
                    (this.label != null && this.label.equals(that.label)));
        }
        return false;
    }
    
    public int hashCode() {
        return label.hashCode();
    }
    
    public boolean leq_(ConfPolicy p, LabelEnv env, SearchState state) {
        if (p instanceof ConfProjectionPolicy_c) {
            return env.leq(this.label(), ((ConfProjectionPolicy_c)p).label(), state);
        }
        if (p.isTopConfidentiality()) return true;
        Label ub = env.findUpperBound(this.label);
        return env.leq(ub.confProjection(), p, state);
    }

    public String toString(Set printedLabels) {
        return "C(" + label.componentString(printedLabels) + ")";
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
        Policy newPolicy = ts.confProjection(newLabel);
        return substitution.substPolicy(newPolicy).simplify();
    }
    
    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        return label.labelCheck(A, lc);
    }
    
    public boolean isBottomConfidentiality() {
        return label.isBottom();
    }
    public boolean isTopConfidentiality() {
        return label.isTop();
    }        
    public boolean isTop() {
        return isTopConfidentiality();
    }
    public boolean isBottom() {
        return isBottomConfidentiality();
    }
    public boolean hasWritersToReaders() {
        return label.hasWritersToReaders();        
    }    
    public boolean hasVariables() {
        return label.hasVariables();        
    }    
    public ConfPolicy meet(ConfPolicy p) {
        JifTypeSystem ts = (JifTypeSystem)this.ts;
        return ts.meet(this, p);
    }
    public ConfPolicy join(ConfPolicy p) {
        JifTypeSystem ts = (JifTypeSystem)this.ts;
        return ts.join(this, p);
    }    
}
