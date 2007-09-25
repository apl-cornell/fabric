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
  protected Ext extClassDeclImpl() {
    return new ClassDeclExt_c();
  }
  
  @Override
  protected Ext extConstructorDeclImpl() {
    return new ConstructorDeclExt_c();
  }
  
  @Override
  protected Ext extConstructorCallImpl() {
    return new ConstructorCallExt_c();
  }
  
  @Override
  protected Ext extFieldDeclImpl() {
    return new FieldDeclExt_c();
  }
  
  @Override
  protected Ext extMethodDeclImpl() {
    return new MethodDeclExt_c();
  }

  @Override
  protected Ext extNewArrayImpl() {
    return new NewArrayExt_c();
  }
  
  @Override
  protected Ext extNewImpl() {
    return new NewExt_c();
  }
  
  @Override
  protected Ext extNodeImpl() {
    return new FabricExt_c();
  }
}
