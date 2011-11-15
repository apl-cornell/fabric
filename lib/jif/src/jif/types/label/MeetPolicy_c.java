package jif.types.label;

import java.util.*;

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
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** Represents the meet of a number of policies. 
 */
public abstract class MeetPolicy_c extends Policy_c {
    private final Set meetComponents;
    private Integer hashCode = null;
    
    public MeetPolicy_c(Set components, JifTypeSystem ts, Position pos) {
        super(ts, pos);
        this.meetComponents = Collections.unmodifiableSet(flatten(components));
        if (this.meetComponents.isEmpty()) {
            throw new InternalCompilerError("Empty collection!");
        }
    }
    
    public boolean isSingleton() {
        return meetComponents.size() == 1;
    }
    public boolean isCanonical() {
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();            
            if (! c.isCanonical()) {
                return false;
            }
        }        
        return true;
    }
    public boolean isRuntimeRepresentable() {
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();            
            if (! c.isRuntimeRepresentable()) {
                return false;
            }
        }        
        return true;
    }
            
    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        if (o instanceof MeetPolicy_c) {
            MeetPolicy_c that = (MeetPolicy_c)o;
            return this.hashCode() == that.hashCode() && this.meetComponents.equals(that.meetComponents);
        }
        if (o instanceof Policy) {
            // see if it matches a singleton
            return this.meetComponents.equals(Collections.singleton(o));
        }
        return false;
    }
    public int hashCode() {
        if (hashCode == null) {
            hashCode = new Integer(meetComponents.hashCode());
        }
        return hashCode.intValue();
    }
    
    public String toString(Set printedLabels) {
        String s = "";
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();
            s += c.toString(printedLabels);
            
            if (i.hasNext()) {
                s += " meet ";
            }
        }
        
        return s;
    }
    
    protected boolean leq_(Policy p, LabelEnv env, SearchState state) {
        // If this = { .. Pi .. , check there exists an i
        // such that Pi <= p
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            Policy pi = (Policy) i.next();
            
            if (env.leq(pi, p, state)) {
                return true;
            }
        }
        
        return false;
    }
    
    public Collection meetComponents() {
        return Collections.unmodifiableCollection(meetComponents);
    }

    /**
     * @return An equivalent label with fewer components by pulling out
     * less restrictive policies.
     */
    protected Policy simplifyImpl() {
        if (meetComponents.isEmpty()) {
            return this;
        }
        if (meetComponents.size() == 1) {
            return ((Policy)meetComponents.iterator().next()).simplify();
        }

        Collection comps = flatten(meetComponents);
        Set needed = new LinkedHashSet();
        JifTypeSystem jts = (JifTypeSystem) ts;

        for (Iterator i = comps.iterator(); i.hasNext(); ) {
            Policy ci = ((Policy) i.next()).simplify();
            
            if (ci.hasVariables() || ci.hasWritersToReaders()) {
                needed.add(ci);
            }
            else {
                boolean subsumed = false;
                
                for (Iterator j = needed.iterator(); j.hasNext(); ) {
                    Policy cj = (Policy) j.next();
                    
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
        
        if (needed.equals(meetComponents)) {
            return this;
        }
        if (needed.size() == 1) {
            return (Policy)needed.iterator().next();
        }

        return constructMeetPolicy(needed, position);
    }
    
    protected abstract Policy constructMeetPolicy(Set components, Position pos);
    
    private static Set flatten(Set comps) {
        // check if there are any meet policies in there.
        boolean needFlattening = false;
        for (Iterator i = comps.iterator(); i.hasNext(); ) {
            Policy p = (Policy) i.next();
            
            if (p instanceof MeetPolicy_c) {
                needFlattening = true;
                break;
            }
        }
        
        if (!needFlattening) return comps;
        
        Set c = new LinkedHashSet();
        for (Iterator i = comps.iterator(); i.hasNext(); ) {
            Policy p = (Policy) i.next();
            
            if (p.isBottom()) {
                return Collections.singleton(p);
            }
            
            if (p instanceof MeetPolicy_c) {
                Collection lComps = ((MeetPolicy_c)p).meetComponents();
                c.addAll(lComps);                
            }
            else {
                c.add(p);
            }
        }
        
        return c;
    }

    public List throwTypes(TypeSystem ts) {
        List throwTypes = new ArrayList();
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            Policy L = (Policy) i.next();
            throwTypes.addAll(L.throwTypes(ts));
        }
        return throwTypes; 
    }

    public Policy subst(LabelSubstitution substitution) throws SemanticException {        
        if (meetComponents.isEmpty()) {
            return substitution.substPolicy(this).simplify();
        }
        boolean changed = false;
        Set s = new LinkedHashSet();
        
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();
            Policy newc = c.subst(substitution);
            if (newc != c) changed = true;
            s.add(newc);
        }
        
        if (!changed) return substitution.substPolicy(this).simplify();
        
        Policy newMeetPolicy = constructMeetPolicy(flatten(s), position);
        return substitution.substPolicy(newMeetPolicy).simplify();
    }
    public boolean hasWritersToReaders() {
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();
            if (c.hasWritersToReaders()) return true;
        }
        return false;
    }    
    public boolean hasVariables() {
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();
            if (c.hasVariables()) return true;
        }
        return false;
    }    

    
    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        JifTypeSystem ts = (JifTypeSystem)A.typeSystem();
        PathMap X = ts.pathMap().N(A.pc()).NV(A.pc());
        
        if (meetComponents.isEmpty()) {
            return X;
        }

        A = (JifContext)A.pushBlock();
        
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            A.setPc(X.N(), lc);
            Policy c = (Policy) i.next();
            PathMap Xc = c.labelCheck(A, lc);
            X = X.join(Xc);            
        }
        return X;
    }

    public boolean isTop() {
        // top if all policies is top
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();            
            if (!c.isTop()) {
                return false;
            }
        }        
        return true;
    }
    public boolean isBottom() {
        // bottom if any policy is bottom
        for (Iterator i = meetComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();            
            if (c.isBottom()) {
                return true;
            }
        }        
        return false;
    }
}
