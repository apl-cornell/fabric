package fabric.types;

import polyglot.types.*;

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
    if (type.isReference())
      return load("fabric.lang.arrays.ObjectArray.$Impl");
    return load("fabric.lang.arrays." + type.toString() + "Array.$Impl");
  }

  public boolean isFabric(ClassType type) {
    // TODO This whole thing is a hack.
    if ("java.lang.Object".equals(type.toString())) return false;
    while (true) {
      if (type == null) return true;
      if ("java.lang.Object".equals(type.toString())) return true;
      if (!(type instanceof ParsedClassType)) return false;
      ParsedClassType pct = (ParsedClassType) type;
      // XXX Assume any class loaded from the DeserializedClassInitializer was
      // compiled with loom.
      if (pct.job() == null
          && !(pct.initializer() instanceof DeserializedClassInitializer))
        return false;
      type = (ClassType) pct.superType();
    }
  }

}
