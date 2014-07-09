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
import polyglot.ast.Stmt;
import fabil.visit.ProxyRewriter;

/**
 * 
 */
public abstract class ClassMemberExt_c extends FabILExt_c implements
    ClassMemberExt {

  @Override
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  @Override
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  @Override
  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  @Override
  public List<ClassMember> staticImplMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  @Override
  public List<Stmt> staticImplInitMember(ProxyRewriter pr) {
    return Collections.emptyList();
  }

  @Override
  public List<ClassMember> staticInterfaceMember(ProxyRewriter pr,
      ClassDecl parent) {
    return Collections.emptyList();
  }

  @Override
  public List<ClassMember> staticProxyMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

}
