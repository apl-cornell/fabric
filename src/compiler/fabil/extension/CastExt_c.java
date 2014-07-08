/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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

import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.qq.QQ;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class CastExt_c extends ExprExt_c {
  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ExprExt_c#rewriteProxiesImpl(fabil.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    FabILTypeSystem ts = pr.typeSystem();
    Cast cast = (Cast) node();
    if (!ts.isFabricReference(cast.castType())) return cast;

    // Get the exact proxy before we cast.
    QQ qq = pr.qq();
    return qq.parseExpr("(%T) fabric.lang.Object._Proxy.$getProxy(%E)", cast
        .castType(), cast.expr());
  }
}
