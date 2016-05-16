package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifCheckedEndorseStmtExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;

import polyglot.util.SerialVersionUID;

public class FabricCheckedEndorseStmtExt extends JifCheckedEndorseStmtExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricCheckedEndorseStmtExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  protected void updateContextForConsequent(LabelChecker lc, JifContext A,
      PathMap Xexpr) {
    super.updateContextForConsequent(lc, A, Xexpr);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfexpr = (FabricPathMap) Xexpr;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfexpr.CL()));
  }
}
