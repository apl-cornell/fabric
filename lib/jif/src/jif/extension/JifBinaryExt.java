package jif.extension;

import java.util.ArrayList;
import java.util.List;

import jif.ast.Jif_c;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.visit.LabelChecker;
import polyglot.ast.*;
import polyglot.types.SemanticException;

/** The Jif extension of the <code>Binary</code> node. 
 *  
 *  @see polyglot.ast.Binary_c
 */
public class JifBinaryExt extends JifExprExt 
{
    public JifBinaryExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheck(LabelChecker lc) throws SemanticException
    {
        Binary be = (Binary) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();

        List throwTypes = new ArrayList(be.del().throwTypes(ts));

        A = (JifContext) be.del().enterScope(A);

        Expr left = (Expr) lc.context(A).labelCheck(be.left());
        PathMap Xl = getPathMap(left);

        A = (JifContext) A.pushBlock();

        if (be.operator() == Binary.COND_AND || be.operator() == Binary.COND_OR) {
            // if it's a short circuit evaluation, then
            // whether the right is executed or not depends on the _value_
            // of the left sub-expression.
            A.setPc(Xl.NV(), lc);            
        }
        else {
            // non-short circuit operator, the right sub-expression
            // will always be evaluated, provided the left sub-expression
            // terminated normally.
            A.setPc(Xl.N(), lc);            
        }

        Expr right = (Expr) lc.context(A).labelCheck(be.right());
        PathMap Xr = getPathMap(right);

        A = (JifContext) A.pop();

        PathMap X = Xl.N(ts.notTaken()).join(Xr);

        if (be.throwsArithmeticException()) {
            checkAndRemoveThrowType(throwTypes, ts.ArithmeticException());
            X = X.exc(Xr.NV(), ts.ArithmeticException());
        }

        checkThrowTypes(throwTypes);
        return updatePathMap(be.left(left).right(right), X);
    }
}
