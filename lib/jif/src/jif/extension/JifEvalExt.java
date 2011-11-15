package jif.extension;

import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Eval;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

/** The Jif extension of the <code>Eval</code> node. 
 * 
 *  @see polyglot.ast.Eval
 */
public class JifEvalExt extends JifStmtExt_c
{
    public JifEvalExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
        Eval eval = (Eval) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) eval.del().enterScope(A);

        Expr e = (Expr) lc.context(A).labelCheck(eval.expr());

        PathMap X = getPathMap(e);
        X = X.NV(ts.notTaken());
        return updatePathMap(eval.expr(e), X);
    }
}
