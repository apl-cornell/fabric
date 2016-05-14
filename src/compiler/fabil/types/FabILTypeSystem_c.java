package fabil.types;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import polyglot.ast.TypeNode;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.types.AccessControlResolver;
import polyglot.types.ArrayType;
import polyglot.types.CachingResolver;
import polyglot.types.ClassType;
import polyglot.types.Context;
import polyglot.types.DeserializedClassInitializer;
import polyglot.types.Flags;
import polyglot.types.ImportTable;
import polyglot.types.LazyClassInitializer;
import polyglot.types.LazyInitializer;
import polyglot.types.MethodInstance;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.ParsedClassType;
import polyglot.types.ReferenceType;
import polyglot.types.Resolver;
import polyglot.types.SemanticException;
import polyglot.types.SystemResolver;
import polyglot.types.TopLevelResolver;
import polyglot.types.Type;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem_c;
import polyglot.types.reflect.ClassFile;
import polyglot.types.reflect.ClassFileLazyClassInitializer;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.StringUtil;
import codebases.frontend.CodebaseSource;
import codebases.types.CBClassContextResolver;
import codebases.types.CBImportTable;
import codebases.types.CBLazyClassInitializer;
import codebases.types.CBPackage;
import codebases.types.CBPackageContextResolver;
import codebases.types.CBPackage_c;
import codebases.types.CBPlaceHolder_c;
import codebases.types.CodebaseClassType;
import codebases.types.NamespaceResolver;
import fabric.filemanager.FabricFileManager;
import fabric.lang.Codebase;
import fabric.worker.Worker;

public class FabILTypeSystem_c extends TypeSystem_c implements FabILTypeSystem {
  private fabil.ExtensionInfo extInfo;
  protected Map<URI, NamespaceResolver> namespaceResolvers;
  protected NamespaceResolver platformResolver;
  protected NamespaceResolver applicationResolver;

  @Override
  public CBPackageContextResolver createPackageContextResolver(Package p) {
    return new CBPackageContextResolver(this, p);
  }

  // XXX: There may be a way to override the namespace-less package methods, but
  // createPackage is often called with prefix==null, so a full refactoring
  // helped
  // identify the situations it is called in.
  @Override
  public Package createPackage(URI ns, Package prefix, java.lang.String name) {
    if (prefix != null) {
      ns = ((CBPackage) prefix).namespace();
    }
    return new CBPackage_c(this, ns, prefix, name);
  }

  /**
   * @throws SemanticException
   *           subclasses may throw SemanticExceptions
   */
  @Override
  public Package packageForName(URI ns, Package prefix, java.lang.String name)
      throws SemanticException {
    return createPackage(ns, prefix, name);
  }

  @Override
  public Package packageForName(URI ns, java.lang.String name)
      throws SemanticException {
    if (name == null || name.equals("")) {
      return null;
    }

    String s = StringUtil.getShortNameComponent(name);
    String p = StringUtil.getPackageComponent(name);

    return packageForName(ns, packageForName(ns, p), s);
  }

  @Override
  public Package createPackage(Package prefix, String name) {
    throw new UnsupportedOperationException("Must specify namespace");
  }

  @Override
  public AccessControlResolver createClassContextResolver(ClassType type) {
    assert_(type);
    return new CBClassContextResolver(this, type);
  }

  @Override
  public Object placeHolder(TypeObject o, Set<? extends TypeObject> roots) {
    assert_(o);
    if (o instanceof ParsedClassType) {
      CodebaseClassType ct = (CodebaseClassType) o;

      // This should never happen: anonymous and local types cannot
      // appear in signatures.
      if (ct.isLocal() || ct.isAnonymous()) {
        throw new InternalCompilerError("Cannot serialize " + o + ".");
      }
      String name = getTransformedClassName(ct);

      return new CBPlaceHolder_c(ct.canonicalNamespace(), name);
    }

    return o;
  }

  @Override
  public ClassType TransactionManager() {
    return load("fabric.worker.transaction.TransactionManager");
  }

  @Override
  public ClassType FObject() {
    return load("fabric.lang.Object");
  }

