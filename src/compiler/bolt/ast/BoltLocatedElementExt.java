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

public abstract class BoltLocatedElementExt extends BoltTermExt
    implements LocatedElement {

  private static final long serialVersionUID = SerialVersionUID.generate();

  protected Expr location;

  public BoltLocatedElementExt(Expr location) {
    this.location = location;
  }

  @Override
  public Expr location() {
    return location;
  }

  @Override
  public Node location(Expr location) {
    return location(node(), location);
  }

  protected <N extends Node> N location(N n, Expr location) {
    BoltLocatedElementExt ext = (BoltLocatedElementExt) BoltExt.ext(n);
    if (ext.location == location) return n;
    if (n == node) {
      n = Copy.Util.copy(n);
      ext = (BoltLocatedElementExt) BoltExt.ext(n);
    }
    ext.location = location;
    return n;
  }

  protected Node reconstruct(Node n, Expr location) {
    n = location(n, location);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Expr location = visitChild(this.location, v);
    Node n = superLang().visitChildren(node(), v);
    return reconstruct(n, location);
  }

  @Override
  public Term firstChild() {
    if (location != null) return location;
    return superLang().firstChild(node());
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    if (location != null) {
      v.visitCFG(location, super.firstChild(), ENTRY);
    }

    return super.acceptCFG(v, succs);
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    // TODO Auto-generated method stub
    int TODO;
    super.prettyPrint(w, pp);
  }

}
