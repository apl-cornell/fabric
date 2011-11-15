package jif.types;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jif.types.label.*;
import jif.types.label.CovariantParamLabel;
import jif.types.label.Label;
import jif.types.principal.ParamPrincipal;
import jif.types.principal.Principal;

import polyglot.ext.param.types.Subst_c;
import polyglot.types.ClassType;
import polyglot.types.ConstructorInstance;
import polyglot.types.FieldInstance;
import polyglot.types.MethodInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.CachingTransformingList;
import polyglot.util.InternalCompilerError;
import polyglot.util.Transformation;

public class JifSubst_c extends Subst_c implements JifSubst
{
    public JifSubst_c(JifTypeSystem ts, Map subst, Map cache) {
        super(ts, subst, cache);

        for (Iterator i = entries(); i.hasNext(); ) {
            Map.Entry e = (Map.Entry) i.next();
            if (e.getKey() instanceof ParamInstance && e.getValue() instanceof Param)
                continue;
            throw new InternalCompilerError("bad map: the keys must be UIDs, "+
            "and the values must be Params: " + subst);
        }
    }

    public Param get(ParamInstance pi) {
        return (Param) subst.get(pi);
    }

    public void put(ParamInstance pi, Param param) {
        subst.put(pi, param);
    }

    ////////////////////////////////////////////////////////////////
    // Override substitution methods to handle Jif constructs

    protected boolean cacheTypeEquality(Type t1, Type t2) {
        // don't strip away the instantiation info. At worst, we'll return
        // false more often than we need to, resulting in more instantiations.
        // But at least it'll be correct, otherwise we end up with, say,
        // C[L1] and C[L2] being regarded as equal, and thus having the same
        // substitution.
        return ((JifTypeSystem)ts).equalsNoStrip(t1, t2);
    }
    
    public Type uncachedSubstType(Type t) {
        JifTypeSystem ts = (JifTypeSystem) this.ts;

        if (ts.isLabeled(t)) {
            return ts.labeledType(t.position(),
                                  substType(ts.unlabel(t)),
                                  substLabel(ts.labelOfType(t)));
        }

        return super.uncachedSubstType(t);
    }

    public ClassType substClassType(ClassType t) {
        // Don't bother trying to substitute into a non-Jif class.
        if (! (t instanceof JifClassType)) {
            return t;
        }

        return new JifSubstClassType_c((JifTypeSystem) ts, t.position(),
                                       (JifClassType) t, this);
    }

    public MethodInstance substMethod(MethodInstance mi) {
        mi = super.substMethod(mi);
        
        if (mi instanceof JifProcedureInstance) {
            JifProcedureInstance jmi = (JifProcedureInstance)mi;

            jmi.setPCBound(substLabel(jmi.pcBound()), jmi.isDefaultPCBound());
            jmi.setReturnLabel(substLabel(jmi.returnLabel()), jmi.isDefaultReturnLabel());
            jmi.setConstraints(new CachingTransformingList(jmi.constraints(),
                                                              new ConstraintXform()));

            mi = (MethodInstance)jmi;
        }

        return mi;
    }

    public ConstructorInstance substConstructor(ConstructorInstance ci) {
        ci = super.substConstructor(ci);

        if (ci instanceof JifProcedureInstance) {
            JifProcedureInstance jci = (JifProcedureInstance) ci;

            jci.setPCBound(substLabel(jci.pcBound()), jci.isDefaultPCBound());
            jci.setReturnLabel(substLabel(jci.returnLabel()), jci.isDefaultReturnLabel());
            jci.setConstraints(new CachingTransformingList(jci.constraints(),
                                                              new ConstraintXform()));

            ci = (ConstructorInstance)jci;
        }

        return ci;
    }

    /** Perform substititions on a field. */
    public FieldInstance substField(FieldInstance fi) {
        fi = super.substField(fi);
        if (fi instanceof JifFieldInstance) { 
            JifFieldInstance jfi = (JifFieldInstance)fi;
            jfi.setLabel(substLabel(jfi.label()));
            fi = jfi;
        } 
        return fi;
    }

    ////////////////////////////////////////////////////////////////
    // Substitution methods for Jif constructs

    public List substConstraintList(List constraints) {
        return new CachingTransformingList(constraints, new ConstraintXform());
    }

    public List substLabelList(List labels) {
        return new CachingTransformingList(labels, new LabelXform());
    }

    public List substPrincipalList(List principals) {
        return new CachingTransformingList(principals, new PrincipalXform());
    }

