package codebases.ast;

import java.net.URI;
import java.util.List;

import polyglot.ast.Id;
import polyglot.ast.Import;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile;
import polyglot.ast.TopLevelDecl;
import polyglot.util.Position;

public interface CodebaseNodeFactory extends NodeFactory {
  CodebaseNode CodebaseNode(Position pos, URI ns);

  SourceFile SourceFile(Position pos, PackageNode packageName,
      List<CodebaseDecl> codebases, List<Import> imports,
      List<TopLevelDecl> decls);

  CodebaseDecl CodebaseDecl(Position pos, Id name);
}
