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

import jif.ast.Jif_c;
import jif.extension.JifNewExt;
import jif.translate.ToJavaExt;
import jif.types.label.AccessPath;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.New;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import fabric.ast.FabricUtil;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;

public class NewJifExt_c extends JifNewExt {
  public NewJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    New n = (New) super.labelCheck(lc);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);

    Type newType = n.objectType().type();
    // Bypass check if this is a principal object. This condition will be
    // enforced with the $addDefaultDelegates method
    if (newType instanceof FabricClassType
        && !newType.isSubtype(((FabricTypeSystem) lc.typeSystem())
            .DelegatingPrincipal())) {
      FabricClassType ct = (FabricClassType) newType;
      FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();

      Label accessLabel = ts.toLabel(ct.accessPolicy());
      AccessPath storeap;
      if (ext.location() != null)
        storeap = ts.exprToAccessPath(ext.location(), lc.jifContext());
      else storeap = null;

      if (accessLabel != null) {
        accessLabel =
            StoreInstantiator.instantiate(accessLabel, lc.jifContext(), n,
                newType.toReference(), Jif_c.getPathMap(n).NV(), storeap);
      }

      Label objectLabel = ct.updateLabel();

      if (objectLabel != null) {
        objectLabel =
            StoreInstantiator.instantiate(objectLabel, lc.jifContext(), n,
                newType.toReference(), Jif_c.getPathMap(n).NV(), storeap);
      }

      ext.labelCheck(lc, objectLabel, accessLabel);
    }

    return n;
  }
}
