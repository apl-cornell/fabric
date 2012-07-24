package codebases.ast;

import java.util.List;

import polyglot.ast.Id;
import polyglot.ast.Import;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile;
import polyglot.ast.TopLevelDecl;
import polyglot.types.Package;
import polyglot.util.Position;
import fabric.common.FabricLocation;

public interface CodebaseNodeFactory extends NodeFactory {
  SourceFile SourceFile(Position pos, PackageNode packageName,
      List<CodebaseDecl> codebases, List<Import> imports,
      List<TopLevelDecl> decls);

  CodebaseDecl CodebaseDecl(Position pos, Id name);

  CodebaseNode CodebaseNode(Position pos, FabricLocation namespace,
      String name, FabricLocation externalNS);

  CodebaseNode CodebaseNode(Position pos, FabricLocation namespace,
      String name, FabricLocation externalNS, Package package_);

  // CodebaseTypeNode CodebaseTypeNode(Position pos, String alias,
  // FabricLocation externalNS, Type type);
}
