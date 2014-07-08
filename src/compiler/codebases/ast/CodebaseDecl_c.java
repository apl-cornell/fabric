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
package codebases.ast;

import java.net.URI;

import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Node_c;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeBuilder;
import codebases.types.CBImportTable;
import codebases.types.CodebaseTypeSystem;

public class CodebaseDecl_c extends Node_c implements CodebaseDecl {
  protected Id name;

  public CodebaseDecl_c(Position pos, Id name) {
    super(pos);
    this.name = name;
  }

  @Override
  public Id name() {
    return name;
  }

  @Override
  public Node buildTypes(TypeBuilder tb) throws SemanticException {
    CodebaseTypeSystem ts = (CodebaseTypeSystem) tb.typeSystem();
    CBImportTable it = (CBImportTable) tb.importTable();
    URI ns = it.namespace();
    URI cb = ts.namespaceResolver(ns).resolveCodebaseName(name.id());

    // If the name doesn't exist, a semantic error should have been thrown.
    if (cb == null) {
      throw new SemanticException("Unknown codebase \"" + name + "\"",
          position());
    }

    return this;
  }
}
