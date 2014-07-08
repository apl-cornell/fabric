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
import java.io.Reader;
import java.net.URI;

import javax.tools.FileObject;

import jif.parse.UTF8FileSource;
import polyglot.frontend.Source;
import polyglot.util.InternalCompilerError;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;

public class LocalSource extends UTF8FileSource implements CodebaseSource {
  protected URI namespace;
  protected boolean publish;
  protected Reader reader;

  public LocalSource(FileObject f, boolean userSpecified, URI namespace)
      throws IOException {
    this(f, userSpecified, namespace, true);
  }

  public LocalSource(FileObject f, boolean userSpecified, URI namespace,
      boolean publish) throws IOException {
    super(f, userSpecified);
    this.namespace = namespace;
    this.publish = publish;
  }

  @Override
  public URI namespace() {
    return namespace;
  }

  @Override
  public URI canonicalNamespace() {
    return namespace;
  }

  @Override
  public boolean shouldPublish() {
    return publish;
  }

  @Override
  public void setPublish(boolean pub) {
    this.publish = pub;
  }

  protected FileObject fileObject() {
    return this;
  }

  @Override
  public Source derivedSource(String name) {
    try {
      return new DerivedLocalSource(name, this, false, namespace);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Source publishedSource(URI namespace, String name) {
    // XXX: this is fudging it a little so we can have LocalSources equal
    // RemoteSources when we compile down to bytecode.
    // we define equality on name and NS only if path is null
    try {
      return new PublishedLocalSource(name, this, false, namespace);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Label label() {
    return LabelUtil._Impl.noComponents();
  }

  /**
   * Sources are equal to each other if they refer to the same resource.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof LocalSource) {
      LocalSource s = (LocalSource) o;
      return toUri().equals(s.toUri());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return toUri().hashCode();
  }

  @Override
  public void close() throws IOException {
    if (reader != null) {
      reader.close();
      reader = null;
    }
  }

  @Override
  public Reader open() throws IOException {
    return openReader(false);
  }
}
