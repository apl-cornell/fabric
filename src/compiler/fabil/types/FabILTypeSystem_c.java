package fabil.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import polyglot.ast.TypeNode;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.main.Report;
import polyglot.types.AccessControlResolver;
import polyglot.types.AccessControlWrapperResolver;
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
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.StringUtil;
import codebases.types.CBClassContextResolver;
import codebases.types.CBImportTable;
import codebases.types.CBLazyClassInitializer;
import codebases.types.CBPackageContextResolver;
import codebases.types.CBPackage_c;
import codebases.types.CBPlaceHolder_c;
import codebases.types.CodebaseClassType;
import codebases.types.NamespaceResolver;
import fabric.lang.Codebase;
import fabric.worker.Worker;

public class FabILTypeSystem_c extends TypeSystem_c implements FabILTypeSystem {
  @SuppressWarnings("unchecked")
  private static final Collection<String> TOPICS = CollectionUtil.list(Report.types,
      Report.resolver);

  private fabil.ExtensionInfo extInfo;

  protected Map<URI, NamespaceResolver> namespaceResolvers; 
  protected List<NamespaceResolver> classpathResolvers;
  protected List<NamespaceResolver> sourcepathResolvers;
  protected List<NamespaceResolver> signatureResolvers;
  protected List<NamespaceResolver> runtimeResolvers;

  protected NamespaceResolver platformResolver; 

  @Override
  public CBPackageContextResolver createPackageContextResolver(URI namespace,
      Package p) {
    return new CBPackageContextResolver(this, namespace, p);
  }
  
  @Override
  public Resolver packageContextResolver(URI namespace, Package p,
      ClassType accessor) {
    if (accessor == null) {
      return p.resolver();
    } else {
      return new AccessControlWrapperResolver(createPackageContextResolver(
          namespace, p), accessor);
    }
  }

  @Override
  public Resolver packageContextResolver(URI namespace, Package p) {
    return packageContextResolver(namespace, p, null);
  }

  @Override
  public Package createPackage(URI ns, Package prefix, String name) {
    return new CBPackage_c(this, ns, prefix, name);
  }
  
  @Override
  public Package packageForName(URI ns, Package prefix, String name)
      throws SemanticException {
    return createPackage(ns, prefix, name);
  }

