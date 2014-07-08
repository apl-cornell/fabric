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

import jif.extension.JifArrayAccessExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;
import polyglot.ast.ArrayAccess;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import fabric.types.FabricReferenceType;

/**
 * 
 */
public class FabricArrayAccessExt extends JifArrayAccessExt {

  /**
   * @param toJava
   */
  public FabricArrayAccessExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    ArrayAccess n = (ArrayAccess) super.labelCheck(lc);
    Expr array = n.array();
    Type type = lc.typeSystem().unlabel(array.type());
    DereferenceHelper.checkAccess(array, (FabricReferenceType) type, lc, node()
        .position());
    return n;
  }

}
