package bolt.ast;

import java.util.List;

import polyglot.ast.Expr_c;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class NewLabel_c extends Expr_c implements NewLabel {

  private static final long serialVersionUID = SerialVersionUID.generate();

  protected Label label;

  public NewLabel_c(Position pos, Label label) {
    this(pos, label, null);
  }

  public NewLabel_c(Position pos, Label label, Ext ext) {
    super(pos, ext);
    this.label = label;
  }

  @Override
  public Label label() {
    return label;
  }

  @Override
  public NewLabel label(Label label) {
    return label(this, label);
  }

  protected <N extends NewLabel_c> N label(N n, Label label) {
    if (n.label == label) return n;
    n = copyIfNeeded(n);
    n.label = label;
    return n;
  }

  protected <N extends NewLabel_c> N reconstruct(N n, Label label) {
    n = label(n, label);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Label label = visitChild(this.label, v);
    return reconstruct(this, label);
  }

  @Override
  public Term firstChild() {
    return label;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    v.visitCFG(label, this, EXIT);
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
