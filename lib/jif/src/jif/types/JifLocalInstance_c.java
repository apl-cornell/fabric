package jif.types;

import jif.types.label.Label;
import polyglot.types.Flags;
import polyglot.types.LocalInstance_c;
import polyglot.types.Type;
import polyglot.util.Position;

/** An implementation of the <code>JifLocalInstance</code> interface. 
 */
public class JifLocalInstance_c extends LocalInstance_c implements JifLocalInstance {
    protected Label label;
    public JifLocalInstance_c(JifTypeSystem ts, Position pos, Flags flags,
            Type type, String name) {
        
        super(ts, pos, flags, type, name);
    }
    
    public void subst(VarMap bounds) {
        this.setLabel(bounds.applyTo(label));
        this.setType(bounds.applyTo(type));
    }

    public boolean isCanonical() {
        return label != null && label.isCanonical() && super.isCanonical();
    }
    
    public Label label() {
        return label;
    }
    
    public void setLabel(Label L) {        
        this.label = L;
    }
    
    public String toString() {
        return super.toString() + " " + label;
    }
}
