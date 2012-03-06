package fabric.ast;

import jif.ast.JifDelFactory_c;
import polyglot.ast.JL;
import codebases.ast.CodebaseImportDel_c;
import fabric.extension.FabricCallDel;
import fabric.extension.FabricFieldDeclDel;
import fabric.extension.FabricNewDel;
import fabric.extension.FabricNewLabelDel;
import fabric.extension.FabricPrincipalExprDel;
import fabric.extension.MethodDeclJifDel;

/** Factory class for creating delegates for fabric types. */
public class FabricDelFactory_c extends JifDelFactory_c implements
    FabricDelFactory {

  //////////////////////////////////////////////////////////////////////////////
  // new ast methods                                                          //
  //////////////////////////////////////////////////////////////////////////////
  
  @Override
  public final JL delAbortStmt() {
    JL e = delAbortStmtImpl();
    
    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
      JL e2 = ((FabricDelFactory) nextDelFactory()).delAbortStmt();
      e = composeDels(e,e2);
    }
    
    return postDelAbortStmt(e);
  }
  
  @Override
  protected JL delFieldDeclImpl() {
    return new FabricFieldDeclDel();
  }
  
  protected JL delAbortStmtImpl() {
    return delBranchImpl();
  }
  
  protected JL postDelAbortStmt(JL e) {
    return postDelBranch(e);
  }
  
  @Override
  public final JL delAtomic() {
    JL e = delAtomicImpl();

    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
      JL e2 = ((FabricDelFactory)nextDelFactory()).delAtomic();
      e = composeDels(e, e2);
    }
    return postDelAtomic(e);
  }

  protected JL delAtomicImpl() {
    return delBlockImpl();
  }
  
  protected JL postDelAtomic(JL e) {
    return postDelBlock(e);
  }
  
  @Override
  public final JL delAmbNewFabricArray() {
    JL e = delAmbNewFabricArrayImpl();
    
    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
      JL e2 = ((FabricDelFactory)nextDelFactory()).delAmbNewFabricArray();
      e = composeDels(e,e2);
    }
    
    return postDelAmbNewFabricArray(e);
  }

  
  protected JL delAmbNewFabricArrayImpl() {
    return delAmbNewArrayImpl();
  }
  
  protected JL postDelAmbNewFabricArray(JL e) {
    return postDelAmbNewArray(e);
  }
  
  @Override
  public final JL delWorker() {
    JL e = delWorkerImpl();

    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
        JL e2 = ((FabricDelFactory)nextDelFactory()).delWorker();
        e = composeDels(e, e2);
    }
    return postDelWorker(e);
  }

  protected JL delWorkerImpl() {
    return delExprImpl();
  }

  protected JL postDelWorker(JL e) {
    return postDelExpr(e);
  }

  @Override
  public final JL delFabricArrayInit() {
    JL e = delFabricArrayInitImpl();

    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
        JL e2 = ((FabricDelFactory)nextDelFactory()).delFabricArrayInit();
        e = composeDels(e, e2);
    }
    return postDelFabricArrayInit(e);
  }

  protected JL delFabricArrayInitImpl() {
    return delArrayInitImpl();
  }

  protected JL postDelFabricArrayInit(JL e) {
    return postDelArrayInit(e);
  }

  @Override
  public final JL delFabricArrayTypeNode() {
    JL e = delFabricArrayTypeNodeImpl();

    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
        JL e2 = ((FabricDelFactory)nextDelFactory()).delFabricArrayTypeNode();
        e = composeDels(e, e2);
    }
    return postDelFabricArrayTypeNode(e);
  }

  protected JL delFabricArrayTypeNodeImpl() {
    return delArrayTypeNodeImpl();
  }

  protected JL postDelFabricArrayTypeNode(JL e) {
    return postDelArrayTypeNode(e);
  }

  @Override
  public final JL delNewFabricArray() {
    JL e = delNewFabricArrayImpl();

    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
        JL e2 = ((FabricDelFactory)nextDelFactory()).delNewFabricArray();
        e = composeDels(e, e2);
    }
    return postDelNewFabricArray(e);
  }

  protected JL delNewFabricArrayImpl() {
    return delNewArrayImpl();
  }

  protected JL postDelNewFabricArray(JL e) {
    return postDelNewArray(e);
  }

  @Override
  public final JL delRemoteWorkerGetter() {
    JL e = delRemoteWorkerGetterImpl();

    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
        JL e2 = ((FabricDelFactory)nextDelFactory()).delRemoteWorkerGetter();
        e = composeDels(e, e2);
    }
    return postDelRemoteWorkerGetter(e);
  }
  
  protected JL delRemoteWorkerGetterImpl() {
    return delExprImpl();
  }

  protected JL postDelRemoteWorkerGetter(JL e) {
    return postDelExpr(e);
  }
  
  @Override
  public final JL delRetryStmt() {
    JL e = delRetryStmtImpl();
    
    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
      JL e2 = ((FabricDelFactory) nextDelFactory()).delRetryStmt();
      e = composeDels(e, e2);
    }
    
    return postDelRetryStmt(e);
  }
  
  protected JL delRetryStmtImpl() {
    return delBranchImpl();
  }
  
  protected JL postDelRetryStmt(JL e) {
    return postDelBranch(e);
  }

  //////////////////////////////////////////////////////////////////////////////
  // overridden factory methods                                               //
  //////////////////////////////////////////////////////////////////////////////
  
  @Override
  protected JL delNewImpl() {
    return new FabricNewDel();
  }
  
  @Override
  protected JL delNewLabelImpl() {
    return new FabricNewLabelDel();
  }
  
  public JL delPrincipalExpr() {
    return new FabricPrincipalExprDel();
  }
  
  @Override
  protected JL delCallImpl() {
    return new FabricCallDel();
  }

  @Override
  protected JL delImportImpl() {
    return new CodebaseImportDel_c();
  }

  @Override
  protected JL delMethodDeclImpl() {
    return new MethodDeclJifDel();
  }

  @Override
  public JL delCodebaseNode() {
    return delNode();
  }

  @Override
  public JL delCodebaseDecl() {
    return delNode();
  }
  
}
