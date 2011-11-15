package jif.types;

import jif.types.label.Label;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** An implementation of the <code>JifFieldInstance</code> interface.
 */
public class JifFieldInstance_c extends FieldInstance_c
                               implements JifFieldInstance
{
    protected Label label;
    protected boolean hasInitializer;
    
    public JifFieldInstance_c(JifTypeSystem ts, Position pos,
	ReferenceType container, Flags flags,
	Type type, String name) {

	super(ts, pos, container, flags, type, name);
    }

    public void subst(VarMap bounds) {
        this.setLabel(bounds.applyTo(label));
        this.setType(bounds.applyTo(type));
    }

    public Label label() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public boolean hasInitializer() {
        return hasInitializer;
    }

    public void setHasInitializer(boolean hasInitializer) {
        this.hasInitializer = hasInitializer;        
    }

    private FieldInstance findOrigFieldInstance() {
        if (this.container() instanceof JifSubstType) {
            JifSubstType jst = (JifSubstType)this.container();
            if (jst.base() instanceof ParsedClassType) {
                return ((ParsedClassType) jst.base()).fieldNamed(this.name());
            }
            else {
                throw new InternalCompilerError("Unexpected base type");
            }
        }
        return this;        
    }
    public boolean isConstant() {
        FieldInstance orig = findOrigFieldInstance();
        if (this != orig) {
            return orig.isConstant();        
        }
        return super.isConstant();
    }
    public Object constantValue() {
        FieldInstance orig = findOrigFieldInstance();
        if (this != orig) {
            return orig.constantValue();        
        }
        return super.constantValue();
    }
    public boolean constantValueSet() {
        FieldInstance orig = findOrigFieldInstance();
        if (this != orig) {
            return orig.constantValueSet();        
        }
        return super.constantValueSet();
    }
    public FieldInstance constantValue(Object constantValue) {
        FieldInstance orig = findOrigFieldInstance();
        if (this != orig) {
            throw new InternalCompilerError("Cant modify constant value on a copy");            
        }
        return super.constantValue(constantValue);
    }
    public FieldInstance notConstant() {
        FieldInstance orig = findOrigFieldInstance();
        if (this != orig) {
            throw new InternalCompilerError("Cant modify constant value on a copy");            
        }
        return super.notConstant();
    }
    public void setConstantValue(Object constantValue) {
        FieldInstance orig = findOrigFieldInstance();
        if (this != orig) {
            throw new InternalCompilerError("Cant modify constant value on a copy");            
        }
        super.setConstantValue(constantValue);
    }
    public String toString() {
        return super.toString() + " label = " + label;
    }
    
}
