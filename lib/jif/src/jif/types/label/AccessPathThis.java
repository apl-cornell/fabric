package jif.types.label;

import jif.types.*;
import jif.visit.LabelChecker;
import polyglot.main.Report;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * Represents a final access path rooted at "this".
 * @see jif.types.label.AccessPath
 */
public class AccessPathThis extends AccessPathRoot {
    private ClassType ct;
    /**
     * 
     * @param ct may be null.
     */
    public AccessPathThis(ClassType ct, Position pos) {
        super(pos);
        this.ct = ct;
    }
    
    public boolean isCanonical() { return true; }
    public boolean isNeverNull() { return true; }
    public AccessPath subst(AccessPathRoot r, AccessPath e) {
        if (r instanceof AccessPathThis) {            
            if (this.equals(r)) {
                return e;
            }
        }
        return this;
    }
    
    public String toString() {
        String name = "<not-typechecked>";
        if (Report.should_report(Report.debug, 2)) { 
            if (ct != null) name = ct.fullName();
            return "this(of " + name + ")";
        }
        if (Report.should_report(Report.debug, 1)) { 
            if (ct != null) name = ct.name();
            return "this(of " + name + ")";
        }
        return "this";
    }
    
    public String exprString() {
        return "this";
    }
    public boolean equals(Object o) {
        if (o instanceof AccessPathThis) {
            AccessPathThis that = (AccessPathThis)o;
            if (this.ct == that.ct || this.ct == null || that.ct == null) 
                return true;
            // return true if this.ct <= that.ct or that.ct <= this.ct  
            return this.ct.isSubtype(that.ct) || that.ct.isSubtype(this.ct);
        }
        return false;        
    }

    public int hashCode() {
        return -572309;
    }
    public Type type() {
        return ct;
    }
    
    public PathMap labelcheck(JifContext A, LabelChecker lc) {
    	JifTypeSystem ts = (JifTypeSystem)A.typeSystem();
    	JifClassType ct = (JifClassType)A.currentClass();
    	

    	PathMap X = ts.pathMap();
    	X = X.N(A.pc());
    	
    	// X(this).NV = this_label, which is upper-bounded by the begin label. 
    	X = X.NV(lc.upperBound(ct.thisLabel(), A.pc()));	    	
        return X;
    }
    public void verify(JifContext A) throws SemanticException {
        if (ct == null) {
            ct = A.currentClass();
        }
        else {
            if (!A.currentClass().isSubtype(ct)) {
                throw new InternalCompilerError("Unexpected class type for " +
                		"access path this: wanted a supertype of " + 
                		A.currentClass());
            }
        }
    }
}
