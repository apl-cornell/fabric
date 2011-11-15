package jif.extension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Block;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.types.SemanticException;

/** The Jif extension of the <code>Block</code> node. 
 * 
 *  @see polyglot.ast.Block_c
 */
public class JifBlockExt extends JifStmtExt_c
{
    public JifBlockExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException
    {
        Block bs = (Block) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) bs.del().enterScope(A);

        // A path map incorporating all statements in the block seen so far.
        PathMap Xblock = ts.pathMap();
        Xblock = Xblock.N(A.pc());

        A = (JifContext) A.pushBlock();

        List l = new ArrayList(bs.statements().size());

        for (Iterator i = bs.statements().iterator(); i.hasNext(); ) {
            Stmt s = (Stmt) i.next();
            s = (Stmt) lc.context(A).labelCheck(s);
            l.add(s);

            PathMap Xs = getPathMap(s);

            // At this point, the environment A should have been extended
            // to include any declarations of s.  Reset the PC label.
            A.setPc(Xs.N(), lc);

            Xblock = Xblock.N(ts.notTaken()).join(Xs);
        }

        A = (JifContext) A.pop();

        return updatePathMap(bs.statements(l), Xblock);
    }
}
