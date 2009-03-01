package fabric.types;

import java.util.List;
import java.util.Map;

import polyglot.ext.param.types.Subst;
import polyglot.frontend.Source;
import polyglot.types.ClassType;
import polyglot.types.Context;
import polyglot.types.Flags;
import polyglot.types.ImportTable;
import polyglot.types.LazyClassInitializer;
import polyglot.types.LocalInstance;
import polyglot.types.Package;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

import fabil.types.FabILImportTable;
import fabric.translate.DynamicPrincipalToFabilExpr_c;
import jif.ast.JifUtil;
import jif.translate.PrincipalToJavaExpr;
import jif.types.JifLocalInstance;
import jif.types.JifSubst_c;
import jif.types.JifTypeSystem_c;
import jif.types.Solver;
import jif.types.label.VarLabel;
import jif.types.principal.Principal;

public class FabricTypeSystem_c extends JifTypeSystem_c implements FabricTypeSystem {

  public ClassType FObject() {
    return load("fabric.lang.Object");
  }
  
  public FabricTypeSystem_c(TypeSystem jlts) {
    super(jlts);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List defaultPackageImports() {
    // Include fabric.lang and fabric.client as default imports.
    List<String> result = super.defaultPackageImports();
    result.add("fabric.lang");
    result.add("fabric.client");
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

  public ClassType RemoteClient() {
    return load("fabric.client.RemoteClient");
  }
  
  public ClassType Client() {
    return load("fabric.client.FabricClient");
  }
  
  public ClassType Core() {
    return load("fabric.client.Core");
  }

  private JifLocalInstance clientLocalInstance = null;
  
  public LocalInstance clientLocalInstance() {
    if (clientLocalInstance == null) {
      // Always use the same local instance, because jif now use pointer identity to compare local instances
      // for the purpose of label checking.
      clientLocalInstance = (JifLocalInstance)localInstance(Position.compilerGenerated(), 
                                                            Flags.FINAL, Client(), "client$");
      clientLocalInstance.setLabel(freshLabelVariable(clientLocalInstance.position(), "client$", "client$"));
    }
    return clientLocalInstance;
  }
  
  public Principal clientPrincipal(Position pos) {
//    return dynamicPrincipal(pos, new AccessPathClient(pos, this));
    try {
      return dynamicPrincipal(pos, JifUtil.varInstanceToAccessPath(clientLocalInstance(), pos));
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

    if ((equals(strpFromType, Client()) || equals(strpFromType, RemoteClient())) 
        && equals(strpToType, Principal())) {
      return true;
    }
    
    return super.isCastValid(fromType, toType);
  }
  
  @Override
  public boolean isImplicitCastValid(Type fromType, Type toType) {
    Type strpFromType = strip(fromType);
    Type strpToType = strip(toType);

    if ((equals(strpFromType, Client()) || equals(strpFromType, RemoteClient())) 
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
  
  @Override
  public Subst subst(Map substMap, Map cache) {
    return new FabricSubst_c(this, substMap, cache);
  }
}