  @Override
  public Package packageForName(URI ns, String name) throws SemanticException {
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
  public Object placeHolder(TypeObject o, Set roots) {
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
    return new FabILParsedClassType_c(this, init, fromSource);
  }

  @Override
  public Context createContext() {
    return new FabILContext_c(this);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public List<String> defaultPackageImports() {
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
  //XXX: Why is this method here?
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

  @Override
  public ClassType fabricRuntimeArrayOf(Type type) {
    if (type.isReference())
      return load("fabric.lang.arrays.ObjectArray");
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

  /*
   * (non-Javadoc)
   * @see fabil.types.FabILTypeSystem#isJavaInlineable(polyglot.types.Type)
   */
  @Override
  public boolean isJavaInlineable(Type type) {
    return isSubtype(type, JavaInlineable());
  }

  @Override
  public boolean isJavaInlineable(TypeNode type) {
    return isJavaInlineable(type.type());
  }

  @SuppressWarnings("rawtypes")
  @Override
  public MethodInstance methodInstance(Position pos, ReferenceType container,
      Flags flags, Type returnType, String name, List argTypes, List excTypes) {
    assert_(container);
    assert_(returnType);
    assert_(argTypes);
    assert_(excTypes);
    return new FabILMethodInstance_c(this, pos, container, flags,
                                returnType, name, argTypes, excTypes);
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
    String typeName = fullName;
    return typeName.startsWith("java") 
        || typeName.startsWith("fabric")
        || typeName.startsWith("jif");
  }

  @Override
  public void initialize(ExtensionInfo extInfo) throws SemanticException {
    //There is no toplevel resolver -- names are resolved via the source's codebase
    initialize(null, extInfo);
    this.loadedResolver = null;
    this.systemResolver = null;
    this.extInfo = (fabil.ExtensionInfo) extInfo;
    initResolvers();
  }

  protected void initResolvers() {
    //NB: getOptions() and getFabILOptions() do not return the same thing if
    //  we are compiling from Fabric source! getOptions() returns the FabricOptions()
    //  object (which implements FabILOptions).  In particular, to get the right signaturepath
    //  we have to call getFabILOptions().
    List<URI> cp = extInfo.classpath();
    List<URI> sp = extInfo.sourcepath();
    List<URI> sigcp = extInfo.signaturepath();
    List<URI> rtcp = extInfo.bootclasspath();

    namespaceResolvers = new HashMap<URI, NamespaceResolver>();
    signatureResolvers = new ArrayList<NamespaceResolver>();
    classpathResolvers = new ArrayList<NamespaceResolver>();
    sourcepathResolvers = new ArrayList<NamespaceResolver>();
    
    runtimeResolvers = new ArrayList<NamespaceResolver>();
    for(URI uri : rtcp) {
      if (Report.should_report(TOPICS, 2))
        Report.report(2, "Initializing FabIL runtime resolver: " +  uri);

      NamespaceResolver nsr = namespaceResolver(uri);
      nsr.loadEncodedClasses(true);
      nsr.loadRawClasses(true);
      nsr.loadSource(true);
      runtimeResolvers.add(nsr);
    }

    for(URI uri : sigcp) {
      if (Report.should_report(TOPICS, 2))
        Report.report(2, "Initializing FabIL signature resolver: " +  uri);
      NamespaceResolver nsr = namespaceResolver(uri);
      nsr.loadEncodedClasses(true);
      //A raw signature class is an oxymoron
      nsr.loadRawClasses(false);
      nsr.loadSource(true);
      signatureResolvers.add(nsr);
    }
    
    platformResolver = namespaceResolver(extInfo.platformNamespace());
    platformResolver.loadSource(true);
    boolean src_in_cp = sp.isEmpty();
    
    for(URI uri : cp) {
      if (Report.should_report(TOPICS, 2))
        Report.report(2, "Initializing FabIL classpath resolver: " +  uri);

      NamespaceResolver nsr = namespaceResolver(uri);
      nsr.loadEncodedClasses(true);
      nsr.loadRawClasses(true);
      nsr.loadSource(src_in_cp);
      classpathResolvers.add(nsr);
    }
    
    for(URI uri : sp) {
      if (Report.should_report(TOPICS, 2))
        Report.report(2, "Initializing FabIL sourcepath resolver: " +  uri);

      NamespaceResolver nsr = namespaceResolver(uri);
      nsr.loadEncodedClasses(true);
      nsr.loadSource(true);

      if(!classpathResolvers.contains(nsr))
        nsr.loadEncodedClasses(false);
      sourcepathResolvers.add(nsr);
    }
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
    //Worker must be running!
    if(!Worker.isInitialized())
      throw new InternalCompilerError("Worker is not initialized.");
    
      NamespaceResolver nsr = namespaceResolver(namespace);
      if(nsr.codebase() != null)
        return nsr.codebase();
      
      throw new InternalCompilerError("Cannot get codebase for namespace:" + namespace +":" + nsr.getClass());
  }

  @Override
  public ClassFileLazyClassInitializer classFileLazyClassInitializer(ClassFile clazz) {
    return new CBLazyClassInitializer(clazz, this);
}

  /// Deprecated/Unsupported methods
  
  private UnsupportedOperationException toplevel_resolution_error() {
    return new UnsupportedOperationException("Top level resolution is unsupported with codebases.");
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
    throw toplevel_resolution_error();
  }

  @Override
  @Deprecated
  public boolean packageExists(String name) {
    throw toplevel_resolution_error();
  }

  @Override
  @Deprecated
  public CBPackageContextResolver createPackageContextResolver(Package p) {
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
      throw new InternalCompilerError("Must call initResolvers() first!");
    return platformResolver;
  }

  @Override
  public List<NamespaceResolver> signatureResolvers() {
    if (signatureResolvers == null)
      throw new InternalCompilerError("Must call initResolvers() first!");
    return signatureResolvers;
  }

  @Override
  public List<NamespaceResolver> classpathResolvers() {
    if (classpathResolvers == null)
      throw new InternalCompilerError("Must call initResolvers() first!");

    return classpathResolvers;
  }
  
  @Override
  public List<NamespaceResolver> sourcepathResolvers() {
    if (sourcepathResolvers == null)
      throw new InternalCompilerError("Must call initResolvers() first!");

    return sourcepathResolvers;
  }

  @Override
  public List<NamespaceResolver> runtimeResolvers() {
    if (runtimeResolvers == null)
      throw new InternalCompilerError("Must call initResolvers() first!");

    return runtimeResolvers;
  }

}
