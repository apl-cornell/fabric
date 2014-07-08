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

import polyglot.ast.ArrayAccess;
import polyglot.ast.ArrayAccessAssign;
import polyglot.ast.Expr;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class ArrayAccessAssignExt_c extends ExprExt_c {

  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter rewriter) {
    ArrayAccessAssign assign = node();
    ArrayAccess left = (ArrayAccess) assign.left();
    Expr array = left.array();

    // Only rewrite Fabric arrays.
    FabILTypeSystem ts = rewriter.typeSystem();
    if (!ts.isFabricType(array.type())) return null;

    array = (Expr) left.visitChild(array, rewriter);
    Expr index = (Expr) left.visitChild(left.index(), rewriter);
    Expr right = (Expr) assign.visitChild(assign.right(), rewriter);

    return rewriter.qq().parseExpr("%E.set(%E, %E)", array, index, right);
  }

  @Override
  public ArrayAccessAssign node() {
    return (ArrayAccessAssign) super.node();
  }

}
