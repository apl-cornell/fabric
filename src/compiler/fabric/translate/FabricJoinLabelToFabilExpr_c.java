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
package fabric.translate;

import java.util.Iterator;
import java.util.LinkedList;

import jif.translate.JifToJavaRewriter;
import jif.translate.JoinLabelToJavaExpr_c;
import jif.types.label.JoinLabel;
import jif.types.label.Label;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import fabric.visit.FabricToFabilRewriter;

public class FabricJoinLabelToFabilExpr_c extends JoinLabelToJavaExpr_c {
  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw)
      throws SemanticException {
    JoinLabel L = (JoinLabel) label;

    if (L.joinComponents().size() == 1) {
      return rw.labelToJava(L.joinComponents().iterator().next());
    }

    LinkedList<Label> l = new LinkedList<Label>(L.joinComponents());
    Iterator<Label> iter = l.iterator();
    Label head = iter.next();
    Expr e = rw.labelToJava(head);
    while (iter.hasNext()) {
      head = iter.next();
      Expr f = rw.labelToJava(head);
      Expr loc = ((FabricToFabilRewriter) rw).currentLocation();
      e = rw.qq().parseExpr("%E.join(%E, %E)", e, loc, f);
    }
    return e;
  }

}
