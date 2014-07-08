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

import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt;
import jif.translate.ToJavaExt_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabric.ast.Store;
import fabric.types.FabricTypeSystem;

/**
 * 
 */
public class StoreToFabilExt_c extends ToJavaExt_c implements ToJavaExt {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Store store = (Store) node();
    FabricTypeSystem ts = (FabricTypeSystem) rw.jif_ts();
    if (store.isLocalStore()) {
      return rw.qq().parseExpr("Worker.getWorker().getLocalStore()");
    }
    /* TODO XXX HUGE HACK. WE SHOULD NOT CALL fetch(). REMOVE AFTER SURROGATES PROBLEM IS FIXED. */
    return rw.qq().parseExpr("%E.fetch().$getStore()", store.expr());
  }

}
