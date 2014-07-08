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

import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Pass;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.TypeExists;
import polyglot.types.TypeSystem;

public class CBTypeExists extends TypeExists {
  public static Goal create(Scheduler scheduler, URI ns, String name) {
    return scheduler.internGoal(new CBTypeExists(ns, name));
  }

  protected URI namespace;
  protected String typeName;

  protected CBTypeExists(URI ns, String name) {
    super(name);
    this.namespace = ns;
  }

  @Override
  public Pass createPass(ExtensionInfo extInfo) {
    TypeSystem ts = extInfo.typeSystem();
    return new CBTypeExistsPass(extInfo.scheduler(), ts, this);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof CBTypeExists
        && ((CBTypeExists) o).namespace.equals(namespace) && super.equals(o);
  }

  @Override
  public String toString() {
    return "CBTypeExists( [" + namespace + "] " + typeName + ")";
  }

  public URI namespace() {
    return namespace;
  }

}
