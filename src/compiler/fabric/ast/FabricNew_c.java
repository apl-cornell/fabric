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
package fabric.ast;

import java.util.List;

import jif.ast.JifNew_c;
import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.Term;
import polyglot.ast.Term_c;
import polyglot.ast.TypeNode;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import fabric.extension.FabricExt;
import fabric.extension.LocatedExt_c;

public class FabricNew_c extends JifNew_c {

  public FabricNew_c(Position pos, TypeNode tn, List<Expr> arguments,
      ClassBody body) {
    super(pos, tn, arguments, body);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    FabricExt fabExt = FabricUtil.fabricExt(this);
    Expr location = ((LocatedExt_c) fabExt).location();
    if (qualifier != null) {
      v.visitCFG(qualifier, tn, ENTRY);
    }

    Term last = tn;
    // if (label != null) {
    // v.visitCFG(last, label, ENTRY);
    // last = label;
    // }

    if (location != null) {
      v.visitCFG(last, location, ENTRY);
      last = location;
    }

    if (body() != null) {
      v.visitCFG(last, listChild(arguments, body()), ENTRY);
      v.visitCFGList(arguments, body(), ENTRY);
      v.visitCFG(body(), this, EXIT);
    } else {
      if (!arguments.isEmpty()) {
        v.visitCFG(last, Term_c.<Expr, Expr, Expr> listChild(arguments, null),
            ENTRY);
        v.visitCFGList(arguments, this, EXIT);
      } else {
        v.visitCFG(last, this, EXIT);
      }
    }

    return succs;
  }

}
