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
package codebases.ast;

import java.net.URI;
import java.util.List;

import polyglot.ast.Id;
import polyglot.ast.Import;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile;
import polyglot.ast.TopLevelDecl;
import polyglot.types.Package;
import polyglot.util.Position;

public interface CodebaseNodeFactory extends NodeFactory {
  SourceFile SourceFile(Position pos, PackageNode packageName,
      List<CodebaseDecl> codebases, List<Import> imports,
      List<TopLevelDecl> decls);

  CodebaseDecl CodebaseDecl(Position pos, Id name);

  CodebaseNode CodebaseNode(Position pos, URI namespace, String name,
      URI externalNS);

  CodebaseNode CodebaseNode(Position pos, URI namespace, String name,
      URI externalNS, Package package_);

}
