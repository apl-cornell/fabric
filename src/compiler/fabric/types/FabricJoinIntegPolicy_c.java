package fabric.types;

import java.util.Set;

import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.IntegPolicy;
import jif.types.label.JoinIntegPolicy_c;
import jif.visit.LabelChecker;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public class FabricJoinIntegPolicy_c extends JoinIntegPolicy_c {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricJoinIntegPolicy_c(Set<IntegPolicy> components, FabricTypeSystem ts,
      Position pos) {
    super(components, ts, pos);
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
