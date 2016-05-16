package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifDoExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;

import polyglot.util.SerialVersionUID;

public class FabricDoExt extends JifDoExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricDoExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  protected void updateContextForGuard(LabelChecker lc, JifContext A,
      PathMap Xbod) {
    super.updateContextForGuard(lc, A, Xbod);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfbod = (FabricPathMap) Xbod;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfbod.CL()));
  }
}
