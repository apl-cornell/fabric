package jif.extension;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import jif.types.*;
import jif.types.label.Label;
import jif.types.label.VarLabel;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.main.Report;
import polyglot.types.ArrayType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.StringUtil;

/** A checker of subtype relationships. 
 */
public class SubtypeChecker
{
    private final Type origSupertype;
    private final Type origSubtype;

    public SubtypeChecker(Type supertype, Type subtype) {
        JifTypeSystem ts = (JifTypeSystem)supertype.typeSystem();
        origSupertype = ts.unlabel(supertype);
        origSubtype = ts.unlabel(subtype);                        
    }

    /** Check that subtype <= supertype */
    public void addSubtypeConstraints(LabelChecker lc, Position pos) throws SemanticException
    {
        addSubtypeConstraints(lc, pos, origSupertype, origSubtype);
    }
    /** Check that subtype <= supertype */
    protected void addSubtypeConstraints(LabelChecker lc, Position pos,
            Type supertype, Type subtype) throws SemanticException
            {
        // make sure that we take the top level labels off the types
        JifTypeSystem ts = lc.jifTypeSystem();
        supertype = ts.unlabel(supertype);
        subtype = ts.unlabel(subtype);                

        if (Report.should_report(Report.types, 1))
            Report.report(1, "Adding subtype constraints: " + supertype + " >= " + subtype);

        if (! recursiveAddSubtypeConstraints(lc, pos, supertype, subtype, false)) {
            throw new SemanticException(subtype + " is not a subtype of " +
                                        supertype + ".", pos);
        }
            }

    private Label label(Param param, Position pos) throws SemanticException {
        if (param instanceof Label) {
            return (Label) param;
        }
        if (param == null) {
            throw new SemanticException("No parameter given; expected a " +
                                        "label parameter.",
                                        pos);            
        }

        throw new SemanticException("Parameter " + param + " is not a label.",
                                    param.position());
    }

    private Principal principal(Param param, Position pos) throws SemanticException {

        if (param instanceof Principal) {
            return (Principal) param;
        }

        if (param == null) {
            throw new SemanticException("No parameter given; expected a " + 
                                        "principal parameter.",
                                        pos);            
        }

        throw new SemanticException("Parameter " + param +
                                    " is not a principal.", param.position());
    }

    /**
     * Insert constraints so that subtype <= supertype.
     * Walk up the class hierarchy from subtype until we find supertype.
     * If not found, throw a <code>SemanticException</code>.
     * This really should not happen since we're past type checking.
     */
    private void addParamConstraints(LabelChecker lc, Position pos,
            JifClassType supertype, JifClassType subtype) throws SemanticException
            {
        if (Report.should_report(Report.types, 2))
            Report.report(2, "Adding param constraints: " + supertype + " >= " + subtype);

        JifContext A = lc.jifContext();

        Iterator iter = polyTypeForClass(supertype).params().iterator();
        Iterator supIter = supertype.actuals().iterator();
        Iterator subIter = subtype.actuals().iterator();

        int counter = 0;
        while (iter.hasNext() && supIter.hasNext() && subIter.hasNext()) {
            counter++;
            final int count = counter;
            final ParamInstance pi = (ParamInstance) iter.next();
            Param supParam = (Param) supIter.next();
            Param subParam = (Param) subIter.next();

            if (pi.isInvariantLabel() || pi.isCovariantLabel()) {
                LabelConstraint.Kind kind = 
                    pi.isInvariantLabel() ? LabelConstraint.EQUAL // subParam == supParam
                            : pi.isCovariantLabel() ? LabelConstraint.LEQ   // subParam <= supParam
                                    : null;

                final Type lOrigSubtype = origSubtype;
                final Type lOrigSupertype = origSupertype;
                lc.constrain(new NamedLabel("sub_param_"+count,
                                            StringUtil.nth(count) + " param of subtype " + lOrigSubtype,
                                            label(subParam, pos)), 
                            kind, 
                            new NamedLabel(
                                           "sup_param_"+count,
                                           StringUtil.nth(count) + " param of supertype " + lOrigSupertype,
                                           label(supParam, pos)), 
                           A.labelEnv(),
                           pos,
                           new ConstraintMessage() {
                    public String msg() {
                        return lOrigSubtype + " is not a subtype of " + 
                        lOrigSupertype + 
                        ", since the subtype relation between label " + 
                        "parameters is not satisfied.";
                    }
                    public String detailMsg() {
                        String variance = pi.isInvariantLabel() 
                        ? "invariant"
                                : "covariant";
                        String reln = kind() == LabelConstraint.EQUAL 
                        ? "equal to"
                                : "less restrictive than";
                        return lOrigSubtype + " is not a subtype of " + 
                        lOrigSupertype + ". Subtyping requires " +
                        "the " + StringUtil.nth(count) + 
                        " parameter of the subtype to be " +
                        reln + 
                        " the " + StringUtil.nth(count) + 
                        " parameter of the supertype, since that " +
                        "parameter is " + variance + ".";
                    }
                }
                );
            }
            else if (pi.isPrincipal()) {
                lc.constrain(principal(supParam, pos), 
                             PrincipalConstraint.EQUIV, 
                            principal(supParam, pos), 
                           A.labelEnv(),
                           pos,
                           new ConstraintMessage() {
                    public String msg() {
                        return origSubtype + " is not a subtype of " + 
                        origSupertype + ", since the principals are not equivalent.";
                    }
                    public String detailMsg() {
                        return origSubtype + " is not a subtype of " + 
                        origSupertype + ". Subtyping requires " +
                        "the " + StringUtil.nth(count) + 
                        " parameter of the subtype to be equivalent to the " +
                        StringUtil.nth(count) + " parameter of the supertype.";
                    }
                });
            }
        }

        if (iter.hasNext() || supIter.hasNext() || subIter.hasNext())
            throw new InternalCompilerError(pos,
            "Instantiation type parameter count mismatch.");
            }

