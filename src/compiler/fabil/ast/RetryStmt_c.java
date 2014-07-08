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
package fabil.ast;

import java.util.*;

import polyglot.ast.Stmt_c;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.PrettyPrinter;

public class RetryStmt_c extends Stmt_c implements RetryStmt {
  public RetryStmt_c(Position pos) {
    super(pos);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    return Collections.EMPTY_LIST;
  }

  public Term firstChild() {
    return null;
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
      w.write("retry;");
  }

  @Override
  public String toString() {
    return "retry";
  }
}
