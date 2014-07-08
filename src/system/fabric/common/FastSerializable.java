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
package fabric.common;

import java.io.DataOutput;
import java.io.IOException;

/**
 * A common interface for all classes that implement custom serialization. It's
 * called <code>FastSerializable</code> because Java serialization is so frick'n
 * slow.
 * <p>
 * (We'd call this "FabricSerializable", but then that kinda sounds like a
 * Fabric object that's somehow "serializable".)
 * </p>
 */
public interface FastSerializable {
  void write(DataOutput out) throws IOException;
}