  @Override
  public ClassType JavaInlineable() {
    return load("fabric.lang.JavaInlineable");
  }

  @Override
  public ClassType WrappedJavaInlineable() {
    return load("fabric.lang.WrappedJavaInlineable");
  }

  @Override
  public ClassType AbortException() {
    return load("fabric.worker.AbortException");
  }

  @Override
  public ClassType FabricThread() {
    return load("fabric.common.FabricThread");
  }

  @Override
  public ClassType Thread() {
    return load("java.lang.Thread");
  }

  @Override
  public ClassType RemoteWorker() {
    return load("fabric.worker.remote.RemoteWorker");
  }

  @Override
  public ClassType RemoteCallException() {
    return load("fabric.worker.remote.RemoteCallException");
  }

  @Override
  public ClassType Worker() {
    return load("fabric.worker.Worker");
  }

  @Override
  public ClassType Principal() {
    return load("fabric.lang.security.Principal");
  }

  @Override
  public ClassType DelegatingPrincipal() {
    return load("fabric.lang.security.DelegatingPrincipal");
  }

  @Override
  public Type Store() {
    return load("fabric.worker.Store");
  }

  @Override
  public Type Label() {
    return load("fabric.lang.security.Label");
  }

  @Override
  public Type ConfPolicy() {
    return load("fabric.lang.security.ConfPolicy");
  }

  @Override
  public ClassType InternalError() {
    return load("java.lang.InternalError");
  }

  @Override
  public ParsedClassType createClassType(LazyClassInitializer init,
      Source fromSource) {
    if (fromSource == null) {
      // local raw class file.
      return createClassType(init, fromSource, extInfo.localNamespace());
    } else {
      URI ns = ((CodebaseSource) fromSource).canonicalNamespace();
      return createClassType(init, fromSource, ns);
    }
  }

  @Override
  public ParsedClassType createClassType(LazyClassInitializer init,
      Source fromSource, URI ns) {
    return new FabILParsedClassType_c(this, init, fromSource, ns);
  }

  @Override
  public Context createContext() {
    return new FabILContext_c(this);
  }

  @Override
  public List<String> defaultPackageImports() {
    // Include fabric.lang as a default import.
    List<String> result = new ArrayList<>(6);
    result.add("fabric.lang");
    result.add("fabric.lang.security");
    result.add("fabric.worker");
    result.add("fabric.worker.remote");
    result.addAll(super.defaultPackageImports());
    return result;
  }

  @Override
  // XXX: Why is this method here?
  protected List<? extends MethodInstance> findAcceptableMethods(
      ReferenceType container, String name, List<? extends Type> argTypes,
      ClassType currClass, boolean fromClient) throws SemanticException {
    List<? extends MethodInstance> result =
        super.findAcceptableMethods(container, name, argTypes, currClass,
            fromClient);
    if (isJavaInlineable(container)) {
      // Remove any methods from fabric.lang.Object. They don't really exist.
      for (MethodInstance mi : (List<MethodInstance>) FObject().methods()) {
        result.remove(mi);
      }
    }
    return result;
  }

  @Override
  public ClassType fabricRuntimeArrayOf(Type type) {
    if (type.isReference()) return load("fabric.lang.arrays.ObjectArray");
    return load("fabric.lang.arrays." + type.toString() + "Array");
  }

  @Override
  public ClassType fabricRuntimeArrayImplOf(Type type) {
    if (type.isReference())
      return load("fabric.lang.arrays.ObjectArray._Impl");
    return load("fabric.lang.arrays." + type.toString() + "Array._Impl");
  }

  @Override
  public ClassType toFabricRuntimeArray(ArrayType type) {
    return fabricRuntimeArrayOf(type.base());
  }

  @Override
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

  @Override
  public FabricArrayType fabricArrayOf(Type type) {
    return fabricArrayOf(type.position(), type);
  }

  @Override
  public FabricArrayType fabricArrayOf(Position pos, Type type) {
    assert_(type);
    return fabricArrayType(pos, type);
  }

  private Map<Type, FabricArrayType> fabricArrayTypeCache = new HashMap<>();

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

