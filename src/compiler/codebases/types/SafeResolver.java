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
import java.util.Arrays;

import codebases.frontend.ExtensionInfo;
import fabric.lang.Codebase;
import fabric.lang.security.Label;

/**
 * This resolver preempts lookups to inner by first using the platform resolver.
 * This ensures the platform classes cannot be redefined by a codebase.
 * 
 * @author owen
 */
public class SafeResolver extends PathResolver implements NamespaceResolver {
  protected NamespaceResolver inner;

  public SafeResolver(ExtensionInfo extInfo, NamespaceResolver inner) {
    super(extInfo, inner.namespace(), Arrays.asList(new NamespaceResolver[] {
        extInfo.typeSystem().platformResolver(), inner }));
    this.inner = inner;
  }

  @Override
  public URI resolveCodebaseNameImpl(String name) {
    return inner.resolveCodebaseName(name);
  }

  @Override
  public boolean loadRawClasses(boolean use) {
    return inner.loadRawClasses(use);
  }

  @Override
  public boolean loadEncodedClasses(boolean use) {
    return inner.loadEncodedClasses(use);
  }

  @Override
  public boolean loadSource(boolean use) {
    return inner.loadSource(use);
  }

  @Override
  public Label label() {
    return inner.label();
  }

  @Override
  public Codebase codebase() {
    return inner.codebase();
  }

}
