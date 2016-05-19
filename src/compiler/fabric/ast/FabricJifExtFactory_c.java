package fabric.ast;

import fabric.extension.AbortJifExt_c;
import fabric.extension.AtomicJifExt_c;
import fabric.extension.CallJifExt_c;
import fabric.extension.ClassBodyJifExt_c;
import fabric.extension.ConstructorDeclJifExt;
import fabric.extension.FabricArrayAccessAssignExt;
import fabric.extension.FabricArrayAccessExt;
import fabric.extension.FabricBinaryExt;
import fabric.extension.FabricBranchExt;
import fabric.extension.FabricCastExt;
import fabric.extension.FabricClassDeclExt;
import fabric.extension.FabricConditionalExt;
import fabric.extension.FabricDoExt;
import fabric.extension.FabricFieldAssignExt;
import fabric.extension.FabricFieldExt;
import fabric.extension.FabricForExt;
import fabric.extension.FabricIfExt;
import fabric.extension.FabricInstanceofExt;
import fabric.extension.FabricLabeledExt;
import fabric.extension.FabricSwitchExt;
import fabric.extension.FabricTryExt;
import fabric.extension.FabricWhileExt;
import fabric.extension.MethodDeclJifExt;
import fabric.extension.NewJifExt_c;
import fabric.extension.RemoteWorkerGetterJifExt_c;
import fabric.extension.RetryJifExt_c;
import fabric.extension.StageJifExt_c;
import fabric.extension.StoreJifExt_c;
import fabric.extension.StoreToFabilExt_c;
import fabric.extension.WorkerJifExt_c;
import fabric.translate.AbortToFabilExt_c;
import fabric.translate.ArrayAccessToFabilExt_c;
import fabric.translate.AtomicToFabilExt_c;
import fabric.translate.BinaryToFabilExt_c;
import fabric.translate.CallToFabilExt_c;
import fabric.translate.CastToFabilExt_c;
import fabric.translate.CatchToFabilExt_c;
import fabric.translate.ClassBodyToFabilExt_c;
import fabric.translate.ClassDeclToFabilExt_c;
import fabric.translate.CodebaseDeclToFabilExt_c;
import fabric.translate.CodebaseNodeToFabilExt_c;
import fabric.translate.ConditionalToFabilExt_c;
import fabric.translate.ConstructorDeclToFabilExt_c;
import fabric.translate.FieldDeclToFabilExt_c;
import fabric.translate.FieldToFabilExt_c;
import fabric.translate.ForToFabilExt_c;
import fabric.translate.IfToFabilExt_c;
import fabric.translate.InstanceOfToFabilExt_c;
import fabric.translate.MethodDeclToFabilExt_c;
import fabric.translate.NewFabricArrayToFabilExt_c;
import fabric.translate.NewLabelToFabilExt_c;
import fabric.translate.NewToFabilExt_c;
import fabric.translate.PackageNodeToFabilExt_c;
import fabric.translate.PrincipalExprToFabilExt_c;
import fabric.translate.RemoteWorkerGetterToFabilExt_c;
import fabric.translate.RetryToFabilExt_c;
import fabric.translate.SourceFileToFabilExt_c;
import fabric.translate.StageToFabilExt_c;
import fabric.translate.TryToFabilExt_c;
import fabric.translate.WhileToFabilExt_c;
import fabric.translate.WorkerToFabilExt_c;

import jif.ast.JifExtFactory_c;
import jif.ast.JifExt_c;
import jif.extension.JifFieldDeclExt_c;
import jif.extension.JifLabelExprExt;
import jif.extension.JifPrincipalExprExt;
import jif.extension.JifSourceFileExt;
import jif.translate.ArrayAccessAssignToJavaExt_c;
import jif.translate.BranchToJavaExt_c;
import jif.translate.DoToJavaExt_c;
import jif.translate.FieldAssignToJavaExt_c;
import jif.translate.LabeledToJavaExt_c;
import jif.translate.SwitchToJavaExt_c;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;

/**
 * This class extends the Jif Extension factory to provide Jif extension objects
 * for atomic sections.
 */
