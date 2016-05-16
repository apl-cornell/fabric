package fabric.types;

import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.WriterPolicy_c;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public class FabricWriterPolicy_c extends WriterPolicy_c {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricWriterPolicy_c(Principal owner, Principal writer,
      FabricTypeSystem ts, Position pos) {
    super(owner, writer, ts, pos);
  }

  @Override
  protected void updateContextForWriter(LabelChecker lc, JifContext A,
      PathMap Xowner) {
    super.updateContextForWriter(lc, A, Xowner);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfowner = (FabricPathMap) Xowner;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfowner.CL()));
  }
}
