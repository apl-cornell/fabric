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

import jif.extension.JifCastExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;
import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import fabric.types.FabricReferenceType;

public class FabricCastExt extends JifCastExt {

  public FabricCastExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {

    // TODO: this causes ref and n to be label checked twice, but it seems
    // there's no way around it.

    Expr ref = (Expr) lc.labelCheck(node().expr());
    TypeNode n = (TypeNode) lc.labelCheck(node().castType());

    if (n.type().isReference())
      DereferenceHelper.checkAccess(ref, (FabricReferenceType) n.type(), lc,
          node().position());

    return super.labelCheck(lc);
  }

  @Override
  public Cast node() {
    return (Cast) super.node();
  }
}
