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
package fabric.types;

import java.net.URI;
import java.util.Collection;
import java.util.Set;

import jif.types.JifParsedPolyType;
import polyglot.types.MethodInstance;
import codebases.types.CodebaseClassType;

public interface FabricParsedClassType extends JifParsedPolyType,
    FabricClassType, CodebaseClassType {

  void removeMethod(MethodInstance mi);

  void setCanonicalNamespace(URI ns);

  /**
   * @param Namespace
   *          dependencies
   */
  void setNamespaceDependencies(Set<CodebaseClassType> dependencies);

  /**
   * @return
   */
  Collection<CodebaseClassType> namespaceDependencies();

}
