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

import polyglot.ast.Expr;
import polyglot.ast.Instanceof;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class InstanceofExt_c extends ExprExt_c {

  @Override
  protected Expr rewriteProxiesImpl(ProxyRewriter pr) {
    FabILTypeSystem ts = pr.typeSystem();
    Instanceof node = node();

    TypeNode compareType = node.compareType();
    if (!ts.isFabricReference(compareType)) return node;

    // Get the exact proxy before checking instanceof.
    QQ qq = pr.qq();
    return qq.parseExpr(
        "fabric.lang.Object._Proxy.$getProxy(%E) instanceof %T", node.expr(),
        compareType);
  }

  @Override
  public Instanceof node() {
    return (Instanceof) super.node();
  }

}
