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

import java.net.URI;

import polyglot.frontend.Pass;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.TypeExists;
import polyglot.frontend.passes.TypeExistsPass;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorInfo;
import polyglot.util.ErrorQueue;
import codebases.types.CodebaseTypeSystem;

public class CBTypeExistsPass extends TypeExistsPass implements Pass {

  private final CodebaseTypeSystem ts;

  public CBTypeExistsPass(Scheduler scheduler, TypeSystem ts, TypeExists goal) {
    super(scheduler, ts, goal);
    this.ts = (CodebaseTypeSystem) ts;
  }

  @Override
  public boolean run() {
    URI ns = ((CBTypeExists) goal).namespace();
    String name = goal.typeName();
    try {
      // Try to resolve the type; this may throw a
      // MissingDependencyException on the job to load the file
      // containing the type.
      Named n = ts.namespaceResolver(ns).find(name);
      if (n instanceof Type) {
        return true;
      }
    } catch (SemanticException e) {
      ErrorQueue eq = ts.extensionInfo().compiler().errorQueue();
      eq.enqueue(ErrorInfo.SEMANTIC_ERROR, e.getMessage(), e.position());
    }
    return false;
  }

}
