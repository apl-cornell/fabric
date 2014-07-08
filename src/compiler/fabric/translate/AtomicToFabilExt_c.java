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
package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.ast.Atomic;
import polyglot.ast.Node;
import jif.translate.BlockToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class AtomicToFabilExt_c extends BlockToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) {
    Atomic b = (Atomic) node();
    return ((FabILNodeFactory) rw.java_nf()).Atomic(b.position(), b.statements());
  }
}
