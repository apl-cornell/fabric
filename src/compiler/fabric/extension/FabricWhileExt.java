package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifWhileExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;

import polyglot.util.SerialVersionUID;

public class FabricWhileExt extends JifWhileExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricWhileExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  protected void updateContextForBody(LabelChecker lc, JifContext A,
      PathMap Xexpr) {
    super.updateContextForBody(lc, A, Xexpr);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfexpr = (FabricPathMap) Xexpr;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfexpr.CL()));
  }
}
