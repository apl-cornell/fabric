package fabric.ast;

import fabric.extension.*;

/**
 * This class constructs {@link FabricExt} objects for the fabric language
 * constructs.
 * @author mdgeorge
 *
 */
public class FabricFabExtFactory_c extends AbstractFabricExtFactory_c {
  
  // TODO: construct appropriate fabric extensions
  
  protected FabricExt extNewImpl() {
    return new NewExt_c();
  }
}
