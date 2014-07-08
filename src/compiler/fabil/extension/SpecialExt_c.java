/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
import polyglot.ast.Special;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import fabil.visit.ProxyRewriter;

public class SpecialExt_c extends ExprExt_c {

  @Override
  protected Expr rewriteProxiesImpl(ProxyRewriter pr) {
    Special special = node();

    if (!pr.typeSystem().isFabricClass(special.type())) return special;

    TypeNode qualifier = special.qualifier();
    QQ qq = pr.qq();
    if (qualifier != null) {
      // Tack on a "._Impl" to the qualifier.
      special =
          (Special) qq.parseExpr(qualifier + "._Impl." + special.kind()).type(
              special.type());
    }

    if (special.kind() != Special.THIS) return special;
    return qq.parseExpr("(%T) %E.$getProxy()", special.type(), special);
  }

  @Override
  public Special node() {
    return (Special) super.node();
  }

}
