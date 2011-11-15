package jif.extension;

import java.util.ArrayList;
import java.util.List;

import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;

/** The Jif extension of the <code>ArrayAccessAssign</code> node. 
 */
public class JifArrayAccessAssignExt extends JifAssignExt
{
    public JifArrayAccessAssignExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckLHS(LabelChecker lc)
    throws SemanticException
    {
        ArrayAccessAssign assign = (ArrayAccessAssign)node();
        final ArrayAccess aie = (ArrayAccess) assign.left();
        JifContext A = lc.jifContext();
        A = (JifContext) aie.del().enterScope(A);
        JifTypeSystem ts = lc.jifTypeSystem();

        List throwTypes = new ArrayList(assign.del().throwTypes(ts));

        if (assign.left() != aie) {
            throw new InternalCompilerError(aie +
                                            " is not the left hand side of " + assign);
        }

        Type npe = ts.NullPointerException();
        Type oob = ts.OutOfBoundsException();
        Type ase = ts.ArrayStoreException();
        Type are = ts.ArithmeticException();

        final Expr array = (Expr) lc.context(A).labelCheck(aie.array());
        PathMap Xarr = getPathMap(array);

        A = (JifContext) A.pushBlock();
        A.setPc(Xarr.N(), lc);

        Expr index = (Expr) lc.context(A).labelCheck(aie.index());
        PathMap Xind = getPathMap(index);

        PathMap Xlhs = Xarr.join(Xind);
        A = (JifContext) A.pushBlock();

        if (assign.operator() != Assign.ASSIGN) {
            // op= assignments evaluate the value in the lhs before evaluating the rhs
            // Thus, the evaluation of the RHS occurs after the NPE or OOB exceptions may have been thrown.
            if (!((JifArrayAccessDel)assign.left().del()).arrayIsNeverNull()) {
                // a null pointer exception may be thrown
                checkAndRemoveThrowType(throwTypes, npe);
                Xlhs = Xlhs.exc(lc.upperBound(Xarr.NV(), Xind.N()), npe);             
            }
            if (((JifArrayAccessDel)assign.left().del()).outOfBoundsExcThrown()) {
                // an out of bounds exception may be thrown
                checkAndRemoveThrowType(throwTypes, oob);
                Xlhs = Xlhs.exc(lc.upperBound(Xarr.NV(), Xind.NV()), oob);
            }
        }
        A.setPc(Xlhs.N(), lc);

        Expr rhs = (Expr) lc.context(A).labelCheck(assign.right());
        PathMap Xrhs = rhsPathMap(lc.context(A), rhs, throwTypes);

        A = (JifContext) A.pop();
        A = (JifContext) A.pop();

        Label La = arrayBaseLabel(array, ts);

        PathMap X = Xlhs.join(Xrhs);

        if (assign.operator() != Assign.ASSIGN) {
            // the normal value include the value that was already in the array 
            X = X.NV(lc.upperBound(La, X.NV()));

            if (((JifAssignDel)assign.del()).throwsArithmeticException()) {
                checkAndRemoveThrowType(throwTypes, are);
                X = X.exc(Xrhs.NV(), are);
            }
            Xrhs = X; // the value computed to store in the array actually depends on everything computed so far 
        }
        else {
            // at this point here, for an = assignment, evaluation may decide to throw a NPE or OOB
            if (!((JifArrayAccessDel)assign.left().del()).arrayIsNeverNull()) {
                // a null pointer exception may be thrown
                checkAndRemoveThrowType(throwTypes, npe);
                X = X.exc(lc.upperBound(Xarr.NV(), X.N()), npe);             
            }
            if (((JifArrayAccessDel)assign.left().del()).outOfBoundsExcThrown()) {
                // an out of bounds exception may be thrown
                checkAndRemoveThrowType(throwTypes, oob);
                X = X.exc(lc.upperBound(Xarr.NV(), Xind.NV(), X.N()), oob);
            }

            if (assign.throwsArrayStoreException()) {
                checkAndRemoveThrowType(throwTypes, ase);
                X = X.exc(lc.upperBound(Xarr.NV(), Xrhs.NV(), X.N()), ase);
            }
        }

        NamedLabel namedLa = new NamedLabel("La",
                                            "Label of the array base type",
                                            La);
        lc.constrain(new NamedLabel("rhs.nv", 
                                    "label of successful evaluation of right hand of assignment",
                                    Xrhs.NV()).
                                    join(lc, 
                                         "lhs.n", 
                                         "label of successful evaluation of array access " + aie,
                                         X.N()), 
                     LabelConstraint.LEQ, 
                     namedLa,
                     A.labelEnv(),
                     aie.position(),
                     new ConstraintMessage() {
            public String msg() {
                return "Label of succesful evaluation of array " +
                "access and right hand side of the " +
                "assignment is not less restrictive than " +
                "the label for the array base type.";
            }
            public String detailMsg() { 
                return "More information may be revealed by the successul " +
                "evaluation of the array access " + aie + 
                " and the right hand side of the assignment " +
                "than is allowed to flow to elements of the " + 
                "array. Elements of the array can only " +
                "contain information up to the label of the " +
                "array base type, La.";
            }
            public String technicalMsg() {
                return "Invalid assignment: " + namedLhs().toString() + 
                " is not less restrictive than the label of " +
                "array element.";
            }                     
        }
        );

        lc.constrain(new NamedLabel("Li", 
                                    "Lower bound for side-effects", 
                                    A.currentCodePCBound()), 
                    LabelConstraint.LEQ, 
                    namedLa,
                    A.labelEnv(),
                    aie.position(),
                    new ConstraintMessage() {
            public String msg() {
                return "Effect of assignment to array " + array + 
                " is not bounded below by the PC bound.";
            }
            public String detailMsg() { 
                return "Assignment to the array " + array + 
                " is a side effect which reveals more" +
                " information than this method is allowed" +
                " to; the side effects of this method must" +
                " be bounded below by the method's PC" +
                " bound, Li.";
            }
            public String technicalMsg() {
                return "Invalid assignment: Li is more " +
                "restrictive than array base label.";
            }

        }
        );

        Expr lhs = (Expr) updatePathMap(aie.index(index).array(array), X);

        checkThrowTypes(throwTypes);
        return (Assign) updatePathMap(assign.right(rhs).left(lhs), X);
    }

    protected PathMap rhsPathMap(LabelChecker checker, Expr rhs, List throwTypes) {
        return getPathMap(rhs);
    }

    private Label arrayBaseLabel(Expr array, JifTypeSystem ts) {        
        ArrayType arrayType = (ArrayType)ts.unlabel(array.type());
        return ts.labelOfType(arrayType.base());	
    }

}
