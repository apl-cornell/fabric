package fabric.ast;

import jif.ast.AbstractJifExtFactory_c;
import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;

/**
 * This class serves the same purpose as {@link AbstractJifExtFactory_c} and its
 * parent class {@link polyglot.ast.AbstractExtFactory_c}: it provides a default
 * implementation of the factory method for each fabric extension class that
 * delegates to the factory method for its parent class's extension.
 */
public class AbstractFabExtFactory_c extends AbstractJifExtFactory_c implements
FabricExtFactory {

  public AbstractFabExtFactory_c() {
  }

  public AbstractFabExtFactory_c(ExtFactory next) {
    super(next);
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
    return extBlockImpl();
  }

  protected Ext postExtAtomic(Ext e) {
    return postExtBlock(e);
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
    return extStmtImpl();
  }

  protected Ext postExtAbortStmt(Ext e) {
    return postExtStmt(e);
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
    return extStmtImpl();
  }

  protected Ext postExtRetryStmt(Ext e) {
    return postExtStmt(e);
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
    return extExprImpl();
  }

  protected Ext postExtWorker(Ext e) {
    return postExtExpr(e);
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
    return extNewArrayImpl();
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

    return postExtNewFabricArray(e);
  }

  protected Ext extAmbNewFabricArrayImpl() {
    return extAmbNewArrayImpl();
  }

  protected Ext postExtAmbNewFabricArrayImpl(Ext e) {
    return postExtAmbNewArray(e);
  }

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
    return extExprImpl();
  }

  protected Ext postExtRemoteWorkerGetter(Ext e) {
    return postExtExpr(e);
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
    return extArrayInitImpl();
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
    return extArrayTypeNodeImpl();
  }

  protected Ext postExtFabricArrayTypeNode(Ext e) {
    return postExtArrayTypeNode(e);
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
    return extNodeImpl();
  }

  protected Ext postExtCodebaseNode(Ext e) {
    return postExtNode(e);
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
    return extNodeImpl();
  }

  protected Ext postExtCodebaseDecl(Ext e) {
    return postExtNode(e);
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
    return extExprImpl();
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

  protected Ext postExtAccessPolicy(Ext e) {
    return postExtNode(e);
  }

  protected Ext extAccessPolicyImpl() {
    return extNodeImpl();
  }

}
