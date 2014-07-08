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

import fabil.ast.FabILNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.DynamicPrincipalToJavaExpr_c;
import jif.translate.JifToJavaRewriter;
import jif.types.label.AccessPathLocal;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;

public class DynamicPrincipalToFabilExpr_c extends DynamicPrincipalToJavaExpr_c {
  @Override
  public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException {
    DynamicPrincipal dp = (DynamicPrincipal)principal;
    if (dp.path() instanceof AccessPathLocal) {
      AccessPathLocal apl = (AccessPathLocal)dp.path();
      LocalInstance li = apl.localInstance();

      FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();
      FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();

      if (ts.equals(li, ts.workerLocalInstance())) {
        // Local worker.
        return nf.Call(li.position(), 
//                       nf.Local(li.position(), nf.Id(li.position(), "worker$")),
                       rw.qq().parseExpr("worker$"),
                       nf.Id(Position.compilerGenerated(), "getPrincipal"));
      }
      else if (ts.equals(li.type(), ts.RemoteWorker())) {
        // Remote worker
        return nf.Call(li.position(), 
                       nf.Local(li.position(), nf.Id(li.position(), li.name())), 
                       nf.Id(Position.compilerGenerated(), "getPrincipal"));
      }
      else if (ts.equals(li.type(), ts.Store())) {
        // Store
        return nf.Call(li.position(), 
                       nf.Local(li.position(), nf.Id(li.position(), li.name())), 
                       nf.Id(Position.compilerGenerated(), "getPrincipal"));
      }
    }
    
    return super.toJava(principal, rw);
  }
}
