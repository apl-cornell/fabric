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

import java.util.List;

import jif.types.JifContext;
import polyglot.ast.Expr;
import polyglot.ast.JL_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabric.ast.FabricUtil;
import fabric.types.FabricTypeSystem;

public class FabricNewDel extends JL_c {
  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = v.visitEdge(n, ext.location());
      ext = (NewExt_c) ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }

  @Override
  public List<Type> throwTypes(TypeSystem ts) {
    List<Type> toReturn = super.throwTypes(ts);
    Node n = this.node();
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = ext.location();
      toReturn.addAll(loc.del().throwTypes(ts));
    }
    return toReturn;
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    Node n = super.typeCheck(tc);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    FabricTypeSystem ts = (FabricTypeSystem) tc.typeSystem();
    if (ext.location() != null) {
      if (!ts.isSubtype(ext.location().type(), ts.Store())) {
        throw new SemanticException("The location needs to be a Store.", ext
            .location().position());
      }
      JifContext context = (JifContext) tc.context();
      ext =
          (NewExt_c) ext.storePrincipal(ts.exprToPrincipal(ts, ext.location(),
              context));
      n = FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }

  @Override
  public Node disambiguateOverride(Node parent, AmbiguityRemover ar)
      throws SemanticException {
    Node n = super.disambiguateOverride(parent, ar);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = ar.visitEdge(n, ext.location());
      ext = (NewExt_c) ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }

  @Override
  public Node typeCheckOverride(Node parent, TypeChecker tc)
      throws SemanticException {
    Node n = super.typeCheckOverride(parent, tc);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = tc.visitEdge(n, ext.location());
      ext = (NewExt_c) ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }
}
