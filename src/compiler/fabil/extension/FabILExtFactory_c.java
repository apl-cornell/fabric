package fabil.extension;

import polyglot.ast.AbstractExtFactory_c;
import polyglot.ast.Ext;

/**
 * Factory for FabIL extension nodes.
 */
public class FabILExtFactory_c extends AbstractExtFactory_c implements
    FabILExtFactory {

  @Override
  public Ext extFabricArrayTypeNode() {
    Ext e = extFabricArrayTypeNodeImpl();
    return postExtFabricArrayTypeNode(e);
  }

  protected Ext extFabricArrayTypeNodeImpl() {
    return extArrayTypeNodeImpl();
  }

  protected Ext postExtFabricArrayTypeNode(Ext ext) {
    return postExtArrayTypeNode(ext);
  }

  /** Factory method for Atomic objects */
  @Override
  public final Ext extAtomic() {
    Ext e = extAtomicImpl();
    return postExtAtomic(e);
  }

  protected Ext extAtomicImpl() {
    return new AtomicExt_c();
  }

  protected Ext postExtAtomic(Ext ext) {
    return postExtBlock(ext);
  }

  @Override
  public final Ext extAbort() {
    Ext e = extAbortImpl();
    return postExtAbort(e);
  }

  protected Ext extAbortImpl() {
    return new AbortExt_c();
  }

  protected Ext postExtAbort(Ext ext) {
    return postExtStmt(ext);
  }

  @Override
  public final Ext extRetry() {
    Ext e = extRetryImpl();
    return postExtRetry(e);
  }

  protected Ext extRetryImpl() {
    return new RetryExt_c();
  }

  protected Ext postExtRetry(Ext ext) {
    return postExtStmt(ext);
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
  public final Ext extFabricArrayInit() {
    Ext e = extFabricArrayInitImpl();
    return postExtFabricArrayInit(e);
  }

  protected Ext extFabricArrayInitImpl() {
    return new FabricArrayInitExt_c();
  }

  protected Ext postExtFabricArrayInit(Ext ext) {
    return postExtArrayInit(ext);
  }

  @Override
  protected Ext extBinaryImpl() {
    return new BinaryExt_c();
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
  protected Ext extClassBodyImpl() {
    return new ClassBodyExt_c();
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
  protected Ext extInstanceofImpl() {
    return new InstanceofExt_c();
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
  public final Ext extNewFabricArray() {
    Ext e = extNewFabricArrayImpl();
    return postExtNewFabricArray(e);
  }

  protected Ext extNewFabricArrayImpl() {
    return new NewFabricArrayExt_c();
  }

  protected Ext postExtNewFabricArray(Ext e) {
    return postExtNewArray(e);
  }

  @Override
  protected Ext extNodeImpl() {
    return new FabILExt_c();
  }

  @Override
  protected Ext extPackageNodeImpl() {
    return new PackageNodeExt_c();
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

  @Override
  public final Ext extProviderLabel() {
    Ext e = extProviderLabelImpl();
    return postExtProviderLabel(e);
  }

  protected Ext extProviderLabelImpl() {
    return extExprImpl();
  }

  protected Ext postExtProviderLabel(Ext ext) {
    return postExtExpr(ext);
  }

  @Override
  public final Ext extCodebaseNode() {
    Ext e = extCodebaseNodeImpl();
    return postExtCodebaseNode(e);
  }

  protected Ext extCodebaseNodeImpl() {
    return extNodeImpl();
  }

  protected Ext postExtCodebaseNode(Ext ext) {
    return postExtNode(ext);
  }

  @Override
  public final Ext extCodebaseDecl() {
    Ext e = extCodebaseDeclImpl();
    return postExtCodebaseDecl(e);
  }

  protected Ext extCodebaseDeclImpl() {
    return extNodeImpl();
  }

  protected Ext postExtCodebaseDecl(Ext ext) {
    return postExtNode(ext);
  }

  @Override
  public final Ext extCodebaseTypeNode() {
    Ext e = extCodebaseTypeNodeImpl();
    return postExtCodebaseTypeNode(e);
  }

  protected Ext extCodebaseTypeNodeImpl() {
    return extCanonicalTypeNodeImpl();
  }

  protected Ext postExtCodebaseTypeNode(Ext ext) {
    return postExtCanonicalTypeNode(ext);
  }

  @Override
  public final Ext extStoreGetter() {
    Ext e = extStoreGetterImpl();
    return postExtStoreGetter(e);
  }

  protected Ext extStoreGetterImpl() {
    return extExprImpl();
  }

  protected Ext postExtStoreGetter(Ext ext) {
    return postExtExpr(ext);
  }

  @Override
  protected Ext extCaseImpl() {
    return new CaseExt_c();
  }

}
