package jif.extension;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.*;
import polyglot.types.SemanticException;

/** Jif extension of the <code>Switch</code> node.
 *  
 *  @see polyglot.ast.Switch
 */
public class JifSwitchExt extends JifStmtExt_c
{
    public JifSwitchExt(ToJavaExt toJava) {
        super(toJava);
    }

    /** Label check the switch statement.
     * 
     *  PC(branch i) = X(branch 0).N + ... + X(branch i-1).N
     */
    public Node labelCheckStmt(LabelChecker lc) throws SemanticException
    {
        Switch ss = (Switch) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) ss.del().enterScope(A);

        Label notTaken = ts.notTaken();

        Expr e = (Expr) lc.context(A).labelCheck(ss.expr());
        PathMap Xe = getPathMap(e);


        Label L = ts.freshLabelVariable(ss.position(), "switch",
                                        "label of PC at break target for switch statement at " + node().position());

        A = (JifContext) A.pushBlock();
        A.setPc(Xe.NV(), lc);
        A.gotoLabel(Branch.BREAK, null, L);

        PathMap Xa = Xe.N(notTaken);
        List l = new LinkedList();

        for (Iterator iter = ss.elements().iterator(); iter.hasNext(); ) {
            Stmt s = (Stmt) iter.next();
            s = (Stmt) lc.context(A).labelCheck(s);
            l.add(s);

            PathMap Xs = getPathMap(s);
            A.setPc(lc.upperBound(A.pc(), Xs.N()), lc);
            Xa = Xa.join(Xs);
        }

        A = (JifContext) A.pop();
        lc.constrain(new NamedLabel("label of normal termination of swtich statement", Xa.N()),
                     LabelConstraint.LEQ,
                     new NamedLabel("label of break target for the switch stmt", L),
                     A.labelEnv(),
                     ss.position(),
                     false,
                     new ConstraintMessage() {
            public String msg() { 
                return "The information revealed by the normal " +
                "termination of the switch statement " +
                "may be more restrictive than the " +
                "information that can be revealed by " +
                "a break statement being executed in the " +
                "switch statement.";
            }
            public String technicalMsg() {
                return "[join(X(branch_i).n) <= L(break)] is not satisfied.";
            }

        }
        );

        PathMap X = Xa.set(ts.gotoPath(Branch.BREAK, null), notTaken);
        X = X.NV(ts.notTaken());
        X = X.N(L);

        return updatePathMap(ss.elements(l), X);
    }
}
