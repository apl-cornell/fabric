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

import jif.types.ConstArrayType;

/**
 * Unlike FabIL, we follow the jif const array design here. All ArrayTypes
 * created by Jif's Type System are Jif ConstArrayTypes. Similarly, the
 * FabricTypeSystem creates only FabricArrayTypes. In addition to the flags to
 * determine whether an ArrayType is const or not (inherited from
 * ConstArrayType), we add a flag to determine whether the ArrayType is native.
 * 
 * @author mdgeorge
 */
public interface FabricArrayType extends ConstArrayType, FabricReferenceType {
  /** Type is a native (java) array type */
  boolean isNative();
}
