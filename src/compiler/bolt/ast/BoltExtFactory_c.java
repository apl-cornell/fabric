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
  protected Ext extFieldDeclImpl() {
    return new BoltFieldDeclExt();
  }

  @Override
  protected Ext extNewLabelImpl() {
    return new NewLabelExt();
  }
}
