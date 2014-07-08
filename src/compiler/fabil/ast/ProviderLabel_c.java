/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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

import polyglot.ast.ClassLit_c;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

/**
 * AST node implementation for representing the provider label for a class.
 */
public class ProviderLabel_c extends ClassLit_c implements ProviderLabel {

  /**
   * @param pos
   * @param tn
   */
  public ProviderLabel_c(Position pos, TypeNode tn) {
    super(pos, tn);
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    if (!typeNode.type().isClass()) {
      throw new SemanticException("Cannot get provider label of a non-class "
          + "type.", typeNode.position());
    }

    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    return type(ts.Label());
  }

  /** Write the expression to an output file. */
  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
    w.begin(0);
    print(typeNode, w, tr);
    w.write(".provider");
    w.end();
  }

  @Override
  public Node copy(NodeFactory nf) {
    return ((FabILNodeFactory) nf).providerLabel(this.position, this.typeNode);
  }
}
