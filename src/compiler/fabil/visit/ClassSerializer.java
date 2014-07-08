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
package fabil.visit;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import fabil.extension.ClassDeclExt_c;

import polyglot.ast.ClassDecl;
import polyglot.ast.Ext;
import polyglot.ast.NodeFactory;
import polyglot.main.Version;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;

public class ClassSerializer extends polyglot.visit.ClassSerializer {

  public ClassSerializer(TypeSystem ts, NodeFactory nf, Date date,
      ErrorQueue eq, Version ver) {
    super(ts, nf, date, eq, ver);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.ClassSerializer#createSerializationMembers(polyglot.ast.ClassDecl)
   */
  @SuppressWarnings("rawtypes")
  @Override
  public List createSerializationMembers(ClassDecl cd) {
    Ext ext = cd.ext();
    if (ext instanceof ClassDeclExt_c
        && ((ClassDeclExt_c) ext).shouldSerializeType())
      return super.createSerializationMembers(cd);
    return Collections.emptyList();
  }

}
