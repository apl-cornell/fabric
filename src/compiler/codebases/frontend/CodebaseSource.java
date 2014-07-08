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

import polyglot.frontend.Source;
import fabric.lang.security.Label;

public interface CodebaseSource {
  /**
   * The namespace loading this source.
   */
  URI namespace();

  /**
   * The short name of this source (i.e. its filename).
   * 
   * @return
   */
  String name();

  /**
   * The canonical namespace of source is the namespace its dependencies are
   * resolved in.
   * 
   * @return The canonical namespace of this source
   */
  URI canonicalNamespace();

  /**
   * Some compiler jobs are dynamically generated during compiling a resource,
   * but are not directly stored in the resource. For instance when a Fabric
   * interface is translated to FabIL, the compiler generates a *_JIF_IMPL class
   * that implements instanceof for that interface, among other things. To
   * ensure flexible handling between phases, we create a 'derived' source that
   * is backed by the link to the original resource, but permits a new job to be
   * scheduled.
   * 
   * @param name
   * @return A source derived from this CodebaseSource with a new (short) name
   */
  Source derivedSource(String name);

  /**
   * Create a derived source object with a new namespace and short name.
   * 
   * @param namespace
   * @param name
   * @return
   */
  Source publishedSource(URI namespace, String name);

  /**
   * Whether a new class object should be published for this source file and
   * associated with the codebase representing the namespace.
   * 
   * @return
   */
  boolean shouldPublish();

  /**
   * The label of this source file.
   */
  Label label();

  /** Open the source file. */
  public Reader open() throws IOException;

  /** Close the source file. */
  public void close() throws IOException;

  void setPublish(boolean pub);

}