public class FabricJifExtFactory_c extends JifExtFactory_c implements
FabricExtFactory {
  // ////////////////////////////////////////////////////////////////////////////
  // overridden Jif AST nodes (TODO: should be ext.del's?) //
  // ////////////////////////////////////////////////////////////////////////////

  public FabricJifExtFactory_c() {
    super();
  }

  public FabricJifExtFactory_c(FabricExtFactory next) {
    super(next);
  }

  @Override
  protected Ext extBinaryImpl() {
    return new FabricBinaryExt(new BinaryToFabilExt_c());
  }

  @Override
  protected Ext extCallImpl() {
    return new CallJifExt_c(new CallToFabilExt_c());
  }

  @Override
  protected Ext extCastImpl() {
    return new FabricCastExt(new CastToFabilExt_c());
  }

  @Override
  protected Ext extClassBodyImpl() {
    return new ClassBodyJifExt_c(new ClassBodyToFabilExt_c());
  }

  @Override
  protected Ext extClassDeclImpl() {
    return new FabricClassDeclExt(new ClassDeclToFabilExt_c());
  }

  @Override
  protected Ext extFieldDeclImpl() {
    return new JifFieldDeclExt_c(new FieldDeclToFabilExt_c());
  }

  @Override
  protected Ext extFieldImpl() {
    return new FabricFieldExt(new FieldToFabilExt_c());
  }

  @Override
  protected Ext extArrayAccessImpl() {
    return new FabricArrayAccessExt(new ArrayAccessToFabilExt_c());
  }

  @Override
  protected Ext extArrayAccessAssignImpl() {
    return new FabricArrayAccessAssignExt(new ArrayAccessAssignToJavaExt_c());
  }

  @Override
  protected Ext extMethodDeclImpl() {
    return new MethodDeclJifExt(new MethodDeclToFabilExt_c());
  }

  @Override
  protected Ext extConstructorDeclImpl() {
    return new ConstructorDeclJifExt(new ConstructorDeclToFabilExt_c());
  }

  @Override
  protected Ext extNewImpl() {
    return new NewJifExt_c(new NewToFabilExt_c());
  }

  @Override
  protected Ext extNewLabelImpl() {
    return new JifLabelExprExt(new NewLabelToFabilExt_c());
  }

  @Override
  protected Ext extPrincipalExprImpl() {
    return new JifPrincipalExprExt(new PrincipalExprToFabilExt_c());
  }

  @Override
  protected Ext extPackageNodeImpl() {
    return new JifExt_c(new PackageNodeToFabilExt_c());
  }

  @Override
  protected Ext extSourceFileImpl() {
    return new JifSourceFileExt(new SourceFileToFabilExt_c());
  }

  @Override
  protected Ext extInstanceofImpl() {
    return new FabricInstanceofExt(new InstanceOfToFabilExt_c());
  }

  // ////////////////////////////////////////////////////////////////////////////
  // new Fabric AST nodes //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public final Ext extRemoteWorkerGetter() {
    Ext e = extRemoteWorkerGetterImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extRemoteWorkerGetter();
      e = composeExts(e, e2);
    }
    return postExtRemoteWorkerGetter(e);
  }

  protected Ext extRemoteWorkerGetterImpl() {
    return new RemoteWorkerGetterJifExt_c(new RemoteWorkerGetterToFabilExt_c());
  }

  protected Ext postExtRemoteWorkerGetter(Ext e) {
    return postExtExpr(e);
  }

  @Override
  public final Ext extRetryStmt() {
    Ext e = extRetryStmtImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extRetryStmt();
      e = composeExts(e, e2);
    }
    return postExtRetryStmt(e);
  }

  protected Ext extRetryStmtImpl() {
    return new RetryJifExt_c(new RetryToFabilExt_c());
  }

  protected Ext postExtRetryStmt(Ext e) {
    return postExtBranch(e);
  }

  @Override
  public final Ext extAbortStmt() {
    Ext e = extAbortStmtImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extAbortStmt();
      e = composeExts(e, e2);
    }
    return postExtAbortStmt(e);
  }

  protected Ext extAbortStmtImpl() {
    return new AbortJifExt_c(new AbortToFabilExt_c());
  }

  protected Ext postExtAbortStmt(Ext e) {
    return postExtBranch(e);
  }

  @Override
  public final Ext extStageStmt() {
    Ext e = extStageStmtImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extStageStmt();
      e = composeExts(e, e2);
    }
    return postExtStageStmt(e);
  }

  protected Ext extStageStmtImpl() {
    return new StageJifExt_c(new StageToFabilExt_c());
  }

  protected Ext postExtStageStmt(Ext e) {
    return postExtBranch(e);
  }

  @Override
  public final Ext extAtomic() {
    Ext e = extAtomicImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extAtomic();
      e = composeExts(e, e2);
    }
    return postExtAtomic(e);
  }

  protected Ext extAtomicImpl() {
    return new AtomicJifExt_c(new AtomicToFabilExt_c());
  }

  protected Ext postExtAtomic(Ext e) {
    return postExtBlock(e);
  }

  @Override
  public final Ext extWorker() {
    Ext e = extWorkerImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extWorker();
      e = composeExts(e, e2);
    }
    return postExtWorker(e);
  }

  protected Ext extWorkerImpl() {
    return new WorkerJifExt_c(new WorkerToFabilExt_c());
  }

  protected Ext postExtWorker(Ext e) {
    return postExtLocal(e);
  }

  @Override
  public final Ext extStore() {
    Ext e = extStoreImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extStore();
      e = composeExts(e, e2);
    }
    return postExtStore(e);
  }

  protected Ext extStoreImpl() {
    return new StoreJifExt_c(new StoreToFabilExt_c());
  }

  protected Ext postExtStore(Ext e) {
    return postExtExpr(e);
  }

  @Override
  public final Ext extAccessPolicy() {
    Ext e = extAccessPolicyImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extAccessPolicy();
      e = composeExts(e, e2);
    }
    return postExtAccessPolicy(e);
  }

  protected Ext extAccessPolicyImpl() {
    return new AccessPolicyJifExt_c(new CannotAccessPolicyToFabilExt_c());
  }

  protected Ext postExtAccessPolicy(Ext e) {
    return postExtTerm(e);
  }

  @Override
  public final Ext extFabricArrayInit() {
    Ext e = extFabricArrayInitImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extFabricArrayInit();
      e = composeExts(e, e2);
    }
    return postExtFabricArrayInit(e);
  }

  protected Ext extFabricArrayInitImpl() {
    return extArrayInit();
  }

  protected Ext postExtFabricArrayInit(Ext e) {
    return postExtArrayInit(e);
  }

  @Override
  public final Ext extFabricArrayTypeNode() {
    Ext e = extFabricArrayTypeNodeImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extFabricArrayTypeNode();
      e = composeExts(e, e2);
    }
    return postExtFabricArrayTypeNode(e);
  }

  protected Ext extFabricArrayTypeNodeImpl() {
    return extArrayTypeNode();
  }

  protected Ext postExtFabricArrayTypeNode(Ext e) {
    return postExtArrayTypeNode(e);
  }

  @Override
  public final Ext extNewFabricArray() {
    Ext e = extNewFabricArrayImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extNewFabricArray();
      e = composeExts(e, e2);
    }
    return postExtNewFabricArray(e);
  }

  protected Ext extNewFabricArrayImpl() {
    return new NewFabricArrayJifExt_c(new NewFabricArrayToFabilExt_c());
  }

  protected Ext postExtNewFabricArray(Ext e) {
    return postExtNewArray(e);
  }

  @Override
  public final Ext extAmbNewFabricArray() {
    Ext e = extAmbNewFabricArrayImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extAmbNewFabricArray();
      e = composeExts(e, e2);
    }
    return postExtAmbNewFabricArray(e);
  }

  protected Ext extAmbNewFabricArrayImpl() {
    return extAmbNewArray();
  }

  protected Ext postExtAmbNewFabricArray(Ext e) {
    return postExtNewArray(e);
  }

  @Override
  public final Ext extCodebaseNode() {
    Ext e = extCodebaseNodeImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extCodebaseNode();
      e = composeExts(e, e2);
    }
    return postExtCodebaseNode(e);
  }

  protected Ext extCodebaseNodeImpl() {
    return new JifExt_c(new CodebaseNodeToFabilExt_c());
  }

  protected Ext postExtCodebaseNode(Ext e) {
    return postExtPackageNode(e);
  }

  @Override
  public final Ext extCodebaseDecl() {
    Ext e = extCodebaseDeclImpl();

    ExtFactory nextEF = nextExtFactory();
    if (nextEF instanceof FabricExtFactory) {
      Ext e2 = ((FabricExtFactory) nextEF).extCodebaseDecl();
      e = composeExts(e, e2);
    }
    return postExtCodebaseDecl(e);
  }

  protected Ext extCodebaseDeclImpl() {
    return new JifExt_c(new CodebaseDeclToFabilExt_c());
  }

  protected Ext postExtCodebaseDecl(Ext e) {
    return postExtNode(e);
  }

  @Override
  protected Ext extFieldAssignImpl() {
    return new FabricFieldAssignExt(new FieldAssignToJavaExt_c());
  }

  @Override
  protected Ext extConditionalImpl() {
    return new FabricConditionalExt(new ConditionalToFabilExt_c());
  }

  @Override
  protected Ext extDoImpl() {
    return new FabricDoExt(new DoToJavaExt_c());
  }

  @Override
  protected Ext extForImpl() {
    return new FabricForExt(new ForToFabilExt_c());
  }

  @Override
  protected Ext extIfImpl() {
    return new FabricIfExt(new IfToFabilExt_c());
  }

  @Override
  protected Ext extSwitchImpl() {
    return new FabricSwitchExt(new SwitchToJavaExt_c());
  }

  @Override
  protected Ext extWhileImpl() {
    return new FabricWhileExt(new WhileToFabilExt_c());
  }

  @Override
  protected Ext extBranchImpl() {
    return new FabricBranchExt(new BranchToJavaExt_c());
  }

  @Override
  protected Ext extLabeledImpl() {
    return new FabricLabeledExt(new LabeledToJavaExt_c());
  }

  @Override
  protected Ext extTryImpl() {
    return new FabricTryExt(new TryToFabilExt_c());
  }

  @Override
  protected Ext extCatchImpl() {
    return new JifExt_c(new CatchToFabilExt_c());
  }
}
