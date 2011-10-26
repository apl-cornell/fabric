package fabric.ast;

import jif.ast.JifExtFactory_c;
import jif.ast.Jif_c;
import jif.extension.JifBinaryExt;
import jif.extension.JifCastExt;
import jif.extension.JifConstructorDeclExt;
import jif.extension.JifFieldDeclExt_c;
import jif.extension.JifLabelExprExt;
import jif.extension.JifSourceFileExt;
import jif.translate.FieldToJavaExt_c;
import polyglot.ast.Ext;
import fabric.extension.AbortJifExt_c;
import fabric.extension.AtomicJifExt_c;
import fabric.extension.CallJifExt_c;
import fabric.extension.ClassBodyJifExt_c;
import fabric.extension.FabricClassDeclExt;
import fabric.extension.FabricFieldExt;
import fabric.extension.MethodDeclJifExt;
import fabric.extension.NewJifExt_c;
import fabric.extension.RemoteWorkerGetterJifExt_c;
import fabric.extension.RetryJifExt_c;
import fabric.extension.WorkerJifExt_c;
import fabric.translate.AbortToFabilExt_c;
import fabric.translate.AtomicToFabilExt_c;
import fabric.translate.BinaryToFabilExt_c;
import fabric.translate.CallToFabilExt_c;
import fabric.translate.CastToFabilExt_c;
import fabric.translate.ClassBodyToFabilExt_c;
import fabric.translate.ClassDeclToFabilExt_c;
import fabric.translate.CodebaseDeclToFabilExt_c;
import fabric.translate.CodebaseNodeToFabilExt_c;
import fabric.translate.ConstructorDeclToFabilExt_c;
import fabric.translate.FieldDeclToFabilExt_c;
import fabric.translate.MethodDeclToFabilExt_c;
import fabric.translate.NewFabricArrayToFabilExt_c;
import fabric.translate.NewLabelToFabilExt_c;
import fabric.translate.NewToFabilExt_c;
import fabric.translate.PackageNodeToFabilExt_c;
import fabric.translate.RemoteWorkerGetterToFabilExt_c;
import fabric.translate.RetryToFabilExt_c;
import fabric.translate.SourceFileToFabilExt_c;
import fabric.translate.WorkerToFabilExt_c;

/**
 * This class extends the Jif Extension factory to provide Jif extension objects
 * for atomic sections.
 */
public class FabricJifExtFactory_c extends JifExtFactory_c implements FabricExtFactory {
  //////////////////////////////////////////////////////////////////////////////
  // overridden Jif AST nodes (TODO: should be ext.del's?)                    //
  //////////////////////////////////////////////////////////////////////////////
  
  public FabricJifExtFactory_c() {
    super();
  }
  
  public FabricJifExtFactory_c(FabricExtFactory next) {
    super(next);
  }

  @Override
  public Ext extBinaryImpl() {
    return new JifBinaryExt(new BinaryToFabilExt_c());
  }
  
  @Override
  public Ext extCallImpl() {
    return new CallJifExt_c(new CallToFabilExt_c());
  }
  
  @Override
  public Ext extCastImpl() {
    return new JifCastExt(new CastToFabilExt_c());
  }
  
  @Override
  public Ext extClassBodyImpl() {
    return new ClassBodyJifExt_c(new ClassBodyToFabilExt_c());
  }
  
  @Override
  public Ext extClassDeclImpl() {
    return new FabricClassDeclExt(new ClassDeclToFabilExt_c());
  }
  
  @Override
  protected Ext extFieldDeclImpl() {
    return new JifFieldDeclExt_c(new FieldDeclToFabilExt_c());
  }

  @Override
  protected Ext extFieldImpl() {
    return new FabricFieldExt(new FieldToJavaExt_c());
  }

  @Override
  public Ext extMethodDeclImpl() {
    return new MethodDeclJifExt(new MethodDeclToFabilExt_c());
  }

  @Override
  public Ext extConstructorDeclImpl() {
    return new JifConstructorDeclExt(new ConstructorDeclToFabilExt_c());
  }
  
  @Override
  public Ext extNewImpl() {
    return new NewJifExt_c(new NewToFabilExt_c());
  }

  @Override
  public Ext extNewLabelImpl() {
    return new JifLabelExprExt(new NewLabelToFabilExt_c());
  }

  @Override
  protected Ext extPackageNodeImpl() {
    return new Jif_c(new PackageNodeToFabilExt_c());
  }  
  
  @Override
  protected Ext extSourceFileImpl() {
    return new JifSourceFileExt(new SourceFileToFabilExt_c());
  }
  //////////////////////////////////////////////////////////////////////////////
  // new Fabric AST nodes                                                     //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  public Ext extRemoteWorkerGetter() {
    return new RemoteWorkerGetterJifExt_c(new RemoteWorkerGetterToFabilExt_c());
  }

  @Override
  public Ext extRetryStmt() {
    return new RetryJifExt_c(new RetryToFabilExt_c());
  }
  
  @Override
  public Ext extAbortStmt() {
    return new AbortJifExt_c(new AbortToFabilExt_c());
  }
  
  @Override
  public Ext extAtomic() {
    return new AtomicJifExt_c(new AtomicToFabilExt_c());
  }
  
  @Override
  public Ext extWorker() {
    return new WorkerJifExt_c(new WorkerToFabilExt_c());
  }
  
  @Override
  public Ext extFabricArrayInit() {
    return extArrayInit();
  }
  
  @Override
  public Ext extFabricArrayTypeNode() {
    return extArrayTypeNode();
  }
  
  @Override
  public Ext extNewFabricArray() {
    return new NewFabricArrayJifExt_c(new NewFabricArrayToFabilExt_c());
  }
  
  @Override
  public Ext extAmbNewFabricArray() {
    return extAmbNewArray(); 
  }

  @Override
  public Ext extCodebaseNode() {
    return new Jif_c(new CodebaseNodeToFabilExt_c());
  }

  @Override
  public Ext extCodebaseDecl() {
    return new Jif_c(new CodebaseDeclToFabilExt_c());
  }
  
}
