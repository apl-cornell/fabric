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
package fabric.extension;

import java.util.List;

import jif.ast.JifUtil;
import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;

public class ClassBodyExt_c extends NodeExt_c {

  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    ClassBodyJifExt_c ext = (ClassBodyJifExt_c) JifUtil.jifExt(n);
    List<ClassMember> remote_wrappers = visitList(ext.remoteWrappers(), v);
    ext.setRemoteWrappers(remote_wrappers);
    return n;
  }
}
