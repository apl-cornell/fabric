package jif.types.principal;

import java.util.Collections;
import java.util.Set;

import jif.types.JifTypeSystem;
import jif.types.hierarchy.LabelEnv;
import polyglot.main.Report;
import polyglot.types.TypeObject;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** An implementation of the <code>VarPrincipal</code> interface. 
 */
public class VarPrincipal_c extends Principal_c implements VarPrincipal {
    private final transient int uid = ++counter;
    private static int counter = 0;
    private String name;
    
    /**
     * Does whatever this variable resolves to need to be runtime representable?
     */
    private boolean mustRuntimeRepresentable = false;

    protected String description;

    public VarPrincipal_c(String name, String description, JifTypeSystem ts, Position pos) {
        super(ts, pos);
        this.name = name;
        setDescription(description);
    }
       
    protected void setDescription(String description) {
        this.description = description;
    }

    public boolean isCanonical() { return true; }     
    
    public void setMustRuntimeRepresentable() {
        this.mustRuntimeRepresentable = true;
    }
    public boolean mustRuntimeRepresentable() {
        return this.mustRuntimeRepresentable;
    }
    
    public boolean equalsImpl(TypeObject o) {
        return this == o;
    }    
    public int hashCode() { return -88393 + uid; }
    
    public Set variables() {
        return Collections.singleton(this);        
    }

    public String name() {
        return name;
    }
    
    public String toString() {
        return name();
    }

    public boolean isRuntimeRepresentable() {
        return false;
    }    
}
