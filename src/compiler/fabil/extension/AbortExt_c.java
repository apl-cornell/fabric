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
package fabil.extension;

import polyglot.ast.Node;
import fabil.visit.AtomicRewriter;

public class AbortExt_c extends FabILExt_c {
  @Override
  public Node rewriteAtomic(AtomicRewriter ar) {
    return ar.qq().parseStmt("throw new fabric.worker.UserAbortException();");
  }
}
