package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifArrayInitExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;

import polyglot.util.SerialVersionUID;

public class FabricArrayInitExt extends JifArrayInitExt {
    private static final long serialVersionUID = SerialVersionUID.generate();

    public FabricArrayInitExt(ToJavaExt toJava) {
        super(toJava);
    }

  @Override
  protected void updateContextForNextElem(LabelChecker lc, JifContext A,
      PathMap Xelem) {
    super.updateContextForNextElem(lc, A, Xelem);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfelem = (FabricPathMap) Xelem;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfelem.CL()));
  }
}
