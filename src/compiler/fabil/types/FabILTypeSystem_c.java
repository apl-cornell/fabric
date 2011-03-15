package fabil.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fabil.FabILOptions;
import fabil.frontend.CodebaseSource;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;

import polyglot.ast.TypeNode;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public class FabILTypeSystem_c extends TypeSystem_c implements FabILTypeSystem {

  private CachingResolver runtimeClassResolver;

  @Override
  public void initialize(TopLevelResolver loadedResolver, ExtensionInfo extInfo)
      throws SemanticException {
    super.initialize(loadedResolver, extInfo);
    // replace the system resolver with one that handles codebases.
    // XXX: it would be better if polyglot used a factory method to create the
    // system resolver
    this.systemResolver = createSystemResolver(loadedResolver, extInfo);
  }

  public CodebaseSystemResolver createSystemResolver(TopLevelResolver loadedResolver, ExtensionInfo extInfo) {
    return new CodebaseSystemResolver(loadedResolver, extInfo);
  }
  
  @Override
  public CodebaseClassContextResolver createClassContextResolver(ClassType type) {
    assert_(type);
    return new CodebaseClassContextResolver(this, type);
  }

  @Override
  public CodebasePackageContextResolver createPackageContextResolver(Package p) {
    assert_(p);
    return new CodebasePackageContextResolver(this, (CodebasePackage)p);
  }

  @Override
  public CodebasePackage createPackage(Package prefix, String name) {
    return new CodebasePackage_c(this, prefix, name);
  }
  
  public ClassType TransactionManager() {
    return load("fabric.worker.transaction.TransactionManager");
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
    return load("fabric.worker.AbortException");
  }

  public ClassType FabricThread() {
    return load("fabric.common.FabricThread");
  }

  public ClassType Thread() {
    return load("java.lang.Thread");
  }

  public ClassType RemoteWorker() {
    return load("fabric.worker.remote.RemoteWorker");
  }

  public ClassType RemoteCallException() {
    return load("fabric.worker.remote.RemoteCallException");
  }

  public ClassType Worker() {
    return load("fabric.worker.Worker");
  }

  public ClassType Principal() {
    return load("fabric.lang.security.Principal");
  }

  public ClassType DelegatingPrincipal() {
    return load("fabric.lang.security.DelegatingPrincipal");
  }

  public Type Store() {
    return load("fabric.worker.Store");
  }

  public Type Label() {
    return load("fabric.lang.security.Label");
  }

  public ClassType InternalError() {
    return load("java.lang.InternalError");
  }

//  // I don't understand why this method is deprecated.  
//  // There doesn't seem to be any other way to create
//  // packages.  All non-deprecated methods call this one.
//  /** @deprecated */
//  public Package createPackage(Package prefix, String name) {
//      assert_(prefix);
//      return new FabILPackage_c(this, prefix, name);
//  }

  /*
   * (non-Javadoc)
   * @see
   * polyglot.types.TypeSystem_c#createClassType(polyglot.types.LazyClassInitializer
   * , polyglot.frontend.Source)
   */
  @Override
  public CodebaseClassType createClassType(LazyClassInitializer init,
      Source fromSource) {
    return new FabILParsedClassType_c(this, init, fromSource);
  }

  @Override
  public Context createContext() {
    return new FabILContext_c(this);
  }

  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public List defaultPackageImports() {
    // Include fabric.lang as a default import.
    List<String> result = new ArrayList<String>(6);
    result.add("fabric.lang");
    result.add("fabric.lang.security");
    result.add("fabric.worker");
    result.add("fabric.worker.remote");
    result.addAll(super.defaultPackageImports());
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected List<MethodInstance> findAcceptableMethods(ReferenceType container,
      String name, @SuppressWarnings("rawtypes") List argTypes,
      ClassType currClass) throws SemanticException {
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

  public ClassType fabricRuntimeArrayOf(Type type) {
    if (type.isReference())
      return loadRuntime("fabric.lang.arrays.ObjectArray");
    return loadRuntime("fabric.lang.arrays." + type.toString() + "Array");
  }

  public ClassType fabricRuntimeArrayImplOf(Type type) {
    if (type.isReference())
      return loadRuntime("fabric.lang.arrays.ObjectArray._Impl");
    return loadRuntime("fabric.lang.arrays." + type.toString() + "Array._Impl");
  }

  public ClassType toFabricRuntimeArray(ArrayType type) {
    return fabricRuntimeArrayOf(type.base());
  }

  public FabricArrayType fabricArrayOf(Type type, int dims) {
    return fabricArrayOf(type.position(), type, dims);
  }

  public FabricArrayType fabricArrayOf(Position pos, Type type, int dims) {
    if (dims > 1)
      return fabricArrayOf(pos, fabricArrayOf(pos, type, dims - 1));

    if (dims == 1) return fabricArrayOf(pos, type);

    throw new InternalCompilerError(
        "Must call fabricArrayOf(type, dims) with dims > 0");
  }

  public FabricArrayType fabricArrayOf(Type type) {
    return fabricArrayOf(type.position(), type);
  }

  public FabricArrayType fabricArrayOf(Position pos, Type type) {
    assert_(type);
    return fabricArrayType(pos, type);
  }

  private Map<Type, FabricArrayType> fabricArrayTypeCache =
      new HashMap<Type, FabricArrayType>();

  protected FabricArrayType fabricArrayType(Position pos, Type type) {
    FabricArrayType t = fabricArrayTypeCache.get(type);
    if (t == null) {
      t = createFabricArrayType(pos, type);
      fabricArrayTypeCache.put(type, t);
    }

    return t;
  }

  protected FabricArrayType createFabricArrayType(Position pos, Type type) {
    return new FabricArrayType_c(this, pos, type);
  }

  @Override
  protected ArrayType createArrayType(Position pos, Type type) {
    return new JavaArrayType_c(this, pos, type);
  }

  /*
   * (non-Javadoc)
   * @see polyglot.types.TypeSystem_c#importTable(polyglot.types.Package)
   */
  @Override
  public ImportTable importTable(Package pkg) {
    throw new UnsupportedOperationException("Import table must be associated with a source");
  }
  
  /*
   * (non-Javadoc)
   * @see polyglot.types.TypeSystem_c#importTable(java.lang.String,
   * polyglot.types.Package)
   */
  @Override
  public ImportTable importTable(String sourceName, Package pkg) {
    throw new UnsupportedOperationException("Import table must be associated with a source");
  }

  public CodebaseImportTable importTable(CodebaseSource source, Package pkg) {
    return new CodebaseImportTable_c(this, pkg, source);
  }

  @Override
  public Flags legalMethodFlags() {
    return super.legalMethodFlags().set(FabILFlags.ATOMIC);
  }

  @Override
  public Flags legalConstructorFlags() {
    // TODO add atomic when we can do that for constructors
    return super.legalConstructorFlags();
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricType(polyglot.types.Type)
   */
  public boolean isFabricType(Type type) {
    if (type.isPrimitive()) return true;
    return isFabricReference(type);
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricType(polyglot.ast.TypeNode)
   */
  public boolean isFabricType(TypeNode type) {
    return isFabricType(type.type());
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricThread(polyglot.types.Type)
   */
  public boolean isThread(Type type) {
    return isSubtype(type, Thread());
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricThread(polyglot.ast.TypeNode)
   */
  public boolean isThread(TypeNode type) {
    return isThread(type.type());
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isPureFabricType(polyglot.types.Type)
   */
  public boolean isPureFabricType(Type type) {
    return isFabricType(type) && !isJavaInlineable(type);
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isPureFabricType(polyglot.ast.TypeNode)
   */
  public boolean isPureFabricType(TypeNode type) {
    return isPureFabricType(type.type());
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricReference(polyglot.types.Type)
   */
  public boolean isFabricReference(Type type) {
    return isFabricArray(type) || isFabricClass(type);
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricReference(polyglot.ast.TypeNode)
   */
  public boolean isFabricReference(TypeNode type) {
    return isFabricReference(type.type());
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricClass(polyglot.types.ClassType)
   */
  public boolean isFabricClass(ClassType type) {
    if (type.flags().contains(FabILFlags.NONFABRIC)) {
      return false;
    }
    if (!type.flags().isInterface()) {
      while (type != null) {
        if (typeEquals(type, FObject())) return true;
        type = (ClassType) type.superType();
      }
      return false;
    }
    return isSubtype(type, FObject());
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricClass(polyglot.types.Type)
   */
  public boolean isFabricClass(Type type) {
    return type.isClass() && isFabricClass(type.toClass());
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricClass(polyglot.ast.TypeNode)
   */
  public boolean isFabricClass(TypeNode type) {
    return isFabricClass(type.type());
  }

  public boolean isPrincipalClass(ClassType type) {
    return isSubtype(type, Principal());
  }

  public boolean isPrincipalClass(Type type) {
    return type.isClass() && isPrincipalClass(type.toClass());
  }

  public boolean isPrincipalClass(TypeNode type) {
    return isPrincipalClass(type.type());
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricArray(polyglot.types.ArrayType)
   */
  public boolean isFabricArray(ArrayType type) {
    return type instanceof FabricArrayType;
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricArray(polyglot.types.Type)
   */
  public boolean isFabricArray(Type type) {
    return type.isArray() && isFabricArray(type.toArray());
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isFabricArray(polyglot.ast.TypeNode)
   */
  public boolean isFabricArray(TypeNode type) {
    return isFabricArray(type.type());
  }

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isJavaInlineable(polyglot.types.Type)
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

  @Override
  public Flags legalTopLevelClassFlags() {
    Flags f = super.legalTopLevelClassFlags();
    f = f.set(FabILFlags.NONFABRIC);
    return f;
  }

  @Override
  public Flags legalInterfaceFlags() {
    Flags f = super.legalInterfaceFlags();
    f = f.set(FabILFlags.NONFABRIC);
    return f;
  }

  @SuppressWarnings("deprecation")
  @Override
  public String translateClass(Resolver c, ClassType t) {
    // Fully qualify classes in fabric.lang.security.
    if (t.package_() != null) {
      // Using the deprecated method because the non-deprecated version
      // (packageForName) declares that a SemanticException can be thrown, even
      // though the two methods behave identically.
      if (t.package_().equals(createPackage("fabric.lang.security"))) {
        return super.translateClass(null, t);
      }
    }    
    return super.translateClass(c, t);
  }

  public boolean isPlatformType(Named name) {
    return isPlatformType(name.fullName());
  }
    
  public boolean isPlatformType(String fullName) {
    FabILOptions opt = (FabILOptions) extInfo.getOptions();
    if(!opt.runWorker()) {
      return true;
    }
    String typeName = fullName;
    return typeName.startsWith("java")
      || typeName.startsWith("fabric")
      || typeName.startsWith("jif");
  }
  
  public String absoluteName(Codebase context, String fullName, boolean resolve) throws SemanticException {
    if(!isPlatformType(fullName)) {
      if(resolve) {
         FClass fcls = context.resolveClassName(fullName);
        if(fcls == null) {
          new java.lang.Exception().printStackTrace();
          throw new SemanticException("Codebase " + SysUtil.oid(context) + " has no entry for " + fullName);
        }
        Codebase cb = fcls.getCodebase();
        return SysUtil.codebasePrefix(cb) + fullName;
      }
      else {
        return SysUtil.codebasePrefix(context) + fullName;
      }
    } else
      return fullName;
  }

}
