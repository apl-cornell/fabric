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
import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import fabil.types.FabILImportTable;
import fabric.translate.DynamicPrincipalToFabilExpr_c;
import fabric.translate.FabricPairLabelToFabilExpr_c;

public class FabricTypeSystem_c extends JifTypeSystem_c implements FabricTypeSystem {

  public ClassType FObject() {
    return load("fabric.lang.Object");
  }
  
  public FabricTypeSystem_c(TypeSystem jlts) {
    super(jlts);
  }

  @Override
  public String PrincipalClassName() {
    return "fabric.lang.Principal";
  }

  @SuppressWarnings("unchecked")
  @Override
  public List defaultPackageImports() {
    // Include fabric.lang and fabric.worker as default imports.
    List<String> result = super.defaultPackageImports();
    result.add("fabric.lang");
    result.add("fabric.worker");
    return result;
  }
  
  @Override
  public ImportTable importTable(Package pkg) {
    // the FabILImportTable works around the ambiguous import of
    // java.lang.Object and fabric.lang.Object 
    return new FabILImportTable(this, pkg);
  }

  @Override
  public ImportTable importTable(String sourcename, Package pkg) {
    // the FabILImportTable works around the ambiguous import of
    // java.lang.Object and fabric.lang.Object 
    return new FabILImportTable(this, pkg, sourcename);
  }

  @Override
  public ParsedClassType createClassType(LazyClassInitializer init,
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
  
  @SuppressWarnings("unchecked")
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
  
  @SuppressWarnings("unchecked")
  public ConfPolicy representableConfProjection(Label L) {
    if (L instanceof ArgLabel) {
      return super.confProjection(((ArgLabel) L).upperBound());
    }
    else if (L instanceof MeetLabel) {
      Set<ConfPolicy> confPols = new HashSet<ConfPolicy>();
      for (Label l : (Collection<Label>)((MeetLabel)L).meetComponents()) {
        confPols.add(representableConfProjection(l));
      }
      return meetConfPolicy(L.position(), confPols);
    }
    else if (L instanceof JoinLabel) {
      Set<ConfPolicy> confPols = new HashSet<ConfPolicy>();
      for (Label l : (Collection<Label>)((JoinLabel)L).joinComponents()) {
        confPols.add(representableConfProjection(l));
      }
      return joinConfPolicy(L.position(), confPols);
    }
    return super.confProjection(L);
  }
  
  @SuppressWarnings("unchecked")
  public IntegPolicy representableIntegProjection(Label L) {
    if (L instanceof ArgLabel) {
      return super.integProjection(((ArgLabel) L).upperBound());
    }
    else if (L instanceof MeetLabel) {
      Set<IntegPolicy> integPols = new HashSet<IntegPolicy>();
      for (Label l : (Collection<Label>)((MeetLabel)L).meetComponents()) {
        integPols.add(representableIntegProjection(l));
      }
      return meetIntegPolicy(L.position(), integPols);
    }
    else if (L instanceof JoinLabel) {
      Set<IntegPolicy> integPols = new HashSet<IntegPolicy>();
      for (Label l : (Collection<Label>)((JoinLabel)L).joinComponents()) {
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
      for (Label l : (Collection<Label>)ml.meetComponents()) {
        if (containsThisLabel(l)) return true;
      }
    }
    else if (label instanceof JoinLabel) {
      JoinLabel jl = (JoinLabel)label;
      for (Label l : (Collection<Label>)jl.joinComponents()) {
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
}
