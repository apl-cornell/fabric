package fabric.types;

import jif.translate.LabelToJavaExpr;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.ConfPolicy;
import jif.types.label.IntegPolicy;
import jif.types.label.PairLabel_c;
import jif.visit.LabelChecker;

import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public class FabricPairLabel_c extends PairLabel_c {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricPairLabel_c(FabricTypeSystem ts, ConfPolicy confPolicy,
      IntegPolicy integPolicy, Position pos, LabelToJavaExpr trans) {
    super(ts, confPolicy, integPolicy, pos, trans);
  }

  @Override
  protected void updateContextForSecond(LabelChecker lc, JifContext A,
      PathMap Xfirst) {
    super.updateContextForSecond(lc, A, Xfirst);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xffirst = (FabricPathMap) Xfirst;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xffirst.CL()));
  }
}
