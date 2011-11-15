package jif.types.label;

import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * Represents a final access path rooted at a local variable.
 * @see jif.types.label.AccessPath
 */
public class AccessPathLocal extends AccessPathRoot {
    protected LocalInstance li;
    protected String name;
    private boolean neverNull = false;
    public AccessPathLocal(LocalInstance li, String name, Position pos) {
        super(pos);
        this.li = li;
        this.name = name;
        if (li != null && !name.startsWith(li.name())) {
            throw new InternalCompilerError("Inconsistent local names");
        }
    }
    
    public boolean isCanonical() { return !(li.type() instanceof UnknownType); }
    public AccessPath subst(AccessPathRoot r, AccessPath e) {
        if (r instanceof AccessPathLocal) {            
            if (li.equals(((AccessPathLocal)r).li)) {
                return e;
            }
        }
        return this;
    }
    
    public AccessPathLocal name(String name) {
        AccessPathLocal apl = (AccessPathLocal)this.copy();
        apl.name = name;
        if (apl.li != null && !name.startsWith(apl.li.name())) {
            throw new InternalCompilerError("Inconsistent local names");
        }
        return apl;
    }
    
    public boolean isNeverNull() {
        return neverNull;
    }
    public void setIsNeverNull() {
        this.neverNull = true;
    }
    public String toString() {        
        return niceName();
    }
    public String exprString() {
        return niceName();
    }
    private String niceName() {
        if (li != null && li.name() != null && name.startsWith(li.name())) return li.name();
        return name;
    }
    public String name() {
        return name;
    }
    
    public LocalInstance localInstance() {
        return this.li;
    }
    public boolean equals(Object o) {
        if (o instanceof AccessPathLocal) {
            AccessPathLocal that = (AccessPathLocal)o;
            // use pointer equality for li instead of equals, so
            // that we don't mistakenly equate two local instances
            // with the same name but from different methods/defining
            // scopes
            return this.name.equals(that.name) && li == that.li; 
        }
        return false;        
    }

    public int hashCode() {
        return name.hashCode();
    }
    public Type type() {
        if (li == null) return null;
        return li.type();
    }

    public PathMap labelcheck(JifContext A, LabelChecker lc) {
    	JifTypeSystem ts = (JifTypeSystem)A.typeSystem();
    	Label L = ts.labelOfLocal(li, A.pc());

    	PathMap X = ts.pathMap();
    	X = X.N(A.pc());
    	X = X.NV(lc.upperBound(L, A.pc()));
        
        return X;
    }
    public void verify(JifContext A) throws SemanticException {
        if (li == null) {
            li = A.findLocal(name);
        }
        else {
            if (!li.equals(A.findLocal(name))) {
                throw new InternalCompilerError("Unexpected local instance for name " + name);
            }
        }
        if (!li.flags().isFinal()) {
            throw new SemanticException("Non-final local variable used in access path", position());
        }
    }
}
