package bolt.ast;

import java.util.List;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.CollectionUtil;
import polyglot.util.ListUtil;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class DisjunctivePrincipal_c extends Principal_c
    implements DisjunctivePrincipal {

  private static final long serialVersionUID = SerialVersionUID.generate();

  protected List<Principal> disjuncts;

  public DisjunctivePrincipal_c(Position pos, List<Principal> disjuncts) {
    this(pos, disjuncts, null);
  }

  public DisjunctivePrincipal_c(Position pos, List<Principal> disjuncts,
      Ext ext) {
    super(pos, ext);
    assert disjuncts != null;
    this.disjuncts = ListUtil.copy(disjuncts, true);
  }

  @Override
  public List<Principal> disjuncts() {
    return disjuncts;
  }

  @Override
  public DisjunctivePrincipal disjuncts(List<Principal> disjuncts) {
    return disjuncts(this, disjuncts);
  }

  protected <N extends DisjunctivePrincipal_c> N disjuncts(N n,
      List<Principal> disjuncts) {
    if (CollectionUtil.equals(n.disjuncts, disjuncts)) return n;
    n = copyIfNeeded(n);
    n.disjuncts = ListUtil.copy(disjuncts, true);
    return n;
  }

  protected <N extends DisjunctivePrincipal_c> N reconstruct(N n,
      List<Principal> disjuncts) {
    n = disjuncts(n, disjuncts);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    List<Principal> disjuncts = visitList(this.disjuncts, v);
    return reconstruct(this, disjuncts);
  }

  @Override
  public Term firstChild() {
    return listChild(disjuncts, null);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    v.visitCFGList(disjuncts, this, EXIT);
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
