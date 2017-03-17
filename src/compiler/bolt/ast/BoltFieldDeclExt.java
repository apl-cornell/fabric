package bolt.ast;

import polyglot.ast.Node;
import polyglot.util.CodeWriter;
import polyglot.util.Copy;
import polyglot.util.SerialVersionUID;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class BoltFieldDeclExt extends BoltTermExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  protected Label label;

  public BoltFieldDeclExt() {
    this(null);
  }

  public BoltFieldDeclExt(Label label) {
    this.label = label;
  }

  public Label label() {
    return label;
  }

  public Node label(Label label) {
    return label(node(), label);
  }

  protected <N extends Node> N label(N n, Label label) {
    BoltFieldDeclExt ext = (BoltFieldDeclExt) BoltExt.ext(n);
    if (ext.label == label) return n;
    if (n == node) {
      n = Copy.Util.copy(n);
      ext = (BoltFieldDeclExt) BoltExt.ext(n);
    }
    ext.label = label;
    return n;
  }

  protected Node reconstruct(Node n, Label label) {
    n = label(n, label);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = superLang().visitChildren(node(), v);
    Label label = visitChild(this.label, v);
    return reconstruct(n, label);
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    // TODO Auto-generated method stub
    int TODO;
    super.prettyPrint(w, pp);
  }

}
