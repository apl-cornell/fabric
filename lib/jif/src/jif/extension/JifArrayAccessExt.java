package jif.extension;

import java.util.ArrayList;
import java.util.List;

import jif.ast.*;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.Position;

/** The Jif extension of the <code>ArrayAccess</code> node. 
 */
public class JifArrayAccessExt extends JifExprExt
{
    public JifArrayAccessExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckIncrement(LabelChecker lc) throws SemanticException
    {
        JifNodeFactory nf = (JifNodeFactory)lc.nodeFactory();
        ArrayAccess ae = (ArrayAccess) node();
        Position pos = ae.position();
        ArrayAccessAssign aae = nf.ArrayAccessAssign(pos, ae, Assign.ADD_ASSIGN, 
                                                     nf.IntLit(pos, IntLit.INT, 1));

        aae = (ArrayAccessAssign)lc.labelCheck(aae);

        return aae.left();
    }

    public Node labelCheck(LabelChecker lc)
    throws SemanticException
    {
        JifContext A = lc.jifContext();
        JifTypeSystem ts = lc.jifTypeSystem();
        ArrayAccess aie = (ArrayAccess) node();

        List throwTypes = new ArrayList(aie.del().throwTypes(ts));

        Expr array = (Expr) lc.context(A).labelCheck(aie.array());
        PathMap Xa = getPathMap(array);

        A = (JifContext) A.pushBlock();
        A.setPc(Xa.N(), lc);

        Expr index = (Expr) lc.context(A).labelCheck(aie.index());
        PathMap Xb = getPathMap(index);

        A = (JifContext) A.pop();

        Label La = arrayBaseLabel(array, ts);

        Type npe = ts.NullPointerException();
        Type oob = ts.OutOfBoundsException();

        PathMap X2 = Xa.join(Xb);
        if (!((JifArrayAccessDel)node().del()).arrayIsNeverNull()) {
            // a null pointer exception may be thrown
            checkAndRemoveThrowType(throwTypes, npe);
            X2 = X2.exc(Xa.NV(), npe);             
        }
        if (((JifArrayAccessDel)node().del()).outOfBoundsExcThrown()) {
            // an out of bounds exception may be thrown
            checkAndRemoveThrowType(throwTypes, oob);
            X2 = X2.exc(lc.upperBound(Xa.NV(), Xb.NV()), oob);
        }

        PathMap X = X2.NV(lc.upperBound(La, X2.NV()));

        checkThrowTypes(throwTypes);
        return updatePathMap(aie.index(index).array(array), X);
    }

    private Type arrayType(Expr array, JifTypeSystem ts) {
        Type arrayType = array.type();
        if (array instanceof Local) {
            arrayType = ((Local)array).localInstance().type();	
        }

        return ts.unlabel(arrayType); 
    }

    private Label arrayBaseLabel(Expr array, JifTypeSystem ts) {
        Type arrayType = arrayType(array, ts);	
        return ts.labelOfType(((ArrayType)arrayType).base());	
    }

}
