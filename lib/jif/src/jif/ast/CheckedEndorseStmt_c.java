package jif.ast;

import polyglot.ast.Expr;
import polyglot.ast.If;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

/** An implementation of the <code>CheckedEndorseStmt</code> interface.
 */
public class CheckedEndorseStmt_c extends EndorseStmt_c implements CheckedEndorseStmt
{
    private Expr expr;
    public CheckedEndorseStmt_c(Position pos, Expr e, LabelNode bound,
            LabelNode label, If body) {
        super(pos, bound, label, body);
        this.expr = e;
    }

    public Expr expr() {
        return expr;
    }

    public CheckedEndorseStmt expr(Expr expr) {
        CheckedEndorseStmt_c n = (CheckedEndorseStmt_c) copy();
        n.expr = expr;
        return n;
    }
    
    protected DowngradeStmt_c reconstruct(Expr expr, LabelNode bound, LabelNode label, Stmt body) {
        CheckedEndorseStmt_c n = (CheckedEndorseStmt_c)super.reconstruct(bound, label, body);
        if (this.expr != expr) {
            if (n == this) n = (CheckedEndorseStmt_c)this.copy();
            n.expr = expr;
        }
        return n;
    }

    public Node visitChildren(NodeVisitor v) {
        Expr expr = (Expr) visitChild(this.expr(), v);
        LabelNode bound = this.bound()==null?null:((LabelNode) visitChild(this.bound(), v));
        LabelNode label = (LabelNode) visitChild(this.label(), v);
        Stmt body = (Stmt) visitChild(this.body(), v);
        return reconstruct(expr, bound, label, body);
    }

    public String toString() {
        return downgradeKind() + "(" + expr() + ", " + (bound()==null?"":(bound() + " to ")) + label() + ") " + body();
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write(downgradeKind());
        w.write("(");
        print(expr, w, tr);
        w.write(", ");
        if (bound() != null) {
            print(bound(), w, tr);
            w.write(" to ");
        }
        print(label(), w, tr);
        w.write(") ");
        printSubStmt(body(), w, tr);
    }
    
}
