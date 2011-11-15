package jif.types.label;

import java.util.Collections;
import java.util.Set;

import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.hierarchy.LabelEnv;
import polyglot.main.Report;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public class ThisLabel_c extends Label_c implements ThisLabel {
    private final ReferenceType ct;
    private final String fullName;
    
    public ThisLabel_c(JifTypeSystem ts, ReferenceType ct, Position pos) {
        super(ts, pos);
        this.ct = ct;
        if (ct.isClass()) {
            this.fullName = ct.toClass().fullName();
        }
        else if (ct.isArray()) {
            this.fullName = ct.toArray().base().toString() + "[]";
        }
        else throw new InternalCompilerError("Only class types and arrays allowed");
        setDescription("label of the special variable \"this\"");
    }
    

    public boolean isRuntimeRepresentable() { return false; }
    public boolean isCovariant() {
        // the this label is not actually covariant, it's really an arg-label
        // in disguise (think about the receiver of the method or field-access
        // being passed in as an additional argument.
        return false; 
    }
    
    public boolean isComparable() { return true; }
    public boolean isCanonical() { return true; }
    public boolean isDisambiguatedImpl() { return true; }
    public boolean isEnumerable() { return true; }
    
    public ReferenceType classType() {
        return ct;
    }
    
    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        if (! (o instanceof ThisLabel)) {
            return false;
        }           
        ThisLabel that = (ThisLabel) o;
        return this.ct.equals(that.classType());
    }
    public int hashCode() {
        return fullName.hashCode();
    }
    
    public String componentString(Set printedLabels) {
        if (Report.should_report(Report.debug, 2)) { 
            return "<this (of " + fullName + ")>";
        }
        else if (Report.should_report(Report.debug, 1)) {
            return "<this (of " + fullName + ")>";
        }
        return "this";            
    }

    public boolean leq_(Label L, LabelEnv env, LabelEnv.SearchState state) {
        // We know nothing about the this label, save that it is equal to itself,
        // and whatever is in the environment.
        return false;
    }
 
    public Set variables() {
        return Collections.EMPTY_SET;        
    }
    
    
}
