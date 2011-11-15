package jif.extension;

import java.util.ArrayList;
import java.util.List;

import jif.ast.JifInstantiator;
import jif.ast.JifUtil;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.*;
import polyglot.types.*;

/** The Jif extension of the <code>LocalAssign</code> node. 
 */
public class JifFieldAssignExt extends JifAssignExt
{
    public JifFieldAssignExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckLHS(LabelChecker lc)
    throws SemanticException
    {
        Assign assign = (Assign) node();
        Field fe = (Field) assign.left();
       

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        // commented out by zlt
        // A = (JifContext) fe.enterScope(A);

        List throwTypes = new ArrayList(assign.del().throwTypes(ts));
        Type npe = ts.NullPointerException();
        Type are = ts.ArithmeticException();

        Receiver target = JifFieldExt.checkTarget(lc, fe);
        PathMap Xe = getPathMap(target);

        // check rhs
        A = (JifContext) A.pushBlock();
        A.setPc(Xe.N(), lc); 

        Expr rhs = (Expr) lc.context(A).labelCheck(assign.right());
        PathMap Xr = rhsPathMap(lc.context(A), rhs, throwTypes);

        A = (JifContext) A.pop();

        PathMap X = Xe.join(Xr); 

        if (((JifAssignDel)assign.del()).throwsArithmeticException()) {
            checkAndRemoveThrowType(throwTypes, are);
            X = X.exc(Xr.NV(), are);
        }

        if (!((JifFieldDel)fe.del()).targetIsNeverNull()) {
            // may throw a null pointer exception
            checkAndRemoveThrowType(throwTypes, npe);
            X = X.exc(lc.upperBound(Xe.NV(), Xr.N()), npe);
        }


        // Must be done after visiting target to get PC right.        

        // Find the field instance again. This ensures that
        // we have the correctly instantiated type, as label checking
        // of the target may have produced a new type for the target.
        ReferenceType targetType = JifFieldExt.targetType(ts, A, target, fe);
        final JifFieldInstance fi = (JifFieldInstance)ts.findField(targetType, fe.name());
        fe = fe.fieldInstance(fi);

        Label Lf = ts.labelOfField(fi, A.pc());
        

        if (target instanceof Expr) {
            if (!(target instanceof Special)) {
                Lf = JifInstantiator.instantiate(Lf, A, (Expr)target, JifFieldExt.targetType(ts, A, target, fe), getPathMap(target).NV());
            }
            else {
                JifClassType jct = (JifClassType) A.currentClass();
                Lf = JifInstantiator.instantiate(Lf, A, (Expr)target, JifFieldExt.targetType(ts, A, target, fe), jct.thisLabel());
            }
        }

        Label L = Lf;

        if (target instanceof Expr) {
            // instantiate the type of the field
            Type ft = JifInstantiator.instantiate(fe.type(), A, (Expr)target, JifFieldExt.targetType(ts, A, target, fe), getPathMap(target).NV());         
            fe = (Field)fe.type(ft);
        }

        if (target instanceof Special && A.checkingInits()) {
            // Relax the constraint: instead of X[nv] <= L, use
            // X[nv] <= {L; Lr}, where Lr is the return label of the
            // constructor. We can do this because Lr <= <var this>, 
            // and {L; Lr} <= X(this.f).nv
            Label Lr = A.constructorReturnLabel();

            if (Lr != null) 
                L = lc.upperBound(L, Lr);

            // if it is a final field being initialized,
            // add a definitional assertion that the field is equivalent
            // to the expression being assigned to it.
            if (fi.flags().isFinal() && JifUtil.isFinalAccessExprOrConst(ts, assign.right())) { 
                if (ts.isLabel(fi.type())) {
                    Label dl = ts.dynamicLabel(fi.position(), JifUtil.varInstanceToAccessPath(fi, fi.position()));                
                    Label rhs_label = JifUtil.exprToLabel(ts, assign.right(), A);
                    A.addDefinitionalAssertionEquiv(dl, rhs_label);
                }
                else if (ts.isImplicitCastValid(fi.type(), ts.Principal())) {
                    DynamicPrincipal dp = ts.dynamicPrincipal(fi.position(), JifUtil.varInstanceToAccessPath(fi, fi.position()));                
                    Principal rhs_principal = JifUtil.exprToPrincipal(ts, assign.right(), A);
                    A.addDefinitionalEquiv(dp, rhs_principal);                    
                }
                else {
                    // record that this field and the value assigned to it are the same.
                    A.addDefinitionalAssertionEquiv(JifUtil.varInstanceToAccessPath(fi, fi.position()),
                                                    JifUtil.exprToAccessPath(assign.right(), A)); 
                }
            }                            

        }

        lc.constrain(new NamedLabel("rhs.nv", 
                                    "label of successful evaluation of right hand of assignment", 
                                    X.NV()), 
                    LabelConstraint.LEQ, 
                    new NamedLabel("label of field " + fi.name(), L),
                    A.labelEnv(),
                    fe.position(),
                    new ConstraintMessage() {
            public String msg() {
                return "Label of right hand side not less " + 
                "restrictive than the label for field " + 
                fi.name();
            }
            public String detailMsg() { 
                return "More information is revealed by the successful " +
                "evaluation of the right hand side of the " +
                "assignment than is allowed to flow to " +
                "the field " + fi.name() + ".";
            }
            public String technicalMsg() {
                return "Invalid assignment: path NV of rhs is " +
                "more restrictive than the declared label " +
                "of the field <" + fi.name() + ">.";
            }

        }
        );

        if (target instanceof Special && A.checkingInits()) {
            // In constructors, assignments to fields are not
            // considered as side-effects.
        }
        else {
            lc.constrain(new NamedLabel("Li", 
                                        "Lower bound for side-effects", 
                                        A.currentCodePCBound()), 
                        LabelConstraint.LEQ, 
                        new NamedLabel("label of field " + fi.name(), L),
                        A.labelEnv(),
                        fe.position(),
                        new ConstraintMessage() {
                public String msg() {
                    return "Effect of assignment to field " + fi.name() + 
                    " is not bounded below by the PC bound.";
                }
                public String detailMsg() { 
                    return "Assignment to the field " + fi.name() + 
                    " is a side effect which reveals more" +
                    " information than this method is allowed" +
                    " to; the side effects of this method must" +
                    " be bounded below by the method's PC" +
                    " bound, Li.";
                }
                public String technicalMsg() {
                    return "Invalid assignment: Li is more " +
                    "restrictive than the declared label " +
                    "of the field <" + fi.name() + ">.";
                }

            }
            );
        }

        if (assign.operator() != Assign.ASSIGN) {
            // e.g. f += 1
            X = X.NV(lc.upperBound(X.NV(), Lf));
        }

        Expr lhs = (Expr) updatePathMap(fe.target(target), X);

        checkThrowTypes(throwTypes);
        return (Assign) updatePathMap(assign.right(rhs).left(lhs), X);
    }
    protected PathMap rhsPathMap(LabelChecker lc, Expr rhs, List throwTypes) {
        return getPathMap(rhs);
    }
}
