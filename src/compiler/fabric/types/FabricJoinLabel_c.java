package fabric.types;

import java.util.Set;

import jif.translate.LabelToJavaExpr;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.JoinLabel_c;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public class FabricJoinLabel_c extends JoinLabel_c {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricJoinLabel_c(Set<Label> components, FabricTypeSystem ts,
      Position pos, LabelToJavaExpr trans) {
    super(components, ts, pos, trans);
  }

  @Override
  protected void updateContextForComp(LabelChecker lc, JifContext A,
      PathMap Xprev) {
    super.updateContextForComp(lc, A, Xprev);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfprev = (FabricPathMap) Xprev;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfprev.CL()));
  }
}