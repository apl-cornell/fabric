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
package codebases.visit;

import java.net.URI;

import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.Named;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeBuilder;
import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseTypeSystem;
import codebases.types.NamespaceResolver;

public class CBTypeBuilder extends TypeBuilder {
  protected final CodebaseTypeSystem ts;
  protected final URI ns;

  public CBTypeBuilder(Job job, CodebaseTypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
    this.ts = ts;
    this.ns = ((CodebaseSource) job.source()).canonicalNamespace();
  }

  @Override
  public CodebaseTypeSystem typeSystem() {
    return ts;
  }

  // /Copied from supertype to replace calls to system resolver
  @Override
  protected ParsedClassType newClass(Position pos, Flags flags, String name)
      throws SemanticException {
    NamespaceResolver nr = ts.namespaceResolver(ns);
    ParsedClassType ct = ts.createClassType(job().source());

    ct.position(pos);
    ct.flags(flags);
    ct.name(name);

    if (inCode) {
      ct.kind(ClassType.LOCAL);
      ct.outer(currentClass());
      ct.setJob(job());

      if (currentPackage() != null) {
        ct.package_(currentPackage());
      }

      return ct;
    } else if (currentClass() != null) {
      ct.kind(ClassType.MEMBER);
      ct.outer(currentClass());
      ct.setJob(job());

      currentClass().addMemberClass(ct);

      if (currentPackage() != null) {
        ct.package_(currentPackage());
      }

      // if all the containing classes for this class are member
      // classes or top level classes, then add this class to the
      // parsed resolver.
      ClassType container = ct.outer();
      boolean allMembers = (container.isMember() || container.isTopLevel());
      while (container.isMember()) {
        container = container.outer();
        allMembers =
            allMembers && (container.isMember() || container.isTopLevel());
      }

      if (allMembers) {
        nr.add(ct.fullName(), ct);

        // Also save in the cache using the name a class file would use.
        String classFileName = typeSystem().getTransformedClassName(ct);
        nr.add(classFileName, ct);
      }

      return ct;
    } else {
      ct.kind(ClassType.TOP_LEVEL);
      ct.setJob(job());

      if (currentPackage() != null) {
        ct.package_(currentPackage());
      }

      Named dup = nr.check(ct.fullName());

      if (dup != null && dup.fullName().equals(ct.fullName())) {
        throw new SemanticException("Duplicate class \"" + ct.fullName()
            + "\".", pos);
      }

      nr.add(ct.fullName(), ct);

      return ct;
    }
  }
}
