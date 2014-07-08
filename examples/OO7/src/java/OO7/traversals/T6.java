/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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

/**
 * Traversal T6: Sparse traversal speed. Traverse the assembly hierarchy. As
 * each base assembly is visited, visit each of its referenced unshared
 * composite parts. As each composite part is visited, visit the root atomic
 * part. Return a count of the number of atomic parts visited when done. Note
 * that this is like T1 except that instead of doing a DFS on all of the atomic
 * parts in the composite part, T6 just visits one (the root part).
 */
public class T6 extends PrivatePartTraversal {
  private int result = 0;

  public static void main(String[] args) {
    new T6().mainImpl(args);
  }

  public void visitAtomicPart(AtomicPart current) {
    result++;
  }

  public void visitCompositePart(CompositePart p) {
    p.rootPart().accept(this);
  }
}

/*
 * * vim: ts=2 sw=2 cindent cino=\:0 et syntax=java
 */
