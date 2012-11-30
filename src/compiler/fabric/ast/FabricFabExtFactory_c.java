package fabric.ast;

import jif.translate.CannotToJavaExt_c;
import jif.translate.CanonicalLabelNodeToJavaExt_c;
import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;
import fabric.extension.FabricExt;
import fabric.extension.LabelNodeExt_c;
import fabric.extension.NewExt_c;
import fabric.extension.NewFabricArrayExt_c;
import fabric.extension.NewLabelExt_c;
import fabric.extension.NodeExt_c;
import fabric.extension.PrincipalExprExt_c;

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

  @Override
  protected Ext extLabelNodeImpl() {
    return new LabelNodeExt_c(new CannotToJavaExt_c());
  }

  @Override
  protected Ext extCanonicalLabelNodeImpl() {
    return new LabelNodeExt_c(new CanonicalLabelNodeToJavaExt_c());
  }
}
