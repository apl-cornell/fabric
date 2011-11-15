package jif.extension;

import jif.ast.*;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.SemanticDetailedException;
import jif.types.label.AccessPath;
import jif.types.principal.Principal;
import polyglot.ast.*;
import polyglot.ast.Binary.Operator;
import polyglot.types.SemanticException;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeChecker;

public class JifBinaryDel extends JifJL_c
{
    public static final Binary.Operator ACTSFOR  = new Operator("actsfor", Precedence.RELATIONAL);
    public static final Binary.Operator EQUIV  = new Operator("equiv", Precedence.RELATIONAL);

    public JifBinaryDel() { }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
        Binary b = (Binary)node();
        JifTypeSystem ts = (JifTypeSystem)tc.typeSystem();
        if ((b.operator() == Binary.LE || b.operator() == EQUIV) && 
                (ts.isLabel(b.left().type()) || ts.isLabel(b.right().type()))) {
            if (!(ts.isLabel(b.left().type()) && ts.isLabel(b.right().type()))) {
                throw new SemanticException("The operator " + b.operator() + " requires both operands to be labels.", b.position());
            }

            // we have a label comparison
            // make sure that both left and right are LabelExprs.
            JifNodeFactory nf = (JifNodeFactory)tc.nodeFactory();
            LabelExpr lhs;
            if (b.left() instanceof LabelExpr) {
                lhs = (LabelExpr)b.left();
            }
            else {
                if (!JifUtil.isFinalAccessExprOrConst(ts, b.left())) {
                    throw new SemanticException(
                            "An expression used in a label test must be either a final access path, principal parameter or a constant principal",
                            b.left().position());
                }
                lhs = nf.LabelExpr(b.left().position(), 
                                   JifUtil.exprToLabel(ts, b.left(), (JifContext)tc.context()));
                lhs = (LabelExpr)lhs.visit(tc);
            }
            LabelExpr rhs;
            if (b.right() instanceof LabelExpr) {
                rhs = (LabelExpr)b.right();
            }
            else {
                if (!JifUtil.isFinalAccessExprOrConst(ts, b.right())) {
                    throw new SemanticException(
                            "An expression used in a label test must either be a final access path or a \"new label\"",
                            b.right().position());
                }
                rhs = nf.LabelExpr(b.right().position(),
                                   JifUtil.exprToLabel(ts, b.right(), (JifContext)tc.context()));
                rhs = (LabelExpr)rhs.visit(tc);
            }
            return b.left(lhs).right(rhs).type(ts.Boolean());
        }
        
        boolean leftPrinc = ts.isImplicitCastValid(b.left().type(), ts.Principal());
        boolean rightPrinc = ts.isImplicitCastValid(b.right().type(), ts.Principal());
        if (b.operator() == ACTSFOR || (b.operator() == EQUIV && (leftPrinc || rightPrinc))) {
            if (!(leftPrinc && rightPrinc)) {
                throw new SemanticException("The operator " + b.operator() + " requires both operands to be principals.", b.position());
            }
            // we have an actsfor comparison
            // make sure that both left and right are principals.
            checkPrincipalExpr(tc, b.left());
            checkPrincipalExpr(tc, b.right());
            return b.type(ts.Boolean());            
        }
        
        if (b.operator() == EQUIV) {
            throw new SemanticException("The equiv operator requires either both operands to be principals, or both operands to be labels.", b.position());
        }
        
        return super.typeCheck(tc);
    }

    private void checkPrincipalExpr(TypeChecker tc, Expr expr) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem)tc.typeSystem();
        if (expr instanceof PrincipalExpr) return;

        if (expr.type() != null && expr.type().isCanonical() && 
                !JifUtil.isFinalAccessExprOrConst(ts, expr)) {
            // illegal dynamic principal. But try to convert it to an access path
            // to allow a more precise error message.
            AccessPath ap = JifUtil.exprToAccessPath(expr, (JifContext)tc.context()); 
            ap.verify((JifContext)tc.context());

            // previous line should throw an exception, but throw this just to
            // be safe.
            throw new SemanticDetailedException(
                "Illegal dynamic principal.",
                "Only final access paths or principal expressions can be used as a dynamic principal. " +
                "A final access path is an expression starting with either \"this\" or a final " +
                "local variable \"v\", followed by zero or more final field accesses. That is, " +
                "a final access path is either this.f1.f2....fn, or v.f1.f2.....fn, where v is a " +
                "final local variables, and each field f1 to fn is a final field. A principal expression " +
                "is either a principal parameter, or an external principal.",
                expr.position());                                        
        }

        Principal p = JifUtil.exprToPrincipal(ts, expr, (JifContext)tc.context());
        if (!p.isRuntimeRepresentable()) {
            throw new SemanticDetailedException(
                    "A principal used in an actsfor must be runtime-representable.",                    
                    "Both principals used in an actsfor test must be " +
                    "represented at runtime, since the actsfor test is a dynamic " +
                    "test. The principal " + p + 
                    " is not represented at runtime, and thus cannot be used " +
                    "in an actsfor test.",
                    expr.position());
        }
        
    }
    
}
