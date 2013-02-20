package jpa2fab.ext;

import polyglot.ast.Ext;
import polyglot.ext.jl5.ast.JL5ExtFactory;
import polyglot.ext.jl5.translate.JL5ToJLExtFactory_c;
import polyglot.translate.ext.NewArrayToExt_c;

public class JPA2FabILExtFactory_c extends JL5ToJLExtFactory_c implements
    JL5ExtFactory {

  public JPA2FabILExtFactory_c() {
    super();
  }

  public JPA2FabILExtFactory_c(JL5ExtFactory extFactory) {
    super(extFactory);
  }

  @Override
  protected Ext extClassDeclImpl() {
    return new ClassDeclToFabIL_c();
  }

  // constructor translation
  @Override
  protected Ext extConstructorCallImpl() {
    return new ConstructorCallToFabIL_c();
  }

  @Override
  protected Ext extConstructorDeclImpl() {
    return new ConstructorDeclToFabIL_c();
  }

  // 'new' translation (for entity types, arrays?)
  //  -- need to insert store expressions when outside of entity.

  @Override
  protected Ext extNewArrayImpl() {
    return new NewArrayToExt_c();
  }

  @Override
  protected Ext extNewImpl() {
    return new NewToFabIL_c();
  }

  // java collections -> fabil collections (for entity fields)    
  @Override
  protected Ext extImportImpl() {
    return new ImportToFabIL_c();
  }

  @Override
  protected Ext extPackageNodeImpl() {
    return new PackageNodeToFabIL_c();
  }

}
