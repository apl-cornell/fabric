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

public class NewPrincipal_c extends Expr_c implements NewPrincipal {

  private static final long serialVersionUID = SerialVersionUID.generate();

  protected Principal principal;

  public NewPrincipal_c(Position pos, Principal principal) {
    this(pos, principal, null);
  }

  public NewPrincipal_c(Position pos, Principal principal, Ext ext) {
    super(pos, ext);
    this.principal = principal;
  }

  @Override
  public Principal principal() {
    return principal;
  }

  @Override
  public NewPrincipal principal(Principal principal) {
    return principal(this, principal);
  }

  protected <N extends NewPrincipal_c> N principal(N n, Principal principal) {
    if (n.principal == principal) return n;
    n = copyIfNeeded(n);
    n.principal = principal;
    return n;
  }

  protected <N extends NewPrincipal_c> N reconstruct(N n, Principal principal) {
    n = principal(n, principal);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Principal principal = visitChild(this.principal, v);
    return reconstruct(this, principal);
  }

  @Override
  public Term firstChild() {
    return principal;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    v.visitCFG(principal, this, EXIT);
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
