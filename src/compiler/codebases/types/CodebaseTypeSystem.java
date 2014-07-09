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
package codebases.types;

import java.net.URI;

import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.types.LazyClassInitializer;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import fabric.lang.Codebase;

public interface CodebaseTypeSystem extends TypeSystem {

  CBImportTable importTable(Source source, URI ns, Package pkg);

  void initialize(ExtensionInfo extensionInfo) throws SemanticException;

  NamespaceResolver namespaceResolver(URI uri);

  NamespaceResolver platformResolver();

  boolean packageExists(URI namespace, String name);

  Named forName(URI namespace, String name) throws SemanticException;

  Package packageForName(URI ns, Package prefix, String name)
      throws SemanticException;

  Package createPackage(URI ns, Package prefix, String name);

  Package packageForName(URI ns, String name) throws SemanticException;

  Codebase codebaseFromNS(URI namespace);

  /**
   * @param init
   * @param fromSource
   * @param ns
   * @return
   */
  ParsedClassType createClassType(LazyClassInitializer init, Source fromSource,
      URI ns);
}
