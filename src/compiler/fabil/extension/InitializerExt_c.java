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
package fabil.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Initializer;
import polyglot.ast.Stmt;
import polyglot.types.Flags;
import fabil.visit.ProxyRewriter;

public class InitializerExt_c extends ClassMemberExt_c {

  @Override
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    Initializer init = node();

    if (init.flags().isStatic()) return super.implMember(pr, parent);
    return Collections.singletonList((ClassMember) init);
  }

  @Override
  public List<Stmt> staticImplInitMember(ProxyRewriter pr) {
    Initializer init = node();
    Flags flags = init.flags();

    if (!flags.isStatic()) return super.staticImplInitMember(pr);

    return Collections.singletonList((Stmt) init.body());
  }

  @Override
  public Initializer node() {
    return (Initializer) super.node();
  }
}
