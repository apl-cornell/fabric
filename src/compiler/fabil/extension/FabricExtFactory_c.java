package fabil.extension;

import polyglot.ast.AbstractExtFactory_c;
import polyglot.ast.Ext;

/**
 * Factory for Fabric extension nodes.
 */
public class FabricExtFactory_c extends AbstractExtFactory_c implements
    FabricExtFactory {

  /** Factory method for Atomic objects */
  public final Ext extAtomic() {
    Ext e = extAtomicImpl();

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
  protected Ext extArrayAccessAssignImpl() {
    return new ArrayAccessAssignExt_c();
  }

  @Override
  protected Ext extArrayAccessImpl() {
    return new ArrayAccessExt_c();
  }

  @Override
  protected Ext extArrayInitImpl() {
    return new ArrayInitExt_c();
  }

  @Override
  protected Ext extCallImpl() {
    return new CallExt_c();
  }

  @Override
  protected Ext extCastImpl() {
    return new CastExt_c();
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
  protected Ext extEvalImpl() {
    return new EvalExt_c();
  }

  @Override
  protected Ext extExprImpl() {
    return new ExprExt_c();
  }

  @Override
  protected Ext extFieldAssignImpl() {
    return new FieldAssignExt_c();
  }

  @Override
  protected Ext extFieldDeclImpl() {
    return new FieldDeclExt_c();
  }

  @Override
  protected Ext extFieldImpl() {
    return new FieldExt_c();
  }

  @Override
  protected Ext extInitializerImpl() {
    return new InitializerExt_c();
  }

  @Override
  protected Ext extMethodDeclImpl() {
    return new MethodDeclExt_c();
  }

  @Override
  protected Ext extNewImpl() {
    return new NewExt_c();
  }

  @Override
  protected Ext extNewArrayImpl() {
    return new NewArrayExt_c();
  }

  @Override
  protected Ext extNodeImpl() {
    return new FabricExt_c();
  }

  @Override
  protected Ext extSpecialImpl() {
    return new SpecialExt_c();
  }

  @Override
  protected Ext extTypeNodeImpl() {
    return new TypeNodeExt_c();
  }

  @Override
  protected Ext extUnaryImpl() {
    return new UnaryExt_c();
  }
}
