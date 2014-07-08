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
package fabric.extension;

import jif.extension.JifInstanceofExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.ast.Instanceof;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import fabric.types.FabricReferenceType;

/**
 * 
 */
public class FabricInstanceofExt extends JifInstanceofExt {

  public FabricInstanceofExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Expr ref = (Expr) lc.labelCheck(node().expr());
    TypeNode type = (TypeNode) lc.labelCheck(node().compareType());

    DereferenceHelper.checkAccess(ref, (FabricReferenceType) type.type(), lc,
        node().position());

    return super.labelCheck(lc);
  }

  @Override
  public Instanceof node() {
    return (Instanceof) super.node();
  }

}
