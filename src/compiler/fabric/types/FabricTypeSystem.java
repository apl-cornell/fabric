package fabric.types;

import polyglot.types.ClassType;
import polyglot.types.TypeSystem;

public interface FabricTypeSystem extends TypeSystem {

  // TODO: fabric.client or fabric.lang?
  /** return the ClassType corresponding to fabric.client.TransactionManager */
  ClassType TransactionManager();

}
