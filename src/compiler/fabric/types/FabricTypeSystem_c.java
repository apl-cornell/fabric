package fabric.types;

import polyglot.types.ClassType;
import polyglot.types.TypeSystem_c;

public class FabricTypeSystem_c extends TypeSystem_c implements FabricTypeSystem{
  public ClassType TransactionManager() { return load("fabric.client.TransactionManager"); }
  public ClassType FObject()            { return load("fabric.lang.Object"); }
}
