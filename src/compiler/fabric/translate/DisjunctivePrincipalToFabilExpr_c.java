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

import jif.translate.DisjunctivePrincipalToJavaExpr_c;
import jif.translate.JifToJavaRewriter;
import jif.types.JifTypeSystem;
import jif.types.principal.DisjunctivePrincipal;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import fabric.visit.FabricToFabilRewriter;

public class DisjunctivePrincipalToFabilExpr_c extends
    DisjunctivePrincipalToJavaExpr_c {
  @Override
  public Expr toJava(Principal principal, JifToJavaRewriter rw)
      throws SemanticException {
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    JifTypeSystem ts = rw.jif_ts();
    Expr e = null;
    DisjunctivePrincipal dp = (DisjunctivePrincipal) principal;

    for (Principal p : dp.disjuncts()) {
      Expr pe = rw.principalToJava(p);
      if (e == null) {
        e = pe;
      } else {
        e =
            rw.qq().parseExpr(
                ts.PrincipalUtilClassName() + ".disjunction(%E, %E, %E)",
                ffrw.currentLocation(), pe, e);
      }
    }
    return e;
  }

}
