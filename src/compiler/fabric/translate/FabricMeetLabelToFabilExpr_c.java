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
package fabric.translate;

import java.util.Iterator;
import java.util.LinkedList;

import jif.translate.JifToJavaRewriter;
import jif.translate.MeetLabelToJavaExpr_c;
import jif.types.label.Label;
import jif.types.label.MeetLabel;
import polyglot.ast.Expr;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import fabric.visit.FabricToFabilRewriter;

public class FabricMeetLabelToFabilExpr_c extends MeetLabelToJavaExpr_c {
  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw)
      throws SemanticException {
    MeetLabel L = (MeetLabel) label;

    if (L.meetComponents().size() == 1) {
      return rw.labelToJava(L.meetComponents().iterator().next());
    }

    boolean simplify = true;
    if (rw.context().currentCode() instanceof ConstructorInstance
        && rw.currentClass().isSubtype(rw.jif_ts().PrincipalClass()))
      simplify = false;

    LinkedList<Label> l = new LinkedList<Label>(L.meetComponents());
    Iterator<Label> iter = l.iterator();
    Label head = iter.next();
    Expr e = rw.labelToJava(head);
    while (iter.hasNext()) {
      head = iter.next();
      Expr f = rw.labelToJava(head);
      Expr loc = ((FabricToFabilRewriter) rw).currentLocation();
      e =
          rw.qq().parseExpr("%E.meet(%E, %E, %E)", e, loc, f,
              rw.java_nf().BooleanLit(Position.compilerGenerated(), simplify));

    }
    return e;
  }

}
