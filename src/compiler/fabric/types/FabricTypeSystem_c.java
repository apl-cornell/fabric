package fabric.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import codebases.frontend.CodebaseSource;
import codebases.types.CBClassContextResolver;
import codebases.types.CBImportTable;
import codebases.types.CBLazyClassInitializer;
import codebases.types.CBPackage;
import codebases.types.CBPackageContextResolver;
import codebases.types.CBPackage_c;
import codebases.types.CBPlaceHolder_c;
import codebases.types.CodebaseResolver;
import codebases.types.NamespaceResolver;

import fabil.types.FabILFlags;

import fabric.FabricOptions;
import fabric.ast.FabricUtil;
import fabric.ast.RemoteWorkerGetter;
import fabric.extension.NewExt_c;
import fabric.lang.Codebase;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.NodePrincipal;
import fabric.translate.ConjunctivePrincipalToFabilExpr_c;
import fabric.translate.DisjunctivePrincipalToFabilExpr_c;
import fabric.translate.DynamicPrincipalToFabilExpr_c;
import fabric.translate.FabricJoinLabelToFabilExpr_c;
import fabric.translate.FabricMeetLabelToFabilExpr_c;
import fabric.translate.FabricPairLabelToFabilExpr_c;
import fabric.translate.FabricWritersToReadersLabelToFabilExpr_c;
import fabric.translate.ProviderLabelToFabilExpr_c;
import fabric.worker.Store;
import fabric.worker.Worker;

import jif.translate.LabelToJavaExpr;
import jif.translate.PrincipalToJavaExpr;
import jif.types.ActsForConstraint;
import jif.types.Assertion;
import jif.types.DefaultSignature;
import jif.types.JifClassType;
import jif.types.JifConstructorInstance;
import jif.types.JifContext;
import jif.types.JifMethodInstance;
import jif.types.JifTypeSystem_c;
import jif.types.LabelLeAssertion;
import jif.types.LabelSubstitution;
import jif.types.LabeledType;
import jif.types.Param;
import jif.types.ParamInstance;
import jif.types.Path;
import jif.types.Solver;
import jif.types.hierarchy.LabelEnv;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathField;
import jif.types.label.AccessPathThis;
import jif.types.label.AccessPathUninterpreted;
import jif.types.label.ArgLabel;
import jif.types.label.ConfPolicy;
import jif.types.label.ConfProjectionPolicy;
import jif.types.label.ConfProjectionPolicy_c;
import jif.types.label.IntegPolicy;
import jif.types.label.IntegProjectionPolicy_c;
import jif.types.label.JoinConfPolicy;
import jif.types.label.JoinConfPolicy_c;
import jif.types.label.JoinLabel;
import jif.types.label.Label;
import jif.types.label.MeetConfPolicy;
import jif.types.label.MeetLabel;
import jif.types.label.PairLabel;
import jif.types.label.ParamLabel;
import jif.types.label.ProviderLabel;
import jif.types.label.ReaderPolicy;
import jif.types.label.ThisLabel;
import jif.types.principal.BottomPrincipal;
import jif.types.principal.ConjunctivePrincipal;
import jif.types.principal.DisjunctivePrincipal;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.ExternalPrincipal;
import jif.types.principal.ExternalPrincipal_c;
import jif.types.principal.ParamPrincipal;
import jif.types.principal.Principal;
import jif.types.principal.TopPrincipal;

import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.ext.param.types.Subst;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.types.AccessControlResolver;
import polyglot.types.CachingResolver;
import polyglot.types.ClassType;
import polyglot.types.ConstructorInstance;
import polyglot.types.FieldInstance;
import polyglot.types.Flags;
import polyglot.types.ImportTable;
import polyglot.types.LazyClassInitializer;
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
import polyglot.types.TypeSystem;
import polyglot.types.reflect.ClassFile;
import polyglot.types.reflect.ClassFileLazyClassInitializer;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.StringUtil;

