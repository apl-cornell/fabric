package fabric.types;

import jif.types.JifClassType;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.AccessPathLocal;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.util.Position;

/**
 *
 */
public class AccessPathLocalWorker extends AccessPathLocal {

  /**
   * @param li
   * @param name
   * @param pos
   */
  public AccessPathLocalWorker(WorkerLocalInstance li, Position pos) {
    super(li, "worker$", pos);
  }

  @Override
  public PathMap labelcheck(JifContext A, LabelChecker lc) {
    FabricTypeSystem ts = (FabricTypeSystem) A.typeSystem();
    Label l = ts.thisLabel(this.position(), (JifClassType) A.currentClass());
    return ts.pathMap().N(l).NV(l);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof AccessPathLocalWorker)
      return li == ((AccessPathLocalWorker) other).li;
    else return false;
  }
}
