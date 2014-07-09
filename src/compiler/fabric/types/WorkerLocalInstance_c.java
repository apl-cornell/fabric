/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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

import jif.types.JifLocalInstance_c;
import jif.types.JifTypeSystem;
import jif.types.label.Label;
import polyglot.types.Flags;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 * There can be only one worker$. When we read signatures from class files, use
 * the TypeSystem's workerLocalInstance
 */
public class WorkerLocalInstance_c extends JifLocalInstance_c implements
    WorkerLocalInstance {
  public WorkerLocalInstance_c(JifTypeSystem ts, Position pos, Flags flags,
      Type type, String name) {
    super(ts, pos, flags, type, name);
  }

  @Override
  public Object intern() {
    return ((FabricTypeSystem) ts).workerLocalInstance();
  }

  @Override
  public Label label() {
    FabricTypeSystem ts = (FabricTypeSystem) this.ts;
    return ts.bottomLabel();
  }
}
