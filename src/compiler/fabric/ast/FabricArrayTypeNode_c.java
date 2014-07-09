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
package fabric.ast;

import polyglot.ast.ArrayTypeNode_c;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeBuilder;
import fabric.types.FabricTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class FabricArrayTypeNode_c extends ArrayTypeNode_c implements
    FabricArrayTypeNode {
  @Deprecated
  public FabricArrayTypeNode_c(Position pos, TypeNode base) {
    this(pos, base, null);
  }

  public FabricArrayTypeNode_c(Position pos, TypeNode base, Ext ext) {
    super(pos, base, ext);
  }

  @Override
  public Node copy(NodeFactory nf) {
    FabricNodeFactory fabricNodeFactory = (FabricNodeFactory) nf;
    return fabricNodeFactory.FabricArrayTypeNode(position, base);
  }

  @Override
  public Node buildTypes(TypeBuilder tb) {
    FabricTypeSystem ts = (FabricTypeSystem) tb.typeSystem();
    return type(ts.fabricArrayOf(position(), base().type()));
  }

  @Override
  public Node disambiguate(AmbiguityRemover ar) {
    FabricTypeSystem ts = (FabricTypeSystem) ar.typeSystem();
    NodeFactory nf = ar.nodeFactory();

    if (!base().isDisambiguated()) return this;

    if (!base().type().isCanonical()) return this;

    return nf.CanonicalTypeNode(position(),
        ts.fabricArrayOf(position(), base().type()));
  }
}
