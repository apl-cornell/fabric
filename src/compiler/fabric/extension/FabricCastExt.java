package fabric.extension;

import jif.extension.JifCastExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricReferenceType;

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

  @Override
  protected void updateContextForType(LabelChecker lc, JifContext A,
      PathMap Xexpr) {
    super.updateContextForType(lc, A, Xexpr);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfexpr = (FabricPathMap) Xexpr;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfexpr.CL()));
  }
}
