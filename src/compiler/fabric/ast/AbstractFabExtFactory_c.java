package fabric.ast;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;
import jif.ast.AbstractJifExtFactory_c;

/**
 * This class serves the same purpose as {@link AbstractJifExtFactory_c} and its
 * parent class {@link polyglot.ast.AbstractExtFactory_c}: it provides a default
 * implementation of the factory method for each fabric extension class that
 * delegates to the factory method for its parent class's extension.
 * 
 * @author mdgeorge
 *
 */
public class AbstractFabExtFactory_c extends AbstractJifExtFactory_c
                                     implements FabricExtFactory {
  
  public AbstractFabExtFactory_c() {
    super();
  }
  
  public AbstractFabExtFactory_c(ExtFactory next) {
    super(next);
  }
  
  public final Ext extAtomic() {
    Ext e = extAtomicImpl();
    if (nextExtFactory() != null && 
            nextExtFactory() instanceof FabricExtFactory) {
        FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory(); 
        Ext e2 = nextFac.extAtomic();
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
  
  public final Ext extAbort() {
    Ext e = extAbortImpl();
    if (nextExtFactory() != null && 
        nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory(); 
      Ext e2 = nextFac.extAbort();
      e = composeExts(e, e2);
    }
    return postExtAbort(e);
  }
  
  protected Ext extAbortImpl() {
    return extStmtImpl();
  }
  
  protected Ext postExtAbort(Ext e) {
    return postExtStmt(e);
  }

  public final Ext extRetry() {
    Ext e = extRetryImpl();
    if (nextExtFactory() != null && 
        nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory(); 
      Ext e2 = nextFac.extRetry();
      e = composeExts(e, e2);
    }
    return postExtRetry(e);
  }
  
  protected Ext extRetryImpl() {
    return extStmtImpl();
  }
  
  protected Ext postExtRetry(Ext e) {
    return postExtStmt(e);
  }

  public final Ext extClient() {
    Ext e = extClientImpl();
    if (nextExtFactory() != null && 
        nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory(); 
      Ext e2 = nextFac.extClient();
      e = composeExts(e, e2);
    }
    return postExtClient(e);
  }
  
  protected Ext extClientImpl() {
    return extExprImpl();
  }
  
  protected Ext postExtClient(Ext e) {
    return postExtExpr(e);
  }

  public final Ext extNewFabricArray() {
    Ext e = extNewFabricArrayImpl();
    if (nextExtFactory() != null &&
        nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extNewFabricArray();
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
  
  public final Ext extRemoteClientGetter() {
    Ext e = extRemoteClientGetterImpl();
    if (nextExtFactory() != null && 
        nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory(); 
      Ext e2 = nextFac.extRemoteClientGetter();
      e = composeExts(e, e2);
    }
    return postExtRemoteClientGetter(e);
  }
  
  protected Ext extRemoteClientGetterImpl() {
    return extExprImpl();
  }
  
  protected Ext postExtRemoteClientGetter(Ext e) {
    return postExtExpr(e);
  }

  public final Ext extFabricArrayInit() {
    Ext e = extFabricArrayInitImpl();
    if (nextExtFactory() != null &&
        nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extFabricArrayInit();
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

  public Ext extFabricArrayTypeNode() {
    Ext e = extFabricArrayTypeNodeImpl();
    if (nextExtFactory() != null &&
        nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extFabricArrayTypeNode();
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
}
