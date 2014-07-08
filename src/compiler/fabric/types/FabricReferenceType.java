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
package fabric.types;

import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import polyglot.types.ReferenceType;

/**
 * This interface extends polyglot ReferenceTypes with an updateLabel and
 * accessPolicy.
 */

public interface FabricReferenceType extends ReferenceType {
  /**
   * Return the update label that objects of this type are enforced at. This
   * label is the join in the trust ordering of the update labels of every
   * field.
   */
  Label updateLabel();

  /**
   * Return the access label that objects of this type are enforced at. This
   * label is the join in the trust ordering of the access labels of every field
   * and the confidentiality projection of every method's begin label.
   */
  ConfPolicy accessPolicy();
}
