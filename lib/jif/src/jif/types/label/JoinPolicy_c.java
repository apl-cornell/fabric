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

/** Represents a join of a number of policies. 
 */
public abstract class JoinPolicy_c extends Policy_c {
    private final Set joinComponents;
    private Integer hashCode = null;
    
    public JoinPolicy_c(Set components, JifTypeSystem ts, Position pos) {
        super(ts, pos);
        this.joinComponents = Collections.unmodifiableSet(flatten(components));
        if (this.joinComponents.isEmpty()) {
            throw new InternalCompilerError("Empty collection!");
        }
    }
    
    public boolean isSingleton() {
        return joinComponents.size() == 1;
    }
    public boolean isCanonical() {
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();            
            if (! c.isCanonical()) {
                return false;
            }
        }        
        return true;
    }
    public boolean isRuntimeRepresentable() {
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();            
            if (! c.isRuntimeRepresentable()) {
                return false;
            }
        }        
        return true;
    }
            
    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        if (o instanceof JoinPolicy_c) {
            JoinPolicy_c that = (JoinPolicy_c)o;
            return this.hashCode() == that.hashCode() && this.joinComponents.equals(that.joinComponents);
        }
        if (o instanceof Policy) {
            // see if it matches a singleton
            return this.joinComponents.equals(Collections.singleton(o));
        }
        return false;
    }
    public int hashCode() {
        if (hashCode == null) {
            hashCode = new Integer(joinComponents.hashCode());
        }
        return hashCode.intValue();
    }
    
    public String toString(Set printedLabels) {
        String s = "";
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();
            s += c.toString(printedLabels);
            
            if (i.hasNext()) {
                s += "; ";
            }
        }
        
        return s;
    }
    
    protected boolean leq_(Policy p, LabelEnv env, SearchState state) {
        // If this = { .. Pi .. } and L = { .. Pj' .. }, check if for all i,
        // there exists a j, such that Pi <= Pj'
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            Policy pi = (Policy) i.next();
            
            if (! env.leq(pi, p, state)) {
                return false;
            }
        }
        
        return true;
    }
    
    public Collection joinComponents() {
        return Collections.unmodifiableCollection(joinComponents);
    }

    /**
     * @return An equivalent label with fewer components by pulling out
     * less restrictive policies.
     */
    protected Policy simplifyImpl() {
        if (joinComponents.isEmpty()) {
            return this;
        }
        if (joinComponents.size() == 1) {
            return ((Policy)joinComponents.iterator().next()).simplify();
        }

        Collection comps = flatten(joinComponents);
        Set needed = new LinkedHashSet();
        JifTypeSystem jts = (JifTypeSystem) ts;

        for (Iterator i = comps.iterator(); i.hasNext(); ) {
            Policy ci = ((Policy) i.next()).simplify();
            
            boolean subsumed = false;
            
            if (ci.hasVariables() || ci.hasWritersToReaders()) {
                needed.add(ci);
            }
            else {
                for (Iterator j = needed.iterator(); j.hasNext(); ) {
                    Policy cj = (Policy) j.next();
                    
                    if (cj.hasVariables() || cj.hasWritersToReaders()) {
                        continue;
                    }
                    
                    if (jts.leq(ci, cj)) {
                        subsumed = true;
                        break;
                    }
                    
                    if (jts.leq(cj, ci)) { 
                        j.remove();
                    }
                }
                
                if (! subsumed)
                    needed.add(ci);
            }
        }
        
        if (needed.equals(joinComponents)) {
            return this;
        }
        if (needed.size() == 1) {
            return (Policy)needed.iterator().next();
        }

        return constructJoinPolicy(needed, position);
    }
    
    protected abstract Policy constructJoinPolicy(Set components, Position pos);
    
    private static Set flatten(Set comps) {
        // check if there are any join policies in there.
        boolean needFlattening = false;
        for (Iterator i = comps.iterator(); i.hasNext(); ) {
            Policy p = (Policy) i.next();
            
            if (p instanceof JoinPolicy_c) {
                needFlattening = true;
                break;
            }
        }
        
        if (!needFlattening) return comps;
        
        Set c = new LinkedHashSet();
        for (Iterator i = comps.iterator(); i.hasNext(); ) {
            Policy p = (Policy) i.next();
            
            if (p.isTop()) {
                return Collections.singleton(p);
            }
            
            if (p instanceof JoinPolicy_c) {
                Collection lComps = ((JoinPolicy_c)p).joinComponents();
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
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            Policy L = (Policy)i.next();
            throwTypes.addAll(L.throwTypes(ts));
        }
        return throwTypes; 
    }

    public Policy subst(LabelSubstitution substitution) throws SemanticException {        
        if (joinComponents.isEmpty()) {
            return substitution.substPolicy(this).simplify();
        }
        boolean changed = false;
        Set s = new LinkedHashSet();
        
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();
            Policy newc = c.subst(substitution);
            if (newc != c) changed = true;
            s.add(newc);
        }
        
        if (!changed) return substitution.substPolicy(this).simplify();
        
        Policy newJoinPolicy = constructJoinPolicy(flatten(s), position);
        return substitution.substPolicy(newJoinPolicy).simplify();
    }
    
    public boolean hasWritersToReaders() {
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();
            if (c.hasWritersToReaders()) return true;
        }
        return false;
    }    

    public boolean hasVariables() {
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();
            if (c.hasVariables()) return true;
        }
        return false;
    }    

    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        JifTypeSystem ts = (JifTypeSystem)A.typeSystem();
        PathMap X = ts.pathMap().N(A.pc()).NV(A.pc());
        
        if (joinComponents.isEmpty()) {
            return X;
        }

        A = (JifContext)A.pushBlock();
        
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            A.setPc(X.N(), lc);
            Policy c = (Policy) i.next();
            PathMap Xc = c.labelCheck(A, lc);
            X = X.join(Xc);            
        }
        return X;
    }
    
    public boolean isTop() {
        // top if any policy is top
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();            
            if (c.isTop()) {
                return true;
            }
        }        
        return false;
    }
    public boolean isBottom() {
        // bottom if all policies are bottom
        for (Iterator i = joinComponents.iterator(); i.hasNext(); ) {
            Policy c = (Policy) i.next();            
            if (! c.isBottom()) {
                return false;
            }
        }        
        return true;
    }
}
