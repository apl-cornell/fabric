package fabric.types;

import jif.types.JifLocalInstance_c;
import jif.types.JifTypeSystem;
import jif.types.label.Label;
import polyglot.types.Flags;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 * There can be only one worker$. When we read signatures from class files, use
 * the TypeSystem's workerLocalInstance
 */
public class WorkerLocalInstance_c extends JifLocalInstance_c implements
WorkerLocalInstance {
  public WorkerLocalInstance_c(JifTypeSystem ts, Position pos, Flags flags,
      Type type, String name) {
    super(ts, pos, flags, type, name);
  }

  @Override
  public Object intern() {
    return ((FabricTypeSystem) ts).workerLocalInstance();
  }

  @Override
  public Label label() {
    FabricTypeSystem ts = (FabricTypeSystem) this.ts;
    return ts.bottomLabel();
  }
}
