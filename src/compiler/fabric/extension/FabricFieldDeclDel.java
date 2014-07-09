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
package fabric.extension;

import jif.extension.JifFieldDeclDel;
import jif.types.label.ConfPolicy;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.AmbiguityRemover;
import fabric.ast.FabricFieldDecl;
import fabric.types.FabricDefaultSignature;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricTypeSystem;

public class FabricFieldDeclDel extends JifFieldDeclDel {

  @Override
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
    FabricFieldDecl n = (FabricFieldDecl) super.disambiguate(ar);
    FabricFieldInstance ffi = (FabricFieldInstance) n.fieldInstance();
    FabricTypeSystem fts = (FabricTypeSystem) ar.typeSystem();
    FabricDefaultSignature fds = fts.fabricDefaultSignature();
    ConfPolicy Li;
    if (n.accessPolicy() == null) {
      Li = fds.defaultAccessPolicy(n).confProjection();
    } else {
      Li = n.accessPolicy().label().confProjection();
    }
    // TODO: it seems fishy that Li can be null even if accessLabel is not null,
    // but it happens (in r3139 while building MapServ for example).
    if (!fts.accessPolicyValid(Li)) {
      throw new SemanticException(
          "Access policies may only contain static elements like (principal or label) parameters and constants.",
          n.position());
    }

    ffi.setAccessPolicy(Li);
    return n.fieldInstance(ffi);
  }
}
