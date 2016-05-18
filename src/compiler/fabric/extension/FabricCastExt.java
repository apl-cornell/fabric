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

    // TODO: this causes ref and n to be label checked twice, but it seems
    // there's no way around it.

    Expr ref = (Expr) lc.labelCheck(node().expr());
    TypeNode n = (TypeNode) lc.labelCheck(node().castType());

    if (n.type().isReference())
      DereferenceHelper.checkAccess(ref, (FabricReferenceType) n.type(), lc,
          node().position());

    return super.labelCheck(lc);
  }

  @Override
  public Cast node() {
    return (Cast) super.node();
  }
}
