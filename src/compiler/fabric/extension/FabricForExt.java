package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifForExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;

import polyglot.util.SerialVersionUID;

public class FabricForExt extends JifForExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricForExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  protected void updateContextForBody(LabelChecker lc, JifContext A,
      PathMap Xcond) {
    super.updateContextForBody(lc, A, Xcond);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfcond = (FabricPathMap) Xcond;
    Af.setConflictLabel(Xfcond.CL());
  }

  @Override
  protected void updateContextForNextInit(LabelChecker lc, JifContext A,
      PathMap Xprev) {
    super.updateContextForNextInit(lc, A, Xprev);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfprev = (FabricPathMap) Xprev;
    Af.setConflictLabel(Xfprev.CL());
  }

  @Override
  protected void updateContextForNextIter(LabelChecker lc, JifContext A,
      PathMap Xprev) {
    super.updateContextForNextIter(lc, A, Xprev);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfprev = (FabricPathMap) Xprev;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfprev.CL()));
  }
}
