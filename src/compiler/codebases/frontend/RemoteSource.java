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

import java.io.IOException;
import java.io.Reader;
import java.net.URI;

import javax.tools.FileObject;

import jif.parse.UTF8FileSource;
import polyglot.frontend.Source;
import polyglot.util.InternalCompilerError;
import fabric.common.NSUtil;
import fabric.filemanager.FabricFileObject;
import fabric.lang.FClass;
import fabric.lang.security.Label;

public class RemoteSource extends UTF8FileSource implements CodebaseSource {
  protected final URI namespace;
  protected final FClass fcls;
  protected boolean publish;

  protected Reader reader = null;

  public RemoteSource(FileObject fo, FClass fcls, boolean userSpecified)
      throws IOException {
    this(fo, fcls, userSpecified, false);
  }

  public RemoteSource(FileObject fo, FClass fcls, boolean userSpecified,
      boolean publish) throws IOException {
    super(fo, userSpecified);
    this.fcls = fcls;
    this.namespace = NSUtil.namespace(fcls.getCodebase());
    this.publish = publish;
  }

  @Override
  public boolean shouldPublish() {
    return publish;
  }

  @Override
  public void close() throws IOException {
    if (reader != null) {
      reader.close();
      reader = null;
    }
  }

  /**
   * Sources are equal to each other if they refer to the same resource.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof CodebaseSource) {
      CodebaseSource s = (CodebaseSource) o;
      return name().equals(s.name())
          && canonicalNamespace().equals(s.canonicalNamespace());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return name().hashCode() ^ canonicalNamespace().hashCode();
  }

  @Override
  public URI namespace() {
    return namespace;
  }

  @Override
  public URI canonicalNamespace() {
    return NSUtil.namespace(fcls.getCodebase());
  }

  @Override
  public Source derivedSource(final String name) {
    try {
      return new RemoteSource(new FabricFileObject(fcls,
          NSUtil.absoluteName(fcls), name), fcls, user_specified, false);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Source publishedSource(final URI ns, final String name) {
    try {
      return new RemoteSource(new FabricFileObject(fcls,
          NSUtil.absoluteName(fcls), name), fcls, user_specified, false) {
        @Override
        public String path() {
          return ns.resolve(name).toString();
        }
      };
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Label label() {
    return fcls.get$$updateLabel();
  }

  @Override
  public void setPublish(boolean pub) {
    // We're not supporting this yet.
    throw new InternalCompilerError("Tried to republish remote source!");
  }

  @Override
  public Reader open() throws IOException {
    return openReader(false);
  }

  @Override
  public String path() {
    return toUri().toString() + ".fab";
  }

  @Override
  public String toString() {
    return path();
  }
}
