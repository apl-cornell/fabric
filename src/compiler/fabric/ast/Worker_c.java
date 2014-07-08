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
package fabric.ast;

import polyglot.ast.Id;
import polyglot.ast.Local_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;
import fabric.types.FabricTypeSystem;

public class Worker_c extends Local_c implements Worker {
  public Worker_c(Position pos, Id workerId) {
    super(pos, workerId);
  }

  /**
   * @throws SemanticException
   */
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) tc.typeSystem();

    Worker c = (Worker) this.type(ts.Worker());
    c = (Worker) c.localInstance(ts.workerLocalInstance());

    return c;
  }

  @Override
  public String toString() {
    return name();
  }

  // @Override
  // public boolean isConstant() {
  // return true;
  // }
}