    /** Check that subtype <= supertype */
    private boolean recursiveAddSubtypeConstraints(LabelChecker lc,
            Position pos, Type supertype, Type subtype, final boolean inNonConstArrayType) throws SemanticException
            {
        if (Report.should_report(Report.types, 2))
            Report.report(2, "Adding subtype constraints: " + supertype + " >= " + subtype);

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();

        Type unlblSupertype = ts.unlabel(supertype);
        Type unlblSubtype = ts.unlabel(subtype);

        if (ts.isLabeled(supertype) && ts.isLabeled(subtype)) {
            // the two types are labeled. make sure that 
            // the label of supertype is at least as restrictve as that
            // of subtype, or if at least one of them is invariant, that they are equal
            final Type lOrigSubtype = origSubtype;
            final Type lOrigSupertype = origSupertype;
            lc.constrain(new NamedLabel("label of type " + subtype,
                                        ts.labelOfType(subtype)), 
                        LabelConstraint.LEQ, 
                        new NamedLabel("label of type " + supertype,
                                       ts.labelOfType(supertype)), 
                        A.labelEnv(),
                        pos,
                        !(ts.labelOfType(subtype) instanceof VarLabel || ts.labelOfType(supertype) instanceof VarLabel ),
                        new ConstraintMessage() {
                public String msg() {
                    String s = lOrigSubtype + " is not a subtype of " + 
                    lOrigSupertype + ".";
                    if (inNonConstArrayType) {
                        s += " The base type of arrays must be equivalent.";
                    }
                    return s;
                }
                public String detailMsg() {
                    if (inNonConstArrayType) {
                        return lOrigSubtype + " is not a subtype of " + 
                        lOrigSupertype + ". Subtyping requires " +
                        "the base types of arrays to be equivalent.";                                                
                    }
                    return lOrigSubtype + " is not a subtype of " + 
                    lOrigSupertype + ". Subtyping requires " +
                    "the label of the subtype to be less " +
                    "restrictive than the label of the " +
                    "supertype.";
                }
            }
            );                
        }


        if (unlblSupertype instanceof JifClassType && unlblSubtype.isNull())
            return true;

        if (unlblSubtype instanceof JifClassType && unlblSupertype instanceof JifClassType) {
            JifClassType sub = (JifClassType) unlblSubtype;
            JifClassType sup = (JifClassType) unlblSupertype;


            LinkedList subPossibles = new LinkedList();
            subPossibles.add(sub);
            Set checkedPossibles = new HashSet();
            while (!subPossibles.isEmpty()) {
                JifClassType poss = (JifClassType)subPossibles.removeFirst();
                if (ts.equalsStrip(polyTypeForClass(poss), polyTypeForClass(sup))) {
                    // Insert constraints between parameters.
                    addParamConstraints(lc, pos, sup, poss);
                    return true;
                }
                // poss isn't the appropriate polytype.
                checkedPossibles.add(poss);

                // add the superclass and all interfaces of poss to the set of candidates.
                Type possParent = poss.superType();
                if (possParent instanceof JifClassType && 
                        !checkedPossibles.contains(possParent) && 
                        !subPossibles.contains(possParent)) {
                    subPossibles.add(possParent);
                }

                for (Iterator iter = poss.interfaces().iterator(); iter.hasNext(); ) {
                    Type possInterface = (Type) iter.next();
                    if (possInterface instanceof JifClassType && 
                            !checkedPossibles.contains(possInterface) && 
                            !subPossibles.contains(possInterface)) {
                        subPossibles.add(possInterface);
                    }
                }
            }
            return false;
        }

        if (unlblSubtype instanceof ArrayType && unlblSupertype instanceof ArrayType) {
            // both subtype and supertype are arrays, say of D and C respectively
            // i.e. subtype == D[], supertype = C[]
            // we insist that C[] >= D[] iff C >= D and D >= C.
            Type subBase = ((ArrayType)unlblSubtype).base();  
            Type supBase = ((ArrayType)unlblSupertype).base();

            // check that the const-ness of the arrays is suitable.
            boolean superIsConst = false;
            boolean superIsNonConst = false;
            boolean subIsBoth = false;
            if (unlblSupertype instanceof ConstArrayType) {
                ConstArrayType unlblSuperCat = (ConstArrayType)unlblSupertype; 
                superIsConst = unlblSuperCat.isConst();
                superIsNonConst = unlblSuperCat.isNonConst();
            }
            if (unlblSubtype instanceof ConstArrayType) {
                ConstArrayType unlblSubCat = (ConstArrayType)unlblSubtype; 
                subIsBoth = unlblSubCat.isConst() && unlblSubCat.isNonConst();
            }
            if (superIsConst) {
                // sub must be const
                if (!subIsBoth && (!(unlblSubtype instanceof ConstArrayType) || 
                        !((ConstArrayType)unlblSubtype).isConst())) {
                    throw new SemanticException("A normal array is not a subtype of a const array", pos);
                }
            }
            if (superIsNonConst) {
                // sub must be not-const
                if (!subIsBoth && unlblSubtype instanceof ConstArrayType && !((ConstArrayType)unlblSubtype).isNonConst()) {
                    throw new SemanticException("A const array is not a subtype of a non-const array", pos);
                }
            }

            if (superIsConst || subIsBoth) {
                // the super and sub types are both const arrays, and so are covariant
                // or the sub type is for a cloned array, or an array initializer.
                if (!recursiveAddSubtypeConstraints(lc, pos, supBase, subBase, false)) {
                    return false; 
                }
            }
            else  {
                // the super and sub types are both non-const arrays, and so are invariant.
                if (!recursiveAddSubtypeConstraints(lc, pos, subBase, supBase, true) ||
                        !recursiveAddSubtypeConstraints(lc, pos, supBase, subBase, true)) {
                    return false; 
                }
            }
        }

        return true;
            }

    /**
     * Return the <code>JifPolyType</code> for the given 
     * <code>JifClassType jct</code>. If <code>jct</code> is an 
     * instance of <code>JifPolyType</code>, then <code>jct</code> is returned; 
     * otherwise, if <code>jct</code> is an instance of 
     * <code>JifSubstType</code> then <code>jct.base()</code> 
     * is returned.
     *  
     */
    public static JifPolyType polyTypeForClass(JifClassType jct) {
        if (jct instanceof JifPolyType) {
            return (JifPolyType)jct;
        }
        else if (jct instanceof JifSubstType) {
            return (JifPolyType)((JifSubstType)jct).base();
        }
        throw new InternalCompilerError("Unexpected JifClassType instance." +
                                        "Expected a JifPolyType or JifSubstType, but got " + 
                                        jct.getClass().getName(), jct.position());
    }
}
