/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.ArrayType;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class TypeNodeExt_c extends FabILExt_c {

  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    TypeNode tn = node();
    Type type = tn.type();

    // Only rewrite array types.
    if (!type.isArray()) return tn;

    // Only rewrite Fabric arrays.
    FabILTypeSystem ts = pr.typeSystem();
    ArrayType at = type.toArray();
    if (!ts.isPureFabricType(at)) return tn;

    NodeFactory nf = pr.nodeFactory();
    return nf.CanonicalTypeNode(Position.compilerGenerated(),
        ts.toFabricRuntimeArray(at));
  }

  @Override
  public TypeNode node() {
    return (TypeNode) super.node();
  }

}
