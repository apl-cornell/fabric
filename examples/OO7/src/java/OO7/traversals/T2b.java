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
package OO7.traversals;

import OO7.*;

import java.util.Iterator;
import java.util.HashMap;

/**
 * Traversal T2: Traversal with updates Repeat Traversal T1, but update objects
 * during the traversal. There are three types of update patterns in this
 * traversal. In each, a single update to an atomic part consists of swapping
 * its (x,y) attributes. The three types of updates are: B Update every atomic
 * part as it is encountered.
 */
public class T2b extends PrivatePartTraversal {
  HashMap visited = null;

  public static void main(String[] args) {
    new T2b().mainImpl(args);
  }

  public void visitAtomicPart(AtomicPart current) {
    long x = current.x();
    current.setX(current.y());
    current.setY(x);

    Iterator iter = current.from().iterator();
    while (iter.hasNext()) {
      Connection connection = (Connection) iter.next();
      AtomicPart part = connection.to();
      if (!visited.containsKey(part)) {
        visited.put(part, part);
        part.accept(this);
      }
    }
  }

  public void visitCompositePart(CompositePart p) {
    visited = new HashMap();
    p.rootPart().accept(this);
    visited = null;
  }
}

/*
 * * vim: ts=2 sw=2 cindent cino=\:0 et syntax=java
 */
