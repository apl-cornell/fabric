package fabric.types;

import jif.types.JifLocalInstance_c;
import jif.types.JifTypeSystem;
import polyglot.types.Flags;
import polyglot.types.Type;
import polyglot.util.Internable;
import polyglot.util.Position;

/**
 * There can be only one worker$. When we read signatures from class files, use
 * the TypeSystem's workerLocalInstance
 */
public class WorkerLocalInstance_c extends JifLocalInstance_c implements Internable {
  public WorkerLocalInstance_c(JifTypeSystem ts, Position pos, Flags flags,
      Type type, String name) {
    super(ts, pos, flags, type, name);
  }
   
  @Override
  public Object intern() {
    return ((FabricTypeSystem)ts).workerLocalInstance();
  }

}
