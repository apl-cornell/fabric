package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricReferenceType;

import jif.extension.JifInstanceofExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
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
    Expr ref = (Expr) lc.labelCheck(node().expr());
    TypeNode type = (TypeNode) lc.labelCheck(node().compareType());

    DereferenceHelper.checkAccess(ref, (FabricReferenceType) type.type(), lc,
        node().position());

    return super.labelCheck(lc);
  }

  @Override
  public Instanceof node() {
    return (Instanceof) super.node();
  }

  @Override
  protected void updateContextForType(LabelChecker lc, JifContext A,
      PathMap Xexpr) {
    super.updateContextForType(lc, A, Xexpr);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfexpr = (FabricPathMap) Xexpr;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfexpr.CL()));
  }

}
