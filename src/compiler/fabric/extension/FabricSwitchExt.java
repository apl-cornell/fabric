package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifSwitchExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;

import polyglot.util.SerialVersionUID;

public class FabricSwitchExt extends JifSwitchExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricSwitchExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  protected void updateContextForCases(LabelChecker lc, JifContext A,
      PathMap Xval) {
    super.updateContextForCases(lc, A, Xval);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfval = (FabricPathMap) Xval;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfval.CL()));
  }

  @Override
  protected void updateContextForNextCase(LabelChecker lc, JifContext A,
      PathMap Xprev) {
    super.updateContextForNextCase(lc, A, Xprev);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfprev = (FabricPathMap) Xprev;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfprev.CL()));
  }
}
