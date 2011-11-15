package jif.extension;

import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.Labeled;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.types.SemanticException;

/** The Jif extension of the <code>Labeled</code> node. 
 * 
 *  @see polyglot.ast.Labeled
 */
public class JifLabeledExt extends JifStmtExt_c
{
    public JifLabeledExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException
    {
        Labeled ls = (Labeled) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) ls.del().enterScope(A);

        String label = ls.label();

        Label L1 = ts.freshLabelVariable(node().position(), label, 
                                         "label of the PC at the labeled position " + label + " (" +
                                         ls.position()+")");
        Label L2 = ts.freshLabelVariable(node().position(), label,
                                         "label of the PC at the labeled position " + label + " (" +
                                         ls.position()+")");


        A = (JifContext) A.pushBlock();
        A.gotoLabel(polyglot.ast.Branch.CONTINUE, label, L1);
        A.gotoLabel(polyglot.ast.Branch.BREAK, label, L2);

        A.setPc(lc.upperBound(A.pc(), L1), lc);

        Stmt s = (Stmt) lc.context(A).labelCheck(ls.statement());

        A = (JifContext) A.pop();

        PathMap Xs = getPathMap(s);

        PathMap X = Xs.N(lc.upperBound(Xs.N(), L2));

        X = X.set(ts.gotoPath(polyglot.ast.Branch.CONTINUE, label),
                  ts.notTaken());
        X = X.set(ts.gotoPath(polyglot.ast.Branch.BREAK, label),
                  ts.notTaken());

        return updatePathMap(ls.statement(s), X);
    }
}
