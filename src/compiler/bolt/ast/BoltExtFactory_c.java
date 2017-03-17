package bolt.ast;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;

public final class BoltExtFactory_c extends BoltAbstractExtFactory_c {

  public BoltExtFactory_c() {
    super();
  }

  public BoltExtFactory_c(ExtFactory nextExtFactory) {
    super(nextExtFactory);
  }

  @Override
  protected Ext extNodeImpl() {
    return new BoltExt();
  }

  @Override
  protected Ext extTermImpl() {
    return new BoltTermExt();
  }

  @Override
  protected Ext extArrayTypeNodeImpl() {
    return new BoltArrayTypeNodeExt(null);
  }

  @Override
  protected Ext extCallImpl() {
    return new BoltCallExt();
  }

  @Override
  protected Ext extFieldDeclImpl() {
    return new BoltFieldDeclExt();
  }

  @Override
  protected Ext extNewImpl() {
    return new BoltNewExt();
  }

  @Override
  protected Ext extBoltNewArrayImpl() {
    return new BoltLocatedElementExt(null);
  }

  @Override
  protected Ext extNewLabelImpl() {
    return new NewLabelExt();
  }

  @Override
  protected Ext extNewPrincipalImpl() {
    return new NewPrincipalExt();
  }

  @Override
  protected Ext extPrologueImpl() {
    return new BoltPrologueExt();
  }
}
