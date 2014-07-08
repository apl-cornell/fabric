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
package fabric.types;

import jif.types.JifClassType;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.AccessPathLocal;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.util.Position;

/**
 * 
 */
public class AccessPathLocalWorker extends AccessPathLocal {

  /**
   * @param li
   * @param name
   * @param pos
   */
  public AccessPathLocalWorker(WorkerLocalInstance li, Position pos) {
    super(li, "worker$", pos);
  }

  @Override
  public PathMap labelcheck(JifContext A, LabelChecker lc) {
    FabricTypeSystem ts = (FabricTypeSystem) A.typeSystem();
    Label l = ts.thisLabel(this.position(), (JifClassType) A.currentClass());
    return ts.pathMap().N(l).NV(l);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof AccessPathLocalWorker)
      return li == ((AccessPathLocalWorker) other).li;
    else return false;
  }
}
