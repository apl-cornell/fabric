package jif.types;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jif.types.label.ArgLabel;
import jif.types.label.Label;
import polyglot.main.Report;
import polyglot.types.ClassType;
import polyglot.types.ConstructorInstance_c;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.util.TypedList;

/** An implementation of the <code>JifConstructorInstance</code> interface. 
 */
public class JifConstructorInstance_c extends ConstructorInstance_c
implements JifConstructorInstance
{
    protected Label pcBound;
    protected Label returnLabel;
    protected List constraints;
    protected boolean isDefaultPCBound;
    protected boolean isDefaultReturnLabel;

    public JifConstructorInstance_c(JifTypeSystem ts, Position pos,
            ClassType container, Flags flags,
            Label pcBound, boolean isDefaultPCBound, Label returnLabel, 
            boolean isDefaultReturnLabel, List formalTypes, List formalArgLabels,
            List excTypes, List constraints) {

        super(ts, pos, container, flags, formalTypes, excTypes);
        this.pcBound = pcBound;
        this.returnLabel = returnLabel;
        this.constraints = TypedList.copyAndCheck(constraints, Assertion.class, true);

        this.pcBound = pcBound;
        this.isDefaultPCBound = isDefaultPCBound;
        this.returnLabel = returnLabel;
        this.isDefaultReturnLabel = isDefaultReturnLabel;
        this.throwTypes = TypedList.copyAndCheck(throwTypes, 
                                                 Type.class, 
                                                 true);
        this.formalTypes = TypedList.copyAndCheck(formalTypes, 
                                                  Type.class, 
                                                  true);
    }


    public Label pcBound() {
        return pcBound;
    }

    public Label externalPC() {
        return pcBound;
    }

    public Label returnLabel() {
        return returnLabel;
    }

    public void setReturnLabel(Label returnLabel, boolean isDefault) {
        this.returnLabel = returnLabel;
        this.isDefaultReturnLabel = isDefault;
    }

    public boolean isDefaultReturnLabel() {
        return isDefaultReturnLabel;
    }

    public void  setPCBound(Label pcBound, boolean isDefault) {
        this.pcBound = pcBound;
        this.isDefaultPCBound = isDefault;
    }

    public boolean isDefaultPCBound() {
        return isDefaultPCBound;
    }

    public List constraints() {
        return constraints;
    }

    public void setConstraints(List constraints) {
        this.constraints = TypedList.copyAndCheck(constraints, Assertion.class, true);
    }

    public boolean isCanonical() {
        if (!(super.isCanonical()
                && pcBound.isCanonical()
                && returnLabel.isCanonical()
                && listIsCanonical(constraints)
                && formalTypes != null)) {
            return false;
        }

        JifTypeSystem jts = (JifTypeSystem)typeSystem();
        // also need to make sure that every formal type is labeled with an arg label
        for (Iterator i = formalTypes().iterator(); i.hasNext(); ) {
            Type t = (Type) i.next();
            if (!jts.isLabeled(t) || !(jts.labelOfType(t) instanceof ArgLabel)) return false;
        }    
        return true;
    }

    public void subst(VarMap bounds) {
        if (this.pcBound != null) 
            this.pcBound = bounds.applyTo(pcBound);

        if (this.returnLabel != null) 
            this.returnLabel = bounds.applyTo(returnLabel);

        List formalTypes = new LinkedList();
        for (Iterator i = formalTypes().iterator(); i.hasNext(); ) {
            Type t = (Type) i.next();
            formalTypes.add(bounds.applyTo(t));
        }
        this.setFormalTypes(formalTypes);

        List throwTypes = new LinkedList();
        for (Iterator i = throwTypes().iterator(); i.hasNext(); ) {
            Type t = (Type) i.next();
            throwTypes.add(bounds.applyTo(t));
        }

        this.setThrowTypes(throwTypes);
    }    

    public void subst(LabelSubstitution subst) throws SemanticException {
        TypeSubstitutor tsbs = new TypeSubstitutor(subst);
        setPCBound(pcBound().subst(subst), isDefaultPCBound());
        setReturnLabel(returnLabel().subst(subst), isDefaultReturnLabel());

        List formalTypes = new LinkedList();
        for (Iterator i = formalTypes().iterator(); i.hasNext(); ) {
            Type t = (Type) i.next();
            formalTypes.add(tsbs.rewriteType(t));
        }
        this.setFormalTypes(formalTypes);

        List throwTypes = new LinkedList();
        for (Iterator i = throwTypes().iterator(); i.hasNext(); ) {
            Type t = (Type) i.next();
            throwTypes.add(tsbs.rewriteType(t));
        }
        this.setThrowTypes(throwTypes);

    }
    public String debugString() {
        return debugString(true);
    }
    private String debugString(boolean showInstanceKind) {
        String s = "";
        if (showInstanceKind) {
            s = "constructor ";
        }
        s += flags.translate() + container + "(";

        for (Iterator i = formalTypes.iterator(); i.hasNext(); ) {
            Type t = (Type) i.next();
            s += ((JifTypeSystem)ts).unlabel(t).toString();

            if (i.hasNext()) {
                s += ", ";
            }
        }

        s += ")";

        return s;
    }

    public String signature() {
        if (Report.should_report(Report.debug, 1)) { 
            return fullSignature();
        }
        return debugString(false);
    }
    public String fullSignature() {
        String s = container.toString();
        if (!isDefaultPCBound() || Report.should_report(Report.debug, 1)) {
            s += pcBound;
        }
        s += "(";

        for (Iterator i = formalTypes.iterator(); i.hasNext(); ) {
            Type t = (Type) i.next();
            s += t.toString();

            if (i.hasNext()) {
                s += ",";
            }
        }
        s += ")";
        if (!isDefaultReturnLabel() || Report.should_report(Report.debug, 1)) {
            s += ":" + returnLabel;
        }

        return s;
    }

}
