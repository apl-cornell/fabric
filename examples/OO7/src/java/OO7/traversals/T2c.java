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

/**
 * Traversal T2: Traversal with updates Repeat Traversal T1, but update objects
 * during the traversal. There are three types of update patterns in this
 * traversal. In each, a single update to an atomic part consists of swapping
 * its (x,y) attributes. The three types of updates are: C Update each atomic
 * part in a composite part four times. When done, return the number of update
 * operations that were actually performed.
 */
public class T2c extends PrivatePartTraversal {
  HashMap visited = null;
  int result = 0;

  public static void main(String[] args) {
    new T2c().mainImpl(args);
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
    for (int i = 0; i < 4; i++) {
      visited = new HashMap();
      p.rootPart().accept(this);
      result += visited.size();
      visited = null;
    }
  }
}

/*
 * * vim: ts=2 sw=2 cindent cino=\:0 et syntax=java
 */
