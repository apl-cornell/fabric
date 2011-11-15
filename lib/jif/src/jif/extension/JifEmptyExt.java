package jif.extension;

import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Node;

/** The Jif extension of the <code>Empty</code> node. 
 * 
 *  @see polyglot.ast.Empty
 */
public class JifEmptyExt extends JifStmtExt_c
{
    public JifEmptyExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) {
        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) node().del().enterScope(A);

        PathMap X = ts.pathMap();
        X = X.N(A.pc());
        return updatePathMap(node(), X);
    }
}
