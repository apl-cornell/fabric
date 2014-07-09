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
package fabric.filemanager;

import java.net.URI;

import javax.tools.JavaFileManager.Location;

import fabric.common.NSUtil;
import fabric.lang.Codebase;

/**
 * Concrete implementation of FabricLocation
 */
public class CodebaseLocation_c implements CodebaseLocation {

  private final String name;
  private final URI namespace;
  private transient Codebase codebase;

  /**
   * @param namespace
   */
  public CodebaseLocation_c(URI namespace) {
    this.name = namespace.toString();
    this.namespace = namespace;
    this.codebase = NSUtil.fetch_codebase(namespace);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isOutputLocation() {
    return false;
  }

  @Override
  public Codebase codebase() {
    return codebase;
  }

  @Override
  public URI namespace() {
    return namespace;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Location) {
      Location f = (Location) o;
      return f.getName().equals(name);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }

}
