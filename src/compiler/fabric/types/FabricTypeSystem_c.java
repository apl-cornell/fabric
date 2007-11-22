package fabric.types;

import java.util.List;

import polyglot.ast.TypeNode;
import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.Position;

public class FabricTypeSystem_c extends TypeSystem_c implements
    FabricTypeSystem {

  public ClassType TransactionManager() {
    return load("fabric.client.TransactionManager");
  }

  public ClassType FObject() {
    return load("fabric.lang.Object");
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.TypeSystem_c#createArrayType(polyglot.util.Position,
   *      polyglot.types.Type)
   */
  @Override
  protected ArrayType createArrayType(Position pos, Type type) {
    return new FabricArrayType_c(this, pos, type);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.TypeSystem_c#createClassType(polyglot.types.LazyClassInitializer,
   *      polyglot.frontend.Source)
   */
  @Override
  public ParsedClassType createClassType(LazyClassInitializer init,
      Source fromSource) {
    return new FabricParsedClassType_c(this, init, fromSource);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List defaultPackageImports() {
    // Include fabric.lang as a default import.
    List<String> result = super.defaultPackageImports();
    result.add("fabric.lang");
    return result;
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

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#toFArray(polyglot.types.ArrayType)
   */
  public ClassType toFArray(ArrayType type) {
    return fArrayOf(type.base());
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.TypeSystem_c#importTable(polyglot.types.Package)
   */
  @Override
  public ImportTable importTable(Package pkg) {
    return new FabricImportTable(this, pkg);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.TypeSystem_c#importTable(java.lang.String,
   *      polyglot.types.Package)
   */
  @Override
  public ImportTable importTable(String sourceName, Package pkg) {
    return new FabricImportTable(this, pkg, sourceName);
  }

  public boolean isFabric(ClassType type) {
    // TODO This whole thing is a hack.
    if ("java.lang.Object".equals(type.toString())) return false;
    while (true) {
      if (type == null) return true;
      if ("fabric.lang.Object".equals(type.toString())) return true;
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

  public boolean isFabric(Type type) {
    return type instanceof ClassType && isFabric((ClassType) type);
  }

  public boolean isFabric(TypeNode type) {
    return isFabric(type.type());
  }

}
