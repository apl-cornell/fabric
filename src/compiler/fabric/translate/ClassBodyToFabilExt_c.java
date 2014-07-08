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
package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import jif.ast.JifUtil;
import jif.translate.ClassBodyToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabric.extension.ClassBodyJifExt_c;

public class ClassBodyToFabilExt_c extends ClassBodyToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    ClassBody cb = (ClassBody) node();
    List<ClassMember> members = new ArrayList<ClassMember>(cb.members());
    ClassBodyJifExt_c cb_ext = (ClassBodyJifExt_c) JifUtil.jifExt(cb);
    List<ClassMember> remote_wrappers = cb_ext.remoteWrappers();
    members.addAll(remote_wrappers);
    return rw.java_nf().ClassBody(cb.position(), members);
  }
}
