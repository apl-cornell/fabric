package bolt.ast;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class ExprLabel_c extends Label_c implements ExprLabel {

  protected Expr expr;

  public ExprLabel_c(Position pos, Expr expr) {
    this(pos, expr, null);
  }

  public ExprLabel_c(Position pos, Expr expr, Ext ext) {
    super(pos, ext);
    this.expr = expr;
  }

  @Override
  public Expr expr() {
    return expr;
  }

  @Override
  public Label expr(Expr expr) {
    return expr(this, expr);
  }

  protected <N extends ExprLabel_c> N expr(N n, Expr expr) {
    if (n.expr == expr) return n;
    n = copyIfNeeded(n);
    n.expr = expr;
    return n;
  }

  protected <N extends ExprLabel_c> N reconstruct(N n, Expr expr) {
    n = expr(n, expr);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Expr expr = visitChild(this.expr, v);
    return reconstruct(this, expr);
  }

  @Override
  public Term firstChild() {
    return expr;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    v.visitCFG(expr, this, EXIT);
    return succs;
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    // TODO Auto-generated method stub
    int TODO;
    super.prettyPrint(w, pp);
  }

  @Override
  public void dump(CodeWriter w) {
    // TODO Auto-generated method stub
    int TODO;
    super.dump(w);
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    int TODO;
    return super.toString();
  }

}
