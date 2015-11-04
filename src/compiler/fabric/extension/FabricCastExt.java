package fabric.extension;

import fabric.types.FabricReferenceType;

import jif.extension.JifCastExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;

public class FabricCastExt extends JifCastExt {

  public FabricCastExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Cast checkedNode = (Cast) super.labelCheck(lc);

    Expr ref = checkedNode.expr();
    TypeNode n = checkedNode.castType();

    if (n.type().isReference())
      return DereferenceHelper.checkAccess(checkedNode, ref, (FabricReferenceType) n.type(), lc, checkedNode.position());

    return checkedNode;
  }

  @Override
  public Cast node() {
    return (Cast) super.node();
  }
}
