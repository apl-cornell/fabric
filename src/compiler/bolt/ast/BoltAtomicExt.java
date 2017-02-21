package bolt.ast;

import java.util.List;

import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.util.CodeWriter;
import polyglot.util.CollectionUtil;
import polyglot.util.Copy;
import polyglot.util.ListUtil;
import polyglot.util.SerialVersionUID;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class BoltAtomicExt extends BoltTermExt {

  private static final long serialVersionUID = SerialVersionUID.generate();

  protected List<TypeNode> throwTypes;

  public BoltAtomicExt(List<TypeNode> throwTypes) {
    assert throwTypes != null;
    this.throwTypes = ListUtil.copy(throwTypes, true);
  }

  public List<TypeNode> throwTypes() {
    return throwTypes;
  }

  public Node throwTypes(List<TypeNode> throwTypes) {
    return throwTypes(node(), throwTypes);
  }

  protected <N extends Node> N throwTypes(N n, List<TypeNode> throwTypes) {
    assert throwTypes != null;

    BoltAtomicExt ext = (BoltAtomicExt) BoltExt.ext(n);
    if (CollectionUtil.equals(ext.throwTypes, throwTypes)) return n;
    if (n == node) {
      n = Copy.Util.copy(n);
      ext = (BoltAtomicExt) BoltExt.ext(n);
    }
    ext.throwTypes = ListUtil.copy(throwTypes, true);
    return n;
  }

  protected Node reconstruct(Node n, List<TypeNode> throwTypes) {
    n = throwTypes(n, throwTypes);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = superLang().visitChildren(node(), v);
    List<TypeNode> throwTypes = visitList(this.throwTypes, v);
    return reconstruct(n, throwTypes);
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    // TODO Auto-generated method stub
    int TODO;
    super.prettyPrint(w, pp);
  }

}
