package fabric.ast;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;
import fabric.extension.*;

/**
 * This class constructs {@link FabricExt} objects for the Fabric language
 * constructs.
 * 
 * @author mdgeorge
 */
public class FabricFabExtFactory_c extends AbstractFabExtFactory_c {

  public FabricFabExtFactory_c() {
    super();
  }

  public FabricFabExtFactory_c(ExtFactory next) {
    super(next);
  }

  @Override
  protected FabricExt extNodeImpl() {
    return new NodeExt_c();
  }

  @Override
  protected FabricExt extNewImpl() {
    return new NewExt_c();
  }

  @Override
  protected FabricExt extNewFabricArrayImpl() {
    return new NewFabricArrayExt_c();
  }

  @Override
  protected FabricExt extNewLabelImpl() {
    return new NewLabelExt_c();
  }

  @Override
  protected Ext extPrincipalExprImpl() {
    return new PrincipalExprExt_c();
  }
}
