/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabil.extension;

import polyglot.ast.AbstractDelFactory_c;
import polyglot.ast.JL;
import codebases.ast.CodebaseImportDel_c;

public class FabILDelFactory_c extends AbstractDelFactory_c implements
    FabILDelFactory {

  @Override
  public final JL delFabricArrayTypeNode() {
    JL e = delFabricArrayTypeNodeImpl();

    FabILDelFactory nextDelFactory = (FabILDelFactory) nextDelFactory();

    if (nextDelFactory != null) {
      JL e2 = nextDelFactory.delFabricArrayTypeNode();
      e = composeDels(e, e2);
    }

    return postDelFabricArrayTypeNode(e);
  }

  protected JL delFabricArrayTypeNodeImpl() {
    return new FabricArrayTypeNodeDel_c();
  }

  protected JL postDelFabricArrayTypeNode(JL del) {
    return postDelArrayTypeNode(del);
  }

  @Override
  public final JL delFabricArrayInit() {
    JL e = delFabricArrayInitImpl();

    FabILDelFactory nextDelFactory = (FabILDelFactory) nextDelFactory();

    if (nextDelFactory != null) {
      JL e2 = nextDelFactory.delFabricArrayInit();
      e = composeDels(e, e2);
    }

    return postDelFabricArrayInit(e);
  }

  protected JL delFabricArrayInitImpl() {
    return delArrayInitImpl();
  }

  protected JL postDelFabricArrayInit(JL del) {
    return postDelArrayInit(del);
  }

  @Override
  public final JL delProviderLabel() {
    JL e = delProviderLabelImpl();

    FabILDelFactory nextDelFactory = (FabILDelFactory) nextDelFactory();

    if (nextDelFactory != null) {
      JL e2 = nextDelFactory.delProviderLabel();
      e = composeDels(e, e2);
    }

    return postDelProviderLabel(e);
  }

  protected JL delProviderLabelImpl() {
    return delExprImpl();
  }

  protected JL postDelProviderLabel(JL del) {
    return postDelExpr(del);
  }

  @Override
  protected JL delImportImpl() {
    return new CodebaseImportDel_c();
  }

  @Override
  public JL delCodebaseNode() {
    return delPackageNode();
  }

  @Override
  public JL delCodebaseDecl() {
    return delNode();
  }

  @Override
  public JL delCodebaseTypeNode() {
    return delCanonicalTypeNode();
  }
}
