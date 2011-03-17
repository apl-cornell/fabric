package fabric.types;

import java.util.*;

import jif.ast.JifUtil;
import jif.translate.LabelToJavaExpr;
import jif.translate.PrincipalToJavaExpr;
import jif.types.*;
import jif.types.label.*;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;
import polyglot.ext.param.types.Subst;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import fabil.frontend.CodebaseSource;
import fabil.types.*;
import fabric.FabricOptions;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.translate.DynamicPrincipalToFabilExpr_c;
import fabric.translate.FabricPairLabelToFabilExpr_c;
import fabric.translate.ProviderLabelToFabilExpr_c;

public class FabricTypeSystem_c extends JifTypeSystem_c implements FabricTypeSystem {

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
    return new CodebasePackageContextResolver(this, (CodebasePackage) p);
  }

  @Override
  public CodebasePackage createPackage(Package prefix, String name) {
    return new CodebasePackage_c(this, prefix, name);
  }

  public ClassType FObject() {
    return load("fabric.lang.Object");
  }
  
  public FabricTypeSystem_c(TypeSystem jlts) {
    super(jlts);
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
  public ImportTable importTable(Package pkg) {
    throw new UnsupportedOperationException("Import table must be associated with a source");
  }
  
  @Override
  public ImportTable importTable(String sourceName, Package pkg) {
    throw new UnsupportedOperationException("Import table must be associated with a source");
  }

  public CodebaseImportTable importTable(CodebaseSource source, Package pkg) {
    return new CodebaseImportTable_c(this, pkg, source);
  }

  @Override
  public CodebaseClassType createClassType(LazyClassInitializer init,
      Source fromSource) {
    return new FabricParsedClassType_c(this, init, fromSource);
  }

  public ClassType RemoteWorker() {
    return load("fabric.worker.RemoteWorker");
  }
  
  public ClassType Worker() {
    return load("fabric.worker.FabricWorker");
  }
  
  public ClassType Store() {
    return load("fabric.worker.Store");
  }
  
  public ClassType DelegatingPrincipal() {
    return load("fabric.lang.security.DelegatingPrincipal");
  }  

  private JifLocalInstance workerLocalInstance = null;
  
  public JifLocalInstance workerLocalInstance() {
    if (workerLocalInstance == null) {
      // Always use the same local instance, because jif now use pointer identity to compare local instances
      // for the purpose of label checking.
      workerLocalInstance = (JifLocalInstance)localInstance(Position.compilerGenerated(), 
                                                            Flags.FINAL, Worker(), "worker$");
//      workerLocalInstance.setLabel(freshLabelVariable(workerLocalInstance.position(), "worker$", "worker$"));
    }
    return workerLocalInstance;
  }
  
  public Principal workerPrincipal(Position pos) {
//    return dynamicPrincipal(pos, new AccessPathWorker(pos, this));
    try {
      JifLocalInstance li = workerLocalInstance();
      Principal p = dynamicPrincipal(pos, JifUtil.varInstanceToAccessPath(li, pos));
      li.setLabel(pairLabel(li.position(), 
                  readerPolicy(li.position(), p, bottomPrincipal(li.position())), 
                  writerPolicy(li.position(), p, p)));
      return p;
    }
    catch (SemanticException e) {
      // shouldn't happen
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public boolean isCastValid(Type fromType, Type toType) {
    Type strpFromType = strip(fromType);
    Type strpToType = strip(toType);

    if ((equals(strpFromType, Worker()) || equals(strpFromType, RemoteWorker()) || equals(strpFromType, Store())) 
        && equals(strpToType, Principal())) {
      return true;
    }
    
    return super.isCastValid(fromType, toType);
  }
  
  @Override
  public boolean isImplicitCastValid(Type fromType, Type toType) {
    Type strpFromType = strip(fromType);
    Type strpToType = strip(toType);

    if ((equals(strpFromType, Worker()) || equals(strpFromType, RemoteWorker()) || equals(strpFromType, Store())) 
        && equals(strpToType, Principal())) {
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
        new FabricDynamicPrincipal_c(path, this, pos, dynamicPrincipalTranslator());
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
        new FabricDynamicPrincipal_c(path, this, pos, dynamicPrincipalTranslator());
    return t;
  }
  
  public boolean isLocalWorkerAccessPath(AccessPath ap) {
    return ap instanceof AccessPathLocal && ((AccessPathLocal)ap).localInstance() == workerLocalInstance();
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

  public ConfPolicy representableConfProjection(Label L) {
    if (L instanceof ArgLabel) {
      return super.confProjection(((ArgLabel) L).upperBound());
    }
    else if (L instanceof MeetLabel) {
      Set<ConfPolicy> confPols = new HashSet<ConfPolicy>();
      for (Label l : ((MeetLabel)L).meetComponents()) {
        confPols.add(representableConfProjection(l));
      }
      return meetConfPolicy(L.position(), confPols);
    }
    else if (L instanceof JoinLabel) {
      Set<ConfPolicy> confPols = new HashSet<ConfPolicy>();
      for (Label l : ((JoinLabel)L).joinComponents()) {
        confPols.add(representableConfProjection(l));
      }
      return joinConfPolicy(L.position(), confPols);
    }
    return super.confProjection(L);
  }
  
  public IntegPolicy representableIntegProjection(Label L) {
    if (L instanceof ArgLabel) {
      return super.integProjection(((ArgLabel) L).upperBound());
    }
    else if (L instanceof MeetLabel) {
      Set<IntegPolicy> integPols = new HashSet<IntegPolicy>();
      for (Label l : ((MeetLabel)L).meetComponents()) {
        integPols.add(representableIntegProjection(l));
      }
      return meetIntegPolicy(L.position(), integPols);
    }
    else if (L instanceof JoinLabel) {
      Set<IntegPolicy> integPols = new HashSet<IntegPolicy>();
      for (Label l : ((JoinLabel)L).joinComponents()) {
        integPols.add(representableIntegProjection(l));
      }
      return joinIntegPolicy(L.position(), integPols);
    }
    return super.integProjection(L);
  }
  
  public boolean isFabricClass(Type type) {
    if (type instanceof ClassType) {
      ClassType ct = (ClassType)type;
      
      // XXX Interfaces are excluded for now.
      if (ct.flags().isInterface()) return false;
      
      while (ct != null) {
        if (typeEquals(ct, FObject())) {
          return true;
        }
        ct = (ClassType)ct.superType();
      }
    }
    return false;
  }
  
  public boolean isFabricArray(Type t) {
    // unwrap label
    if (t instanceof LabeledType)
      t = ((LabeledType) t).typePart();
    
    if (t instanceof FabricArrayType)
      return !((FabricArrayType) t).isNative();
    
    return false;
  }
  
  public FabricArrayType toFabricArray(Type t) {
    if (t instanceof LabeledType)
      t = ((LabeledType) t).typePart();
    
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
  
  public boolean containsThisLabel(Label label) {
    if (label instanceof ThisLabel) {
      return true;
    }
    else if (label instanceof MeetLabel) {
      MeetLabel ml = (MeetLabel)label;
      for (Label l : ml.meetComponents()) {
        if (containsThisLabel(l)) return true;
      }
    }
    else if (label instanceof JoinLabel) {
      JoinLabel jl = (JoinLabel)label;
      for (Label l : jl.joinComponents()) {
        if (containsThisLabel(l)) return true;
      }
    }
    
    return false;
  }

  // array type constructors ///////////////////////////////////////////////////
  
  public FabricArrayType fabricArrayOf(Position pos, Type t) {
    return new FabricArrayType_c(this, pos, t,
                                 /* isConst */ false, /* isNonConst */ true,
                                 /* isNative */ false);
  }

  public FabricArrayType fabricArrayOf(Position pos, Type t, int dims) {
    if (dims == 1)
      return fabricArrayOf(pos, t);
    else
      return fabricArrayOf(pos, fabricArrayOf(pos, t, dims - 1));
  }
  
  @Override
  protected FabricArrayType arrayType(Position pos, Type type) {
    if (!isLabeled(type)) {
      type = labeledType(pos, type, defaultSignature().defaultArrayBaseLabel(type));
    }
    
    return new FabricArrayType_c(this, pos, type,
                                 /*isConst */ false, /*isNonConst*/ true,
                                 /*isNative*/ true);
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
  
  public boolean isPlatformType(Named name) {
    return isPlatformType(name.fullName());
  }
  
  public boolean isPlatformType(String fullName) {
    FabricOptions opt = (FabricOptions) extInfo.getOptions();
    if(!opt.runWorker()) {
      return true;
    }
    return fullName.startsWith("java")
    || fullName.startsWith("fabric")
    || fullName.startsWith("jif");
  }

  public String absoluteName(Codebase context, String fullName, boolean resolve) throws SemanticException {
    if(!isPlatformType(fullName)) {
      if(resolve && context != null) {
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
