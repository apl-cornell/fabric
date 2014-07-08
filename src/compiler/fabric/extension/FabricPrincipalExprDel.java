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
package fabric.extension;

import polyglot.ast.Expr;
import polyglot.ast.JL_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabric.ast.FabricUtil;
import fabric.types.FabricTypeSystem;

public class FabricPrincipalExprDel extends JL_c {
  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    PrincipalExprExt_c ext = (PrincipalExprExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = v.visitEdge(n, ext.location());
      ext = (PrincipalExprExt_c) ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    Node n = super.typeCheck(tc);
    PrincipalExprExt_c ext = (PrincipalExprExt_c) FabricUtil.fabricExt(n);
    FabricTypeSystem ts = (FabricTypeSystem) tc.typeSystem();
    if (ext.location() != null) {
      if (!ts.isSubtype(ext.location().type(), ts.Store())) {
        throw new SemanticException("The location needs to be a Store.", ext
            .location().position());
      }
    }
    return n;
  }
}
