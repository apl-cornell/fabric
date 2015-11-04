package fabric.extension;

import fabric.types.FabricReferenceType;

import jif.extension.JifInstanceofExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

import polyglot.ast.Expr;
import polyglot.ast.Instanceof;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;

/**
 *
 */
public class FabricInstanceofExt extends JifInstanceofExt {

  public FabricInstanceofExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Instanceof checkedNode = (Instanceof) super.labelCheck(lc);
    Expr ref = checkedNode.expr();
    TypeNode type = checkedNode.compareType();

    return DereferenceHelper.checkAccess(checkedNode, ref, (FabricReferenceType) type.type(), lc,
        checkedNode.position());
  }

  @Override
  public Instanceof node() {
    return (Instanceof) super.node();
  }

}
