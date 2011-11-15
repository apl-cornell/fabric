package jif.types.label;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.PathMap;
import jif.types.hierarchy.LabelEnv;
import jif.types.hierarchy.PrincipalHierarchy;
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
public class WriterPolicy_c extends Policy_c implements WriterPolicy {
    private final Principal owner;
    private final Principal writer;
    
    public WriterPolicy_c(Principal owner, Principal writer, JifTypeSystem ts, Position pos) {
        super(ts, pos); 
        if (owner == null) throw new InternalCompilerError("null owner");
        this.owner = owner;
        this.writer = writer;
    }
    
    public Principal owner() {
        return this.owner;
    }
    public Principal writer() {
        return writer;
    }

    public boolean isSingleton() {
        return true;
    }
    public boolean isCanonical() {
        return owner.isCanonical() && writer.isCanonical();
    }
    public boolean isRuntimeRepresentable() {
        return owner.isRuntimeRepresentable() && writer.isRuntimeRepresentable();
    }

    protected Policy simplifyImpl() {
        return this;
    }
        
    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        if (o instanceof WriterPolicy_c) {
            WriterPolicy_c that = (WriterPolicy_c)o;
            if (this.owner == that.owner || (this.owner != null && this.owner.equals(that.owner))) {
                return this.writer.equals(that.writer);
            }
        }
        return false;
    }
    
    public int hashCode() {
        return (owner==null?0:owner.hashCode()) ^ (writer==null?0:writer.hashCode())  ^ 1234352;
    }
    
    public boolean leq_(IntegPolicy p, LabelEnv env, SearchState state) {
        if (this.isBottomIntegrity() || p.isTopIntegrity())
            return true;
        
        if (p instanceof WriterPolicy) {
            WriterPolicy that = (WriterPolicy) p;            
            // this = { o  <- .. wi .. }
            // that = { o' <- .. wj' .. }
            
            // o >= o'
            if (!env.actsFor(this.owner, that.owner())) {
                return false;
            }
            
            // for all i . wi >= o || exists j . wi >= wj'
            return env.actsFor(this.writer(), that.owner()) ||
                env.actsFor(this.writer(), that.writer());
        }        
        return false;
    }

    public String toString(Set printedLabels) {
        StringBuffer sb = new StringBuffer(owner.toString());
        sb.append("<-");        
        if (!writer.isTopPrincipal()) sb.append(writer.toString());        
        return sb.toString();
    }
    
    public List throwTypes(TypeSystem ts) {
        List throwTypes = new ArrayList();
        throwTypes.addAll(owner.throwTypes(ts));
        throwTypes.addAll(writer.throwTypes(ts));
        return throwTypes; 
    }

    public Policy subst(LabelSubstitution substitution) throws SemanticException {
        boolean changed = false;

        Principal newOwner = owner.subst(substitution);
        if (newOwner != owner) changed = true;
        Principal newWriter = writer.subst(substitution);
        if (newWriter != writer) changed = true;

        if (!changed) return substitution.substPolicy(this).simplify();

        JifTypeSystem ts = (JifTypeSystem)typeSystem();
        WriterPolicy newPolicy = ts.writerPolicy(this.position(), newOwner, newWriter);
        return substitution.substPolicy(newPolicy).simplify();
    }
    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        // check each principal in turn.
        PathMap X = owner.labelCheck(A, lc);
        A.setPc(X.N(), lc);
        PathMap Xr = writer.labelCheck(A, lc);
        X = X.join(Xr);            
        return X;
    }
    
//    public Expr toJava(JifToJavaRewriter rw) throws SemanticException {
//        return toJava.toJava((WriterPolicy)this, rw);               
//    }

    public boolean isBottomIntegrity() {
        return owner.isTopPrincipal() && writer.isTopPrincipal();
    }
    public boolean isTopIntegrity() {
        return owner.isBottomPrincipal() && writer.isBottomPrincipal();
    }        
    public boolean isTop() {
        return isTopIntegrity();
    }
    public boolean isBottom() {
        return isBottomIntegrity();
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
