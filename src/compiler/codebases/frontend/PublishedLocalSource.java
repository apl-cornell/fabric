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
package codebases.frontend;

import java.io.IOException;
import java.net.URI;

import javax.tools.FileObject;

public class PublishedLocalSource extends DerivedLocalSource {

  protected PublishedLocalSource(String name, FileObject derivedFrom,
      boolean userSpecified, URI namespace) throws IOException {
    super(name, derivedFrom, userSpecified, namespace);
  }

  // Allow PublishedLocalSource to be equal to RemoteSource
  @Override
  public boolean equals(Object o) {
    if (o instanceof LocalSource) {
      return super.equals(o);
    } else if (o instanceof CodebaseSource) {
      CodebaseSource src = (CodebaseSource) o;
      return name().equals(src.name())
          && canonicalNamespace().equals(src.canonicalNamespace());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return name().hashCode() ^ namespace().hashCode();
  }

}
