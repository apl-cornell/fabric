/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
package fabric.ast;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.Block_c;
import polyglot.ast.Stmt;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import fabric.visit.AbortRetryCollector;

public class Atomic_c extends Block_c implements Atomic {

  public Atomic_c(Position pos, List<Stmt> statements) {
    super(pos, statements);
  }

  @Override
  public List<Stmt> statements() {
    return super.statements();
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    // TODO There needs to be an edge for AbortException

    // Find all the aborts and retries that are lexically enclosed in the
    // atomic blocks, and add appropriate edges.
    List<AbortStmt> aborts = new ArrayList<AbortStmt>();
    List<RetryStmt> retries = new ArrayList<RetryStmt>();

    for (Stmt s : statements()) {
      AbortRetryCollector c = new AbortRetryCollector(aborts, retries);
      s.visit(c);
    }

    for (AbortStmt abort : aborts) {
      v.edge(abort, this, EXIT);
    }
    for (RetryStmt retry : retries) {
      v.edge(retry, this, ENTRY);
    }

    return super.acceptCFG(v, succs);
  }
}
