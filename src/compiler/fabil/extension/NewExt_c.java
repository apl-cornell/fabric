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

import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.ClassType;
import fabil.ast.New;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class NewExt_c extends AnnotatedExt_c {

  @SuppressWarnings("unchecked")
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    New call = node();
    NodeFactory nf = pr.nodeFactory();

    TypeNode typeNode = call.objectType();
    ClassType type = (ClassType) typeNode.type();

    // Only rewrite if instantiating a pure Fabric type.
    FabILTypeSystem ts = pr.typeSystem();
    if (!ts.isPureFabricType(typeNode))
      return super.rewriteProxiesImpl(pr);

    List<Expr> newargs = new LinkedList<Expr>(call.arguments());
    newargs.add(0, call.location());
    newargs.add(1, call.label());

    TypeNode implType =
        nf.TypeNodeFromQualifiedName(typeNode.position(), type.fullName()
            + "._Impl");
    call = call.objectType(implType);
    call = (New) call.arguments(newargs);

    return pr.qq().parseExpr("(%T) %E.$getProxy()", typeNode, call);
  }

  @Override
  public New node() {
    return (New) super.node();
  }
  
}
