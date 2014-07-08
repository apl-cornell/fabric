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
import polyglot.ast.Eval;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import fabil.visit.ProxyRewriter;

public class EvalExt_c extends FabILExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.FabILExt_c#rewriteProxies(fabil.visit.ProxyRewriter)
   */
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    // Strip off any casts that may have been generated.
    Eval eval = node();
    Expr expr = eval.expr();
    while (expr instanceof Cast) {
      expr = ((Cast) expr).expr();
    }
    
    return eval.expr(expr);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public Eval node() {
    return (Eval) super.node();
  }
}
