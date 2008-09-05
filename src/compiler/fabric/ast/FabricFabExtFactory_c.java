package fabric.ast;

import fabric.extension.*;

/**
 * This class constructs {@link FabricExt} objects for the fabric language
 * constructs.
 * @author mdgeorge
 *
 */
public class FabricFabExtFactory_c extends AbstractFabExtFactory_c {
  
  // TODO: construct appropriate fabric extensions
  
  @Override
  protected FabricExt extNodeImpl() {
    return new NodeExt_c();
  }
  
  @Override
  protected FabricExt extNewImpl() {
    return new NewExt_c();
  }
  
  @Override
  protected FabricExt extNewArrayImpl() {
    return new NewArrayExt_c();
  }
}
