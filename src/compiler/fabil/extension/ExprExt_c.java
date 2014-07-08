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

import polyglot.ast.Expr;
import polyglot.ast.Node;
import fabil.visit.ProxyRewriter;

public class ExprExt_c extends FabILExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.FabILExt_c#rewriteProxies(fabil.visit.ProxyRewriter)
   */
  @Override
  public final Node rewriteProxies(ProxyRewriter pr) {
    Expr expr = rewriteProxiesImpl(pr);
    if (expr != null) expr = expr.type(node().type());
    return expr;
  }

  protected Expr rewriteProxiesImpl(ProxyRewriter pr) {
    return (Expr) super.rewriteProxies(pr);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.FabILExt_c#rewriteProxiesOverride(fabil.visit.ProxyRewriter)
   */
  @Override
  public final Node rewriteProxiesOverride(ProxyRewriter rewriter) {
    Expr expr = rewriteProxiesOverrideImpl(rewriter);
    if (expr != null) expr = expr.type(node().type());
    return expr;
  }

  protected Expr rewriteProxiesOverrideImpl(ProxyRewriter pr) {
    return (Expr) super.rewriteProxiesOverride(pr);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public Expr node() {
    return (Expr) super.node();
  }

}
