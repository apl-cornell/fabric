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

import java.util.Iterator;
import java.util.HashMap;

/** Traverse the assembly hierarchy.  As each base assembly is visited, visit
 * each of its unshared referenced parts.  As each composite part is visited,
 * perform a depth-first search of its graph of atomic parts.  Return the
 * number of visited atomic parts when complete.
 */
public class T1 extends PrivatePartTraversal {
  HashMap visited = null;
  int     result  = 0;

  public static void main(String[] args) {
    new T1().mainImpl(args);
  }

  public void visitAtomicPart (AtomicPart current) {
    if (visited.containsKey(current)) return;
    visited.put(current, current);
    Iterator iter = current.from().iterator();
    while (iter.hasNext()) {
      Connection connection = (Connection) iter.next();
      AtomicPart part = connection.to();
      part.accept(this);
    }
  }

  public void visitCompositePart (CompositePart p) {
    visited = new HashMap();
    p.rootPart().accept(this);
    result += visited.size();
    visited = null;
  }
}

/*
** vim: ts=2 sw=2 cindent cino=\:0 et syntax=java
*/
