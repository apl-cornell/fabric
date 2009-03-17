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
}
