package jif.extension;

import java.util.ArrayList;
import java.util.List;

import jif.translate.ToJavaExt;
import jif.types.*;
import jif.visit.LabelChecker;
import polyglot.ast.*;
import polyglot.types.SemanticException;

/** Jif extension of the <code>Throw</code> node.
 *  
 *  @see polyglot.ast.Throw
 */
public class JifThrowExt extends JifStmtExt_c
{
    public JifThrowExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException
    {
        Throw ths = (Throw) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) ths.del().enterScope(A);

        List throwTypes = new ArrayList(ths.del().throwTypes(ts));

        Expr e = (Expr) lc.context(A).labelCheck(ths.expr());
        PathMap Xe = getPathMap(e);
        PathMap X = Xe;

        if (!((JifThrowDel)ths.del()).thrownIsNeverNull() && !ts.NullPointerException().equals(e.type())) {
            checkAndRemoveThrowType(throwTypes, ts.NullPointerException());
            X = X.exc(Xe.NV(), ts.NullPointerException());            
        }
        checkAndRemoveThrowType(throwTypes, e.type());
        X = X.exc(Xe.NV(), e.type());

        // the evaluation doesn't terminate normally.
        X = X.N(ts.notTaken()).NV(ts.notTaken());

        checkThrowTypes(throwTypes);
        return updatePathMap(ths.expr(e), X);
    }
}
