package fabric.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jif.ast.JifUtil;
import jif.translate.ConjunctivePrincipalToJavaExpr_c;
import jif.translate.DisjunctivePrincipalToJavaExpr_c;
import jif.translate.LabelToJavaExpr;
import jif.translate.PrincipalToJavaExpr;
import jif.types.DefaultSignature;
import jif.types.JifClassType;
import jif.types.JifLocalInstance;
import jif.types.JifTypeSystem_c;
import jif.types.LabeledType;
import jif.types.Solver;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathConstant;
import jif.types.label.AccessPathLocal;
import jif.types.label.ArgLabel;
import jif.types.label.ConfPolicy;
import jif.types.label.IntegPolicy;
import jif.types.label.JoinLabel;
import jif.types.label.Label;
import jif.types.label.MeetLabel;
import jif.types.label.ProviderLabel;
import jif.types.label.ThisLabel;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;
import polyglot.ext.param.types.Subst;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.main.Report;
import polyglot.types.AccessControlResolver;
import polyglot.types.CachingResolver;
import polyglot.types.ClassType;
import polyglot.types.Context;
import polyglot.types.FieldInstance;
import polyglot.types.Flags;
import polyglot.types.ImportTable;
import polyglot.types.LazyClassInitializer;
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
import polyglot.types.TypeSystem;
import polyglot.types.reflect.ClassFile;
import polyglot.types.reflect.ClassFileLazyClassInitializer;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.StringUtil;
import codebases.frontend.CodebaseSource;
import codebases.types.CBClassContextResolver;
import codebases.types.CBImportTable;
import codebases.types.CBPackage;
import codebases.types.CBPackageContextResolver;
import codebases.types.CBPackage_c;
import codebases.types.CBPlaceHolder_c;
import codebases.types.CodebaseResolver;
import codebases.types.NamespaceResolver;
import fabric.lang.Codebase;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.NodePrincipal;
import fabric.translate.DynamicPrincipalToFabilExpr_c;
import fabric.translate.FabricJoinLabelToFabilExpr_c;
import fabric.translate.FabricMeetLabelToFabilExpr_c;
import fabric.translate.FabricPairLabelToFabilExpr_c;
import fabric.translate.ProviderLabelToFabilExpr_c;
import fabric.worker.Store;
import fabric.worker.Worker;

