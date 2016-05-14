package fabric.ast;

import codebases.ast.CodebaseImportDel_c;

import fabric.extension.FabricArrayAccessDel;
import fabric.extension.FabricCallDel;
import fabric.extension.FabricFieldDeclDel;
import fabric.extension.FabricFieldDel;
import fabric.extension.FabricNewDel;
import fabric.extension.FabricNewFabricArrayDel;
import fabric.extension.FabricNewLabelDel;
import fabric.extension.FabricPrincipalExprDel;

import jif.ast.JifDelFactory_c;

import polyglot.ast.JLDel;

/** Factory class for creating delegates for fabric types. */
public class FabricDelFactory_c extends JifDelFactory_c implements
FabricDelFactory {

  // ////////////////////////////////////////////////////////////////////////////
  // new ast methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public final JLDel delAbortStmt() {
    JLDel e = delAbortStmtImpl();

    if (nextDelFactory() != null
        && nextDelFactory() instanceof FabricDelFactory) {
      JLDel e2 = ((FabricDelFactory) nextDelFactory()).delAbortStmt();
      e = composeDels(e, e2);
    }

    return postDelAbortStmt(e);
  }

  @Override
  protected JLDel delFieldDeclImpl() {
    return new FabricFieldDeclDel();
  }

  protected JLDel delAbortStmtImpl() {
    return delBranchImpl();
  }

  protected JLDel postDelAbortStmt(JLDel e) {
    return postDelBranch(e);
  }

  @Override
  public final JLDel delAtomic() {
    JLDel e = delAtomicImpl();

    if (nextDelFactory() != null
        && nextDelFactory() instanceof FabricDelFactory) {
      JLDel e2 = ((FabricDelFactory) nextDelFactory()).delAtomic();
      e = composeDels(e, e2);
    }
    return postDelAtomic(e);
  }

  protected JLDel delAtomicImpl() {
    return delBlockImpl();
  }

  protected JLDel postDelAtomic(JLDel e) {
    return postDelBlock(e);
  }

  @Override
  public final JLDel delAmbNewFabricArray() {
    JLDel e = delAmbNewFabricArrayImpl();

    if (nextDelFactory() != null
        && nextDelFactory() instanceof FabricDelFactory) {
      JLDel e2 = ((FabricDelFactory) nextDelFactory()).delAmbNewFabricArray();
      e = composeDels(e, e2);
    }

    return postDelAmbNewFabricArray(e);
  }

  protected JLDel delAmbNewFabricArrayImpl() {
    return delAmbNewArrayImpl();
  }

  protected JLDel postDelAmbNewFabricArray(JLDel e) {
    return postDelAmbNewArray(e);
  }

  @Override
  public final JLDel delWorker() {
    JLDel e = delWorkerImpl();

    if (nextDelFactory() != null
        && nextDelFactory() instanceof FabricDelFactory) {
      JLDel e2 = ((FabricDelFactory) nextDelFactory()).delWorker();
      e = composeDels(e, e2);
    }
    return postDelWorker(e);
  }

  protected JLDel delWorkerImpl() {
    return delExprImpl();
  }

  protected JLDel postDelWorker(JLDel e) {
    return postDelExpr(e);
  }

  @Override
  public JLDel delStore() {
    JLDel e = delStoreImpl();

    if (nextDelFactory() != null
        && nextDelFactory() instanceof FabricDelFactory) {
      JLDel e2 = ((FabricDelFactory) nextDelFactory()).delStore();
      e = composeDels(e, e2);
    }
    return postDelStore(e);
  }

  protected JLDel delStoreImpl() {
    return delExprImpl();
  }

  protected JLDel postDelStore(JLDel e) {
    return postDelExpr(e);
  }

  @Override
  public final JLDel delFabricArrayInit() {
    JLDel e = delFabricArrayInitImpl();

    if (nextDelFactory() != null
        && nextDelFactory() instanceof FabricDelFactory) {
      JLDel e2 = ((FabricDelFactory) nextDelFactory()).delFabricArrayInit();
      e = composeDels(e, e2);
    }
    return postDelFabricArrayInit(e);
  }

  protected JLDel delFabricArrayInitImpl() {
    return delArrayInitImpl();
  }

  protected JLDel postDelFabricArrayInit(JLDel e) {
    return postDelArrayInit(e);
  }

  @Override
  public final JLDel delFabricArrayTypeNode() {
    JLDel e = delFabricArrayTypeNodeImpl();

    if (nextDelFactory() != null
        && nextDelFactory() instanceof FabricDelFactory) {
      JLDel e2 = ((FabricDelFactory) nextDelFactory()).delFabricArrayTypeNode();
      e = composeDels(e, e2);
    }
    return postDelFabricArrayTypeNode(e);
  }

  protected JLDel delFabricArrayTypeNodeImpl() {
    return delArrayTypeNodeImpl();
  }

  protected JLDel postDelFabricArrayTypeNode(JLDel e) {
    return postDelArrayTypeNode(e);
  }

  @Override
  public final JLDel delNewFabricArray() {
    JLDel e = delNewFabricArrayImpl();

    if (nextDelFactory() != null
        && nextDelFactory() instanceof FabricDelFactory) {
      JLDel e2 = ((FabricDelFactory) nextDelFactory()).delNewFabricArray();
      e = composeDels(e, e2);
    }
    return postDelNewFabricArray(e);
  }

  protected JLDel delNewFabricArrayImpl() {
    return new FabricNewFabricArrayDel();
  }

  protected JLDel postDelNewFabricArray(JLDel e) {
    return postDelNewArray(e);
  }

  @Override
  public final JLDel delRemoteWorkerGetter() {
    JLDel e = delRemoteWorkerGetterImpl();

    if (nextDelFactory() != null
        && nextDelFactory() instanceof FabricDelFactory) {
      JLDel e2 = ((FabricDelFactory) nextDelFactory()).delRemoteWorkerGetter();
      e = composeDels(e, e2);
    }
    return postDelRemoteWorkerGetter(e);
  }

  protected JLDel delRemoteWorkerGetterImpl() {
    return delExprImpl();
  }

  protected JLDel postDelRemoteWorkerGetter(JLDel e) {
    return postDelExpr(e);
  }

  @Override
  public final JLDel delRetryStmt() {
    JLDel e = delRetryStmtImpl();

    if (nextDelFactory() != null
        && nextDelFactory() instanceof FabricDelFactory) {
      JLDel e2 = ((FabricDelFactory) nextDelFactory()).delRetryStmt();
      e = composeDels(e, e2);
    }

    return postDelRetryStmt(e);
  }

  protected JLDel delRetryStmtImpl() {
    return delBranchImpl();
  }

  protected JLDel postDelRetryStmt(JLDel e) {
    return postDelBranch(e);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // overridden factory methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected JLDel delNewImpl() {
    return new FabricNewDel();
  }

  @Override
  protected JLDel delNewLabelImpl() {
    return new FabricNewLabelDel();
  }

  @Override
  public JLDel delPrincipalExpr() {
    return new FabricPrincipalExprDel();
  }

  @Override
  protected JLDel delCallImpl() {
    return new FabricCallDel();
  }

  @Override
  protected JLDel delImportImpl() {
    return new CodebaseImportDel_c();
  }

  @Override
  public JLDel delCodebaseNode() {
    return delNode();
  }

  @Override
  public JLDel delCodebaseDecl() {
    return delNode();
  }

  @Override
  public JLDel delAccessPolicy() {
    return delNode();
  }

  @Override
  protected JLDel delFieldImpl() {
    return new FabricFieldDel();
  }

  @Override
  protected JLDel delArrayAccessImpl() {
    return new FabricArrayAccessDel();
  }

}
