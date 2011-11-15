package jif.extension;

import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.*;
import polyglot.types.SemanticException;
/** Jif extension of the <code>While</code> node.
 *  
 *  @see polyglot.ast.While
 */
public class JifWhileExt extends JifStmtExt_c
{
    public JifWhileExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
        While ws = (While) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) ws.del().enterScope(A);

        Label notTaken = ts.notTaken();

        Label L1 = ts.freshLabelVariable(ws.position(), "while",
                                         "label of PC for the while statement at " + ws.position());
        Label L2 = ts.freshLabelVariable(ws.position(), "while",
                                         "label of PC for end of the while statement at " + ws.position());
        Label loopEntryPC = A.pc(); 

        A = (JifContext) A.pushBlock();

        A.setPc(L1, lc);
        A.gotoLabel(Branch.CONTINUE, null, L1);
        A.gotoLabel(Branch.BREAK, null, L2);

        Expr e = (Expr) lc.context(A).labelCheck(ws.cond());
        PathMap Xe = getPathMap(e);

        A = (JifContext) A.pushBlock();

        A.setPc(Xe.NV(), lc);
        Stmt S = (Stmt) lc.context(A).labelCheck(ws.body());
        PathMap Xs = getPathMap(S);

        A = (JifContext) A.pop();
        A = (JifContext) A.pop();

        lc.constrain(new NamedLabel("while_body.N",
                                    "label of normal termination of the loop body", 
                                    Xs.N()).
                                    join(lc,
                                         "loop_entry_pc",
                                         "label of the program counter just before the loop is executed",
                                         loopEntryPC), 
                     LabelConstraint.LEQ, 
                     new NamedLabel("loop_pc",
                                    "label of the program counter at the top of the loop",
                                    L1),
                    A.labelEnv(),
                    ws.position(), 
                    false,
                    new ConstraintMessage() {
            public String msg() {
                return "The information revealed by the normal " +
                "termination of the body of the while loop " +
                "may be more restrictive than the " +
                "information that should be revealed by " +
                "reaching the top of the loop.";
            }
            public String detailMsg() {
                return "The program counter label at the start of the loop is at least as restrictive " +
                "as the normal termination label of the loop body, and the entry " +
                "program counter label (that is, the program counter label just " +
                "before the loop is executed for the first time).";

            }
            public String technicalMsg() {
                return "X(loopbody).n <= _pc_ of the while statement";
            }                     
        }
        );

        PathMap X = Xe.join(Xs);
        X = X.set(ts.gotoPath(Branch.BREAK, null), notTaken);
        X = X.set(ts.gotoPath(Branch.CONTINUE, null), notTaken);
        X = X.N(lc.upperBound(X.N(), L2));

        return updatePathMap(ws.body(S).cond(e), X);
    }
}
