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
package fabric.ast;

import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import jif.ast.AmbPrincipalNode_c;

/**
 * In Fabric, objects of <code>Worker</code> and <code>RemoteWorker</code> are 
 * treated as principals automatically.
 * 
 * @author qixin
 */
public class FabricAmbPrincipalNode_c extends AmbPrincipalNode_c {
  public FabricAmbPrincipalNode_c(Position pos, Expr expr) {
    super(pos, expr);
  }
  
  public FabricAmbPrincipalNode_c(Position pos, Id name) {
    super(pos, name);
  }
  
  @Override
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
    if (expr != null && expr instanceof Worker) {
      // Local worker principal.
      FabricTypeSystem ts = (FabricTypeSystem)ar.typeSystem();
      FabricNodeFactory nf = (FabricNodeFactory)ar.nodeFactory();

      return nf.CanonicalPrincipalNode(position(), ts.workerPrincipal(position()));
    }
    
    return super.disambiguate(ar);
  }  
}
