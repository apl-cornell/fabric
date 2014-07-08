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
package fabric.filemanager;

import java.net.URI;

import javax.tools.JavaFileManager.Location;

import fabric.lang.Codebase;

/**
 * 
 */
public interface CodebaseLocation extends Location {
  /**
   * Returns the Codebase of this location
   */
  Codebase codebase();

  /** Returns a uri associated with this location */
  URI namespace();
}
