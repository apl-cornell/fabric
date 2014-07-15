package fabil.extension;

import polyglot.ast.AbstractDelFactory_c;
import polyglot.ast.JLDel;
import codebases.ast.CodebaseImportDel_c;

public class FabILDelFactory_c extends AbstractDelFactory_c implements
FabILDelFactory {

  @Override
  public final JLDel delFabricArrayTypeNode() {
    JLDel e = delFabricArrayTypeNodeImpl();

    FabILDelFactory nextDelFactory = (FabILDelFactory) nextDelFactory();

    if (nextDelFactory != null) {
      JLDel e2 = nextDelFactory.delFabricArrayTypeNode();
      e = composeDels(e, e2);
    }

    return postDelFabricArrayTypeNode(e);
  }

  protected JLDel delFabricArrayTypeNodeImpl() {
    return new FabricArrayTypeNodeDel_c();
  }

  protected JLDel postDelFabricArrayTypeNode(JLDel del) {
    return postDelArrayTypeNode(del);
  }

  @Override
  public final JLDel delFabricArrayInit() {
    JLDel e = delFabricArrayInitImpl();

    FabILDelFactory nextDelFactory = (FabILDelFactory) nextDelFactory();

    if (nextDelFactory != null) {
      JLDel e2 = nextDelFactory.delFabricArrayInit();
      e = composeDels(e, e2);
    }

    return postDelFabricArrayInit(e);
  }

  protected JLDel delFabricArrayInitImpl() {
    return delArrayInitImpl();
  }

  protected JLDel postDelFabricArrayInit(JLDel del) {
    return postDelArrayInit(del);
  }

  @Override
  public final JLDel delProviderLabel() {
    JLDel e = delProviderLabelImpl();

    FabILDelFactory nextDelFactory = (FabILDelFactory) nextDelFactory();

    if (nextDelFactory != null) {
      JLDel e2 = nextDelFactory.delProviderLabel();
      e = composeDels(e, e2);
    }

    return postDelProviderLabel(e);
  }

  protected JLDel delProviderLabelImpl() {
    return delExprImpl();
  }

  protected JLDel postDelProviderLabel(JLDel del) {
    return postDelExpr(del);
  }

  @Override
  protected JLDel delImportImpl() {
    return new CodebaseImportDel_c();
  }

  @Override
  public JLDel delCodebaseNode() {
    return delPackageNode();
  }

  @Override
  public JLDel delCodebaseDecl() {
    return delNode();
  }

  @Override
  public JLDel delCodebaseTypeNode() {
    return delCanonicalTypeNode();
  }
}
