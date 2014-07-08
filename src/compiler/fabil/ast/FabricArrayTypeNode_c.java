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
package fabil.ast;

import polyglot.ast.ArrayTypeNode_c;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

public class FabricArrayTypeNode_c extends ArrayTypeNode_c implements
    FabricArrayTypeNode {

  public FabricArrayTypeNode_c(Position pos, TypeNode base) {
    super(pos, base);
  }

  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory fabilNodeFactory = (FabILNodeFactory) nf;
    return fabilNodeFactory.FabricArrayTypeNode(position, base);
  }

}
