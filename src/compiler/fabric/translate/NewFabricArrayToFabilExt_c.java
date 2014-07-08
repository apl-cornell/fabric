/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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
import jif.translate.NewArrayToJavaExt_c;
import jif.types.label.Label;
import polyglot.ast.Expr;
import polyglot.ast.NewArray;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.visit.NodeVisitor;
import fabil.ast.FabricArrayInit;
import fabil.ast.FabILNodeFactory;
import fabric.ast.FabricUtil;
import fabric.ast.NewFabricArray;
import fabric.extension.NewFabricArrayExt_c;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

public class NewFabricArrayToFabilExt_c extends NewArrayToJavaExt_c {
  protected Type baseType;

  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) {
    NewFabricArray n = (NewFabricArray) node();
    baseType = n.baseType().type();
    return rw;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    NewFabricArray n = (NewFabricArray) node();
    NewFabricArrayExt_c ext = (NewFabricArrayExt_c) FabricUtil.fabricExt(n);

    FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();
    FabricTypeSystem ts = (FabricTypeSystem) rw.jif_ts();

    Type base = baseType;
    while (base.isArray()) {
      base = base.toArray().base();
    }

    Label baseLabel = null;
    if (base instanceof FabricClassType && ts.isFabricClass(base)) {
      baseLabel = ((FabricClassType)base).defaultFieldLabel();
    }
    Expr labelExpr = null;
    if (baseLabel != null) {
      labelExpr = rw.labelToJava(baseLabel);
      
      Expr loc = ext.location();
      if (loc == null) loc = nf.StoreGetter(n.position());
      if (loc != null) {
        FabricToFabilRewriter ffrw = (FabricToFabilRewriter)rw;
        labelExpr = ffrw.updateLabelLocation(labelExpr, loc);
      }
    }

    return nf.NewFabricArray(n.position(), n.baseType(), labelExpr, ext.location(), n
        .dims(), n.additionalDims(), (FabricArrayInit) n.init());
  }
}
