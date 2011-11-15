package jif.types.label;

import java.util.Collections;
import java.util.Set;

import jif.types.JifTypeSystem;
import jif.types.hierarchy.LabelEnv;
import polyglot.main.Report;
import polyglot.types.TypeObject;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** An implementation of the <code>VarLabel</code> interface. 
 */
public class VarLabel_c extends Label_c implements VarLabel {
    private final transient int uid = ++counter;
    private static int counter = 0;
    private String name;
    /**
     * Does whatever this variable resolves to need to be runtime representable?
     */
    private boolean mustRuntimeRepresentable = false;
    
    protected VarLabel_c() {
    }
    
    public VarLabel_c(String name, String description, JifTypeSystem ts, Position pos) {
        super(ts, pos);
        this.name = name;
        setDescription(description);
    }
       
    public boolean isEnumerable() { return true; }
    public boolean isComparable() { return true; }    
    public boolean isCanonical() { return true; }     
    public boolean isDisambiguatedImpl() { return true; }     
    public boolean isRuntimeRepresentable() { return false; }
    public boolean isCovariant() { return false; }
    
    public void setMustRuntimeRepresentable() {
        this.mustRuntimeRepresentable = true;
    }
    public boolean mustRuntimeRepresentable() {
        return this.mustRuntimeRepresentable;
    }
    
    public String componentString(Set printedLabels) {
        if (Report.should_report(Report.debug, 2)) { 
            return "<var " + name + " " + uid +  " " + System.identityHashCode(this)+ ">";
        }
        if (Report.should_report(Report.debug, 1)) { 
            return "<var " + name + ">";
        }
        return name;
    }    
    public boolean equalsImpl(TypeObject o) {
        return this == o;
    }    
    public int hashCode() { return -56393 + uid; }
    
    public boolean leq_(Label L, LabelEnv env, LabelEnv.SearchState state) {   
        throw new InternalCompilerError("Cannot compare " + this + ".");
    }
    
    public Set variableComponents() {
        return Collections.singleton(this);
    }
    public Set variables() {
        return Collections.singleton(this);        
    }

    public String name() {
        return name;
    }    
}
