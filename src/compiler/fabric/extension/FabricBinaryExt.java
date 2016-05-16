package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifBinaryExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;

import polyglot.util.SerialVersionUID;

public class FabricBinaryExt extends JifBinaryExt { 
    private static final long serialVersionUID = SerialVersionUID.generate();

    public FabricBinaryExt(ToJavaExt toJava) {
        super(toJava);
    }

  @Override
  protected void updateContextForR(LabelChecker lc, JifContext A,
      PathMap Xleft) {
    super.updateContextForR(lc, A, Xleft);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfleft = (FabricPathMap) Xleft;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfleft.CL()));
  }

  @Override
  protected void updateContextForRShort(LabelChecker lc, JifContext A,
      PathMap Xleft) {
    // TODO: Need to do staging in the short circuiting case.
    super.updateContextForRShort(lc, A, Xleft);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfleft = (FabricPathMap) Xleft;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfleft.CL()));
  }
}
