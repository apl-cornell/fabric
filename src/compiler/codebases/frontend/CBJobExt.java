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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import polyglot.frontend.JobExt;
import codebases.types.CodebaseClassType;

public class CBJobExt implements JobExt {
  protected Set<CodebaseClassType> dependencies;
  protected Map<CodebaseClassType, String> externalDeps;

  public CBJobExt() {
    dependencies = new HashSet<CodebaseClassType>();
    externalDeps = new HashMap<CodebaseClassType, String>();
  }

  public boolean addDependency(CodebaseClassType ct) {
    // System.err.println("ADDING DEPENDENCY:" + ct + " :"
    // +ct.canonicalNamespace() + ":"+ct.getClass());

    return dependencies.add(ct);
  }

  public Set<CodebaseClassType> dependencies() {
    return Collections.unmodifiableSet(dependencies);
  }

  public boolean addExternalDependency(CodebaseClassType ct, String alias) {
    return externalDeps.put(ct, alias) == null;
  }

  public boolean isExternal(CodebaseClassType ct) {
    return externalDeps.containsKey(ct);
  }

  public String aliasFor(CodebaseClassType ct) {
    return externalDeps.get(ct);
  }

}