public class FabricTypeSystem_c extends JifTypeSystem_c implements
    FabricTypeSystem {
  @SuppressWarnings("unchecked")
  private static final Collection<String> TOPICS = CollectionUtil.list(
      Report.types, Report.resolver);

  protected Map<URI, NamespaceResolver> namespaceResolvers;
  protected List<NamespaceResolver> classpathResolvers;
  protected List<NamespaceResolver> sourcepathResolvers;
  protected List<NamespaceResolver> signatureResolvers;
  protected List<NamespaceResolver> runtimeResolvers;

  protected NamespaceResolver platformResolver;

  private fabric.ExtensionInfo extInfo;
  private final FabricDefaultSignature ds;

  /**
   * The codebase to be published to Fabric.
   */
  protected Codebase new_codebase = null;

  public FabricTypeSystem_c(TypeSystem jlts) {
    super(jlts);
    this.ds = new FabricFixedSignature(this);
    this.extInfo = (fabric.ExtensionInfo) super.extInfo;
  }

  @Override
  public DefaultSignature defaultSignature() {
    return ds;
  }

  @Override
  public FabricDefaultSignature fabricDefaultSignature() {
    return ds;
  }

  @Override
  public void initialize(ExtensionInfo extInfo) throws SemanticException {
    // There is no toplevel resolver -- names are resolved via the source's
    // codebase
    initialize(null, extInfo);
    this.loadedResolver = null;
    this.systemResolver = null;
    this.extInfo = (fabric.ExtensionInfo) super.extInfo;
    initResolvers();
  }

  protected void initResolvers() {
    List<URI> cp = extInfo.classpath();
    List<URI> sp = extInfo.sourcepath();
    List<URI> sigcp = extInfo.signaturepath();
    List<URI> rtcp = extInfo.bootclasspath();

    namespaceResolvers = new HashMap<URI, NamespaceResolver>();
    signatureResolvers = new ArrayList<NamespaceResolver>();
    classpathResolvers = new ArrayList<NamespaceResolver>();
    sourcepathResolvers = new ArrayList<NamespaceResolver>();

    runtimeResolvers = new ArrayList<NamespaceResolver>();
    for (URI uri : rtcp) {
      if (Report.should_report(TOPICS, 2))
        Report.report(2, "Initializing Fabric runtime resolver: " + uri);

      NamespaceResolver nsr = namespaceResolver(uri);
      nsr.loadEncodedClasses(true);
      nsr.loadRawClasses(true);
      nsr.loadSource(true);
      runtimeResolvers.add(nsr);
    }

    for (URI uri : sigcp) {
      if (Report.should_report(TOPICS, 2))
        Report.report(2, "Initializing Fabric signature resolver: " + uri);
      NamespaceResolver nsr = namespaceResolver(uri);
      nsr.loadEncodedClasses(true);
      // A raw signature class is an oxymoron
      nsr.loadRawClasses(false);
      nsr.loadSource(true);
      signatureResolvers.add(nsr);
    }

    platformResolver = namespaceResolver(extInfo.platformNamespace());
    platformResolver.loadSource(true);
    boolean src_in_cp = sp.isEmpty();

    for (URI uri : cp) {
      if (Report.should_report(TOPICS, 2))
        Report.report(2, "Initializing FabIL classpath resolver: " + uri);

      NamespaceResolver nsr = namespaceResolver(uri);
      nsr.loadEncodedClasses(true);
      nsr.loadRawClasses(true);
      nsr.loadSource(src_in_cp);
      classpathResolvers.add(nsr);
    }

    for (URI uri : sp) {
      if (Report.should_report(TOPICS, 2))
        Report.report(2, "Initializing FabIL sourcepath resolver: " + uri);

      NamespaceResolver nsr = namespaceResolver(uri);
      nsr.loadEncodedClasses(true);
      nsr.loadSource(true);

      if (!classpathResolvers.contains(nsr)) nsr.loadEncodedClasses(false);
      sourcepathResolvers.add(nsr);
    }
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

  @Override
  public CBPackageContextResolver createPackageContextResolver(Package p) {
    assert_(p);
    return new CBPackageContextResolver(this, p);
  }

  //XXX: There may be a way to override the namespace-less package methods, but 
  // createPackage is often called with prefix==null, so a full refactoring helped
  // identify the situations it is called in.
  @Override
  public Package createPackage(URI ns, Package prefix, java.lang.String name) {
    if (prefix!=null) {
      ns = ((CBPackage)prefix).namespace();
    }
    return new CBPackage_c(this, ns, prefix, name);
  }

  /**
   * @throws SemanticException  
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

  @SuppressWarnings("rawtypes")
  @Override
  public Object placeHolder(TypeObject o, Set roots) {
    assert_(o);
    if (o instanceof FabricParsedClassType) {
      FabricParsedClassType ct = (FabricParsedClassType) o;

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
  public ClassType FObject() {
    return load("fabric.lang.Object");
  }

  @Override
  public String PrincipalClassName() {
    return "fabric.lang.security.Principal";
  }

  @Override
  public String RuntimePackageName() {
    return "fabric.runtime";
  }

  @Override
  public String PrincipalUtilClassName() {
    return "fabric.lang.security.PrincipalUtil";
  }

  @Override
  public String LabelClassName() {
    return "fabric.lang.security.Label";
  }

  @Override
  public String LabelUtilClassName() {
    return "fabric.lang.security.LabelUtil";
  }

  @Override
  public List<String> defaultPackageImports() {
    // Include fabric.lang and fabric.worker as default imports.
    List<String> result = new ArrayList<String>(5);
    result.add("fabric.lang");
    result.add("fabric.lang.security");
    result.add("fabric.worker");
    result.addAll(super.defaultPackageImports());
    return result;
  }

  @Override
  public CBImportTable importTable(Source source, URI ns, Package pkg) {
    return new CBImportTable(this, ns, pkg, source);
  }

  @Override
  public ParsedClassType createClassType(LazyClassInitializer init,
      Source fromSource) {
    return new FabricParsedClassType_c(this, init, fromSource);
  }

  @Override
  public ClassType RemoteWorker() {
    return load("fabric.worker.RemoteWorker");
  }

  @Override
  public ClassType Worker() {
    return load("fabric.worker.FabricWorker");
  }

  @Override
  public ClassType Store() {
    return load("fabric.worker.Store");
  }

  @Override
  public ClassType DelegatingPrincipal() {
    return load("fabric.lang.security.DelegatingPrincipal");
  }

  private JifLocalInstance workerLocalInstance = null;

  @Override
  public JifLocalInstance workerLocalInstance() {
    if (workerLocalInstance == null) {
      // Always use the same local instance, because jif now use pointer
      // identity to compare local instances
      // for the purpose of label checking.
      workerLocalInstance =
          (JifLocalInstance) localInstance(Position.compilerGenerated(),
              Flags.FINAL, Worker(), "worker$");
      // workerLocalInstance.setLabel(freshLabelVariable(workerLocalInstance.position(),
      // "worker$", "worker$"));
    }
    return workerLocalInstance;
  }

  @Override
  public Principal workerPrincipal(Position pos) {
    // return dynamicPrincipal(pos, new AccessPathWorker(pos, this));
    try {
      JifLocalInstance li = workerLocalInstance();
      Principal p =
          dynamicPrincipal(pos, JifUtil.varInstanceToAccessPath(li, pos));
      li.setLabel(pairLabel(li.position(),
          readerPolicy(li.position(), p, bottomPrincipal(li.position())),
          writerPolicy(li.position(), p, p)));
      return p;
    } catch (SemanticException e) {
      // shouldn't happen
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public FieldInstance fieldInstance(Position pos, ReferenceType container,
      Flags flags, Type type, String name) {
    return fabricFieldInstance(pos, container, flags, type, null, name);
  }

  @Override
  public FabricFieldInstance fabricFieldInstance(Position pos,
      ReferenceType container, Flags flags, Type type, Label accessLabel,
      String name) {
    return new FabricFieldInstance_c(this, pos, container, flags, type,
        accessLabel, name);
  }

  @Override
  public boolean isCastValid(Type fromType, Type toType) {
    Type strpFromType = strip(fromType);
    Type strpToType = strip(toType);

    if ((equals(strpFromType, Worker()) || equals(strpFromType, RemoteWorker()) || equals(
        strpFromType, Store())) && equals(strpToType, Principal())) {
      return true;
    }

    return super.isCastValid(fromType, toType);
  }

  @Override
  public boolean isImplicitCastValid(Type fromType, Type toType) {
    Type strpFromType = strip(fromType);
    Type strpToType = strip(toType);

    if ((equals(strpFromType, Worker()) || equals(strpFromType, RemoteWorker()) || equals(
        strpFromType, Store())) && equals(strpToType, Principal())) {
      return true;
    }

    return super.isImplicitCastValid(fromType, toType);
  }

  @Override
  protected PrincipalToJavaExpr dynamicPrincipalTranslator() {
    return new DynamicPrincipalToFabilExpr_c();
  }

  @Override
  public Solver createSolver(String solverName) {
    return new SilenceableSolverGLB(this, extInfo.compiler(), solverName);
  }

  @Override
  public Context createContext() {
    return new FabricContext_c(this, jlts);
  }

  @Override
  public Type strip(Type type) {
    return super.strip(type);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Subst subst(Map substMap, Map cache) {
    return new FabricSubst_c(this, substMap, cache);
  }

  @Override
  public DynamicPrincipal dynamicPrincipal(Position pos, AccessPath path) {
    DynamicPrincipal t =
        new FabricDynamicPrincipal_c(path, this, pos,
            dynamicPrincipalTranslator());
    return t;
  }

  @Override
  public Principal pathToPrincipal(Position pos, AccessPath path) {
    if (path instanceof AccessPathConstant) {
      AccessPathConstant apc = (AccessPathConstant) path;
      if (!apc.isPrincipalConstant()) {
        throw new InternalCompilerError(
            "Dynamic principal with a constant access path: " + apc);
      }
      return (Principal) apc.constantValue();
    }
    DynamicPrincipal t =
        new FabricDynamicPrincipal_c(path, this, pos,
            dynamicPrincipalTranslator());
    return t;
  }

  @Override
  public boolean isLocalWorkerAccessPath(AccessPath ap) {
    return ap instanceof AccessPathLocal
        && ((AccessPathLocal) ap).localInstance() == workerLocalInstance();
  }

  @Override
  protected LabelToJavaExpr pairLabelTranslator() {
    return new FabricPairLabelToFabilExpr_c();
  }

  @Override
  public ProviderLabel providerLabel(JifClassType ct) {
    return new FabricProviderLabel_c(ct, providerLabelTranslator());
  }

  @Override
  protected LabelToJavaExpr providerLabelTranslator() {
    return new ProviderLabelToFabilExpr_c();
  }

  @Override
  public LabelToJavaExpr meetLabelTranslator() {
    return new FabricMeetLabelToFabilExpr_c();
  }

  @Override
  public LabelToJavaExpr joinLabelTranslator() {
    return new FabricJoinLabelToFabilExpr_c();
  }

  // XXX: we may need to override these methods with
  // Fabric-specific translators so that *-junctive principals
  // are created on the correct store.
  @Override
  public PrincipalToJavaExpr conjunctivePrincipalTranslator() {
    return new ConjunctivePrincipalToJavaExpr_c();
  }

  @Override
  public PrincipalToJavaExpr disjunctivePrincipalTranslator() {
    return new DisjunctivePrincipalToJavaExpr_c();
  }

  @Override
  public ConfPolicy representableConfProjection(Label L) {
    if (L instanceof ArgLabel) {
      return super.confProjection(((ArgLabel) L).upperBound());
    } else if (L instanceof MeetLabel) {
      Set<ConfPolicy> confPols = new HashSet<ConfPolicy>();
      for (Label l : ((MeetLabel) L).meetComponents()) {
        confPols.add(representableConfProjection(l));
      }
      return meetConfPolicy(L.position(), confPols);
    } else if (L instanceof JoinLabel) {
      Set<ConfPolicy> confPols = new HashSet<ConfPolicy>();
      for (Label l : ((JoinLabel) L).joinComponents()) {
        confPols.add(representableConfProjection(l));
      }
      return joinConfPolicy(L.position(), confPols);
    }
    return super.confProjection(L);
  }

  @Override
  public IntegPolicy representableIntegProjection(Label L) {
    if (L instanceof ArgLabel) {
      return super.integProjection(((ArgLabel) L).upperBound());
    } else if (L instanceof MeetLabel) {
      Set<IntegPolicy> integPols = new HashSet<IntegPolicy>();
      for (Label l : ((MeetLabel) L).meetComponents()) {
        integPols.add(representableIntegProjection(l));
      }
      return meetIntegPolicy(L.position(), integPols);
    } else if (L instanceof JoinLabel) {
      Set<IntegPolicy> integPols = new HashSet<IntegPolicy>();
      for (Label l : ((JoinLabel) L).joinComponents()) {
        integPols.add(representableIntegProjection(l));
      }
      return joinIntegPolicy(L.position(), integPols);
    }
    return super.integProjection(L);
  }

  @Override
  public boolean isFabricClass(Type type) {
    if (type instanceof ClassType) {
      ClassType ct = (ClassType) type;

      // XXX Interfaces are excluded for now.
      if (ct.flags().isInterface()) return false;

      while (ct != null) {
        if (typeEquals(ct, FObject())) {
          return true;
        }
        ct = (ClassType) ct.superType();
      }
    }
    return false;
  }

  @Override
  public boolean isFabricArray(Type t) {
    // unwrap label
    if (t instanceof LabeledType) t = ((LabeledType) t).typePart();

    if (t instanceof FabricArrayType) return !((FabricArrayType) t).isNative();

    return false;
  }

  @Override
  public FabricArrayType toFabricArray(Type t) {
    if (t instanceof LabeledType) t = ((LabeledType) t).typePart();

    return (FabricArrayType) t;
  }

  @Override
  public Flags legalTopLevelClassFlags() {
    Flags f = super.legalTopLevelClassFlags();
    f = f.set(FabricFlags.NONFABRIC);
    return f;
  }

  @Override
  public Flags legalInterfaceFlags() {
    Flags f = super.legalInterfaceFlags();
    f = f.set(FabricFlags.NONFABRIC);
    return f;
  }

  @Override
  public boolean containsThisLabel(Label label) {
    if (label instanceof ThisLabel) {
      return true;
    } else if (label instanceof MeetLabel) {
      MeetLabel ml = (MeetLabel) label;
      for (Label l : ml.meetComponents()) {
        if (containsThisLabel(l)) return true;
      }
    } else if (label instanceof JoinLabel) {
      JoinLabel jl = (JoinLabel) label;
      for (Label l : jl.joinComponents()) {
        if (containsThisLabel(l)) return true;
      }
    }

    return false;
  }

  // array type constructors ///////////////////////////////////////////////////

  @Override
  public FabricArrayType fabricArrayOf(Position pos, Type t) {
    return new FabricArrayType_c(this, pos, t,
    /* isConst */false, /* isNonConst */true,
    /* isNative */false);
  }

  @Override
  public FabricArrayType fabricArrayOf(Position pos, Type t, int dims) {
    if (dims == 1)
      return fabricArrayOf(pos, t);
    else return fabricArrayOf(pos, fabricArrayOf(pos, t, dims - 1));
  }

  @Override
  protected FabricArrayType arrayType(Position pos, Type type) {
    if (!isLabeled(type)) {
      type =
          labeledType(pos, type, defaultSignature().defaultArrayBaseLabel(type));
    }

    return new FabricArrayType_c(this, pos, type,
    /* isConst */false, /* isNonConst */true,
    /* isNative */true);
  }

  @SuppressWarnings("deprecation")
  @Override
  public String translateClass(Resolver c, ClassType t) {
    // Fully qualify classes in fabric.lang.security.
    if (t.package_() != null) {
      if (t.package_().equals(createPackage("fabric.lang.security"))) {
        return super.translateClass(null, t);
      }
    }

    return super.translateClass(c, t);
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
  public NamespaceResolver platformResolver() {
    if (platformResolver == null)
      throw new InternalCompilerError("Must call initResolvers() first!");
    return platformResolver;
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

  @SuppressWarnings("rawtypes")
  @Override
  public Type typeForClass(Class clazz) throws SemanticException {
    return typeForClass(platformResolver(), clazz);
  }

  @Override
  public Codebase codebaseFromNS(URI namespace) {
    // Worker must be running!
    if (!Worker.isInitialized())
      throw new InternalCompilerError("Worker is not initialized.");

    if (extInfo.localNamespace().equals(namespace)) {
      if (new_codebase == null) {
        Store dest = extInfo.destinationStore();
        // XXX: what should the label of a new codebase be? The join of the
        // integrity of the classpath?
        // or the highest integrity the worker can claim?
        fabric.lang.security.Label lbl = defaultPublishingLabel();
        fabric.util.HashMap classes =
            new fabric.util.HashMap._Impl(dest, lbl, lbl);
        new_codebase =
            (Codebase) new Codebase._Impl(dest, lbl, lbl, classes)
                .$getProxy();
      }
      return new_codebase;
    } else if (extInfo.platformNamespace().equals(namespace)
        || !namespace.getScheme().equals("fab")) {
      throw new InternalCompilerError("Cannot get codebase for " + namespace);
    } else {
      CodebaseResolver cr = (CodebaseResolver) namespaceResolver(namespace);
      return cr.codebase();
    }
  }

  //The default publishing label is the maximum integrity the worker can claim
  protected fabric.lang.security.Label defaultPublishingLabel() {
    Store dest = extInfo.destinationStore();
    NodePrincipal w = Worker.getWorker().getPrincipal();
    NodePrincipal st = dest.getPrincipal();
    if (w != null && st != null)
      return LabelUtil._Impl.writerPolicyLabel(dest, w, st);
    throw new InternalCompilerError("Whatt?!? W: " + w + " ST: " + st);
  }

  @Override
  public fabric.lang.security.Label sourceUpdateLabel(CodebaseSource src) {
    if (src.namespace().equals(extInfo.localNamespace())
        && src.namespace().equals(src.canonicalNamespace()))
      return defaultPublishingLabel();
    else throw new InternalCompilerError("Re-linking not yet supported");
  }

  @Override
  public fabric.lang.security.Label sourceAccessLabel(CodebaseSource src) {
    // Re-use update label for access label. In general the store may allow
    // a more restricted access label, but we can only use the provider
    // label to statically figure out when we can fetch from the store vs.
    // using a replica.
    return sourceUpdateLabel(src);
  }

  @Override
  public ClassFileLazyClassInitializer classFileLazyClassInitializer(
      ClassFile clazz) {
    throw new UnsupportedOperationException("Fabric doesn't support raw classes");
//    return new FabILLazyClassInitializer(clazz, this);
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
    throw toplevel_resolution_error();
  }

  @Override
  @Deprecated
  public boolean packageExists(String name) {
    throw toplevel_resolution_error();
  }

//  @Override
//  @Deprecated
//  public CBPackageContextResolver createPackageContextResolver(Package p) {
//    throw toplevel_resolution_error();
//  }

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
  public Label tjoin(Label L1, Label L2) {
    ConfPolicy cp1 = L1 == null ? null : L1.confProjection();
    ConfPolicy cp2 = L2 == null ? null : L2.confProjection();
    
    IntegPolicy ip1 = L1 == null ? null : L1.integProjection();
    IntegPolicy ip2 = L2 == null ? null : L2.integProjection();
    
    return pairLabel(L1.position(), join(cp1, cp2), meet(ip1, ip2));
  }

  @Override
  public Label tmeet(Label L1, Label L2) {
    ConfPolicy cp1 = L1 == null ? null : L1.confProjection();
    ConfPolicy cp2 = L2 == null ? null : L2.confProjection();
    
    IntegPolicy ip1 = L1 == null ? null : L1.integProjection();
    IntegPolicy ip2 = L2 == null ? null : L2.integProjection();
    
    return pairLabel(L1.position(), meet(cp1, cp2), join(ip1, ip2));
  }

}
