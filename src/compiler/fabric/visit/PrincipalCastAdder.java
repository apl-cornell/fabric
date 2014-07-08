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
package fabric.visit;

import fabric.types.FabricTypeSystem;
import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;

public class PrincipalCastAdder extends AscriptionVisitor {
  public PrincipalCastAdder(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }
  
  @Override
  public Expr ascribe(Expr e, Type toType) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
    if (ts.isPrincipal(toType) 
     && (ts.typeEquals(ts.Worker(), e.type()) 
      || ts.typeEquals(ts.RemoteWorker(), e.type())
      || ts.typeEquals(ts.Store(), e.type()))) {
      Cast result = nf.Cast(e.position(), 
                            nf.CanonicalTypeNode(Position.compilerGenerated(), toType), 
                            e);
      return result.type(toType);
    }
    return e;
  }
}
