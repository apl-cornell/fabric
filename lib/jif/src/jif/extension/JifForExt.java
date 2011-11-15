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

/** The Jif extension of the <code>For</code> node. 
 * 
 *  @see polyglot.ast.For
 */
public class JifForExt extends JifStmtExt_c
{
    public JifForExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
        For fs = (For) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) fs.del().enterScope(A);

        Label notTaken = ts.notTaken();

        A = (JifContext) A.pushBlock();

        // INIT: A.pushBlock();

        PathMap Xinit = ts.pathMap().N(A.pc());

        List inits = new LinkedList();

        for (Iterator i = fs.inits().iterator(); i.hasNext(); ) {
            Stmt s = (Stmt) i.next();
            s = (Stmt) lc.context(A).labelCheck(s);
            inits.add(s);

            PathMap Xs = getPathMap(s);

            // At this point, the environment A should have been extended
            // to include any declarations of s.  We push a new scope
            // on the stack so that we can set the PC.

            A.setPc(Xs.N(), lc);

            Xinit = Xinit.N(notTaken).join(Xs);
        }

        // Now handle the loop body, condition, and iterators.
        Label L1 = ts.freshLabelVariable(fs.position(), "for",
                                         "label of PC for the for statement at " + node().position());
        Label L2 = ts.freshLabelVariable(fs.position(), "for",
                                         "label of PC for end of the for statement at " + node().position());

        A = (JifContext) A.pushBlock();
        Label loopEntryPC = A.pc();         

        A.setPc(L1, lc);
        A.gotoLabel(Branch.CONTINUE, null, L1);
        A.gotoLabel(Branch.BREAK, null, L2);

        PathMap Xe;
        Expr cond = fs.cond();
        if (cond != null) {
            cond = (Expr) lc.context(A).labelCheck(fs.cond());
            Xe = getPathMap(cond);
        }
        else {
            Xe = ts.pathMap().NV(A.pc()).N(A.pc());
        }

        A = (JifContext) A.pushBlock();	
        A.setPc(Xe.NV(), lc);
        Stmt body = (Stmt) lc.context(A).labelCheck(fs.body());
        PathMap Xbody = getPathMap(body);

        A = (JifContext) A.pushBlock();
        A.setPc(Xbody.N(), lc);

        List iters = new LinkedList();

        for (Iterator i = fs.iters().iterator(); i.hasNext(); ) {
            Stmt s = (Stmt) i.next();
            s = (Stmt) lc.context(A).labelCheck(s);
            iters.add(s);

            PathMap Xs = getPathMap(s);

            // At this point, the environment A should have been extended
            // to include any declarations of s.  Reset the PC label.

            A.setPc(Xs.N(), lc);

            Xbody = Xbody.N(notTaken).join(Xs);
        }

        lc.constrain(new NamedLabel("for_body.N",
                                    "label of normal termination of the loop body", 
                                    Xbody.N()).
                                    join(lc,
                                         "loop_entry_pc",
                                         "label of the program counter just before the loop is executed",
                                         loopEntryPC), 
                     LabelConstraint.LEQ, 
                     new NamedLabel("loop_pc",
                                    "label of the program counter at the top of the loop",
                                    L1),
                    lc.context().labelEnv(),
                    fs.position(), 
                    false,
                    new ConstraintMessage() {
            public String msg() {
                return "The information revealed by the normal " +
                "termination of the body of the for loop " +
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
                return "X(loopbody).n <= _pc_ of the for statement";
            }                     
        }
        );

        // Compute the path map for "loop" == "while (cond) body".
        PathMap Xloop = Xe.join(Xbody);
        Xloop = Xloop.set(ts.gotoPath(Branch.CONTINUE, null), notTaken);
        Xloop = Xloop.set(ts.gotoPath(Branch.BREAK, null), notTaken);
        Xloop = Xloop.N(lc.upperBound(Xloop.N(), L2));

        // Compute the path map for "init ; loop"
        PathMap X = Xinit.N(notTaken).join(Xloop);

        return updatePathMap(fs.iters(iters).cond(cond).inits(inits).body(body), X);
    }
}
