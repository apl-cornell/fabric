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

public class ConjunctivePrincipal_c extends Principal_c
    implements ConjunctivePrincipal {

  private static final long serialVersionUID = SerialVersionUID.generate();

  protected List<Principal> conjuncts;

  public ConjunctivePrincipal_c(Position pos, List<Principal> conjuncts) {
    this(pos, conjuncts, null);
  }

  public ConjunctivePrincipal_c(Position pos, List<Principal> conjuncts,
      Ext ext) {
    super(pos, ext);
    assert conjuncts != null;
    this.conjuncts = ListUtil.copy(conjuncts, true);
  }

  @Override
  public List<Principal> conjuncts() {
    return conjuncts;
  }

  @Override
  public ConjunctivePrincipal conjuncts(List<Principal> conjuncts) {
    return conjuncts(this, conjuncts);
  }

  protected <N extends ConjunctivePrincipal_c> N conjuncts(N n,
      List<Principal> conjuncts) {
    if (CollectionUtil.equals(n.conjuncts, conjuncts)) return n;
    n = copyIfNeeded(n);
    n.conjuncts = ListUtil.copy(conjuncts, true);
    return n;
  }

  protected <N extends ConjunctivePrincipal_c> N reconstruct(N n,
      List<Principal> conjuncts) {
    n = conjuncts(n, conjuncts);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    List<Principal> conjuncts = visitList(this.conjuncts, v);
    return reconstruct(this, conjuncts);
  }

  @Override
  public Term firstChild() {
    return listChild(conjuncts, null);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    v.visitCFGList(conjuncts, this, EXIT);
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
