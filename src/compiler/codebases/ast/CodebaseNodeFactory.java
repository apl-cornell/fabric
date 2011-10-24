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
  CodebaseNode CodebaseNode(Position pos, URI namespace, String name, URI externalNS);
  CodebaseNode CodebaseNode(Position pos, URI namespace, String name,
      URI externalNS, Package package_);

//  CodebaseTypeNode CodebaseTypeNode(Position pos, String alias, URI externalNS, Type type);
}
