package jif.extension;

import jif.ast.JifUtil;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.types.label.Label;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.util.ErrorInfo;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;

/** The Jif extension of the <code>If</code> node. 
 * 
 *  @see polyglot.ast.If
 */
public class JifIfExt extends JifStmtExt_c
{
    public JifIfExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
        If is = (If) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) is.del().enterScope(A);

        Expr e = (Expr) lc.context(A).labelCheck(is.cond());

        PathMap Xe = getPathMap(e);

        A = (JifContext) A.pushBlock();
        A.setPc(Xe.NV(), lc);

        // extend the context with any label tests or actsfor tests
        extendContext(lc, A, e, false);

        Stmt S1 = (Stmt) lc.context(A).labelCheck(is.consequent());

        A = (JifContext) A.pop();
        PathMap X1 = getPathMap(S1);

        Stmt S2 = null;
        PathMap X2;

        if (is.alternative() != null) {
            A = (JifContext) A.pushBlock();
            A.setPc(Xe.NV(), lc);

            S2 = (Stmt) lc.context(A).labelCheck(is.alternative());

            A = (JifContext) A.pop();
            X2 = getPathMap(S2);
        }
        else {
            // Simulate the effect of an empty statement.
            // X0[node() := A[pc := Xe[nv][pc]]] == Xe[nv]
            X2 = ts.pathMap().N(Xe.NV());
        }

        /*
	trace("Xe == " + Xe);
	trace("X1 == " + X1);
	trace("X2 == " + X2);
         */

        PathMap X = Xe.N(ts.notTaken()).join(X1).join(X2);
        X = X.NV(ts.notTaken());
        return updatePathMap(is.cond(e).consequent(S1).alternative(S2), X);
    }

    protected static void extendContext(LabelChecker lc, JifContext A, Expr e, boolean warn) throws SemanticException {
        if (e instanceof Binary) {
            Binary b = (Binary)e;
            if (b.operator() == Binary.BIT_AND || b.operator() == Binary.COND_AND) {
                extendContext(lc, A, b.left(), warn);
                extendContext(lc, A, b.right(), warn);
            }
            if (b.operator() == Binary.BIT_OR || b.operator() == Binary.COND_OR) {
                extendContext(lc, A, b.left(), true);
                extendContext(lc, A, b.right(), true);
            }
        }
        if (e instanceof Unary) {
            Unary u = (Unary)e;
            if (u.operator() == Unary.NOT && u.expr() instanceof Binary) {
                extendContext(lc, A, u.expr(), true);                
            }
        }
        extendFact(lc, A, e, warn);
    }

    protected static void extendFact(LabelChecker lc, 
            JifContext A, 
            Expr e, 
            boolean warn) throws SemanticException {
        if (e instanceof Binary) {
            extendFact(lc, A, (Binary)e, warn);
        }       
        if (e instanceof Unary) {
            Unary u = (Unary)e;
            if (u.expr() instanceof Binary) {
                extendFact(lc, A, (Binary)u.expr(), true);                
            }
        }
    }


    protected static void extendFact(LabelChecker lc, JifContext A, Binary b, boolean warn) throws SemanticException {
        JifTypeSystem ts = lc.typeSystem();
        Binary.Operator op = b.operator();

        if (op == JifBinaryDel.ACTSFOR) {            
            Principal actor = JifUtil.exprToPrincipal(ts, b.left(), A);
            Principal granter = JifUtil.exprToPrincipal(ts, b.right(), A);
            if (warn) {
                // give a warning.
                ErrorQueue eq = lc.errorQueue();
                eq.enqueue(ErrorInfo.WARNING,
                           "The Jif compiler can only reason about " +
                           "actsfor tests if they occur as conjuncts in the " +
                           "conditional of an if statement.",
                           b.position());

            }
            else {
                A.addActsFor(actor, granter);
            }
        }
        else if (op == JifBinaryDel.EQUIV && ts.isImplicitCastValid(b.left().type(), ts.Principal())) {
            Principal left = JifUtil.exprToPrincipal(ts, b.left(), A);
            Principal right = JifUtil.exprToPrincipal(ts, b.right(), A);
            if (warn) {
                // give a warning.
                ErrorQueue eq = lc.errorQueue();
                eq.enqueue(ErrorInfo.WARNING,
                           "The Jif compiler can only reason about " +
                           "actsfor tests if they occur as conjuncts in the " +
                           "conditional of an if statement.",
                           b.position());

            }
            else {
                A.addEquiv(left, right);                            
            }
        }
        else if (op == JifBinaryDel.EQUIV && ts.isLabel(b.left().type())) {
            Label lhs = JifUtil.exprToLabel(ts, b.left(), A);
            Label rhs = JifUtil.exprToLabel(ts, b.right(), A);

            if (warn) {
                // give a warning.
                ErrorQueue eq = lc.errorQueue();
                eq.enqueue(ErrorInfo.WARNING,
                           "The Jif compiler can only reason about label tests " +
                           "if they occur as conjuncts in the " +
                           "conditional of an if statement.",
                           b.position());

            }
            else {
                A.addEquiv(lhs, rhs);
            }
        }
        else if (op == Binary.LE && ts.isLabel(b.left().type())) {
            Label lhs = JifUtil.exprToLabel(ts, b.left(), A);
            Label rhs = JifUtil.exprToLabel(ts, b.right(), A);
            if (warn) {
                // give a warning.
                ErrorQueue eq = lc.errorQueue();
                eq.enqueue(ErrorInfo.WARNING,
                           "The Jif compiler can only reason about label tests " +
                           "if they occur as conjuncts in the " +
                           "conditional of an if statement.",
                           b.position());

            }
            else {
                A.addAssertionLE(lhs, rhs);
            }
        }
    }
}
