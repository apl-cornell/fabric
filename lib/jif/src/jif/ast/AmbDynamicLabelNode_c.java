package jif.ast;

import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.SemanticDetailedException;
import jif.types.label.AccessPath;
import jif.types.label.Label;
import jif.visit.JifTypeChecker;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.frontend.MissingDependencyException;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.types.Context;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.*;

/** An implementation of the <tt>AmbDynamicLabel</tt> interface. */
public class AmbDynamicLabelNode_c extends AmbLabelNode_c implements AmbDynamicLabelNode
{
    protected Expr expr;

    public AmbDynamicLabelNode_c(Position pos, Expr expr) {
        super(pos);
        this.expr = expr;
    }

    public String toString() {
        return "*" + expr + "{amb}";
    }

    /** Disambiguate the type of this node. */
    public Node disambiguate(AmbiguityRemover sc) throws SemanticException {
        Context c = sc.context();
        JifTypeSystem ts = (JifTypeSystem) sc.typeSystem();
        JifNodeFactory nf = (JifNodeFactory) sc.nodeFactory();

        if (!sc.isASTDisambiguated(expr)) {
            sc.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return this;
        }

        // run the typechecker over expr.
        TypeChecker tc = new JifTypeChecker(sc.job(), ts, nf, true);
        tc.setCheckConstants(false);
        tc = (TypeChecker) tc.context(sc.context());
        expr = (Expr)expr.visit(tc);

        if (expr.type() == null || !expr.type().isCanonical()) {
            if (expr instanceof Field) {
                Field f = (Field)expr;
                if (ts.unlabel(f.target().type()) instanceof ParsedClassType) {
                    // disambiguate the class of the receiver of the field,
                    // so that type checking will eventually go through.
                    ParsedClassType pct = (ParsedClassType)ts.unlabel(f.target().type());
                    Scheduler sched = sc.job().extensionInfo().scheduler();
                    Goal g = sched.Disambiguated(pct.job());
                    throw new MissingDependencyException(g);                        
                }
//              System.err.println("***Failed with " + expr + " : " + expr.getClass() + " " + expr.type());
//              System.err.println("   unlabeled target type: " + ts.unlabel(f.target().type()));
//              System.err.println("   target : " + f.target() + "  " + f.target().getClass());
//              if (f.target() instanceof Local) {
//              Local loc = (Local)f.target();
//              System.err.println("   local context lookup: " + sc.context().findLocal(loc.name()));
//              }
            }
//          Scheduler sched = sc.job().extensionInfo().scheduler();
//          Goal g = sched.Disambiguated(sc.job());
//          throw new MissingDependencyException(g);                        
            sc.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return this;
        }

        if (expr.type() != null && expr.type().isCanonical() && 
                !JifUtil.isFinalAccessExprOrConst(ts, expr, ts.Label())) {
            // illegal dynamic label. But try to convert it to an access path
            // to allow a more precise error message.
            AccessPath ap = JifUtil.exprToAccessPath(expr, ts.Label(), (JifContext)c); 
            ap.verify((JifContext)c);

            // previous line should throw an exception, but throw this just to
            // be safe.
            throw new SemanticDetailedException(
                                                "Illegal dynamic label.",
                                                "Only final access paths or label expressions can be used as a dynamic label. " +
                                                "A final access path is an expression starting with either \"this\" or a final " +
                                                "local variable \"v\", followed by zero or more final field accesses. That is, " +
                                                "a final access path is either this.f1.f2....fn, or v.f1.f2.....fn, where v is a " +
                                                "final local variables, and each field f1 to fn is a final field. A label expression " +
                                                "is either a label parameter, or a \"new label {...}\" expression.",
                                                this.position());
        }

        // the expression type may not yet be fully determined, but
        // that's ok, as type checking will ensure that it is
        // a suitable expression.
        Label L = JifUtil.exprToLabel(ts, expr, (JifContext)c);
        return nf.CanonicalLabelNode(position(), L);
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("*");
        expr.del().prettyPrint(w, tr);
    }
    public Node visitChildren(NodeVisitor v) {
        Expr expr = (Expr) visitChild(this.expr, v);
        return reconstruct(expr);
    }
    protected AmbDynamicLabelNode_c reconstruct(Expr expr) {
        if (this.expr != expr) {
            AmbDynamicLabelNode_c n = (AmbDynamicLabelNode_c) copy();
            n.expr = expr;
            return n;
        }

        return this;
    }

}
