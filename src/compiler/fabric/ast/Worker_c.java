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

import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;

public class Worker_c extends Local_c implements Worker {
  public Worker_c(Position pos, NodeFactory nf) {
    super(pos, nf.Id(pos, "worker$"));
  }
  
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();

    Worker c = (Worker)this.type(ts.Worker());
    c = (Worker)c.localInstance(ts.workerLocalInstance());
    
    return c;
  }
  
  @Override
  public String toString() {
    return "worker$";
  }
  
//  @Override
//  public boolean isConstant() {
//    return true;
//  }
}