    public Assertion substConstraint(Assertion constraint) {
	if (constraint == null) {
	    return null;
	}

	if (constraint instanceof ActsForConstraint) {
	    ActsForConstraint c = (ActsForConstraint) constraint;
	    c = c.actor(substPrincipal(c.actor()));
	    c = c.granter(substPrincipal(c.granter()));
	    return c;
	}
	else if (constraint instanceof LabelLeAssertion) {
	    LabelLeAssertion c = (LabelLeAssertion) constraint;
	    c = c.lhs(substLabel(c.lhs()));
	    c = c.rhs(substLabel(c.rhs()));
	    return c;
	}
	else if (constraint instanceof CallerConstraint) {
	    CallerConstraint c = (CallerConstraint) constraint;
	    List l = new CachingTransformingList(c.principals(),
                                                 new PrincipalXform());
	    return c.principals(l);
	}
        else if (constraint instanceof AuthConstraint) {
            AuthConstraint c = (AuthConstraint) constraint;
            List l = new CachingTransformingList(c.principals(),
                                                 new PrincipalXform());
            return c.principals(l);
        }
        else if (constraint instanceof AutoEndorseConstraint) {
            AutoEndorseConstraint c = (AutoEndorseConstraint) constraint;
            c = c.endorseTo(substLabel(c.endorseTo()));
            return c;
        }

	return constraint;
    }

    public Label substLabel(Label label) {
        if (label == null) {
	    return null;
	}
    
        try {
            return label.subst(substLabelSubst);
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Unexpected semantic exception", e);
        }  
    }
    
    public Principal substPrincipal(Principal principal) {
        if (principal == null) {
            return null;
        }
        
        try {
            return principal.subst(substLabelSubst);
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Unexpected semantic exception", e);
        }  
    }

    /**
     * An instance of the nested class <code>SubstLabelSubst</code>, to be
     * used by <code>substLabel(Label)</code> and 
     * <code>substPrincipal(Principal)</code>.
     */
    protected SubstLabelSubst substLabelSubst = new SubstLabelSubst();
    
    /**
     * This class is a <code>LabelSubstitution</code> that performs 
     * substitutions on <code>Label</code>s and <code>Principal</code>s. 
     *
     */
    protected class SubstLabelSubst extends LabelSubstitution implements Serializable {
        public Label substLabel(Label L) throws SemanticException {
            if (L instanceof ParamLabel) {
                ParamLabel c = (ParamLabel) L;
                return subLabel(c, c.paramInstance());
            }
            else if (L instanceof CovariantParamLabel) {
                CovariantParamLabel c = (CovariantParamLabel) L;
                return subLabel(c, c.paramInstance());
            }
            return L;
        }

        public Principal substPrincipal(Principal p) throws SemanticException {
            if (p instanceof ParamPrincipal) {
                ParamPrincipal pp = (ParamPrincipal) p;
                return subPrincipal(pp, pp.paramInstance());
            }

            return p;
        }        
    }


    /** Return the substitution of uid, or label if not found. */
    protected Label subLabel(Label label, ParamInstance pi) {
	Param sub = (Param) subst.get(pi);
        JifTypeSystem ts = (JifTypeSystem) typeSystem();

	if (sub instanceof UnknownParam) {
	    return ts.unknownLabel(sub.position());
	}
	else if (sub instanceof Label) {
	    return (Label) sub;
	}
	else if (sub == null) {
	    return label;
	}
	else {
	    throw new InternalCompilerError("Cannot substitute " + label +
		" for " + sub + " with param instance " + pi, label.position());
	}
    }

    /** Return the substitution of uid, or principal if not found. */
    protected Principal subPrincipal(Principal principal, ParamInstance pi) {
	Param sub = (Param) subst.get(pi);
        JifTypeSystem ts = (JifTypeSystem) typeSystem();

	if (sub instanceof UnknownParam) {
	    return ts.unknownPrincipal(sub.position());
	}
	else if (sub instanceof Principal) {
	    return (Principal) sub;
	}
	else if (sub == null) {
	    return principal;
	}
	else {
	    throw new InternalCompilerError("Cannot substitute " + principal +
		" for " + sub + " with param instance " + pi, principal.position());
	}
    }

    ////////////////////////////////////////////////////////////////
    // Substitution machinery

    protected Object substSubstValue(Object value) {
        if (value instanceof Label) {
            return substLabel((Label)value);
        }
        else if (value instanceof Principal) {
            return substPrincipal((Principal)value);
        }
        return super.substSubstValue(value);
    }

    public class ConstraintXform implements Transformation {
	public Object transform(Object o) {
	    return substConstraint((Assertion)o);
	}
    }

    public class LabelXform implements Transformation {
	public Object transform(Object o) {
	    return substLabel((Label)o);
	}
    }

    public class PrincipalXform implements Transformation {
	public Object transform(Object o) {
	    return substPrincipal((Principal)o);
	}
    }
}
