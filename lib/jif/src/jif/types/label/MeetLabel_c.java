package jif.types.label;

import java.util.*;

import jif.translate.LabelToJavaExpr;
import jif.types.*;
import jif.types.hierarchy.LabelEnv;
import jif.visit.LabelChecker;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** An implementation of the <code>MeetLabel</code> interface. 
 */
public class MeetLabel_c extends Label_c implements MeetLabel
{
    private final Set components;
    
    public MeetLabel_c(Set components, JifTypeSystem ts, Position pos, LabelToJavaExpr trans) {
        super(ts, pos, trans);
        this.components = Collections.unmodifiableSet(flatten(components));
        if (this.components.isEmpty()) throw new InternalCompilerError("No empty meets");
    }
    
    public boolean isRuntimeRepresentable() {
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            Label c = (Label) i.next();
            
            if (! c.isRuntimeRepresentable()) {
                return false;
            }
        }
        
        return true;
    }
    public boolean isCanonical() {
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            Label c = (Label) i.next();
            
            if (! c.isCanonical()) {
                return false;
            }
        }
        
        return true;
    }
    
    protected boolean isDisambiguatedImpl() {
        return true;
    }
    /**
     * @return true iff this label is covariant.
     *
     * A label is covariant if it contains at least one covariant component.
     */
    public boolean isCovariant() {
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            Label c = (Label) i.next();
            
            if (c.isCovariant()) {
                return true;
            }
        }
        
        return false;
    }
    public boolean isComparable() {
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            Label c = (Label) i.next();
            
            if (! c.isComparable()) {
                return false;
            }
        }
        
        return true;
    }
    public boolean isEnumerable() {
        return true;
    }
    
    public boolean isBottom() {
        if (components.isEmpty()) return false;
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            Label c = (Label) i.next();
            if (c.isBottom()) {
                return true;
            }
        }
        return false;
    }

    public boolean isTop() {
        return (components.isEmpty());
    }
    
    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        if (o instanceof MeetLabel_c) {
            MeetLabel_c that = (MeetLabel_c)o;
            return this.components.equals(that.components);
        }
        if (o instanceof Label) {
            // see if it matches a singleton
            return this.components.equals(Collections.singleton(o));
        }
        return false;
    }
    public int hashCode() {
        return components.hashCode();
    }
    
    public String componentString(Set printedLabels) {
        return componentString(printedLabels, false);
    }
    private String componentString(Set printedLabels, boolean topLevel) {
        String s = "";
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            Label c = (Label) i.next();
            if (topLevel) {
                s += c.toString(printedLabels);
            }
            else {
                s += c.componentString(printedLabels);                
            }
            
            if (i.hasNext()) {
                s += " meet ";
            }
        }
        
        return s;
    }

    public String toString() {
        return "{" + componentString(new HashSet(), true) + "}";
    }

    public String toString(Set printedLabels) {
        return "{" + componentString(printedLabels, true) + "}";
    }
    
    
    public boolean leq_(Label L, LabelEnv env, LabelEnv.SearchState state) {
        if (! L.isComparable() || ! L.isEnumerable())
            throw new InternalCompilerError("Cannot compare " + L);
        
        // If this = { p1 meet .. meet pn } check that there exists an i,
        // that Pi <= L
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            Label pi = (Label) i.next();
            
            if (env.leq(pi, L, state)) {
                return true;
            }
        }
        
        return false;
    }
    
    public Set meetComponents() {
        return Collections.unmodifiableSet(components);
    }

    public Object copy() {
        MeetLabel_c l = (MeetLabel_c)super.copy();
        l.normalized = null;
        return l;
    }
    private Label normalized = null;
    public Label normalize() {
        if (normalized == null) {
            // memoize the result
            normalized = normalizeImpl();
        }
        return normalized;
    }
    private Label normalizeImpl() {
        if (components.size() == 1) {
            return (Label)components.iterator().next();
        }
        // if there is more than one PairLabel, combine them.
        JifTypeSystem ts = (JifTypeSystem)typeSystem();
        PairLabel pl = null;
        boolean combinedPL = false;
        Set nonPairLabels = new LinkedHashSet();
        for (Iterator iter = meetComponents().iterator(); iter.hasNext();) {
            Label lbl = (Label)iter.next();
            if (lbl instanceof PairLabel) {
                PairLabel p = (PairLabel)lbl;
                if (pl == null) {
                    pl = p;
                }
                else {
                    combinedPL = true;
                    pl = ts.pairLabel(position(),
                                      pl.confPolicy().meet(p.confPolicy()),
                                      pl.integPolicy().meet(p.integPolicy()));                    
                }
            }
            else {
                nonPairLabels.add(lbl);
            }
        }
        if (combinedPL) {
            nonPairLabels.add(pl);
            return ts.meetLabel(position(), nonPairLabels);
        }
        return this;
    }
    /**
     * @return An equivalent label with fewer components by pulling out
     * less restrictive policies.
     */
    protected Label simplifyImpl() {
        if (!this.isDisambiguated() || components.isEmpty()) {
            return this;
        }

        Collection comps = flatten(components);
        Set needed = new LinkedHashSet();
        JifTypeSystem jts = (JifTypeSystem) ts;

        for (Iterator i = comps.iterator(); i.hasNext(); ) {
            Label ci = ((Label) i.next()).simplify();
            
            if (ci.hasVariables() || ci.hasWritersToReaders()) {
                needed.add(ci);
            }
            else {
                boolean subsumed = false;
                
                for (Iterator j = needed.iterator(); j.hasNext(); ) {
                    Label cj = (Label) j.next();
                    
                    if (cj.hasVariables() || cj.hasWritersToReaders()) {
                        continue;
                    }

                    if (jts.leq(cj, ci)) {
                        subsumed = true;
                        break;
                    }
                    
                    if (jts.leq(ci, cj)) { 
                        j.remove();
                    }
                }
                
                if (! subsumed)
                    needed.add(ci);
            }
        }
        
        if (needed.equals(components)) {
            return this;
        }
        if (needed.size() == 1) {
            return (Label)needed.iterator().next();
        }

        return new MeetLabel_c(needed, (JifTypeSystem)ts, position(), ((JifTypeSystem_c)ts).meetLabelTranslator());
    }
    
    private static Set flatten(Set comps) {
        // check if there are any meet labels in there.
        boolean needFlattening = false;
        for (Iterator i = comps.iterator(); i.hasNext(); ) {
            Label L = (Label) i.next();
            
            if (L instanceof MeetLabel) {
                needFlattening = true;
                break;
            }
        }
        
        if (!needFlattening) return comps;
        
        Set c = new LinkedHashSet();
        for (Iterator i = comps.iterator(); i.hasNext(); ) {
            Label L = (Label) i.next();
            
            if (L.isBottom()) {
                return Collections.singleton(L);
            }
            
            if (L instanceof MeetLabel) {
                Collection lComps = ((MeetLabel)L).meetComponents();
                c.addAll(lComps);                
            }
            else {
                c.add(L);
            }
        }
        
        return c;
    }

    public ConfPolicy confProjection() {
        Set confPols = new HashSet();
        for (Iterator iter = components.iterator(); iter.hasNext();) {
            Label c = (Label)iter.next();
            confPols.add(c.confProjection());
        }
        return ((JifTypeSystem)ts).meetConfPolicy(position, confPols);
    }
    public IntegPolicy integProjection() {
        Set integPols = new HashSet();
        for (Iterator iter = components.iterator(); iter.hasNext();) {
            Label c = (Label)iter.next();
            integPols.add(c.integProjection());
        }
        return ((JifTypeSystem)ts).meetIntegPolicy(position, integPols);
    }

    public List throwTypes(TypeSystem ts) {
        List throwTypes = new ArrayList();
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            Label L = (Label) i.next();
            throwTypes.addAll(L.throwTypes(ts));
        }
        return throwTypes; 
    }

    public Label subst(LabelSubstitution substitution) throws SemanticException {        
        if (components.isEmpty() || substitution.stackContains(this) || !substitution.recurseIntoChildren(this)) {
            return substitution.substLabel(this);
        }
        substitution.pushLabel(this);
        boolean changed = false;
        Set s = new LinkedHashSet();
        
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            Label c = (Label) i.next();
            Label newc = c.subst(substitution);
            if (!changed && newc != c) {
                changed = true;
                s = new LinkedHashSet();
                // add all the previous laebls
                for (Iterator j = components.iterator(); j.hasNext(); ) {
                    Label d = (Label) j.next();
                    if (c == d) break;
                    s.add(d);
                }
            }
            if (changed) s.add(newc);
        }
        
        substitution.popLabel(this);
        
        if (!changed) return substitution.substLabel(this);
        
        JifTypeSystem ts = (JifTypeSystem)this.typeSystem();
        Label newmeetLabel = ts.meetLabel(this.position(), flatten(s));
        return substitution.substLabel(newmeetLabel);
    }

    public boolean hasWritersToReaders() {
        for (Iterator iter = meetComponents().iterator(); iter.hasNext();) {
            Label ci = (Label)iter.next();
            if (ci.hasWritersToReaders()) return true;
        }
        return false;        
    }

    
    public Set variableComponents() {
        Set s = new LinkedHashSet();
        for (Iterator iter = meetComponents().iterator(); iter.hasNext();) {
            Label ci = (Label)iter.next();
            s.addAll(ci.variableComponents());
        }
        return s;
    }

    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        JifTypeSystem ts = (JifTypeSystem)A.typeSystem();
        PathMap X = ts.pathMap().N(A.pc()).NV(A.pc());
        
        if (components.isEmpty()) {
            return X;
        }

        A = (JifContext)A.pushBlock();
        
        for (Iterator i = components.iterator(); i.hasNext(); ) {
            A.setPc(X.N(), lc);
            Label c = (Label) i.next();
            PathMap Xc = c.labelCheck(A, lc);
            X = X.join(Xc);            
        }
        return X;
    }    
}
