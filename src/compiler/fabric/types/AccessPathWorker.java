package fabric.types;

import jif.types.label.AccessPathLocal;
import polyglot.util.Position;

/**
 * 
 */
public class AccessPathWorker extends AccessPathLocal {

  /**
   * @param li
   * @param name
   * @param pos
   */
  public AccessPathWorker(WorkerLocalInstance li, Position pos) {
    super(li, "worker$", pos);
  }

}
