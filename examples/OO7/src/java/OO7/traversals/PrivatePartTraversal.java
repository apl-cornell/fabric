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
package OO7.traversals;

import OO7.*;

import java.util.Iterator;

/**
 * Common base class for various traversals. This class represents a visitor
 * that traverses the assembly hierarchy and then operates on the composite
 * parts in some way.
 */
public abstract class PrivatePartTraversal extends Traversal {

  public void visitBenchmark(Benchmark b) {
    Iterator i = b.modulesById().values().iterator();
    while (i.hasNext())
      visitModule((Module) i.next());
  }

  public void visitModule(Module m) {
    m.designRoot().accept(this);
  }

  public void visitComplexAssembly(ComplexAssembly ca) {
    Iterator i = ca.subAssemblies().iterator();
    while (i.hasNext())
      ((Assembly) i.next()).accept(this);
  }

  public void visitBaseAssembly(BaseAssembly ba) {
    Iterator i = ba.componentsShar().iterator();
    while (i.hasNext())
      ((CompositePart) i.next()).accept(this);
  }
}

/*
 * * vim: ts=2 sw=2 cindent cino=\:0 et syntax=java
 */
