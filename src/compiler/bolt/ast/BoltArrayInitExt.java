package bolt.ast;

import static polyglot.ast.Term.ENTRY;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Copy;
import polyglot.util.SerialVersionUID;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class BoltArrayInitExt extends BoltLocatedElementExt {

  private static final long serialVersionUID = SerialVersionUID.generate();

  protected Expr label;

  public BoltArrayInitExt(Expr location, Expr label) {
    super(location);
    this.label = label;
  }

  public Expr label() {
    return label;
  }

  public Node label(Expr label) {
    return label(node(), label);
  }

  protected <N extends Node> N label(N n, Expr label) {
    BoltArrayInitExt ext = (BoltArrayInitExt) BoltExt.ext(n);
    if (ext.label == label) return n;
    if (n == node) {
      n = Copy.Util.copy(n);
      ext = (BoltArrayInitExt) BoltExt.ext(n);
    }
    ext.label = label;
    return n;
  }

  protected Node reconstruct(Node n, Expr location, Expr label) {
    n = location(n, location);
    n = label(n, label);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Expr location = visitChild(this.location, v);
    Expr label = visitChild(this.label, v);
    Node n = superLang().visitChildren(node(), v);
    return reconstruct(n, location, label);
  }

  @Override
  public Term firstChild() {
    // First location, then label, then whatever the super lang specifies.
    if (location != null) return location;
    if (label != null) return label;
    return superLang().firstChild(node());
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    // First location, then label, then whatever the super lang specifies.
    Term prev = location;

    if (prev == null) {
      prev = label;
    } else if (label != null) {
      v.visitCFG(prev, prev = label, ENTRY);
    }

    if (prev != null) {
      v.visitCFG(prev, superLang().firstChild(node()), ENTRY);
    }

    return superLang().acceptCFG(node(), v, succs);
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    // TODO Auto-generated method stub
    int TODO;
    super.prettyPrint(w, pp);
  }

}
