package jif.extension;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jif.JifScheduler;
import jif.types.JifContext;
import jif.types.JifFieldInstance;
import jif.types.JifSubstType;
import jif.types.JifTypeSystem;
import jif.types.label.VarLabel;
import jif.visit.JifTypeChecker;
import polyglot.ast.*;
import polyglot.frontend.MissingDependencyException;
import polyglot.frontend.goals.Goal;
import polyglot.types.*;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

/** The Jif extension of the <code>Field</code> node. 
 * 
 *  @see polyglot.ast.Field
 */
public class JifFieldDel extends JifJL_c
{
    public JifFieldDel() { }

    /**
     * This flag records whether the target of a field access is never
     * null. This flag is by default false, but may be set to true by the
     * dataflow analysis performed by jif.visit.NotNullChecker
     */
    private boolean isTargetNeverNull = false;
    
    /**
     * Since the CFG may visit a node more than once, we need to take the
     * OR of all values set.
     */
    private boolean targetNeverNullAlreadySet = false; 
    
    public void setTargetIsNeverNull(boolean neverNull) {
        if (!targetNeverNullAlreadySet) {
            isTargetNeverNull = neverNull;
        }
        else {
            isTargetNeverNull = isTargetNeverNull && neverNull;
        }
        targetNeverNullAlreadySet = true;
    }

    public boolean targetIsNeverNull() {
        Receiver r = ((Field)node()).target();
        return (r instanceof Special 
                || isTargetNeverNull 
                || r instanceof CanonicalTypeNode);
    }

    public NodeVisitor typeCheckEnter(TypeChecker tc) throws SemanticException {
        JifTypeChecker jtc = (JifTypeChecker)super.typeCheckEnter(tc);
        return jtc.inferClassParameters(true);
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
	Field f = (Field) super.typeCheck(tc);

	Type ft = f.type();
	Field fn = (Field) node();

        JifTypeSystem ts = (JifTypeSystem) tc.typeSystem();
	
	Type ft_type = ft;
	if (ts.isLabeled(ft)) {
	    ft_type = ts.unlabel(ft);
        }

	if (ft_type instanceof JifSubstType && fn.target() instanceof Expr) {
	    JifContext jc = (JifContext) tc.context();
	    ReferenceType rt = targetType((JifTypeSystem) tc.typeSystem(), 
					  jc, 
					  (Expr) fn.target());	    

	    if (rt instanceof JifSubstType) {		
                Type ft1 = ((JifSubstType)rt).subst().substType(ft);
		if (ft1 != ft) //update fieldInstance?
		    f = (Field) f.type(ft1);
	    }

// Jif Dependency bugfix
	    JifFieldInstance fi = (JifFieldInstance) ts.findField(rt.toReference(), fn.name(), jc.currentClass());
	    if(fi.label() instanceof VarLabel && !((JifTypeChecker)tc).disambiguationInProgress()) {
            JifScheduler sched = (JifScheduler) tc.job().extensionInfo().scheduler();
            Type tp = ts.unlabel(fi.container());
            if (tp instanceof ParsedClassType) {
                ParsedClassType pct = (ParsedClassType)tp;        
                Goal g = sched.FieldLabelInference(pct.job());
                throw new MissingDependencyException(g);
            }
//            else throw new InternalCompilerError("Help" + tp.getClass());
	    }
	}
	
	return f;
    }
    
    protected ReferenceType targetType(JifTypeSystem ts, JifContext A, 
	    Expr target) 
    {
	Field fe = (Field) node();
	String name = fe.name();
	ReferenceType rt = A.currentClass();
	if (target instanceof Special) {
	    Special st = (Special) target;
	    if (st.kind() == Special.SUPER) 
		rt = (ReferenceType) A.currentClass().superType();
	    else {
		boolean found = false;
		do {
		    for (Iterator i = rt.fields().iterator(); i.hasNext(); ) {
		        FieldInstance fi = (FieldInstance) i.next();
			if (name.equals(fi.name())) {
			    found = true;
			    break;
			}
		    }
		    if (found) 
			break;

		    rt = (ReferenceType) rt.superType();
		} while (rt != null);
	    }
	}
	else {
	    rt = (ReferenceType) ts.unlabel(target.type());
	}
	
	return rt;
    }

    /** 
     *  List of Types of exceptions that might get thrown.
     * 
     *  This differs from the method defined in Field_c in that it does not
     * throw a null pointer exception if the receiver is guaranteed to be 
     * non-null
     */
    public List throwTypes(TypeSystem ts) {
        if (!targetIsNeverNull()) {
            return Collections.singletonList(ts.NullPointerException());
        }
        return Collections.EMPTY_LIST;
    }    
}
