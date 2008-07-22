package fabric.types;

import java.util.List;

import polyglot.ast.TypeNode;
import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public class FabricTypeSystem_c extends TypeSystem_c implements
    FabricTypeSystem {

  private CachingResolver runtimeClassResolver;

  public ClassType TransactionManager() {
    return load("fabric.client.transaction.TransactionManager");
  }

  public ClassType FObject() {
    return load("fabric.lang.Object");
  }

  public ClassType JavaInlineable() {
    return load("fabric.lang.JavaInlineable");
  }

  public ClassType WrappedJavaInlineable() {
    return load("fabric.lang.WrappedJavaInlineable");
  }

  public ClassType AbortException() {
    return load("fabric.client.AbortException");
  }

  public ClassType FabricThread() {
    return load("fabric.client.FabricThread");
  }

  public ClassType Thread() {
    return load("java.lang.Thread");
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
    result.add("fabric.lang.auth");
    result.add("fabric.client");
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected List<MethodInstance> findAcceptableMethods(ReferenceType container,
      String name, List argTypes, ClassType currClass) throws SemanticException {
    List<MethodInstance> result =
        super.findAcceptableMethods(container, name, argTypes, currClass);
    if (isJavaInlineable(container)) {
      // Remove any methods from fabric.lang.Object. They don't really exist.
      for (MethodInstance mi : (List<MethodInstance>) FObject().methods()) {
        result.remove(mi);
      }
    }
    return result;
  }

  public ClassType fArrayOf(Type type) {
    if (type.isReference())
      return loadRuntime("fabric.lang.arrays.ObjectArray");
    return loadRuntime("fabric.lang.arrays." + type.toString() + "Array");
  }

  public ClassType fArrayImplOf(Type type) {
    if (type.isReference())
      return loadRuntime("fabric.lang.arrays.ObjectArray.$Impl");
    return loadRuntime("fabric.lang.arrays." + type.toString() + "Array.$Impl");
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

  @Override
  public Flags legalMethodFlags() {
    return super.legalMethodFlags().set(FabricFlags.ATOMIC);
  }

  @Override
  public Flags legalConstructorFlags() {
    // TODO add atomic when we can do that for constructors
    return super.legalConstructorFlags();
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricType(polyglot.types.Type)
   */
  public boolean isFabricType(Type type) {
    if (type.isPrimitive()) return true;
    return isFabricReference(type);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricType(polyglot.ast.TypeNode)
   */
  public boolean isFabricType(TypeNode type) {
    return isFabricType(type.type());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricThread(polyglot.types.Type)
   */
  public boolean isThread(Type type) {
    return isSubtype(type, Thread());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricThread(polyglot.ast.TypeNode)
   */
  public boolean isThread(TypeNode type) {
    return isThread(type.type());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isPureFabricType(polyglot.types.Type)
   */
  public boolean isPureFabricType(Type type) {
    return isFabricType(type) && !isJavaInlineable(type);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isPureFabricType(polyglot.ast.TypeNode)
   */
  public boolean isPureFabricType(TypeNode type) {
    return isPureFabricType(type.type());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricReference(polyglot.types.Type)
   */
  public boolean isFabricReference(Type type) {
    return isFabricArray(type) || isFabricClass(type);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricReference(polyglot.ast.TypeNode)
   */
  public boolean isFabricReference(TypeNode type) {
    return isFabricReference(type.type());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricClass(polyglot.types.ClassType)
   */
  public boolean isFabricClass(ClassType type) {
    return isSubtype(type, FObject());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricClass(polyglot.types.Type)
   */
  public boolean isFabricClass(Type type) {
    return type.isClass() && isFabricClass(type.toClass());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricClass(polyglot.ast.TypeNode)
   */
  public boolean isFabricClass(TypeNode type) {
    return isFabricClass(type.type());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricArray(polyglot.types.ArrayType)
   */
  public boolean isFabricArray(ArrayType type) {
    return isFabricType(type.ultimateBase());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricArray(polyglot.types.Type)
   */
  public boolean isFabricArray(Type type) {
    return type.isArray() && isFabricArray(type.toArray());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isFabricArray(polyglot.ast.TypeNode)
   */
  public boolean isFabricArray(TypeNode type) {
    return isFabricArray(type.type());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.types.FabricTypeSystem#isJavaInlineable(polyglot.types.Type)
   */
  public boolean isJavaInlineable(Type type) {
    return isSubtype(type, JavaInlineable());
  }

  public boolean isJavaInlineable(TypeNode type) {
    return isJavaInlineable(type.type());
  }

  /**
   * Determines whether a type was compiled by fabc.
   */
  public boolean isCompiledByFabc(ClassType ct) {
    if (ct instanceof ParsedClassType) {
      ParsedClassType pct = (ParsedClassType) ct;

      // Check whether the class is compiled from source in this run.
      if (pct.job() != null) return true;

      // Check whether the class came from a Java source file.
      LazyInitializer init = pct.initializer();
      return init instanceof DeserializedClassInitializer;
    }

    return false;
  }

  public void setRuntimeClassResolver(LoadedClassResolver lcr) {
    this.runtimeClassResolver = new CachingResolver(lcr);
  }

  /**
   * Same as load(), but ignores source files.
   */
  private ClassType loadRuntime(String name) {
    try {
      return (ClassType) forName(runtimeClassResolver, name);
    } catch (SemanticException e) {
      throw new InternalCompilerError("Cannot find runtime class \"" + name
          + "\"; " + e.getMessage(), e);
    }
  }
}
