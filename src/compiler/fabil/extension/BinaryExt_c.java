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

import polyglot.ast.Binary;
import polyglot.ast.Expr;
import polyglot.qq.QQ;
import fabil.visit.ProxyRewriter;

public class BinaryExt_c extends ExprExt_c {
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    // rewrite == and != to invoke isEquals as appropriate
    Binary node = node();
    QQ qq = pr.qq();

    if (node.left().type().isPrimitive() || node.right().type().isPrimitive())
      return node;

    if (node.operator().equals(Binary.EQ))
      return qq.parseExpr(" fabric.lang.Object._Proxy.idEquals(%E, %E)",
          node.left(), node.right());
    else if (node.operator().equals(Binary.NE))
      return qq.parseExpr("!fabric.lang.Object._Proxy.idEquals(%E, %E)",
          node.left(), node.right());
    else return node;
  }

  @Override
  public Binary node() {
    return (Binary) super.node();
  }
}
