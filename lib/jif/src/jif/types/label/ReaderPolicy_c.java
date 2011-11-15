package jif.types.label;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.PathMap;
import jif.types.hierarchy.LabelEnv;
import jif.types.hierarchy.LabelEnv.SearchState;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** An implementation of the <code>PolicyLabel</code> interface. 
 */
public class ReaderPolicy_c extends Policy_c implements ReaderPolicy {
    private final Principal owner;
    private final Principal reader;
    
    public ReaderPolicy_c(Principal owner, Principal reader, JifTypeSystem ts, Position pos) {
        super(ts, pos); 
        if (owner == null) throw new InternalCompilerError("null owner");
        this.owner = owner;
        this.reader = reader;
    }    
    
    public Principal owner() {
        return this.owner;
    }
    public Principal reader() {
        return reader;
    }
        
    public boolean isSingleton() {
        return true;
    }
    public boolean isCanonical() {
        return owner.isCanonical() && reader.isCanonical();
    }
    public boolean isRuntimeRepresentable() {
        return owner.isRuntimeRepresentable() && reader.isRuntimeRepresentable();
    }
    
    protected Policy simplifyImpl() {
        return this;
    }
        
    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        if (o instanceof ReaderPolicy_c) {
            ReaderPolicy_c that = (ReaderPolicy_c)o;
            if (this.owner == that.owner || (this.owner != null && this.owner.equals(that.owner))) {
                return this.reader.equals(that.reader);
            }
        }
        return false;
    }
    
    public int hashCode() {
        return (owner==null?0:owner.hashCode()) ^ reader.hashCode() ^ 948234;
    }
    
    public boolean leq_(ConfPolicy p, LabelEnv env, SearchState state) {
        if (this.isBottomConfidentiality() || p.isTopConfidentiality())
            return true;

        // if this policy is o:_, then o allows
        // all principals to read info, and thus does
        // not restrict who may read
        if (reader.isBottomPrincipal()) {
            return true;
        }
        if (p instanceof ReaderPolicy) {
            ReaderPolicy that = (ReaderPolicy) p;            
            // this = { o  : .. ri .. }
            // that = { o' : .. rj' .. }
            
            // o' >= o
            if (!env.actsFor(that.owner(), this.owner)) {
                return false;
            }
            
            return env.actsFor(that.reader(), this.owner()) ||
                    env.actsFor(that.reader(), this.reader());
        }            
        return false;
    }

    public String toString(Set printedLabels) {
        StringBuffer sb = new StringBuffer(owner.toString());
        sb.append("->");        
        if (!reader.isTopPrincipal()) sb.append(reader.toString());        
        return sb.toString();
    }
    
    public List throwTypes(TypeSystem ts) {
        List throwTypes = new ArrayList();
        throwTypes.addAll(owner.throwTypes(ts));
        throwTypes.addAll(reader.throwTypes(ts));
        return throwTypes; 
    }

    public Policy subst(LabelSubstitution substitution) throws SemanticException {
        boolean changed = false;

        Principal newOwner = owner.subst(substitution);
        if (newOwner != owner) changed = true;
        Principal newReader = reader.subst(substitution);
        if (newReader != reader) changed = true;
                
        if (!changed) return substitution.substPolicy(this);

        JifTypeSystem ts = (JifTypeSystem)typeSystem();
        ReaderPolicy newPolicy = ts.readerPolicy(this.position(), newOwner, newReader);
        return substitution.substPolicy(newPolicy);
    }
    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        // check each principal in turn.
        PathMap X = owner.labelCheck(A, lc);
        A.setPc(X.N(), lc);
        PathMap Xr = reader.labelCheck(A, lc);
        X = X.join(Xr);            
        return X;
    }

    public boolean isBottomConfidentiality() {
        return owner.isBottomPrincipal() && reader.isBottomPrincipal();
    }
    public boolean isTopConfidentiality() {
        return owner.isTopPrincipal() && reader.isTopPrincipal();
    }
    public boolean isTop() {
        return isTopConfidentiality();
    }
    public boolean isBottom() {
        return isBottomConfidentiality();
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
