package bolt.ast;

import polyglot.ast.Node;
import polyglot.util.CodeWriter;
import polyglot.util.Copy;
import polyglot.util.SerialVersionUID;
import polyglot.visit.PrettyPrinter;

public class BoltArrayTypeNodeExt extends BoltTermExt {

  private static final long serialVersionUID = SerialVersionUID.generate();

  protected ArrayDimKind kind;

  public BoltArrayTypeNodeExt(ArrayDimKind kind) {
    this.kind = kind;
  }

  public ArrayDimKind kind() {
    return kind;
  }

  public Node kind(ArrayDimKind kind) {
    return kind(node(), kind);
  }

  protected <N extends Node> N kind(N n, ArrayDimKind kind) {
    BoltArrayTypeNodeExt ext = (BoltArrayTypeNodeExt) BoltExt.ext(n);
    if (ext.kind == kind) return n;
    if (n == node) {
      n = Copy.Util.copy(n);
      ext = (BoltArrayTypeNodeExt) BoltExt.ext(n);
    }
    ext.kind = kind;
    return n;
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    // TODO Auto-generated method stub
    int TODO;
    super.prettyPrint(w, pp);
  }

}
