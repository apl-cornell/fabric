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

import jif.extension.JifNewArrayExt;
import jif.translate.ToJavaExt;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.NewArray;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import fabric.ast.FabricUtil;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;

public class NewArrayJifExt_c extends JifNewArrayExt {
  public NewArrayJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    NewArray na = (NewArray) node();
    NewFabricArrayExt_c ext = (NewFabricArrayExt_c) FabricUtil.fabricExt(na);

    Type baseType = na.baseType().type();
    while (baseType.isArray()) {
      baseType = baseType.toArray().base();
    }
    if (baseType instanceof FabricClassType) {
      FabricClassType ct = (FabricClassType) baseType;
      // TODO: Implement access label checks for arrays
      FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
      Label accessLabel = ts.toLabel(ct.accessPolicy());
      ext.labelCheck(lc, ct.updateLabel(), accessLabel);
    }

    return super.labelCheck(lc);
  }
}
