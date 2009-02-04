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
  
  public Ext extAtomic() {
    Ext e = extAtomicImpl();
    if (nextExtFactory() != null && 
            nextExtFactory() instanceof FabricExtFactory) {
        FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory(); 
        Ext e2 = nextFac.extAtomic();
        e = composeExts(e2, e);
    }
    return postExtAtomic(e);
  }

  protected Ext extAtomicImpl() {
    return extBlockImpl();
  }

  protected Ext postExtAtomic(Ext e) {
    return postExtBlock(e);
  }

}
