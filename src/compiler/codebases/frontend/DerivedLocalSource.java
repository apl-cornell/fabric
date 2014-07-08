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
package codebases.frontend;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;

import javax.tools.FileObject;

public class DerivedLocalSource extends LocalSource {
  private final String name;
  private URI uri;

  protected DerivedLocalSource(String name, FileObject derivedFrom,
      boolean userSpecified, URI namespace) throws IOException {
    super(derivedFrom, userSpecified, namespace, false);
    this.name = name;
  }

  /**
   * Open the source file. For compiler generated source, open the file this
   * source is derived from.
   */
  @Override
  public Reader open() throws IOException {
    if (reader == null) reader = fileObject().openReader(false);
    return reader;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public URI toUri() {
    if (uri == null) {
      File f = new File(super.toUri().getPath()).getParentFile();
      uri = new File(f, name).toURI();
    }
    return uri;
  }

}
