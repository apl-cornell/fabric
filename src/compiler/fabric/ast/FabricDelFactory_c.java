package fabric.ast;

import fabric.extension.FabricCallDel;
import fabric.extension.FabricNewDel;
import fabric.extension.FabricNewLabelDel;
import jif.ast.JifDelFactory_c;
import polyglot.ast.JL;

/** Factory class for creating delegates for fabric types. */
public class FabricDelFactory_c extends JifDelFactory_c implements
    FabricDelFactory {

  //////////////////////////////////////////////////////////////////////////////
  // new ast methods                                                          //
  //////////////////////////////////////////////////////////////////////////////
  
  public final JL delAbortStmt() {
    JL e = delAbortStmtImpl();
    
    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
      JL e2 = ((FabricDelFactory) nextDelFactory()).delAbortStmt();
      e = composeDels(e,e2);
    }
    
    return postDelAbortStmt(e);
  }
  
  protected JL delAbortStmtImpl() {
    return delBranchImpl();
  }
  
  protected JL postDelAbortStmt(JL e) {
    return postDelBranch(e);
  }
  
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
  
  public final JL delClient() {
    JL e = delClientImpl();

    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
        JL e2 = ((FabricDelFactory)nextDelFactory()).delClient();
        e = composeDels(e, e2);
    }
    return postDelClient(e);
  }

  protected JL delClientImpl() {
    return delExprImpl();
  }

  protected JL postDelClient(JL e) {
    return postDelExpr(e);
  }

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

  public final JL delRemoteClientGetter() {
    JL e = delRemoteClientGetterImpl();

    if (nextDelFactory() != null && nextDelFactory() instanceof FabricDelFactory) {
        JL e2 = ((FabricDelFactory)nextDelFactory()).delRemoteClientGetter();
        e = composeDels(e, e2);
    }
    return postDelRemoteClientGetter(e);
  }
  
  protected JL delRemoteClientGetterImpl() {
    return delExprImpl();
  }

  protected JL postDelRemoteClientGetter(JL e) {
    return postDelExpr(e);
  }
  
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
  
  @Override
  protected JL delCallImpl() {
    return new FabricCallDel();
  }
}
