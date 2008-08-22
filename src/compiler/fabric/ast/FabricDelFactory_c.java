package fabric.ast;

import jif.ast.JifDelFactory_c;
import polyglot.ast.JL;

public class FabricDelFactory_c extends JifDelFactory_c implements
    FabricDelFactory {

  public JL delAtomic() {
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

}
