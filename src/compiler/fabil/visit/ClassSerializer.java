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

import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Ext;
import polyglot.ast.NodeFactory;
import polyglot.main.Version;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import codebases.frontend.ExtensionInfo;
import fabil.extension.ClassDeclExt_c;

public class ClassSerializer extends polyglot.visit.ClassSerializer {

  protected boolean sig_mode;

  public ClassSerializer(TypeSystem ts, NodeFactory nf, long time,
      ErrorQueue eq, Version ver, boolean signatureMode) {
    super(ts, nf, time, eq, ver);
    // Replace TypeEncode with call to factory method.
    this.te = ((ExtensionInfo) ts.extensionInfo()).typeEncoder();
    this.sig_mode = signatureMode;
  }

  @Override
  public List<ClassMember> createSerializationMembers(ClassDecl cd) {
    Ext ext = cd.ext();
    if (sig_mode || ext instanceof ClassDeclExt_c
        && ((ClassDeclExt_c) ext).shouldSerializeType()) {
      return super.createSerializationMembers(cd);
    }
    return Collections.emptyList();
  }

}
