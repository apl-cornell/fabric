package jif.types.label;

import jif.types.*;
import jif.visit.LabelChecker;
import polyglot.main.Report;
import polyglot.types.ClassType;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 * Represents a final access path rooted at a class, e.g. "Foo[Alice]".
 * 
 * @see jif.types.label.AccessPath
 */
public class AccessPathClass extends AccessPathRoot {
    private ClassType ct;
    public AccessPathClass(ClassType ct, Position pos) {
        super(pos);
        this.ct = ct;
    }
    
    public boolean isCanonical() { return true; }
    public boolean isNeverNull() { return true; }
    public AccessPath subst(AccessPathRoot r, AccessPath e) {
        return this;
    }
    
    public String toString() {
        if (Report.should_report(Report.debug, 2)) { 
            return ct.fullName();
        }
        return ct.name();
    }

    public String exprString() {
        return ct.fullName();
    }	
    
    public boolean equals(Object o) {
        if (o instanceof AccessPathClass) {
            return ct.equals(((AccessPathClass)o).ct);
        }
        return false;        
    }

    public Type type() {
        return ct;
    }

    public int hashCode() {
        return -2030;
    }

    public PathMap labelcheck(JifContext A, LabelChecker lc) {
    	JifTypeSystem ts = (JifTypeSystem)A.typeSystem();
    	
    	// there is no information gained by accessing a class statically.
    	return ts.pathMap().N(A.pc()).NV(A.pc());
        
    }
}
