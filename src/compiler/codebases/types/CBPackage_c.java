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

import polyglot.types.Package;
import polyglot.types.Package_c;
import polyglot.types.Resolver;
import polyglot.types.TypeSystem;

public class CBPackage_c extends Package_c implements CBPackage {
  protected URI namespace;

  /** Used for deserializing types. */
  protected CBPackage_c() {
  }

  public CBPackage_c(TypeSystem ts, URI ns) {
    this(ts, ns, null, null);
  }

  public CBPackage_c(TypeSystem ts, URI ns, String name) {
    this(ts, ns, null, name);
  }

  public CBPackage_c(TypeSystem ts, URI ns, Package prefix, String name) {
    super(ts, prefix, name);
    this.namespace = ns;
  }

  @Override
  public Resolver resolver() {
    if (memberCache == null) {
      memberCache =
          ((CodebaseTypeSystem) ts).createPackageContextResolver(this);
    }
    return memberCache;
  }

  @Override
  public URI namespace() {
    return namespace;
  }

}
