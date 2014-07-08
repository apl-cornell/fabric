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

import java.util.LinkedList;
import java.util.List;

import polyglot.ast.ConstructorCall;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.visit.ProxyRewriter;

public class ConstructorCallExt_c extends FabILExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    // Need to add a $location argument to super(...) and this(...) constructor
    // calls if we are compiling a Fabric class.
    NodeFactory nf = pr.nodeFactory();
    ConstructorCall call = node();

    // Ensure that we're translating a Fabric type.
    Type containerType = call.constructorInstance().container();
//    if (!"java.lang.Object".equals(containerType.toString())
//        && !pr.typeSystem().isFabricClass(containerType))
    if (!pr.typeSystem().isFabricClass(containerType))
      return super.rewriteProxies(pr);

    List<Expr> args = new LinkedList<Expr>(call.arguments());
    args.add(0, nf.AmbExpr(Position.compilerGenerated(), nf.Id(Position
        .compilerGenerated(), "$location")));
    args.add(1, nf.AmbExpr(Position.compilerGenerated(), nf.Id(Position
        .compilerGenerated(), "$label")));
    return call.arguments(args);
  }

  @Override
  public ConstructorCall node() {
    return (ConstructorCall) super.node();
  }
}
