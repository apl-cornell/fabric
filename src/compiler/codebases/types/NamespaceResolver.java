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
package codebases.types;

import java.net.URI;
import java.util.Map;

import polyglot.types.Importable;
import polyglot.types.SemanticException;
import polyglot.types.TopLevelResolver;
import polyglot.util.Copy;
import fabric.lang.Codebase;
import fabric.lang.security.Label;

public interface NamespaceResolver extends TopLevelResolver, Copy {

  /**
   * Check if a type object is in the cache, returning null if not.
   * 
   * @param name
   *          The name to search for.
   */
  Importable check(String name);

  /**
   * Add an importable type object to the cache.
   * 
   * @param name
   *          The name of the new type.
   * @param q
   *          The type object.
   * @throws SemanticException
   *           Throws when a package or type with the same name already exists
   */
  void add(String name, Importable q) throws SemanticException;

  /**
   * Add a type object to the cache, regardless of whether it exists already.
   * 
   * @param name
   *          The name of the new type.
   * @param q
   *          The type object.
   */
  void replace(String name, Importable q);

  /**
   * The URI of this namespace
   */
  URI namespace();

  /**
   * Get the URI of a codebase using a local alias.
   * 
   * @throws SemanticException
   */
  URI resolveCodebaseName(String name);

  Map<String, URI> codebaseAliases();

  /**
   * Find a type object in this namespace by name.
   * 
   * @param name
   *          The name to search for.
   */
  @Override
  Importable find(String name) throws SemanticException;

  /**
   * Find a type object in this namespace by name.
   * 
   * @param name
   *          The name to search for.
   */
  Importable findImpl(String name) throws SemanticException;

  /**
   * Check if a package exists.
   * 
   * @param name
   *          The name to search for.
   */
  boolean packageExistsImpl(String name);

  /**
   * Specify whether to use encoded class files to resolve names in this
   * namespace.
   * 
   * @return previous value
   */
  boolean loadEncodedClasses(boolean use);

  /**
   * Specify whether to use raw class files to resolve names in this namespace.
   * 
   * @return previous value
   */
  boolean loadRawClasses(boolean use);

  /**
   * Specify whether to use raw class files to resolve names in this namespace.
   * 
   * @return previous value
   */
  boolean loadSource(boolean use);

  /**
   * Returns codebase if this namespace is backed by a codebase, otherwise null.
   * 
   * @return
   */
  Codebase codebase();

  /**
   * An (flow-lattice) upper bound on the integrity and confidentiality of
   * resolution through this namespace.
   * 
   * @return
   */
  Label label();

  URI resolveCodebaseNameImpl(String name);
}
