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
package fabric.types;

import jif.types.JifClassType;
import jif.types.label.Label;

public interface FabricClassType extends JifClassType {
  
  /**
   * Return the label associated with this class' fields, or null if this class
   * has no fields.
   */
  Label defaultFieldLabel();
  
  
  /**
   * Same behavior as above, except change the field labels
   * of Principal classes, so that they don't mention 'this'
   */
  Label defaultFabilFieldLabel();
  
}
