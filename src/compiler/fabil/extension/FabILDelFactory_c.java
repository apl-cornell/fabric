package fabil.extension;

import polyglot.ast.AbstractDelFactory_c;
import polyglot.ast.JL;

public class FabILDelFactory_c extends AbstractDelFactory_c implements
    FabILDelFactory {

  public JL delFabricArrayTypeNode() {
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

  public JL delFabricArrayInit() {
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
}
