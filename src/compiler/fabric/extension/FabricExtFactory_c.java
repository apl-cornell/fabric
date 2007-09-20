package fabric.extension;

import polyglot.ast.AbstractExtFactory_c;
import polyglot.ast.Ext;

/**
 * Factory for Fabric extension nodes.
 */
public class FabricExtFactory_c
     extends AbstractExtFactory_c
  implements FabricExtFactory {
  
  /** Factory method for Atomic objects */
  public final Ext extAtomic() {
    Ext e = extIdImpl();
    
    FabricExtFactory nextExtFactory = (FabricExtFactory) nextExtFactory();
    if (nextExtFactory != null) {
        Ext e2 = nextExtFactory.extAtomic();
        e = composeExts(e, e2);
    }
    return postExtAtomic(e);
  }
  
  
  protected Ext extAtomicImpl() {
    return new AtomicExt_c();
  }
  
  protected Ext postExtAtomic(Ext ext) {
    return postExtBlock(ext);
  }
  
  @Override
  protected Ext extNodeImpl() {
    return new FabricExt_c();
  }
}
