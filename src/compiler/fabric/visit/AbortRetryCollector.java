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
package fabric.visit;

import java.util.List;

import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;
import fabric.ast.AbortStmt;
import fabric.ast.Atomic;
import fabric.ast.RetryStmt;

public class AbortRetryCollector extends NodeVisitor {
  protected List<AbortStmt> aborts;
  protected List<RetryStmt> retries;

  public AbortRetryCollector(List<AbortStmt> aborts, List<RetryStmt> retries) {
    this.aborts = aborts;
    this.retries = retries;
  }

  public List<AbortStmt> getAborts() {
    return aborts;
  }

  public List<RetryStmt> getRetries() {
    return retries;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof AbortStmt) {
      aborts.add((AbortStmt) n);
    } else if (n instanceof RetryStmt) {
      retries.add((RetryStmt) n);
    } else if (n instanceof Atomic) {
      // Only abort/retry the innermost atomic block.
      aborts.clear();
      retries.clear();
    }
    return n;
  }
}
