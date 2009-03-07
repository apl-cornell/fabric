package fabric.ast;

import fabric.extension.FabricCallDel;
import fabric.extension.FabricNewArrayDel;
import fabric.extension.FabricNewDel;
import fabric.extension.FabricNewLabelDel;
import jif.ast.JifDelFactory_c;
import polyglot.ast.JL;

/** Factory class for creating delegates for fabric types. */
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

  @Override
  protected JL delNewImpl() {
    return new FabricNewDel();
  }
  
  @Override
  protected JL delNewArrayImpl() {
    return new FabricNewArrayDel();
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
