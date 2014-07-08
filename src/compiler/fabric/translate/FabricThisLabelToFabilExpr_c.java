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
package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.LabelToJavaExpr;
import jif.translate.LabelToJavaExpr_c;
import jif.types.label.Label;
import polyglot.ast.Expr;
import fabric.visit.FabricToFabilRewriter;

/**
 * 
 */
public class FabricThisLabelToFabilExpr_c extends LabelToJavaExpr_c implements
    LabelToJavaExpr {
  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw) {

    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    Expr loc = ffrw.currentLocation();
    Expr workerPrincipal =
        rw.qq().parseExpr("Worker.getWorker().getPrincipal()");
    return rw.qq().parseExpr(
        rw.runtimeLabelUtil() + ".writerPolicyLabel(%E, %E, %E)", loc,
        workerPrincipal, workerPrincipal);
  }

}
