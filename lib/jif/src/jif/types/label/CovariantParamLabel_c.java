package jif.types.label;


import java.util.Collections;
import java.util.Set;

import jif.translate.ParamToJavaExpr_c;
import jif.types.*;
import jif.types.hierarchy.LabelEnv;
import jif.visit.LabelChecker;
import polyglot.main.Report;
import polyglot.types.TypeObject;
import polyglot.util.Position;

/** An implementation of the <code>CovariantLabel</code> interface. 
 */
public class CovariantParamLabel_c extends Label_c implements CovariantParamLabel {
    private final ParamInstance paramInstance;
    public CovariantParamLabel_c(ParamInstance paramInstance, JifTypeSystem ts, Position pos) {
        super(ts, pos, new ParamToJavaExpr_c());
        this.paramInstance = paramInstance;
        String className = null;
        if (paramInstance != null && paramInstance.container() != null) {
            className = paramInstance.container().fullName();
        }
        if (className != null && paramInstance.name() != null) {
            setDescription("covariant label parameter " + 
                           paramInstance.name() + " of class " + 
                           className);
        }
    }	
    
    public ParamInstance paramInstance() {
        return paramInstance;
    }
    public boolean isRuntimeRepresentable() { 
        return ((JifTypeSystem)ts).isParamsRuntimeRep(paramInstance.container()); 
    }
    public boolean isCovariant() { return true; }
    public boolean isComparable() { return true; }
    public boolean isCanonical() { return paramInstance.isCanonical(); }
    protected boolean isDisambiguatedImpl() { return isCanonical(); }
    public boolean isEnumerable() { return true; }
    
    public int hashCode() {
        return paramInstance.hashCode();
    }
    public boolean equalsImpl(TypeObject o) {
        if (! (o instanceof CovariantParamLabel)) {
            return false;
        }           
        CovariantParamLabel that = (CovariantParamLabel) o;
        return (this.paramInstance == that.paramInstance());
    }
    
    public String componentString(Set printedLabels) {
        if (Report.should_report(Report.debug, 1)) { 
            return "<covariant-param-label " + this.paramInstance + ">";
        }
        return this.paramInstance.name();
    }

    public PathMap labelCheck(JifContext A, LabelChecker lc) {
        JifTypeSystem ts = (JifTypeSystem)A.typeSystem();
        Label l;
        if (A.inStaticContext()) {
            // return a special arg label
            ArgLabel al = ts.argLabel(this.position, paramInstance); 
            if (A.inConstructorCall()) {
                al.setUpperBound(ts.thisLabel(this.position(), (JifClassType)A.currentClass()));
            }
            else {
                al.setUpperBound(ts.topLabel());
            }
            l = al;
        }
        else {
            l = ts.thisLabel(this.position(), (JifClassType)A.currentClass());
        }
        return ts.pathMap().N(A.pc()).NV(l);
    }
    
    public boolean leq_(Label L, LabelEnv env, LabelEnv.SearchState state) {
        // only leq if equal to this parameter, which is checked before 
        // this method is called.
        return false;
    }
    public Set variables() {
        return Collections.EMPTY_SET;        
    }

}
