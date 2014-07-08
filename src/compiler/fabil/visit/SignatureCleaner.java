/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
package fabil.visit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.FieldDecl;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.visit.NodeVisitor;

/**
 * Removes everything except Polyglot type information from classes. Used when
 * compiling signatures.
 */
public class SignatureCleaner extends NodeVisitor {

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof ClassDecl) {
      // Remove extends and implements clauses.
      ClassDecl decl = (ClassDecl) n;
      decl = decl.interfaces(Collections.<TypeNode> emptyList());
      decl = decl.superClass(null);
      return decl;
    }

    if (n instanceof ClassBody) {
      // Remove everything except Polyglot's type info and nested classes.
      ClassBody body = (ClassBody) n;
      List<ClassMember> members = new ArrayList<ClassMember>();
      for (Object o : body.members()) {
        ClassMember member = (ClassMember) o;
        if (member instanceof ClassDecl) {
          members.add(member);
          continue;
        }

        if (member instanceof FieldDecl) {
          FieldDecl field = (FieldDecl) member;
          if (field.name().startsWith("jlc$")) {
            members.add(field);
            continue;
          }
        }
      }

      return body.members(members);
    }

    return n;
  }

}
