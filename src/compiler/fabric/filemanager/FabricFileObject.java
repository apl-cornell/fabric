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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import polyglot.filemanager.ExtFileObject;

/**
 * FabricSourceObject represents an FClass associated with a particular
 * codebase. This object is being used in FabricFileManager.
 */
public class FabricFileObject extends ExtFileObject {
  private final fabric.lang.FClass data;
  private final String name;

  public FabricFileObject(fabric.lang.FClass o, URI uri, String name) {
    super(uri, Kind.OTHER);
    data = o;
    this.name = name;
  }

  public fabric.lang.FClass getData() {
    return data;
  }

  @Override
  public InputStream openInputStream() throws IOException {
    String src = data.getSource();
    ByteArrayInputStream bais;
    try {
      bais = new ByteArrayInputStream(src.getBytes("UTF-8"));
    } catch (IOException e) {
      throw new IOException(
          "Bad Java implementation: UTF-8 encoding must be supported");
    }
    return bais;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return toUri().resolve(name).toString();
  }
}
