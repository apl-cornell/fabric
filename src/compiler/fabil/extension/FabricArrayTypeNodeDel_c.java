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

import polyglot.ast.ArrayTypeNode;
import polyglot.ast.JL_c;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeBuilder;
import fabil.ast.FabricArrayTypeNode;
import fabil.types.FabILTypeSystem;

public class FabricArrayTypeNodeDel_c extends JL_c {

  @Override
  public Node buildTypes(TypeBuilder tb) {
    FabricArrayTypeNode atn = (FabricArrayTypeNode) jl();
    FabILTypeSystem ts = (FabILTypeSystem) tb.typeSystem();
    return atn.type(ts.fabricArrayOf(atn.position(), atn.base().type()));
  }

  @Override
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
    ArrayTypeNode atn = (ArrayTypeNode) jl();
    FabILTypeSystem ts = (FabILTypeSystem) ar.typeSystem();
    NodeFactory nf = ar.nodeFactory();

    TypeNode base = atn.base();
    if (!base.isDisambiguated()) return atn;

    Type baseType = base.type();
    if (!baseType.isCanonical()) return atn;

    if (!ts.isFabricType(baseType)) {
      throw new SemanticException(
          "Non-Fabric objects cannot be stored in Fabric arrays.",
          atn.position());
    }

    return nf.CanonicalTypeNode(atn.position(),
        ts.fabricArrayOf(atn.position(), baseType));
  }

}
