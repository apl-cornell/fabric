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

import jif.types.JifFieldInstance_c;
import jif.types.label.ConfPolicy;
import polyglot.types.Flags;
import polyglot.types.ReferenceType;
import polyglot.types.Type;
import polyglot.util.Position;

public class FabricFieldInstance_c extends JifFieldInstance_c implements
    FabricFieldInstance {

  public FabricFieldInstance_c(FabricTypeSystem ts, Position pos,
      ReferenceType container, Flags flags, Type type, ConfPolicy accessLabel,
      String name) {
    super(ts, pos, container, flags, type, name);
    this.accessLabel = accessLabel;
  }

  protected ConfPolicy accessLabel;

  @Override
  public ConfPolicy accessPolicy() {
    return accessLabel;
  }

  @Override
  public void setAccessPolicy(ConfPolicy accessLabel) {
    this.accessLabel = accessLabel;
  }

}