public class FabricTypeSystem_c extends JifTypeSystem_c
    implements FabricTypeSystem {
  protected Map<URI, NamespaceResolver> namespaceResolvers;
  protected NamespaceResolver platformResolver;
  protected NamespaceResolver applicationResolver;

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
  public fabric.ExtensionInfo extensionInfo() {
    return extInfo;
  }

  @Override
  public ClassType fatalException() {
    return load("fabric.common.exceptions.ApplicationError");
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
    FabricOptions opt = extInfo.getOptions();
    boolean loadRaw = opt.skipLabelChecking;

    namespaceResolvers = new HashMap<>();
    platformResolver = namespaceResolver(extInfo.platformNamespace());
    platformResolver.loadRawClasses(loadRaw);

    applicationResolver = namespaceResolver(extInfo.localNamespace());
    applicationResolver.loadRawClasses(loadRaw);
  }

  @Override
  public CBPackageContextResolver createPackageContextResolver(Package p) {
    assert_(p);
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
    List<String> result = new ArrayList<>(5);
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
    if (fromSource == null) {
      if (extInfo.getJifOptions().skipLabelChecking)
        // local raw class file.
        return createClassType(init, fromSource, extInfo.localNamespace());
      else throw new InternalCompilerError(
          "Attempting to create class type for raw class!");
    } else {
      URI ns = ((CodebaseSource) fromSource).canonicalNamespace();
      return createClassType(init, fromSource, ns);
    }
  }

  @Override
  public ParsedClassType createClassType(LazyClassInitializer init,
      Source fromSource, URI ns) {
    return new FabricParsedClassType_c(this, init, fromSource, ns);
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

  protected WorkerLocalInstance workerLocalInstance = null;

  protected DynamicPrincipal workerLocalPrincipal = null;

  @Override
  public ThisLabel thisLabel(Position pos, ReferenceType ct) {
    return new FabricThisLabel_c(this, ct, pos);
  }

  @Override
  public WorkerLocalInstance workerLocalInstance() {
    if (workerLocalInstance == null) {
      // Always use the same local instance, because jif now use pointer
      // identity to compare local instances
      // for the purpose of label checking.
      workerLocalInstance = new WorkerLocalInstance_c(this,
          Position.compilerGenerated(), Flags.FINAL, Worker(), "worker$");
    }
    return workerLocalInstance;
  }

  @Override
  public Principal workerLocalPrincipal(Position pos) {
    if (workerLocalPrincipal == null) {
      WorkerLocalInstance wli = workerLocalInstance();
      workerLocalPrincipal =
          dynamicPrincipal(pos, new AccessPathLocalWorker(wli, pos));
      wli.setLabel(pairLabel(wli.position(),
          readerPolicy(wli.position(), workerLocalPrincipal,
              bottomPrincipal(wli.position())),
          writerPolicy(wli.position(), workerLocalPrincipal,
              workerLocalPrincipal)));
    }
    return workerLocalPrincipal;
  }

  @Override
  public AccessPath workerLocalAccessPath(Position pos) {
    return new AccessPathLocalWorker(workerLocalInstance(), pos);
  }

  @Override
  public Principal storePrincipal(fabric.ast.Store store, FabricContext context,
      Position pos) throws SemanticException {
    AccessPath ap = exprToAccessPath(store, context);
    return dynamicPrincipal(pos, ap);
  }

  @Override
  public Principal remoteWorkerPrincipal(RemoteWorkerGetter worker,
      FabricContext context, Position pos) throws SemanticException {
    AccessPath ap = exprToAccessPath(worker, context);
    return dynamicPrincipal(pos, ap);
  }

  @Override
  public ExternalPrincipal externalPrincipal(Position pos, String name) {
    return new ExternalPrincipal_c(name, this,
        new FabExternalPrincipalToJavaExpr_c(), pos);
  }

  @Override
  public boolean isFinalAccessExpr(Expr e) {
    if (e instanceof fabric.ast.Store) {
      fabric.ast.Store store = (fabric.ast.Store) e;
      return isFinalAccessExpr(store.expr());
    } else if (e instanceof New) {
      // treat New expressions as "constant"
      // this aids instantiation of Store
      // principals in prodecure signatures.
      return true;
    }
    return super.isFinalAccessExpr(e);
  }

  @Override
  public AccessPath exprToAccessPath(Expr e, Type expectedType,
      JifContext context) throws SemanticException {
    if (e instanceof RemoteWorkerGetter) {
      throw new UnsupportedOperationException(
          "RemoteWorker access paths not yet supported");
    } else if (e instanceof fabric.ast.Worker) {
      return workerLocalAccessPath(e.position());
    } else if (e instanceof New) {
      // support instantiation of this.store$ in constructors
      New nw = (New) e;
      FabricTypeSystem ts = (FabricTypeSystem) context.typeSystem();
      ClassType ct = (ClassType) ts.unlabel(nw.type());
      NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(nw);
      if (ext.location() == null) {
        return new AccessPathNew(ct,
            currentStoreAccessPathFor(context.currentClass(), context),
            nw.position());
      } else {
        return new AccessPathNew(ct, exprToAccessPath(ext.location(), context),
            nw.position());
      }
    } else if (e instanceof fabric.ast.Store) {
      fabric.ast.Store st = (fabric.ast.Store) e;
      // if we wanted to allow "new C().store$" then we need to
      // check whether st.expr is a New expr.
      // This doesn't seem useful, though.
      return new AccessPathStore(exprToAccessPath(st.expr(), context), Store(),
          st.position());
    }
    return super.exprToAccessPath(e, expectedType, context);
  }

  @Override
  public AccessPath storeAccessPathFor(Expr ref, JifContext context)
      throws SemanticException {
    AccessPath storeap;
    Position pos = Position.compilerGenerated();
    if (isFinalAccessExpr(ref)) {
      storeap =
          new AccessPathStore(exprToAccessPath(ref, context), Store(), pos);
    } else {
      storeap =
          new AccessPathUninterpreted(String.valueOf(ref) + ".store$", pos);
    }
    return storeap;
  }

  @Override
  public AccessPath currentStoreAccessPathFor(ClassType ct, JifContext context)
      throws SemanticException {
    Position pos = Position.compilerGenerated();
    return new AccessPathStore(new AccessPathThis(ct, pos), Store(), pos);
  }

  @Override
  public FieldInstance fieldInstance(Position pos, ReferenceType container,
      Flags flags, Type type, String name) {
    return fabricFieldInstance(pos, container, flags, type, null, name);
  }

  @Override
  public FabricFieldInstance fabricFieldInstance(Position pos,
      ReferenceType container, Flags flags, Type type, ConfPolicy accessLabel,
      String name) {
    return new FabricFieldInstance_c(this, pos, container, flags, type,
        accessLabel, name);
  }

  @Override
  public boolean isCastValid(Type fromType, Type toType) {
    Type strpFromType = strip(fromType);
    Type strpToType = strip(toType);

    if ((equals(strpFromType, Worker()) || equals(strpFromType, RemoteWorker())
        || equals(strpFromType, Store())) && equals(strpToType, Principal())) {
      return true;
    }

    return super.isCastValid(fromType, toType);
  }

  @Override
  public boolean isImplicitCastValid(Type fromType, Type toType) {
    Type strpFromType = strip(fromType);
    Type strpToType = strip(toType);

    if ((equals(strpFromType, Worker()) || equals(strpFromType, RemoteWorker())
        || equals(strpFromType, Store())) && equals(strpToType, Principal())) {
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
  public FabricContext createContext() {
    return new FabricContext_c(this, jlts);
  }

  @Override
  public Type strip(Type type) {
    return super.strip(type);
  }

  @Override
  protected Subst<ParamInstance, Param> substImpl(
      Map<ParamInstance, ? extends Param> substMap) {
    return new FabricSubst_c(this, substMap);
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
    return new ConjunctivePrincipalToFabilExpr_c();
  }

  @Override
  public PrincipalToJavaExpr disjunctivePrincipalTranslator() {
    return new DisjunctivePrincipalToFabilExpr_c();
  }

  @Override
  public ConfPolicy representableConfProjection(Label L) {
    if (L instanceof ArgLabel) {
      return super.confProjection(((ArgLabel) L).upperBound());
    } else if (L instanceof MeetLabel) {
      Set<ConfPolicy> confPols = new HashSet<>();
      for (Label l : ((MeetLabel) L).meetComponents()) {
        confPols.add(representableConfProjection(l));
      }
      return meetConfPolicy(L.position(), confPols);
    } else if (L instanceof JoinLabel) {
      Set<ConfPolicy> confPols = new HashSet<>();
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
      Set<IntegPolicy> integPols = new HashSet<>();
      for (Label l : ((MeetLabel) L).meetComponents()) {
        integPols.add(representableIntegProjection(l));
      }
      return meetIntegPolicy(L.position(), integPols);
    } else if (L instanceof JoinLabel) {
      Set<IntegPolicy> integPols = new HashSet<>();
      for (Label l : ((JoinLabel) L).joinComponents()) {
        integPols.add(representableIntegProjection(l));
      }
      return joinIntegPolicy(L.position(), integPols);
    }
    return super.integProjection(L);
  }

  /**
   * Returns true if the type has runtime methods for cast and instanceof
   */
  @Override
  public boolean needsDynamicTypeMethods(Type ct) {
    return isParamsRuntimeRep(ct) || isPersistent(ct) || isFabricInterface(ct);
  }

  /**
   * Returns true if the type uses an external class to define its is dynamic type methods
   */
  @Override
  public boolean needsImplClass(Type ct) {
    return isFabricInterface(ct); // fct is an interface
  }

  /**
   * Returns true if type extends fabric.lang.Object
   */
  @Override
  public boolean isPersistent(Type type) {
    if (type == null) throw new NullPointerException();
    type = unlabel(type);
    if (type instanceof ClassType) {
      ClassType ct = (ClassType) type;

      while (ct != null) {
        if (typeEquals(ct, FObject())) {
          return true;
        }
        ct = (ClassType) ct.superType();
      }
    }
    return false;
  }

  /**
   * Returns true if type does not extend fabric.lang.Object
   */
  @Override
  public boolean isTransient(Type type) {
    if (type == null) throw new NullPointerException();
    return type != null && !isPersistent(type);
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
  public boolean isFabricInterface(Type type) {
    if (type instanceof ClassType) {
      ClassType ct = (ClassType) type;

      if (!ct.flags().isInterface()) return false;

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
    f = f.set(FabILFlags.NONFABRIC);
    return f;
  }

  // TODO: determine scenarios when this labels
  // can be used by the remote call wrapper
  @Override
  public boolean containsThisLabel(Assertion as) {
    if (as instanceof LabelLeAssertion) {
      LabelLeAssertion leq = (LabelLeAssertion) as;
      return containsThisLabel(leq.lhs()) || containsThisLabel(leq.rhs());
    } else if (as instanceof ActsForConstraint) {
      ActsForConstraint<?, ?> afc = (ActsForConstraint<?, ?>) as;
      boolean hasThis = false;

      if (afc.actor() instanceof Label)
        hasThis |= containsThisLabel((Label) afc.actor());

      if (!hasThis && afc.granter() instanceof Label)
        hasThis |= containsThisLabel((Label) afc.actor());

      return hasThis;
    }

    return false;
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
    } else if (label instanceof ArgLabel) {
      ArgLabel jl = (ArgLabel) label;
      return containsThisLabel(jl.upperBound());
    }
    return false;
  }

  @Override
  public boolean containsArgLabel(Label label) {
    if (label instanceof ArgLabel) {
      return true;
    } else if (label instanceof MeetLabel) {
      MeetLabel ml = (MeetLabel) label;
      for (Label l : ml.meetComponents()) {
        if (containsArgLabel(l)) return true;
      }
    } else if (label instanceof JoinLabel) {
      JoinLabel jl = (JoinLabel) label;
      for (Label l : jl.joinComponents()) {
        if (containsArgLabel(l)) return true;
      }
    }
    return false;
  }

  // TODO: determine scenarios when arg labels
  // can be used by the remote call wrapper
  @Override
  public boolean containsArgLabel(Assertion as) {
    if (as instanceof LabelLeAssertion) {
      LabelLeAssertion leq = (LabelLeAssertion) as;
      return containsArgLabel(leq.lhs()) || containsArgLabel(leq.rhs());
    } else if (as instanceof ActsForConstraint) {
      ActsForConstraint<?, ?> afc = (ActsForConstraint<?, ?>) as;
      boolean hasThis = false;

      if (afc.actor() instanceof Label)
        hasThis |= containsArgLabel((Label) afc.actor());

      if (!hasThis && afc.granter() instanceof Label)
        hasThis |= containsArgLabel((Label) afc.actor());

      return hasThis;
    }
    return false;
  }

  @Override
  public boolean accessPolicyValid(ConfPolicy pol) throws SemanticException {
    if (pol instanceof ConfProjectionPolicy) {
      ConfProjectionPolicy_c cpp = (ConfProjectionPolicy_c) pol;
      return accessPolicyValid(cpp.label());
    } else if (pol instanceof JoinConfPolicy) {
      JoinConfPolicy jcp = (JoinConfPolicy) pol;
      for (ConfPolicy p : jcp.joinComponents())
        if (!accessPolicyValid(p)) return false;
    } else if (pol instanceof MeetConfPolicy) {
      MeetConfPolicy mcp = (MeetConfPolicy) pol;
      for (ConfPolicy p : mcp.meetComponents())
        if (!accessPolicyValid(p)) return false;
    } else if (pol instanceof ReaderPolicy) {
      ReaderPolicy rp = (ReaderPolicy) pol;
      return accessPolicyValid(rp.owner()) && accessPolicyValid(rp.reader());
    }
    // TODO: verify other cases are either impossible or valid.
    return false;
  }

  protected boolean accessPolicyValid(Label label) throws SemanticException {
    if (label instanceof MeetLabel) {
      MeetLabel ml = (MeetLabel) label;
      for (Label l : ml.meetComponents())
        if (!accessPolicyValid(l)) return false;
    } else if (label instanceof JoinLabel) {
      JoinLabel jl = (JoinLabel) label;
      for (Label l : jl.joinComponents())
        if (!accessPolicyValid(l)) return false;
    } else if (label instanceof PairLabel) {
      PairLabel pair = (PairLabel) label;
      return accessPolicyValid(pair.confPolicy());
    } else if (label instanceof ParamLabel || label instanceof ThisLabel
        || label instanceof ProviderLabel) {
      return true;
    }
    // TODO: verify other cases are either impossible or valid.
    return false;
  }

  protected boolean accessPolicyValid(Principal p) throws SemanticException {
    if (p instanceof ConjunctivePrincipal) {
      ConjunctivePrincipal cp = (ConjunctivePrincipal) p;
      for (Principal pp : cp.conjuncts())
        if (!accessPolicyValid(pp)) return false;
      return true;
    } else if (p instanceof DisjunctivePrincipal) {
      DisjunctivePrincipal cp = (DisjunctivePrincipal) p;
      for (Principal pp : cp.disjuncts())
        if (!accessPolicyValid(pp)) return false;
      return true;
    } else if (p instanceof ParamPrincipal || p instanceof ExternalPrincipal
        || p instanceof BottomPrincipal || p instanceof TopPrincipal) {
      return true;
    } else if (p instanceof DynamicPrincipal) {
      DynamicPrincipal dp = (DynamicPrincipal) p;
      if (dp.path() instanceof AccessPathStore) {
        AccessPathStore aps = (AccessPathStore) dp.path();
        // Store principals are the only dynamic principals
        // that can appear in access labels, but the path
        // must be a this path.
        return aps.path() instanceof AccessPathThis;
      }
    }
    return false;
  }

  // array type constructors ///////////////////////////////////////////////////

  @Override
  public FabricArrayType fabricArrayOf(Position pos, Type t) {
    return new FabricArrayType_c(this, pos, t, /* isConst */false,
        /* isNonConst */true, /* isNative */false);
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
      type = labeledType(pos, type,
          defaultSignature().defaultArrayBaseLabel(type));
    }

    return new FabricArrayType_c(this, pos, type, /* isConst */false,
        /* isNonConst */true, /* isNative */true);
  }

  @Override
  public String translateClass(Resolver c, ClassType t) {
    // Fully qualify classes in fabric.lang.security.
    if (t.package_() != null) {
      @SuppressWarnings("deprecation")
      Package createPackage = createPackage("fabric.lang.security");
      if (t.package_().equals(createPackage)) {
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

  @Override
  public Type typeForClass(Class<?> clazz) throws SemanticException {
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
            new fabric.util.HashMap._Impl(dest).fabric$util$HashMap$(
            /*
             * // XXX when HashMap becomes parameterized, these will be the
             * labels. , lbl, lbl.confPolicy()
             */
            );
        new_codebase = new Codebase._Impl(dest).fabric$lang$Codebase$(lbl,
            lbl.confPolicy(), classes);
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

  // The default publishing label is the maximum integrity the worker can claim
  protected fabric.lang.security.Label defaultPublishingLabel() {
    Store dest = extInfo.destinationStore();
    NodePrincipal w = Worker.getWorker().getPrincipal();
    NodePrincipal st = dest.getPrincipal();
    if (w != null && st != null) {
      // FIXME: default label should be *<-w,st
      // fabric.util.ArrayList writers = new fabric.util.ArrayList._Impl(dest);
      // writers.add(w);
      // writers.add(st);
      return LabelUtil._Impl.writerPolicyLabel(dest,
          Worker.getWorker().getLocalStore().getTopPrincipal(), st);
    }
    throw new InternalCompilerError("Error W: " + w + " ST: " + st);
  }

  @Override
  public fabric.lang.security.Label sourceUpdateLabel(CodebaseSource src) {
    if (src.namespace().equals(extInfo.localNamespace())
        && src.namespace().equals(src.canonicalNamespace()))
      return defaultPublishingLabel();
    else throw new InternalCompilerError("Re-linking not yet supported");
  }

  @Override
  public fabric.lang.security.ConfPolicy sourceAccessPolicy(
      CodebaseSource src) {
    // Re-use update label for access label. In general the store may allow
    // a more restricted access label, but we can only use the provider
    // label to statically figure out when we can fetch from the store vs.
    // using a replica.
    return sourceUpdateLabel(src).confPolicy();
  }

  @Override
  public ClassFileLazyClassInitializer classFileLazyClassInitializer(
      ClassFile clazz) {
//    throw new UnsupportedOperationException(
//        "Fabric doesn't support raw classes");
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
    throw toplevel_resolution_error();
  }

  @Override
  @Deprecated
  public boolean packageExists(String name) {
    throw toplevel_resolution_error();
  }

  // @Override
  // @Deprecated
  // public CBPackageContextResolver createPackageContextResolver(Package p) {
  // throw toplevel_resolution_error();
  // }

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

    return replaceProjections(
        pairLabel(L1.position(), join(cp1, cp2), meet(ip1, ip2)));
  }

  @Override
  public Label tmeet(Label L1, Label L2) {
    ConfPolicy cp1 = L1 == null ? null : L1.confProjection();
    ConfPolicy cp2 = L2 == null ? null : L2.confProjection();

    IntegPolicy ip1 = L1 == null ? null : L1.integProjection();
    IntegPolicy ip2 = L2 == null ? null : L2.integProjection();

    return replaceProjections(
        pairLabel(L1.position(), meet(cp1, cp2), join(ip1, ip2)));
  }

  @Override
  public boolean tleq(LabelEnv env, Label L1, Label L2) {
    ConfPolicy cp1 = L1 == null ? null : L1.confProjection();
    ConfPolicy cp2 = L2 == null ? null : L2.confProjection();

    IntegPolicy ip1 = L1 == null ? null : L1.integProjection();
    IntegPolicy ip2 = L2 == null ? null : L2.integProjection();

    return env.leq(cp1, cp2) && env.leq(ip2, ip1);
  }

  @Override
  public Label toLabel(ConfPolicy c) {
    if (c instanceof ConfProjectionPolicy_c) {
      ConfProjectionPolicy_c p = (ConfProjectionPolicy_c) c;
      return join(p.label(), noComponentsLabel());
    }
    return replaceProjections(
        pairLabel(c.position(), c, topIntegPolicy(c.position())));
  }

  @Override
  public Label replaceProjections(Label label) {
    LabelSubstitution ls = new LabelSubstitution() {

      @Override
      public Label substLabel(Label L) throws SemanticException {
        if (L instanceof PairLabel) {
          PairLabel pair = (PairLabel) L;
          ConfPolicy c = pair.confPolicy();
          IntegPolicy i = pair.integPolicy();

          Label conf = null, integ = null;
          if (c instanceof ConfProjectionPolicy_c) {
            ConfProjectionPolicy_c proj = (ConfProjectionPolicy_c) c;
            conf = join(proj.label().subst(this), noComponentsLabel());
          } else if (c instanceof JoinConfPolicy_c) {
            JoinConfPolicy_c jp = (JoinConfPolicy_c) c;
            Set<Label> lifted = new HashSet<>();
            Set<ConfPolicy> confpols = new HashSet<>();

            for (ConfPolicy cp : jp.joinComponents()) {
              if (cp instanceof ConfProjectionPolicy_c) {
                ConfProjectionPolicy_c cpproj = (ConfProjectionPolicy_c) cp;
                lifted
                    .add(join(cpproj.label().subst(this), noComponentsLabel()));
              } else confpols.add(cp);
            }
            ConfPolicy new_jp = joinConfPolicy(jp.position(), confpols);
            Label jplabel =
                pairLabel(jp.position(), new_jp, topIntegPolicy(jp.position()));
            lifted.add(jplabel);
            conf = joinLabel(L.position(), lifted);
          }

          // XXX: TODO: the dual replacements for integrity.
          if (i instanceof IntegProjectionPolicy_c) {
            IntegProjectionPolicy_c proj = (IntegProjectionPolicy_c) i;
            integ = meet(proj.label().subst(this), noComponentsLabel());
          }

          if (conf == null && integ == null)
            return L;
          else if (conf == null)
            conf = pairLabel(L.position(), c, topIntegPolicy(L.position()));
          else if (integ == null)
            integ = pairLabel(L.position(), bottomConfPolicy(L.position()), i);

          return join(conf, integ);
        }
        return L;
      }
    };
    try {
      return label.subst(ls);
    } catch (SemanticException e) {
      throw new InternalCompilerError("Unexpected semantic exception", e);
    }
  }

  @Override
  public AccessPolicyInstance accessPolicyInstance(Position pos,
      ParsedClassType ct, ConfPolicy policy) {
    return new AccessPolicyInstance_c(pos, ct, policy);
  }

  @Override
  public ConstructorInstance constructorInstance(Position pos,
      ClassType container, Flags flags, List<? extends Type> formalTypes,
      List<? extends Type> excTypes) {
    // XXX: I really don't know if this is the right values to be using for the
    // begin access and end confidentiality policies.
    return fabricConstructorInstance(pos, container, flags, unknownLabel(pos),
        false, noAccesses, true, unknownLabel(pos), false,
        noAccesses, true, formalTypes, Collections.<Label> emptyList(),
        excTypes, Collections.<Assertion> emptyList());
  }

  public FabricConstructorInstance fabricConstructorInstance(Position pos,
      ClassType container, Flags flags, Label startLabel,
      boolean isDefaultStartLabel, Label beginAccessLab,
      boolean isDefaultBeginAccess, Label returnLabel,
      boolean isDefaultReturnLabel, Label endAccessLab,
      boolean isDefaultEndAccess, List<? extends Type> formalTypes,
      List<Label> formalArgLabels, List<? extends Type> excTypes,
      List<Assertion> constraints) {
    FabricConstructorInstance ci =
        new FabricConstructorInstance_c(this, pos, container, flags, startLabel,
            isDefaultStartLabel, beginAccessLab, isDefaultBeginAccess,
            returnLabel, isDefaultReturnLabel, endAccessLab, isDefaultEndAccess,
            formalTypes, formalArgLabels, excTypes, constraints);
    return ci;
  }

  @Override
  public JifConstructorInstance jifConstructorInstance(Position pos,
      ClassType container, Flags flags, jif.types.label.Label startLabel,
      boolean isDefaultStartLabel, jif.types.label.Label returnLabel,
      boolean isDefaultReturnLabel, List<? extends Type> formalTypes,
      List<jif.types.label.Label> formalArgLabels,
      List<? extends Type> excTypes, List<Assertion> constraints) {
    // TODO: Not sure if I should put defaults here or unknownLabel calls.
    return fabricConstructorInstance(pos, container, flags, startLabel,
        isDefaultStartLabel, noAccesses, true, returnLabel,
        isDefaultReturnLabel, noAccesses, true, formalTypes,
        formalArgLabels, excTypes, constraints);
  }

  @Override
  public ConstructorInstance defaultConstructor(Position pos,
      ClassType container) {
    // This hardcodes the default begin access and end confidentiality policy.
    // TODO: Not sure if I should put defaults here or unknownLabel calls.
    assert_(container);
    return fabricConstructorInstance(pos, container, Public(), topLabel(), true,
        noAccesses, true, bottomLabel(), true, noAccesses, true,
        Collections.<Type> emptyList(), Collections.<Label> emptyList(),
        Collections.<Type> emptyList(), Collections.<Assertion> emptyList());
  }

  @Override
  public FabricMethodInstance fabricMethodInstance(Position pos,
      ReferenceType container, Flags flags, Type returnType, String name,
      Label startLabel, boolean isDefaultStartLabel, Label beginAccessLab,
      boolean isDefaultBeginAccess, List<? extends Type> formalTypes,
      List<jif.types.label.Label> formalArgLabels, Label endLabel,
      boolean isDefaultEndLabel, Label endAccessLab, boolean isDefaultEndAccess,
      List<? extends Type> excTypes, List<Assertion> constraints) {
    FabricMethodInstance mi = new FabricMethodInstance_c(this, pos, container,
        flags, returnType, name, startLabel, isDefaultStartLabel,
        beginAccessLab, isDefaultBeginAccess, formalTypes, formalArgLabels,
        endLabel, isDefaultEndLabel, endAccessLab, isDefaultEndAccess, excTypes,
        constraints);
    return mi;
  }

  @Override
  public JifMethodInstance jifMethodInstance(Position pos,
      ReferenceType container, Flags flags, Type returnType,
      java.lang.String name, jif.types.label.Label startLabel,
      boolean isDefaultStartLabel, List<? extends Type> formalTypes,
      List<jif.types.label.Label> formalArgLabels,
      jif.types.label.Label endLabel, boolean isDefaultEndLabel,
      List<? extends Type> excTypes, List<Assertion> constraints) {
    // TODO: Not sure if I should put defaults here or unknownLabel calls.
    return fabricMethodInstance(pos, container, flags, returnType, name,
        startLabel, isDefaultStartLabel, noAccesses, true, formalTypes,
        formalArgLabels, endLabel, isDefaultEndLabel, noAccesses, true,
        excTypes, constraints);
  }

  @Override
  public MethodInstance methodInstance(Position pos, ReferenceType container,
      Flags flags, Type returnType, java.lang.String name,
      List<? extends Type> formalTypes, List<? extends Type> excTypes) {
    // TODO: Not sure if I should put defaults here or unknownLabel calls.
    return fabricMethodInstance(pos, container, flags, returnType, name,
        unknownLabel(pos), false, noAccesses, true, formalTypes,
        Collections.<Label> emptyList(), unknownLabel(pos), false,
        noAccesses, true, excTypes,
        Collections.<Assertion> emptyList());
  }

  @Override
  public FabricPathMap pathMap() {
    return new FabricPathMap(this);
  }

  @Override
  public FabricPathMap pathMap(Path path, jif.types.label.Label L) {
    FabricPathMap m = pathMap();
    return m.set(path, L);
  }

  @Override
  public Label readConflict(Label l) {
    // CL(read l) = WritersToReaders(l)
    return pairLabel(l.position(),
        confProjection(writersToReadersLabel(l.position(), l)),
        topIntegPolicy(l.position()));
  }

  @Override
  public Label writeConflict(Label l) {
    // CL(write l) = WritersToReaders(l) meet C(l)
    Label conf = pairLabel(l.position(), confProjection(l),
        topIntegPolicy(l.position()));
    return pairLabel(l.position(),
        confProjection(meet(writersToReadersLabel(l.position(), l), conf)),
        topIntegPolicy(l.position()));
  }

  @Override
  public LabelToJavaExpr writersToReadersTranslator() {
    return new FabricWritersToReadersLabelToFabilExpr_c();
  }

  /** Label representing that no accesses have been made. */
  protected Label noAccesses = new NoAccesses_c(this, Position.compilerGenerated());

  @Override
  public Label noAccesses() {
    return noAccesses;
  }

  @Override
  public Label meet(Label L1, Label L2) {
    if (L1 instanceof NoAccesses) {
      return L2.simplify();
    }
    if (L2 instanceof NoAccesses) {
      return L1.simplify();
    }
    return super.meet(L1, L2);
  }

  protected AccessPathField accessPathField(AccessPath path, FieldInstance fi,
      java.lang.String fieldName, Position pos) {
    return new FabricAccessPathField(path, fi, fieldName, pos);
  }
}
