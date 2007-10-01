package fabric.types;

import polyglot.types.ClassType;
import polyglot.types.Type;
import polyglot.types.TypeSystem_c;

public class FabricTypeSystem_c extends TypeSystem_c implements
    FabricTypeSystem {

  public ClassType TransactionManager() {
    return load("fabric.client.TransactionManager");
  }

  public ClassType FObject() {
    return load("fabric.lang.Object");
  }

  public ClassType fArrayOf(Type type) {
    if (type.isReference()) return load("fabric.lang.arrays.ObjectArray");
    return load("fabric.lang.arrays." + type.toString() + "Array");
  }

  public ClassType fArrayImplOf(Type type) {
    if (type.isReference()) return load("fabric.lang.arrays.ObjectArray.$Impl");
    return load("fabric.lang.arrays." + type.toString() + "Array.$Impl");
  }
  
}
