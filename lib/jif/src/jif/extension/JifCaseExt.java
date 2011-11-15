package jif.extension;

import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Case;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

/** The Jif extension of the <code>Case</code> node. 
 * 
 *  @see polyglot.ast.Case
 */
public class JifCaseExt extends JifStmtExt_c
{
    public JifCaseExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException
    {
        Case cs = (Case) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) cs.del().enterScope(A);

        PathMap X;
        Expr e = cs.expr();
        if (!cs.isDefault()) {
            e = (Expr) lc.context(A).labelCheck(cs.expr());
            X = getPathMap(e).NV(ts.notTaken());
        }
        else {
            X = ts.pathMap().N(A.pc());
        }

        return updatePathMap(cs.expr(e), X);
    }
}
