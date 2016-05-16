package fabric.types;

import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.ReaderPolicy_c;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public class FabricReaderPolicy_c extends ReaderPolicy_c {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricReaderPolicy_c(Principal owner, Principal reader,
      FabricTypeSystem ts, Position pos) {
    super(owner, reader, ts, pos);
  }

  @Override
  protected void updateContextForReader(LabelChecker lc, JifContext A,
      PathMap Xowner) {
    super.updateContextForReader(lc, A, Xowner);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfowner = (FabricPathMap) Xowner;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfowner.CL()));
  }
}
