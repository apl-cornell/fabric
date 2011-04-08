package fabil.extension;

import polyglot.ast.AbstractDelFactory_c;
import polyglot.ast.JL;

public class FabILDelFactory_c extends AbstractDelFactory_c implements
    FabILDelFactory {

  public final JL delFabricArrayTypeNode() {
    JL e = delFabricArrayTypeNodeImpl();
    
    FabILDelFactory nextDelFactory = (FabILDelFactory) nextDelFactory();
    
    if (nextDelFactory != null) {
      JL e2 = nextDelFactory.delFabricArrayTypeNode();
      e = composeDels(e, e2);
    }
    
    return postDelFabricArrayTypeNode(e);
  }

  protected JL delFabricArrayTypeNodeImpl() {
    return new FabricArrayTypeNodeDel_c();
  }
  
  protected JL postDelFabricArrayTypeNode(JL del) {
    return postDelArrayTypeNode(del);
  }

  public final JL delFabricArrayInit() {
    JL e = delFabricArrayInitImpl();
    
    FabILDelFactory nextDelFactory = (FabILDelFactory) nextDelFactory();
    
    if (nextDelFactory != null) {
      JL e2 = nextDelFactory.delFabricArrayInit();
      e = composeDels(e, e2);
    }
    
    return postDelFabricArrayInit(e);
  }
  
  protected JL delFabricArrayInitImpl() {
    return delArrayInitImpl();
  }
  
  protected JL postDelFabricArrayInit(JL del) {
    return postDelArrayInit(del);
  }

  public final JL delProviderLabel() {
    JL e = delProviderLabelImpl();
    
    FabILDelFactory nextDelFactory = (FabILDelFactory) nextDelFactory();
    
    if (nextDelFactory != null) {
      JL e2 = nextDelFactory.delProviderLabel();
      e = composeDels(e, e2);
    }
    
    return postDelProviderLabel(e);
  }
  
  protected JL delProviderLabelImpl() {
    return delExprImpl();
  }
  
  protected JL postDelProviderLabel(JL del) {
    return postDelExpr(del);
  }

  @Override
  protected JL delImportImpl() {
    return new CodebaseImportDel_c();
  }
}