  @Override
  public CBImportTable importTable(Source source, URI ns, Package pkg) {
    return new CBImportTable(this, ns, pkg, source);
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

  @Override
  public boolean isFabricType(Type type) {
    if (type.isPrimitive()) return true;
    return isFabricReference(type);
  }

  @Override
  public boolean isFabricType(TypeNode type) {
    return isFabricType(type.type());
  }

  @Override
  public boolean isThread(Type type) {
    return isSubtype(type, Thread());
  }

  @Override
  public boolean isThread(TypeNode type) {
    return isThread(type.type());
  }

  @Override
  public boolean isPureFabricType(Type type) {
    return isFabricType(type) && !isJavaInlineable(type);
  }

  @Override
  public boolean isPureFabricType(TypeNode type) {
    return isPureFabricType(type.type());
  }

  @Override
  public boolean isFabricReference(Type type) {
    return isFabricArray(type) || isFabricClass(type);
  }

  @Override
  public boolean isFabricReference(TypeNode type) {
    return isFabricReference(type.type());
  }

  @Override
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

  @Override
  public boolean isFabricClass(Type type) {
    return type.isClass() && isFabricClass(type.toClass());
  }

  @Override
  public boolean isFabricClass(TypeNode type) {
    return isFabricClass(type.type());
  }

  @Override
  public boolean isPrincipalClass(ClassType type) {
    return isSubtype(type, Principal());
  }

  @Override
  public boolean isPrincipalClass(Type type) {
    return type.isClass() && isPrincipalClass(type.toClass());
  }

  @Override
  public boolean isPrincipalClass(TypeNode type) {
    return isPrincipalClass(type.type());
  }

  @Override
  public boolean isFabricArray(ArrayType type) {
    return type instanceof FabricArrayType;
  }

  @Override
  public boolean isFabricArray(Type type) {
    return type.isArray() && isFabricArray(type.toArray());
  }

  @Override
  public boolean isFabricArray(TypeNode type) {
    return isFabricArray(type.type());
  }

  @Override
  public boolean isJavaInlineable(Type type) {
    return isSubtype(type, JavaInlineable());
  }

  @Override
  public boolean isJavaInlineable(TypeNode type) {
    return isJavaInlineable(type.type());
  }

  @Override
  public MethodInstance methodInstance(Position pos, ReferenceType container,
      Flags flags, Type returnType, String name, List<? extends Type> argTypes,
      List<? extends Type> excTypes) {
    assert_(container);
    assert_(returnType);
    assert_(argTypes);
    assert_(excTypes);
    return new FabILMethodInstance_c(this, pos, container, flags, returnType,
        name, argTypes, excTypes);
  }

  /**
   * Determines whether a type was compiled by fabc.
   */
  @Override
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

  @Override
  public Flags legalTopLevelClassFlags() {
    Flags f = super.legalTopLevelClassFlags();
    f = f.set(FabILFlags.NONFABRIC);
    return f;
  }

  @Override
  public String translateClass(Resolver c, ClassType t) {
    // Fully qualify classes in fabric.lang.security.
    if (t.package_() != null) {
      // Using the deprecated method because the non-deprecated version
      // (packageForName) declares that a SemanticException can be thrown, even
      // though the two methods behave identically.
      @SuppressWarnings("deprecation")
      Package fls = createPackage("fabric.lang.security");
      if (t.package_().equals(fls)) {
        return super.translateClass(null, t);
      }
    }
    return super.translateClass(c, t);
  }

  @Override
  public void initialize(ExtensionInfo extInfo) throws SemanticException {
    // There is no toplevel resolver -- names are resolved via the source's
    // codebase
    initialize(null, extInfo);
    this.loadedResolver = null;
    this.systemResolver = null;
    this.extInfo = (fabil.ExtensionInfo) extInfo;
    namespaceResolvers = new HashMap<>();
    try {
      initResolvers();
    } catch (IOException e) {
      throw new SemanticException("Could not initialize resolvers", e);
    }
  }

  protected void initResolvers() throws IOException {
    FabricFileManager fileManager =
        (FabricFileManager) extInfo.extFileManager();
    List<File> platform_directories = new ArrayList<>();
    platform_directories.addAll(extInfo.getOptions().signaturepath());
    platform_directories.addAll(extInfo.bootclasspath());
    fileManager.setLocation(extInfo.getOptions().bootclasspath,
        platform_directories);
    platformResolver = namespaceResolver(extInfo.platformNamespace());
    platformResolver.loadRawClasses(true);

    fileManager
        .setLocation(extInfo.getOptions().classpath, extInfo.classpath());

    fileManager.setLocation(extInfo.getOptions().source_path,
        extInfo.sourcepath());
    applicationResolver = namespaceResolver(extInfo.localNamespace());
    applicationResolver.loadRawClasses(true);
  }

  @Override
  public NamespaceResolver namespaceResolver(URI ns) {
    NamespaceResolver sr = namespaceResolvers.get(ns);
    if (sr == null) {
      sr = extInfo.createNamespaceResolver(ns);
      namespaceResolvers.put(ns, sr);
    }
    return sr;
  }

  @Override
  public boolean packageExists(URI ns, String name) {
    return namespaceResolver(ns).packageExists(name);
  }

  @Override
  public Named forName(URI ns, String name) throws SemanticException {
    return forName(namespaceResolver(ns), name);
  }

  @Override
  public Named forName(String name) throws SemanticException {
    return forName(platformResolver(), name);
  }

  @Override
  public Codebase codebaseFromNS(URI namespace) {
    // Worker must be running!
    if (!Worker.isInitialized())
      throw new InternalCompilerError("Worker is not initialized.");

    NamespaceResolver nsr = namespaceResolver(namespace);
    if (nsr.codebase() != null) return nsr.codebase();

    Thread.dumpStack();
    throw new InternalCompilerError("Cannot get codebase for namespace:"
        + namespace + ":" + nsr.getClass());
  }

  @Override
  public ClassFileLazyClassInitializer classFileLazyClassInitializer(
      ClassFile clazz) {
    return new CBLazyClassInitializer((codebases.types.ClassFile) clazz, this);
  }

  // / Deprecated/Unsupported methods

  private UnsupportedOperationException toplevel_resolution_error() {
    return new UnsupportedOperationException(
        "Top level resolution is unsupported with codebases.");
  }

  @Override
  @Deprecated
  public SystemResolver systemResolver() {
    throw toplevel_resolution_error();
  }

  @Override
  @Deprecated
  public SystemResolver saveSystemResolver() {
    throw toplevel_resolution_error();
  }

  @Override
  @Deprecated
  public void restoreSystemResolver(SystemResolver r) {
    throw toplevel_resolution_error();
  }

  @Override
  @Deprecated
  public CachingResolver parsedResolver() {
    throw toplevel_resolution_error();
  }

  @Override
  @Deprecated
  public TopLevelResolver loadedResolver() {
    // XXX: backwards compatibility for JifContext to resolve jif.principals.*
    return platformResolver();
  }

  @Override
  @Deprecated
  public boolean packageExists(String name) {
    throw toplevel_resolution_error();
  }

  @Override
  @Deprecated
  public ImportTable importTable(Package pkg) {
    throw new UnsupportedOperationException(
        "Import table must be associated with a namespace,"
            + " use importTable(Source,URI,Package) instead");
  }

  @Override
  @Deprecated
  public ImportTable importTable(String sourceName, Package pkg) {
    throw new UnsupportedOperationException(
        "Import table must be associated with a namespace,"
            + " use importTable(Source,URI,Package) instead");
  }

  @Override
  public NamespaceResolver platformResolver() {
    if (platformResolver == null)
      throw new InternalCompilerError(
          "Must call initResolvers() first! platform");
    return platformResolver;
  }

  @Override
  public Flags legalFieldFlags() {
    return super.legalFieldFlags().set(FabILFlags.IMMUTABLE);
  }

  @Override
  public Flags legalInterfaceFieldFlags() {
    return super.legalInterfaceFieldFlags().set(FabILFlags.IMMUTABLE);
  }

  @Override
  public StageInstance stageInstance(Type origType) {
    return new StageInstance_c(this, origType);
  }
}
